package com.api.EngineerCollabo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "project_notices")
public class ProjectNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "log", nullable = false)
    private String log;

    @Column(name = "project_id")
    private Integer projectId;

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;
}