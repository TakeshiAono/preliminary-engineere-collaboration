package com.api.EngineerCollabo.util;

import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.ChannelMember;
import org.springframework.stereotype.Component;

@Component
public class ChannelUtil {

    /**
     * チャンネルのオーナーであるかを判定する
     * 
     * @param channel チェック対象のチャンネル
     * @param ownerId オーナーID
     * @return true: オーナーである, false: オーナーでない
     */
    public boolean isOwner(Channel channel, Integer ownerId) {
        // チャンネルがnullの場合はfalseを返す
        if (channel == null) {
            return false;
        }

        // チャンネルのオーナーIDと指定されたオーナーIDが一致するかを確認
        return channel.getOwnerId().equals(ownerId);
    }

    /**
     * チーザーがチャンネルのメンバーであるかを判定する
     * 
     * @param channel チェック対象のチャンネル
     * @param userId ユーザーID
     * @return true: メンバーである, false: メンバーでない
     */
    public boolean isMember(Channel channel, Integer userId) {
        if (channel == null) {
            return false;
        }

        return channel.getChannelMembers().stream()
            .map(ChannelMember::getUserId)
            .anyMatch(memberId -> memberId.equals(userId));
    }
}