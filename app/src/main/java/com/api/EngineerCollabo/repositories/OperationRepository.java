package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.Operation;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

    Operation findById(int id);

    List<Operation> findByProject(Project project);
}