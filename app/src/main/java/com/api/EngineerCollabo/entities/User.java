package com.api.EngineerCollabo;

// import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Data
@Table(name = "Users", indexes = { @Index(name = "project_name_index", columnList = "name") })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column(name = "name", nullable = false, length = 50)
    @Column(name = "name", nullable = true, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "iconUrl", nullable = true, length = 50)
    private String iconUrl;

    @Column(name = "introduce", nullable = true, columnDefinition = "TEXT")
    private String introduce;

    @Column(name = "is_owner", nullable = true, columnDefinition = "boolean default false")
    private Boolean isOwner;
    // TODO: project_idがnullでも保存できてしまう。
    // @ManyToOne()
    // @JoinColumn(name = "project_id", nullable = false, referencedColumnName =
    // "id")
    // private Project project;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserNotice> userNotices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Roll> rolls = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Channel> channels = new ArrayList<>();

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    // private List<Offer> offers = new ArrayList<>();

    // @OneToMany(mappedBy = "scoutedUser", cascade = CascadeType.ALL)
    // private List<Offer> scoutedOffers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "members", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Project> projects = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"))
    private List<User> followers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offers", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "scouted_user_id", referencedColumnName = "id"))
    private List<User> offers = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (getIsOwner() == null) {
            setIsOwner(false);
        }
    }
}