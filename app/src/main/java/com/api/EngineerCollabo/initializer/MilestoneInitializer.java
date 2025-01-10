package com.api.EngineerCollabo.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class MilestoneInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            // マイルストーンのテストデータを追加
            jdbcTemplate.update(
                "INSERT INTO milestones (project_id, name, deadline, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                1, "Phase 1", new Date(), new Date(), new Date()
            );
            
            jdbcTemplate.update(
                "INSERT INTO milestones (project_id, name, deadline, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                1, "Phase 2", new Date(), new Date(), new Date()
            );
        } catch (Exception e) {
            System.out.println("Error during milestone initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 
