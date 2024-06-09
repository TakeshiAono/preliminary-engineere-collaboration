package com.api.EngineerCollabo.initializer;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class TaskInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public TaskInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static Date getRandomDate(LocalDate start, LocalDate end) {
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        LocalDate randomLocalDate = start.plusDays(randomDays);
        return Date.valueOf(randomLocalDate);
    }

    @Override
    public void run(String... args) {
        try {
            for (int i = 1; i <= 100; i++) {
                LocalDate startDate = LocalDate.of(2022, 1, 1); // 開始日
                LocalDate endDate = LocalDate.of(2024, 1, 1); // 終了日
                Date randomDate = getRandomDate(startDate, endDate);
                jdbcTemplate.update(
                        "INSERT INTO tasks (in_charge_user_id, done_at, project_id, deadline, milestone_id, name, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        2, null, 1, randomDate, 1, "バックエンド実装_", "バックエンドの実装をJavaで行う", randomDate, randomDate);
            }
            for (int i = 1; i <= 100; i++) {
                LocalDate startDate = LocalDate.of(2022, 1, 1); // 開始日
                LocalDate endDate = LocalDate.of(2024, 1, 1); // 終了日
                Date randomDate = getRandomDate(startDate, endDate);
                jdbcTemplate.update(
                        "INSERT INTO tasks (in_charge_user_id, done_at, project_id, deadline, milestone_id, name, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        1, randomDate, 1, randomDate, 2, "バックエンド実装_", "バックエンドの実装をJavaで行う", randomDate, randomDate);
            }
        } catch (Exception e) {
            System.out.println("Error during database operation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
