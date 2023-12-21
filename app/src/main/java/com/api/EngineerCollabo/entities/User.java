package com.api.EngineerCollabo;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.PrePersist;

import lombok.Data;

@Entity
@Data
@Table(name = "Users", indexes = {@Index(name = "project_name_index", columnList = "name")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "iconUrl", nullable = true, length = 50)
    private String iconUrl;

    @Column(name = "introduce", nullable = true, columnDefinition = "TEXT")
    private String introduce;

    @Column(name = "is_owner", nullable = false, columnDefinition = "boolean default false")
    private Boolean isOwner;
    // TODO: project_idがnullでも保存できてしまう。
    @ManyToOne()
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();

    @OneToMany(mappedBy = "scoutedUser", cascade = CascadeType.ALL)
    private List<Offer> scoutedOffers = new ArrayList<>();

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getIconUrl() {
//        return iconUrl;
//    }
//
//    public void setIconUrl(String iconUrl) {
//        this.iconUrl = iconUrl;
//    }
//
//    public String getIntroduce() {
//        return iconUrl;
//    }
//
//    public void setIntroduce(String iconUrl) {
//        this.introduce = iconUrl;
//    }
//
//    public Boolean getIsOwner() {
//        return isOwner;
//    }
//
//    public void setIsOwner(Boolean isOwner) {
//        this.isOwner = isOwner;
//    }
//
//    public Project getProject() {
//        return project;
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//    }

    @PrePersist
    public void prePersist()
    {
        if ( getIsOwner() == null )
        {
            setIsOwner( false );
        }
    }
}