package com.doormate.service.impl;

import com.doormate.domain.RepetitionPeriod;
import com.doormate.dto.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
class ReminderServiceTest {

    @Autowired
    private ReminderService reminderService;


    @Test
    void saveReminder() {
        // given
        ReminderDto reminderDto = ReminderDto.builder()
                .title("subtitle test")
                .content("해야할일")
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2023, 5, 3))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(12, 0))
                .repetitionPeriod(RepetitionPeriod.DAILY)
                .repetitionDay("MON TUE WED")
                .build();



        // when
        Long reminderId = reminderService.saveReminder(1L, reminderDto);

        // then
    }

    @Test
    void saveOneReminder() {
        // given
        ReminderDto reminderDto = ReminderDto.builder()
                .title("내생일")
                .content("신난다")
                .startDate(LocalDate.of(2023,6,24))
                .endDate(LocalDate.of(2023,6,25))
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(18, 0))
                .repetitionPeriod(RepetitionPeriod.BASIC)
                .repetitionDay("")
                .build();


        // when
        Long reminderId = reminderService.saveReminder(1L, reminderDto);

        // then
    }
    @Test
    void findAllReminder() {
    }

    @Test
    void updateReminder() {
        // given
        ReminderDto reminderDto = ReminderDto.builder()
                .title("제목")
                .content("해야할일")
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2023, 5, 30))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(12, 0))
                .repetitionPeriod(RepetitionPeriod.WEEKLY)
                .repetitionDay("MON TUE WED")
                .build();



        // when
        Long reminderId = reminderService.saveReminder(1L, reminderDto);
        // then
    }

    @Test
    void deleteReminder() {
    }
}