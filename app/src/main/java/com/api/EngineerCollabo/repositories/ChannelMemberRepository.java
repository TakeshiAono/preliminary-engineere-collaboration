package com.api.EngineerCollabo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.EngineerCollabo.entities.ChannelMember;

import java.util.List;
import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Integer> {

    // 特定のユーザーがメンバーであるチャンネルを取得
    List<ChannelMember> findByUserId(Integer userId);

    // 特定のユーザーが特定のチャンネルに所属しているか確認
    Optional<ChannelMember> findByChannelIdAndUserId(Integer channelId, Integer userId);

    // 特定のチャンネルのメンバー数を取得
    long countByChannelId(Integer channelId);
}
