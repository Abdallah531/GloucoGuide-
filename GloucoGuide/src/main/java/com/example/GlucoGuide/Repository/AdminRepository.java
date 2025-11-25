package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface AdminRepository  extends JpaRepository<Admin, Long> {
    public Admin findByUsername(String username);
}
