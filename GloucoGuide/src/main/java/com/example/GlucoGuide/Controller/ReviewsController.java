package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.DTO.AdminDTO;
import com.example.GlucoGuide.DTO.ReviewsDTO;
import com.example.GlucoGuide.DTO.UserAccountDTO;
import com.example.GlucoGuide.Service.ReviewsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping("/")
    public ResponseEntity<List<ReviewsDTO>> getAllRatings() {
        List<ReviewsDTO> reviews = reviewsService.getAllRatings();
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRating(@RequestParam("value") int value,
                                               @RequestParam("feedback") String feedback,
                                               HttpSession session) {

        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setValue(value);
        reviewsDTO.setFeedback(feedback);
        reviewsDTO.setDate(new Date());
        reviewsDTO.setUserId(userId);
        reviewsDTO.setAdminId(null);

        ReviewsDTO createdReview = reviewsService.createRating(reviewsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> adminCreateRating(@RequestParam("value") int value,
                                                    @RequestParam("feedback") String feedback,
                                                    HttpSession session) {

        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        ReviewsDTO reviewsDTO = new ReviewsDTO();
        reviewsDTO.setValue(value);
        reviewsDTO.setFeedback(feedback);
        reviewsDTO.setDate(new Date());
        reviewsDTO.setAdminId(authenticatedAdmin.getId());
        reviewsDTO.setUserId(null);

        ReviewsDTO createdReview = reviewsService.createRating(reviewsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<String> adminDeleteRatingById(@PathVariable("id") Long id, HttpSession session) {

        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        reviewsService.deleteRatingById(id);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> adminUpdateRating(@PathVariable("id") Long id,
                                                    @RequestParam("value") int value,
                                                    @RequestParam("feedback") String feedback,
                                                    HttpSession session) {

        AdminDTO authenticatedAdmin = (AdminDTO) session.getAttribute("authenticatedAdmin");
        if (authenticatedAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }


        ReviewsDTO reviewsDTO = reviewsService.getRatingById(id);
        if (reviewsDTO.getUserId()!= null) {
            return new ResponseEntity<>("You cant Update User Reviews", HttpStatus.FORBIDDEN);
        }
        reviewsDTO.setValue(value);
        reviewsDTO.setFeedback(feedback);
        reviewsDTO.setDate(new Date());


        ReviewsDTO createdReview = reviewsService.updateRating(reviewsDTO);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> updateRating(@PathVariable("id") Long id,
                                               @RequestParam("value") int value,
                                               @RequestParam("feedback") String feedback,
                                               HttpSession session) {

        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        ReviewsDTO reviewsDTO = reviewsService.getRatingById(id);
        if (reviewsDTO.getAdminId() != null) {
            return new ResponseEntity<>("You cant Update Admin Reviews", HttpStatus.FORBIDDEN);
        }
        if (!Objects.equals(userId, reviewsDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cant Update Other Users Reviews");
        }
        reviewsDTO.setUserId(userId);
        reviewsDTO.setValue(value);
        reviewsDTO.setFeedback(feedback);
        reviewsDTO.setDate(new Date());

        ReviewsDTO createdReview = reviewsService.updateRating(reviewsDTO);
        return ResponseEntity.ok(createdReview);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteRatingById(@PathVariable("id") Long id, HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        ReviewsDTO existingReviews = reviewsService.getRatingById(id);
        if (existingReviews != null && existingReviews.getUserId().equals(userId)) {
            reviewsService.deleteRatingById(id);
            return ResponseEntity.ok("Review deleted successfully");
        } else {
            String error = "You can't access this review.";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
    }
}
