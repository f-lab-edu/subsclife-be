package com.fthon.subsclife.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "task")
    private List<Subscribe> subscribes = new ArrayList<>();

    @OneToMany(mappedBy = "task")
    private List<Remind> reminds = new ArrayList<>();

    @Builder
    public Task(String title, String simpleInfo, String detail, Period period) {
        this.title = title;
        this.simpleInfo = simpleInfo;
        this.detail = detail;
        this.period = period;
    }

    public long getSubscriberCount() {
        return subscribes.size();
    }

    public boolean isSubscribed(Long userId) {
        return subscribes.stream()
                .anyMatch(s -> s.getUser().getId().equals(userId));
    }

}
