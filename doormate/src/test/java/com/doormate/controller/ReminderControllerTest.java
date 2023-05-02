package com.doormate.controller;

import com.doormate.domain.RepetitionPeriod;
import com.doormate.dto.ReminderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
@SpringBootTest
class ReminderControllerTest {

    @Autowired
    private ReminderController reminderController;


    @Test
    void create() {
        //given
        ReminderDto reminderDto = ReminderDto.builder()
                .title("컨트롤러 테스트 일회성 알림 테스트")
                .content("controller 구현")
                .startDate(LocalDate.of(2023, 5, 3))
                .endDate(LocalDate.of(2023, 5, 3))
                .startTime(LocalTime.of(13, 0))
                .endTime(LocalTime.of(18, 0))
                .repetitionPeriod(RepetitionPeriod.BASIC)
                .repetitionDay("")
                .build();

        //when
        String s = reminderController.create(1L, reminderDto);

        //then
        System.out.println(s);
    }
}