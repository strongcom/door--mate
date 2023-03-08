package com.doormate.service.impl;

import com.doormate.domain.Alarm;
import com.doormate.domain.Message;
import com.doormate.domain.Reminder;
import com.doormate.domain.RepetitionPeriod;
import com.doormate.repository.AlarmRepository;
import com.doormate.repository.ReminderRepository;
import com.doormate.util.RepetitionDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;
    private final RepetitionDate repetitionDate;

    // === Alarm 테이블에 저장 === //
    @Transactional
    public Message saveAlarm(Long id) {
        Reminder reminder = reminderRepository.findById(id).orElse(null);
        RepetitionPeriod repetitionPeriod = reminder.getRepetitionPeriod();
        Message message;

        if (repetitionPeriod == RepetitionPeriod.DAILY) {
            message = repetitionDate.saveDailyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.WEEKLY) {
            message = repetitionDate.saveWeeklyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.MONTHLY) {
            message = repetitionDate.saveMonthlyAlarm(id);
        } else if (repetitionPeriod == RepetitionPeriod.YEARLY) {
            message = repetitionDate.saveYearlyAlarm(id);
        } else {
            message = repetitionDate.saveOneAlarm(id);
        }
        return message;
    }

    @Transactional
    public void deleteAlarm(Long id) {
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
