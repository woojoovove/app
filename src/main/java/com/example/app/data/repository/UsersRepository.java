package com.example.app.data.repository;

import com.example.app.data.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// TODO: Port 와 JpaRepository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

}
