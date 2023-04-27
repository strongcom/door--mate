package com.doormate.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    private String subTitle;

    private String content;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private RepetitionPeriod repetitionPeriod;

    private String repetitionDay;

    @OneToMany(mappedBy = "alarmId")
    private List<Alarm> alarmList = new ArrayList<>();

    /**
     * 연관관계 메서드
     */
    public void setUser(User user) {
        if (this.user != null) {
            this.user.getReminderList().remove(this);
        }
        this.user = user;
        user.getReminderList().add(this);
    }

    @Builder
    public Reminder(User user, String title, String content, String subTitle,
                    LocalTime startTime, LocalTime endTime,
                    LocalDate startDate, LocalDate endDate,
                    RepetitionPeriod repetitionPeriod, String repetitionDay) {
        setUser(user);
        this.title = title;
        this.content = content;
        this.subTitle = subTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repetitionPeriod = repetitionPeriod;
        this.repetitionDay = repetitionDay;
    }

    public Alarm toAlarm(Reminder reminder) {
        return Alarm.builder()
                .reminder(reminder)
                .noticeDate(reminder.getStartDate())
                .startTime(reminder.getStartTime())
                .endTime(reminder.getEndTime())
                .build();
    }
}
