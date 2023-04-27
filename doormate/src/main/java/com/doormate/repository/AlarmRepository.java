package com.doormate.repository;

import com.doormate.domain.Alarm;
import com.doormate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Modifying
    @Query("delete from Alarm alarm where alarm.reminder.reminderId = :id")
    void deleteAllByReminderReminderId(@Param("id") Long id);

    @Query("select alarm from Alarm alarm where alarm.noticeDate = :noticeDate")
    List<Alarm> findAllByNoticeDate(LocalDate noticeDate);

    List<Alarm> findAllByUser(User user);
}
