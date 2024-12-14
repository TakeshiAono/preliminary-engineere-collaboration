package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ChannelService;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.entities.Channel;

@RestController
@RequestMapping("/channels")
@CrossOrigin(origins = "http://localhost:5173")
public class ChannelController {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelService channelService;

    @PostMapping("/create")
    public void createChannel(@RequestBody Channel requestChannel) {
        String name = requestChannel.getName();
        Integer userId = requestChannel.getUserId();
        Integer chatRoomId = requestChannel.getChatRoomId();

        if(userId != null){
            channelService.createChannel(name, userId, chatRoomId);
        }
    }

    @GetMapping("/{id}")
    public ResponseChannel responseChannel(@PathVariable("id") Optional<Integer> ID, @RequestParam("userId") Integer userId) {
        if (ID.isPresent()) {
            int id = ID.get();
            Channel channel = channelRepository.findById(id);

            // ユーザーがメンバーかどうか確認
            if (isMember(channel, userId)) {
                return channelService.changeResponseChannel(channel);
            }
        }
        return null; // メンバーでない場合は null を返す
    }

    @GetMapping
    public List<ResponseChannel> responseChannels(@RequestParam("ids") Optional<List<Integer>> ids, @RequestParam("userId") Integer userId) {
        if (ids.isPresent()) {
            List<Channel> channels = channelRepository.findAllById(ids.get());

            // 自分がメンバーであるチャンネルだけを返す
            return channels.stream()
                    .filter(channel -> isMember(channel, userId))
                    .map(channelService::changeResponseChannel)
                    .collect(Collectors.toList());
        }
        return List.of(); // 空のリストを返す
    }

    @PatchMapping("/{id}")
    public void putChanel(@PathVariable("id") Optional<Integer> ID, @RequestBody Channel requestChannel) {
        if (ID.isPresent()) {
            int id = ID.get();
            Channel channel = channelRepository.findById(id);

            String name = requestChannel.getName();
            if (name != null) {
                channel.setName(name);
            }
            channelRepository.save(channel);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteChannel(@PathVariable("id") Optional<Integer> ID) {
        if (ID.isPresent()) {
            int id = ID.get();
            channelRepository.deleteById(id);
        }
    }

    /**
     * チャンネルに自分がメンバーとして含まれているかを判定する
     * 
     * @param channel チェック対象のチャンネル
     * @param userId ユーザーID
     * @return true: メンバーである, false: メンバーでない
     */
    private boolean isMember(Channel channel, Integer userId) {
        // チャンネルまたはメンバーリストがnullの場合はfalseを返す
        if (channel == null || channel.getChannelMembers() == null) {
            return false;
        }

        // チャンネルのメンバーリストを走査し、指定されたユーザーIDが含まれているかを確認
        return channel.getChannelMembers().stream()
                .anyMatch(channelMember -> channelMember.getUser() != null && channelMember.getUser().getId().equals(userId));
    }
}
