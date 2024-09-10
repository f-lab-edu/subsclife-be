package com.fthon.subsclife.repository;

import com.fthon.subsclife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
