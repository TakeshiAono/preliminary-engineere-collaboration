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

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message", nullable = true, columnDefinition = "TEXT")
    private String message;

    @ManyToOne()
    @JoinColumn(name = "scouted_user_id", referencedColumnName = "id")
    private User scoutedUser;

    @ManyToOne()
    @JoinColumn(name = "scout_user_id", referencedColumnName = "id")
    private User scoutUser;

    // // 空のコンストラクタ
    // public User() {}

    // Getter、Setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}