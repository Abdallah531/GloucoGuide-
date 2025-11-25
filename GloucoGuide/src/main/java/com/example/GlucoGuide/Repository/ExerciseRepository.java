package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.Exercise;
import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByDailyListUser(UserAccount user);
}
