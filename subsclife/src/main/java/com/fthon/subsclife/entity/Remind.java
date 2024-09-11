package com.fthon.subsclife.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "REMIND")
public class Remind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    private Task task;

    @Column(name = "ACHIEVEMENT_RATE")
    private Integer achievementRate;

    @Column(name = "ACHIEVE_REASON")
    private String achieveReason;

    @Column(name = "FAIL_REASON")
    private String failReason;

    @Column(name = "IMPROVEMENT_PLAN")
    private String improvementPlan;



    @Builder
    public Remind(User user, Task task, Integer achievementRate, String achieveReason, String failReason, String improvementPlan) {
        this.user = user;
        this.task = task;
        this.achievementRate = achievementRate;
        this.achieveReason = achieveReason;
        this.failReason = failReason;
        this.improvementPlan = improvementPlan;
    }

}
