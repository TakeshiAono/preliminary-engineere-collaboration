package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectNoticeRepository extends JpaRepository<ProjectNotice, Integer> {
    // カスタムのクエリメソッドが不要な場合は、このインターフェースだけで十分です
}