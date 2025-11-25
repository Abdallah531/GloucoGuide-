package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.Medication;
import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByDailyListUser(UserAccount user);
}

