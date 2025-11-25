package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {
    RecordType findByType(String typeName);
}
