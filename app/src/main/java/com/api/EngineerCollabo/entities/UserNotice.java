package com.api.EngineerCollabo.entities;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_notices")
public class UserNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "log", nullable = false)
    private String log;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "offer_id", nullable = true)
    private Integer offerId;

    @Column(name = "application_id", nullable = true)
    private Integer applicationId;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
    
    @OneToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Offer offer;

    @OneToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // 応募削除時に関連通知も削除(不要？)
    private Application application;
}
