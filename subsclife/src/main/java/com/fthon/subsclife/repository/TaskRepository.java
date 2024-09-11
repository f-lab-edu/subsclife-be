package com.fthon.subsclife.repository;

import com.fthon.subsclife.entity.Task;
import com.fthon.subsclife.repository.query.QueryTaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, QueryTaskRepository {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.subscribes WHERE t.id = :taskId")
    Optional<Task> findByIdWithSubscribes(@Param("taskId") Long taskId);

    @Query("SELECT t FROM Task t" +
            " LEFT JOIN FETCH t.subscribes s" +
            " JOIN FETCH s.user u" +
            " WHERE t.id = :taskId")
    Optional<Task> findByIdWithSubscribesAndUser(@Param("taskId") Long taskId);

    // 회고 상세 페이지 용
    @Query("SELECT t FROM Task t" +
            " LEFT JOIN FETCH t.reminds r" +
            " JOIN FETCH r.user u" +
            " WHERE t.id = :taskId")
    Optional<Task> findByIdWithRemindsAndUsers(@Param("taskId") Long taskId);
}
