package com.doormate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.doormate.domain.Message;
import com.doormate.domain.Reminder;
import com.doormate.dto.ReminderDto;
import com.doormate.service.impl.AlarmService;
import com.doormate.service.impl.ReminderService;

import java.util.List;


@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;
    private final AlarmService alarmService;
    private static final String CREATE_REMINDER_MESSAGE = "리마인더 등록 완료";
    private static final String UPDATE_REMINDER_MESSAGE = "알람 수정 완료";
    private static final String DELETE_REMINDER_MESSAGE = "리마인더 삭제 완료";


    @PostMapping
    @ResponseBody
    public Message create(@RequestBody ReminderDto reminderRequestDto) {
        Long savedReminderId = reminderService.saveReminder(reminderRequestDto);
        Message message = alarmService.saveAlarm(savedReminderId);
        return message;
    }


    @PutMapping("/{id}")
    @ResponseBody
    public Message update(@PathVariable Long id, @RequestBody ReminderDto reminderDto) {
        Long savedReminder = reminderService.updateReminder(id, reminderDto);
        alarmService.deleteAlarm(id);
        alarmService.saveAlarm(savedReminder);
        return UPDATE_REMINDER_MESSAGE;
    }

    @DeleteMapping("/{id}")
    public Message delete(@PathVariable(name = "id") Long id) {
        Message message = reminderService.deleteReminder(id);
        return message;
    }

    @GetMapping("/individual")
    @ResponseBody
    public List<Reminder> findDay() {
        return alarmService.findTodayAlarm();
    }

    @GetMapping()
    @ResponseBody
    public List<Reminder> findAll() {
        return reminderService.findAllReminder();
    }
}
