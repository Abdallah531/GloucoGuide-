package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@EnableJpaRepositories
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    public UserAccount findByUsername(String username);


}
