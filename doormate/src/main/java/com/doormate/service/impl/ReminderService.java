package com.doormate.service.impl;

import com.doormate.domain.Message;
import com.doormate.domain.Reminder;
import com.doormate.dto.ReminderDto;
import com.doormate.repository.AlarmRepository;
import com.doormate.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final AlarmRepository alarmRepository;

    private static final String SAVE_REMINDER_SUCCESS_MESSAGE = "리마인더 등록 완료";
    private static final String UPDATE_SUCCESS_MESSAGE = "리마인더 수정 완료";
    private static final String ERROR_NOT_EXISTS_REMINDER_MESSAGE = "해당 리마인더가 존재하지 않습니다.";
    private static final String DELETE_SUCCESS_MESSAGE = "리마인더 삭제 완료";

    @Transactional
    public Long saveReminder(ReminderDto reminderDto) {
        Reminder reminder = reminderDto.toReminder(reminderDto);
        Reminder savedReminder = reminderRepository.save(reminder);
        return savedReminder.getReminderId();
    }

    @Transactional
    public List<Reminder> findAllReminder() {
        return reminderRepository.findAll();
    }

    @Transactional
    public Long updateReminder(Long id, ReminderDto reminderDto) {
        Reminder reminder = reminderRepository.findById(id).orElse(null);
        // 해당 리마인더 존재 여부 확인
        reminder.setTitle(reminderDto.getTitle());
        reminder.setContent(reminderDto.getContent());
        reminder.setSubTitle(reminderDto.toSubtitle(reminderDto));
        reminder.setStartDate(reminderDto.getStartDate());
        reminder.setEndDate(reminderDto.getEndDate());
        reminder.setStartTime(reminderDto.getStartTime());
        reminder.setEndTime(reminderDto.getEndTime());
        reminder.setRepetitionPeriod(reminderDto.getRepetitionPeriod());
        reminder.setRepetitionDay(reminderDto.getRepetitionDay());

        return reminder.getReminderId();
    }

    @Transactional
    public Message deleteReminder(Long id) {
        // 무결성 위반 방지를 위해 자식 테이블 값 삭제 후, 리마인더 삭제
        alarmRepository.deleteAllByReminderReminderId(id);
        reminderRepository.deleteById(id);
        return new Message(DELETE_SUCCESS_MESSAGE);
    }
}
