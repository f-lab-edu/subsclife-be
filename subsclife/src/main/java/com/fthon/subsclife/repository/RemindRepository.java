package com.fthon.subsclife.repository;


import com.fthon.subsclife.entity.Remind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindRepository extends JpaRepository<Remind, Long> {
}
