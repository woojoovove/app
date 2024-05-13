package com.example.app.data.repository;

import com.example.app.data.entity.GroupsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupsEntity, Long> {

    Optional<GroupsEntity> findByName(String name);
}
