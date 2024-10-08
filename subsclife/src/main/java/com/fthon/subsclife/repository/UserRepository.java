package com.fthon.subsclife.repository;

import com.fthon.subsclife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscribes WHERE u.id = :userId")
    Optional<User> findByIdWithSubscribes(@Param("userId") Long userId);

    @Query("SELECT u FROM User u" +
            " LEFT JOIN FETCH u.subscribes s" +
            " JOIN FETCH s.task" +
            " WHERE u.id = :userId")
    Optional<User> findByIdWithSubscribesAndTask(@Param("userId") Long userId);

    /**
     * 특정 태스크에 회고를 작성한 사용자 목록 반환
     */
    @Query("SELECT u FROM User u" +
            " JOIN u.reminds r" +
            " WHERE r.task.id = :taskId")
    List<User> findUsersWhoRemindedByTaskId(@Param("taskId") Long taskId);
}
