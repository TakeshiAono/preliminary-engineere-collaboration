package com.api.EngineerCollabo.entities;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "done_at")
    private Date doneAt;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "in_charge_user_id")
    private Integer inChargeUserId;

    @Column(name = "milestone_id")
    private Integer milestoneId;

    @Column(name = "deadline", nullable = true, columnDefinition = "DATE")
    private Date deadline;

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "project_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "in_charge_user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "milestone_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Milestone milestone;
}
