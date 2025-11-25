package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.Entity.Article;
import com.example.GlucoGuide.Repository.ArticleRepository;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ArticleSearchService {

    private final ArticleRepository articleRepository;
    private final Logger logger = LoggerFactory.getLogger(ArticleSearchService.class);
    private final Directory index = new RAMDirectory();

    private final Lock indexLock = new ReentrantLock();

    public ArticleSearchService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        buildIndex();
    }

    public List<Article> searchArticles(String query) {
        try {
            indexLock.lock();  // Ensure only one thread can build the index at a time
            buildIndex();      // Rebuild the index before searching
        } finally {
            indexLock.unlock();
        }

        try {

            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);

            // Parse the query using Lucene's query parser
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            Query luceneQuery = parser.parse(query);

            // Search Lucene index
            ScoreDoc[] hits = searcher.search(luceneQuery, reader.maxDoc()).scoreDocs;

            // Collect search results
            List<Article> searchResults = new ArrayList<>();
            for (ScoreDoc hit : hits) {
                int docId = hit.doc;
                Document doc = searcher.doc(docId);
                long articleId = Long.parseLong(doc.get("articleId"));
                String content = doc.get("content");

                // Fetch the Article entity from the database using articleId
                Optional<Article> optionalArticle = articleRepository.findById(articleId);
                optionalArticle.ifPresent(article -> {
                    // Add the fetched Article entity to searchResults
                    searchResults.add(article);
                });
            }

            return searchResults;
        } catch (Exception e) {
            logger.error("Error occurred during article search: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private void buildIndex() {
        try (IndexWriter writer = new IndexWriter(index, new IndexWriterConfig(new StandardAnalyzer()))) {
            writer.deleteAll();
            List<Article> articles = articleRepository.findAll();

            logger.debug("Total articles fetched: {}", articles.size());

            for (Article article : articles) {
                Document doc = new Document();
                doc.add(new StringField("articleId", String.valueOf(article.getArticleId()), Field.Store.YES));
                doc.add(new TextField("content", article.getContent(), Field.Store.YES));
                writer.addDocument(doc);

                logger.debug("Document added to index: {}", doc);
            }
        } catch (IOException e) {
            logger.error("Error occurred during index building: {}", e.getMessage());
        }
    }
    private long lastIndexUpdate = 0; // Stores timestamp of the last indexing


}

