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


    //TODO 외래키 관계 유무 (USER_ID, TASK_ID)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TASK_ID")
    private Long taskId;

    @Column(name = "ACHIEVEMENT_RATE")
    private int achievementRate;

    @Column(name = "ACHIEVE_REASON")
    private String achieveReason;

    @Column(name = "FAIL_REASON")
    private String failReason;

    @Column(name = "IMPROVEMENT_PLAN")
    private String improvementPlan;

    @Builder
    public Remind(Long userId, Long taskId, int achievementRate, String achieveReason, String failReason, String improvementPlan) {
        this.userId = userId;
        this.taskId = taskId;
        this.achievementRate = achievementRate;
        this.achieveReason = achieveReason;
        this.failReason = failReason;
        this.improvementPlan = improvementPlan;
    }

}
