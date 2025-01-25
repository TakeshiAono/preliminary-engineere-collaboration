package com.api.EngineerCollabo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.api.EngineerCollabo.converters.JsonNodeConverter;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Projects", indexes = { @Index(name = "user_name_index", columnList = "name") })
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "iconUrl", nullable = true, length = 50)
    private String iconUrl;

    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "deadline", nullable = true, columnDefinition = "DATE")
    private Date deadline;

    @Column(name = "meeting_frequency_code")
    private String meetingFrequencyCode;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "use_technology", columnDefinition = "TEXT") // PostgreSQLの場合
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode useTechnology;

    @Column(name = "recruiting_member_job", nullable = true, columnDefinition = "TEXT") // PostgreSQLの場合
    @Convert(converter = JsonNodeConverter.class)
    private JsonNode recruitingMemberJob;

    @Column(name = "recruiting_text", nullable = true, columnDefinition = "TEXT")
    private String recruitingText;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectNotice> projectNotices = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Directory> directories = new ArrayList<>();

    // @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    // private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Milestone> milestones = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "projects_users", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private List<User> users = new ArrayList<>();
}
