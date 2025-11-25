package com.example.GlucoGuide.Controller;

import com.example.GlucoGuide.Converter.UserConverter;
import com.example.GlucoGuide.DTO.*;
import com.example.GlucoGuide.Entity.*;
import com.example.GlucoGuide.Error.ResourceNotFoundException;
import com.example.GlucoGuide.Service.DailyListService;
import com.example.GlucoGuide.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dailylist")
public class DailyListController {

    @Autowired
    private DailyListService dailyListService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping
    public ResponseEntity<?> getDailyList(HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        List<DailyList> dailyList = dailyListService.getdailyList(userId);
        return new ResponseEntity<>(dailyList, HttpStatus.OK);
    }

    @PostMapping("/addMeal")
    public ResponseEntity<?> addMeal(HttpSession session, @RequestBody MealRequest mealRequest, @RequestParam LocalDate date) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        return  ResponseEntity.ok(dailyListService.addMeal(date, mealRequest, userConverter.convertToEntity(userDTO)));
    }

    @PostMapping("/addMedication")
    public ResponseEntity<?> addMedication(HttpSession session, @RequestBody MedicationRequest medicationRequest, @RequestParam LocalDate date) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(dailyListService.addMedication(date, medicationRequest, userConverter.convertToEntity(userDTO)));
    }

    @PostMapping("/addExercise")
    public ResponseEntity<?> addExercise(HttpSession session, @RequestBody ExerciseRequest exerciseRequest, @RequestParam LocalDate date) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(dailyListService.addExercise(date, exerciseRequest, userConverter.convertToEntity(userDTO)));
    }

    @PutMapping("/updateMeal/{mealId}")
    public DailyList updateMeal(@PathVariable Long mealId, @RequestBody MealRequest mealRequest) {
        return dailyListService.updateMeal(mealId, mealRequest);
    }

    @PutMapping("/updateMedication/{medicationId}")
    public DailyList updateMedication(@PathVariable Long medicationId, @RequestBody MedicationRequest medicationRequest) {
        return dailyListService.updateMedication(medicationId, medicationRequest);
    }

    @PutMapping("/updateExercise/{exerciseId}")
    public DailyList updateExercise(@PathVariable Long exerciseId, @RequestBody ExerciseRequest exerciseRequest) {
        return dailyListService.updateExercise(exerciseId, exerciseRequest);
    }

    @GetMapping("/meals")
    public ResponseEntity<?> getAllMeals(HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        List<Meal> meals = dailyListService.getAllMealsForUser(userId);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/medications")
    public ResponseEntity<?> getAllMedications(HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        List<Medication> medications = dailyListService.getAllMedicationsForUser(userId);
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @GetMapping("/exercises")
    public ResponseEntity<?> getAllExercises(HttpSession session) {
        UserAccountDTO userDTO = (UserAccountDTO) session.getAttribute("authenticatedUser");
        if (userDTO == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        Long userId = userDTO.getUserId();
        List<Exercise> exercises = dailyListService.getAllExercisesForUser(userId);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }
}
