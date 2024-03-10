package com.api.EngineerCollabo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "chat_room_id")
    private Integer chatRoomId;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "chat_room_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
}