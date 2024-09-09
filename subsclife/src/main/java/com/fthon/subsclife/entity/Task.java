package com.fthon.subsclife.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "SIMPLE_INFO")
    private String simpleInfo;

    @Column(name = "DETAIL")
    private String detail;

    @Embedded
    private Period period;

    @Builder
    public Task(String title, String simpleInfo, String detail, Period period) {
        this.title = title;
        this.simpleInfo = simpleInfo;
        this.detail = detail;
        this.period = period;
    }

}
