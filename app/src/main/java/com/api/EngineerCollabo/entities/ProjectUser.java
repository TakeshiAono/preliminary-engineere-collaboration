package com.api.EngineerCollabo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "projects_users")
public class ProjectUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "users_id")
    private Integer userId;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "users_id")
    private User user;

    // 他のフィールドが必要な場合はここに追加
}