package com.api.EngineerCollabo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Users", indexes = { @Index(name = "project_name_index", columnList = "name") })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column(name = "name", nullable = false, length = 50)
    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "iconUrl", nullable = true, length = 50)
    private String iconUrl;

    @Column(name = "introduce", nullable = true, columnDefinition = "TEXT")
    private String introduce;

    @Column(name = "is_owner", nullable = true, columnDefinition = "boolean default false")
    private Boolean isOwner;

    // @Column(name = "user_id")
    // private Integer userId;

    // TODO: project_idがnullでも保存できてしまう。
    // @ManyToOne()
    // @JoinColumn(name = "project_id", nullable = false, referencedColumnName =
    // "id")
    // private Project project;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<UserNotice> userNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Channel> channels = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Offer> offers = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Project> ownerProjects = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "scoutedUser", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Offer> scoutedOffers = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Follower> followers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "projects_users", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    @ToString.Exclude // 双方向リレーションを無視して無限ループを防ぐ
    private List<Project> projects = new ArrayList<>();

    // デフォルトコンストラクタ
    public User() {}

    // JSONデシリアライズ用のコンストラクタ
    public User(Integer id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        if (getIsOwner() == null) {
            setIsOwner(false);
        }
    }
}
