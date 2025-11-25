package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.Meal;
import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByDailyListUser(UserAccount user);
}