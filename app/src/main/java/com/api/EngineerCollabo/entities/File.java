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
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "directory_id")
    private Integer directoryId;

    @ManyToOne()
    @JoinColumn(name = "directory_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
    private Directory directory;

    // @ManyToOne()
    // @JoinColumn(name = "project_id", nullable = false, referencedColumnName =
    // "id")
    // private Project project;
}