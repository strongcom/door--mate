package com.doormate.controller;

import com.doormate.dto.AlarmDto;
import com.doormate.dto.RequestDTO;
import com.doormate.service.impl.AlarmService;
import com.doormate.service.impl.FirebaseCloudMessageService;
import com.doormate.service.impl.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FCMController {


    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final ReminderService reminderService;
    private final AlarmService alarmService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody AlarmDto alarmDto) throws IOException {
        List<Long> reminderList = firebaseCloudMessageService.showAlarm(alarmDto);
        for (Long reminderId : reminderList
        ) {
            RequestDTO requestDTO = firebaseCloudMessageService.reminderToFcmMessage(reminderId);
            firebaseCloudMessageService.sendMessageTo(
                    requestDTO.getTargetToken(),
                    requestDTO.getTitle(),
                    requestDTO.getBody());
        }
        return ResponseEntity.ok().build();
    }
}
