package com.api.EngineerCollabo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    // カスタムのクエリメソッドが不要な場合は、このインターフェースだけで十分です
}