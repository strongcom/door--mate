package com.doormate.service.impl;

import com.doormate.domain.Alarm;
import com.doormate.domain.Reminder;
import com.doormate.domain.RepetitionPeriod;
import com.doormate.exception.NotFoundException;
import com.doormate.repository.AlarmRepository;
import com.doormate.repository.ReminderRepository;
import com.doormate.repository.UserRepository;
import com.doormate.util.RepetitionDateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final UserRepository userRepository;
    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;
    private final RepetitionDateHandler repetitionDateHandler;

    private static final String NOT_FIND_USER_MESSAGE = "해당 회원의 알람 리스트가 존재하지 않습니다.";
    private static final String NOT_FIND_REMINDER_MESSAGE = "해당 리마인더가 존재하지 않습니다.";
    private static final String SUCCESS_SAVED_ALARM_MESSAGE = "반복 설정 등록 완료";

    // === Alarm 테이블에 저장 === //
    @Transactional
    public String saveAlarm(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FIND_REMINDER_MESSAGE));

        RepetitionPeriod repetitionPeriod = reminder.getRepetitionPeriod();

        if (repetitionPeriod == RepetitionPeriod.DAILY) {
            repetitionDateHandler.saveDailyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.WEEKLY) {
            repetitionDateHandler.saveWeeklyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.MONTHLY) {
            repetitionDateHandler.saveMonthlyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.YEARLY) {
            repetitionDateHandler.saveYearlyAlarm(id);
        } else {
            repetitionDateHandler.saveOneAlarm(id);
        }
        return SUCCESS_SAVED_ALARM_MESSAGE;
    }

    @Transactional
    public void deleteAlarm(Long id) {
        reminderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FIND_REMINDER_MESSAGE));
        alarmRepository.deleteAllByReminderReminderId(id);
    }

    @Transactional
    public List<Reminder> findTodayAlarm() {
        List<Alarm> todayAlarmList = alarmRepository.findAllByNoticeDate(LocalDate.now());
        List<Reminder> reminders = new ArrayList<>();
        for (Alarm alarm : todayAlarmList) {
            Reminder reminder = alarm.getReminder();
            reminders.add(reminder);
        }
        return reminders;
    }
}
