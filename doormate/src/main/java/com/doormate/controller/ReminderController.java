package com.doormate.controller;

import com.doormate.domain.Reminder;
import com.doormate.dto.ReminderDto;
import com.doormate.service.impl.AlarmService;
import com.doormate.service.impl.ReminderService;
import com.doormate.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/reminder")
@RequiredArgsConstructor
public class ReminderController {
    private final UserServiceImpl userService;
    private final ReminderService reminderService;
    private final AlarmService alarmService;
    private final UserServiceImpl userService;
    private static final String CREATE_REMINDER_MESSAGE = "리마인더 등록 완료";
    private static final String UPDATE_REMINDER_MESSAGE = "알람 수정 완료";
    private static final String DELETE_REMINDER_MESSAGE = "리마인더 삭제 완료";


    @PostMapping("/{id}")
    public String create(@RequestParam("id") Long id, @RequestBody ReminderDto reminderRequestDto) {
        Long savedReminderId = reminderService.saveReminder(id, reminderRequestDto);
        alarmService.saveAlarm(savedReminderId);
        return CREATE_REMINDER_MESSAGE;
    }


    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody ReminderDto reminderDto) {
        Long savedReminder = reminderService.updateReminder(id, reminderDto);
        alarmService.deleteAlarm(id);
        alarmService.saveAlarm(savedReminder);
        return UPDATE_REMINDER_MESSAGE;
    }

    @DeleteMapping()
    public String delete(@RequestParam Long id) {
        reminderService.deleteReminder(id);
        return DELETE_REMINDER_MESSAGE;
    }

    @GetMapping("/today")
    public List<Reminder> findToday() {
        return alarmService.findTodayAlarm();
    }

    @GetMapping()
    public List<Reminder> findAll() {
        return reminderService.findAllReminder();
    }


}
