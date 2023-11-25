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
@Table(name = "Followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne()    
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private Follower follower;

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