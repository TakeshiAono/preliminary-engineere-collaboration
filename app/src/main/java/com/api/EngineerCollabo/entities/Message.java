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
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "channel_id")
    private Integer channelId;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    // @ManyToOne()
    // @JoinColumn(name = "chat_room_id", nullable = false, referencedColumnName =
    // "id")
    // private ChatRoom chatRoom;

    @ManyToOne()
    @JoinColumn(name = "channel_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Channel channel;

}