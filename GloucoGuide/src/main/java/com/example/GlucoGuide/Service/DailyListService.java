package com.example.GlucoGuide.Service;

import com.example.GlucoGuide.Converter.UserConverter;
import com.example.GlucoGuide.Entity.*;
import com.example.GlucoGuide.Error.ResourceNotFoundException;
import com.example.GlucoGuide.Repository.DailyListRepository;
import com.example.GlucoGuide.Repository.MealRepository;
import com.example.GlucoGuide.Repository.MedicationRepository;
import com.example.GlucoGuide.Repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.GlucoGuide.DTO.ExerciseRequest;
import com.example.GlucoGuide.DTO.MealRequest;
import com.example.GlucoGuide.DTO.MedicationRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DailyListService {

    @Autowired
    private DailyListRepository dailyListRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserService userService;

    public DailyList getDailyList(LocalDate date) {
        Optional<DailyList> dailyListOpt = dailyListRepository.findByDate(date);
        return dailyListOpt.orElse(null);
    }

    public DailyList saveDailyList(DailyList dailyList) {
        return dailyListRepository.save(dailyList);
    }

    public DailyList addMeal(LocalDate date, MealRequest mealRequest, UserAccount userAccount) {
        DailyList dailyList = getOrCreateDailyList(date, userAccount);

        Meal meal = new Meal();
        meal.setName(mealRequest.getName());
        meal.setTime(mealRequest.getTime());
        meal.setDailyList(dailyList);

        dailyList.getMeals().add(meal);

        if (mealRequest.getTime().isAfter(LocalTime.now())) {
            sendNotificationForMeal(mealRequest, userAccount);
        }
        dailyList.setUser(userAccount);

        return saveDailyList(dailyList);
    }



    public DailyList addMedication(LocalDate date, MedicationRequest medicationRequest, UserAccount userAccount) {
        DailyList dailyList = getOrCreateDailyList(date,userAccount);
        dailyList.setUser(userAccount);

        Medication medication = new Medication();
        medication.setName(medicationRequest.getName());
        medication.setDosage(medicationRequest.getDosage());
        medication.setTime(medicationRequest.getTime());
        medication.setDailyList(dailyList);

        dailyList.getMedications().add(medication);

        if (medicationRequest.getTime().isAfter(LocalTime.now())) {
            sendNotificationForMedication(medicationRequest, userAccount);
        }
        return saveDailyList(dailyList);
    }

    public DailyList addExercise(LocalDate date, ExerciseRequest exerciseRequest, UserAccount userAccount) {
        DailyList dailyList = getOrCreateDailyList(date,userAccount);

        Exercise exercise = new Exercise();
        exercise.setName(exerciseRequest.getName());
        exercise.setTime(exerciseRequest.getTime());
        exercise.setDurationMinutes(exerciseRequest.getDurationMinutes());
        exercise.setDailyList(dailyList);

        if (exerciseRequest.getTime().isAfter(LocalTime.now())) {
            sendNotificationForExercise(exerciseRequest, userAccount);
            sendNotificationForExerciseEnd(exerciseRequest, userAccount);
        }

        dailyList.getExercises().add(exercise);
        dailyList.setUser(userAccount);
        return saveDailyList(dailyList);
    }

    @Transactional
    public DailyList updateMeal(Long mealId, MealRequest mealRequest) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found with id: " + mealId));
        DailyList dailyList = meal.getDailyList();

        meal.setName(mealRequest.getName());
        meal.setTime(mealRequest.getTime());
        saveDailyList(dailyList);

        if (mealRequest.getTime().isAfter(LocalTime.now())) {
            sendNotificationForMeal(meal, dailyList.getUser());
        }

        return dailyList;
    }

    @Transactional
    public DailyList updateMedication(Long medicationId, MedicationRequest medicationRequest) {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + medicationId));
        DailyList dailyList = medication.getDailyList();

        medication.setName(medicationRequest.getName());
        medication.setDosage(medicationRequest.getDosage());
        medication.setTime(medicationRequest.getTime());
        saveDailyList(dailyList);

        if (medicationRequest.getTime().isAfter(LocalTime.now())) {
            sendNotificationForMedication(medication, dailyList.getUser());
        }

        return dailyList;
    }

    @Transactional
    public DailyList updateExercise(Long exerciseId, ExerciseRequest exerciseRequest) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + exerciseId));
        DailyList dailyList = exercise.getDailyList();

        exercise.setName(exerciseRequest.getName());
        exercise.setTime(exerciseRequest.getTime());
        exercise.setDurationMinutes(exerciseRequest.getDurationMinutes());
        saveDailyList(dailyList);

        if (exerciseRequest.getTime().isAfter(LocalTime.now())) {
            sendNotificationForExercise(exercise, dailyList.getUser());
            sendNotificationForExerciseEnd(exercise, dailyList.getUser());
        }

        return dailyList;
    }

    private void sendNotificationForMeal(MealRequest mealRequest, UserAccount userAccount) {
        String message = "Reminder: You have a meal planned at " + mealRequest.getTime().toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForMeal(Meal meal, UserAccount userAccount) {
        String message = "Reminder: You have a meal planned at " + meal.getTime().toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForMedication(MedicationRequest medicationRequest, UserAccount userAccount) {
        String message = "Reminder: You have medication scheduled at " + medicationRequest.getTime().toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForMedication(Medication medication, UserAccount userAccount) {
        String message = "Reminder: You have medication scheduled at " + medication.getTime().toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForExercise(ExerciseRequest exerciseRequest, UserAccount userAccount) {
        String message = "Reminder: You have an exercise session planned at " + exerciseRequest.getTime().toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForExercise(Exercise exercise, UserAccount userAccount) {
        String message = "Reminder: You have an exercise session planned at " + exercise.getTime().toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForExerciseEnd(ExerciseRequest exerciseRequest, UserAccount userAccount) {
        LocalTime endTime = exerciseRequest.getTime().plusMinutes(exerciseRequest.getDurationMinutes());
        String message = "Reminder: Your exercise session ends at " + endTime.toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private void sendNotificationForExerciseEnd(Exercise exercise, UserAccount userAccount) {
        LocalTime endTime = exercise.getTime().plusMinutes(exercise.getDurationMinutes());
        String message = "Reminder: Your exercise session ends at " + endTime.toString();
        notificationService.createNotification(userConverter.convertToDTO(userAccount), message);
    }

    private DailyList getOrCreateDailyList(LocalDate date, UserAccount userAccount) {
        Optional<DailyList> dailyListOpt = dailyListRepository.findByDate(date);
        return dailyListOpt.orElseGet(() -> {
            DailyList newDailyList = new DailyList();
            newDailyList.setDate(date);
            newDailyList.setUser(userAccount);
            return saveDailyList(newDailyList);
        });
    }

    public List<DailyList> getdailyList(Long userId) {
        return  dailyListRepository.findByUser(userService.getUserById(userId));
    }
    public List<Meal> getAllMealsForUser(Long userId) {
        UserAccount user = userService.getUserById(userId);
        return mealRepository.findByDailyListUser(user);
    }

    public List<Medication> getAllMedicationsForUser(Long userId) {
        UserAccount user = userService.getUserById(userId);
        return medicationRepository.findByDailyListUser(user);
    }

    public List<Exercise> getAllExercisesForUser(Long userId) {
        UserAccount user = userService.getUserById(userId);
        return exerciseRepository.findByDailyListUser(user);
    }
}
