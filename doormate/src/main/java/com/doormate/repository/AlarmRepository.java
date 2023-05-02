package com.doormate.repository;

import com.doormate.domain.Alarm;
import com.doormate.domain.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    void deleteAllByReminder(Reminder reminder);

    @Query("select alarm from Alarm alarm where alarm.noticeDate=:today")
    List<Alarm> findAllToday(LocalDate today);

}
