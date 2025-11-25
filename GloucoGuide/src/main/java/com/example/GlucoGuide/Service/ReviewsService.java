//package com.example.GlucoGuide.Service;
//
//
//import com.example.GlucoGuide.Entity.Reviews;
//import com.example.GlucoGuide.Repository.ReviewsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ReviewsService {
//
//    @Autowired
//    private ReviewsRepository reviewsRepository;
//
//    public List<Reviews> getAllRatings() {
//        return reviewsRepository.findAll();
//    }
//
//    public Reviews getRatingById(Long id) {
//        return reviewsRepository.findById(id).orElse(null);
//    }
//
//    public void createRating(Reviews reviews) {
//        reviewsRepository.save(reviews);
//    }
//
//    public Reviews updateRating(Long id, Reviews updatedReviews) {
//        updatedReviews.setId(id);
//        return reviewsRepository.save(updatedReviews);
//
//    }
//
//    public void deleteRatingById(Long id) {
//        reviewsRepository.deleteById(id);
//    }
//}

// ReviewsService.java
package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.Converter.ReviewsConverter;
import com.example.GlucoGuide.DTO.ReviewsDTO;
import com.example.GlucoGuide.Entity.Admin;
import com.example.GlucoGuide.Entity.Reviews;
import com.example.GlucoGuide.Entity.UserAccount;
import com.example.GlucoGuide.Repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ReviewsConverter reviewsConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    public List<ReviewsDTO> getAllRatings() {
        return reviewsRepository.findAll()
                .stream()
                .map(reviewsConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public ReviewsDTO getRatingById(Long id) {
        Reviews reviews = reviewsRepository.findById(id).orElse(null);
        assert reviews != null;
        if (reviews.getUser() != null) {
            UserAccount user = userService.getUserById(reviews.getUser().getUserId());
            reviews.setUser(user);
        }
        if (reviews.getAdmin() != null) {
            Admin admin = adminService.getAdminById(reviews.getAdmin().getId());
            reviews.setAdmin(admin);
        }
        return reviewsConverter.entityToDto(reviews);
    }

    public ReviewsDTO createRating(ReviewsDTO reviewsDTO) {
        Reviews reviews = reviewsConverter.dtoToEntity(reviewsDTO);
        if (reviewsDTO.getUserId() != null) {
            UserAccount user = userService.getUserById(reviewsDTO.getUserId());
            reviews.setUser(user);
        }
        if (reviewsDTO.getAdminId() != null) {
            Admin admin = adminService.getAdminById(reviewsDTO.getAdminId());
            reviews.setAdmin(admin);
        }
        reviewsRepository.save(reviews);
        return reviewsConverter.entityToDto(reviews);
    }

    public ReviewsDTO updateRating(ReviewsDTO reviewsDTO) {
        Reviews reviews = reviewsConverter.dtoToEntity(reviewsDTO);
        if (reviewsDTO.getUserId() != null) {
            UserAccount user = userService.getUserById(reviewsDTO.getUserId());
            reviews.setUser(user);
        }
        if (reviewsDTO.getAdminId() != null) {
            Admin admin = adminService.getAdminById(reviewsDTO.getAdminId());
            reviews.setAdmin(admin);
        }
        reviewsRepository.save(reviews);
        return reviewsConverter.entityToDto(reviews);
    }

    public void deleteRatingById(Long id) {
        reviewsRepository.deleteById(id);
    }
}

