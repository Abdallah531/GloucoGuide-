package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.DailyList;
import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface DailyListRepository extends JpaRepository<DailyList, Long> {
    Optional<DailyList> findByDate(LocalDate date);

    List<DailyList> findByUser(UserAccount user);
}