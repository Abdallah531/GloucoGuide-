package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(String Category);
}
