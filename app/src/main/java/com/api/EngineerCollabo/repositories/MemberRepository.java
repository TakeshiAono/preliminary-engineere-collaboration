package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.Member;
import com.api.EngineerCollabo.entities.Message;
import com.api.EngineerCollabo.entities.Offer;
import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.Roll;
import com.api.EngineerCollabo.entities.Skill;
import com.api.EngineerCollabo.entities.User;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findById(int id);

    List<Member> findByUserId(int id);

    List<Member> findByProjectId(int id);
}