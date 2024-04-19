package com.api.EngineerCollabo.entities;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

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


    public Message(String text, String content) {
        this.text = text;
        this.content = content;
    }
}