package com.fthon.subsclife.entity;

import com.fthon.subsclife.exception.TaskSubscriptionClosedException;
import com.fthon.subsclife.exception.UserSubscriptionOverflowException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.fthon.subsclife.common.Constants.*;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME")
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Subscribe> subscribes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Remind> reminds = new ArrayList<>();

    @Builder
    public User(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }


    public long getSubscribeCount() {
        // 이미 만료된 것들은 개수에서 제외해야 함
        return subscribes.stream().filter(subscribe -> {
                        // 태스크의 마감 기한이 now()를 지났을 경우를 제외한 개수
                        return subscribe.getTask()
                                .getPeriod()
                                .getEndDate()
                                .isAfter(LocalDateTime.now());
                }).count();
    }

    public Subscribe subscribe(Task task) {
        validateSubscribe(task);

        return Subscribe.builder()
                .user(this)
                .task(task)
                .build();
    }

    private void validateSubscribe(Task task) {
        // 이미 구독했다면 안됨
        findSubscribedTask(task.getId()).ifPresent(s -> {
            throw new UserSubscriptionOverflowException("이미 구독한 태스크입니다.");
        });

        // 태스크가 이미 시작되었다면 구독할 수 없음
        if (task.getPeriod().getStartDate().isBefore(LocalDateTime.now())) {
            throw new TaskSubscriptionClosedException("이미 시작된 태스크는 구독할 수 없습니다.");
        }

        // 태스크 구독자 수가 구독자 제한보다 많으면 안됨
        if (task.getSubscriberCount() >= TASK_SUBSCRIBER_LIMIT) {
            throw new TaskSubscriptionClosedException("구독 마감된 태스크입니다.");
        }

        // 사용자 구독 수가 구독 제한보다 많으면 안됨
        if (getSubscribeCount() >= USER_SUBSCRIBE_LIMIT) {
            throw new UserSubscriptionOverflowException("구독 개수가 너무 많습니다.");
        }
    }

    public void removeSubscribe(Subscribe subscribe) {
        subscribes.remove(subscribe);
        subscribe.setUser(null);
    }

    public Subscribe unsubscribe(Long taskId) {
        Optional<Subscribe> subscribe = findSubscribedTask(taskId);

        subscribe.ifPresent(this::removeSubscribe);

        return subscribe.orElseThrow(() -> new NoSuchElementException("구독이 존재하지 않습니다."));
    }

    private Optional<Subscribe> findSubscribedTask(Long taskId) {
        return subscribes
                .stream()
                .filter(s -> s.getTask().getId().equals(taskId)).findFirst();
    }
}
