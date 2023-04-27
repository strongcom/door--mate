package com.doormate.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "reminder_id")
    private Reminder reminder;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDate noticeDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    /**
     * 연관관계 메서드
     */
    // Alarm 내용이 생기면서 리마인더와 연관관계가 발생하여 외래키인 리마인더 id의 AlarmList로 연관관계 매핑
    // 즉, 현재 저장되는 모든 알림내역들은 부모 테이블인 리마인더 객체를 외래키로 저장해줌.
    public void setReminder(Reminder reminder) {
        if (this.reminder != null) {  // 현재 알람 객체의 리마인더 값이 null이 아니라면(이미 한번 등록된 이력이 있는 알림)
            this.reminder.getAlarmList().remove(this);  // 해당 리마인더 외래키에서 제외시킴
        }
        this.reminder = reminder;  // 현재 받은 리마인더 객체를 외래키로 저장
        reminder.getAlarmList().add(this);  // 리마인더 테이블에도 알림에 대한 정보를 저장
    }
    @Builder
    public Alarm(Reminder reminder, LocalDate noticeDate, LocalTime startTime, LocalTime endTime) {
        setReminder(reminder);
        this.noticeDate = noticeDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
