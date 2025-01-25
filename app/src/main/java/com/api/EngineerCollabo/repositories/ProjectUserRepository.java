package com.api.EngineerCollabo.repositories;

import com.api.EngineerCollabo.entities.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Integer> {
    // 必要に応じてカスタムクエリを追加
} 