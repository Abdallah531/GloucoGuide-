package com.example.GlucoGuide.Repository;

import com.example.GlucoGuide.Entity.MedicalRecord;
import com.example.GlucoGuide.Entity.RecordType;
import com.example.GlucoGuide.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;
@EnableJpaRepositories
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {


    List<MedicalRecord> findByUserUserId(Long userId);

    List<MedicalRecord> findByUserUserIdAndRecordTypeTypeId(Long userId, Long recordTypeId);

    List<MedicalRecord> findByUserAndRecordTypeAndDateBetweenOrderByDateAsc(UserAccount user, RecordType recordType, Date startDate, Date endDate);

}
