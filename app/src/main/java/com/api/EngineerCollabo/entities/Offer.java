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
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message", nullable = true, columnDefinition = "TEXT")
    private String message;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "scouted_user_id")
    private Integer scoutedUserId;

    @ManyToOne()
    @JoinColumn(name = "scouted_user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private User scoutedUser;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}