package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
