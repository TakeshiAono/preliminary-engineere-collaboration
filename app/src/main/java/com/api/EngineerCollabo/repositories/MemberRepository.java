package com.api.EngineerCollabo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findById(int id);

    List<Member> findByUserId(int id);

    List<Member> findByProjectId(int id);
}