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

import java.util.Optional;

import com.api.EngineerCollabo.repositories.ChannelMemberRepository;
import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ChannelService;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.util.ChannelUtil;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    ChannelMemberRepository channelMemberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelService channelService;

    @Autowired
    ChannelUtil channelUtil;

    @PostMapping("/create")
    public void createChannel(@RequestBody Channel requestChannel) {
        String name = requestChannel.getName();
        Integer ownerId = requestChannel.getOwnerId();

        if(ownerId != null){
            channelService.createChannel(name, ownerId);
            // TODO: チャンネルメンバーレコードに登録する機能を追加する
            // ChannelMember channelMember = ;
            // channelMemberRepository.save(channelMember);
        }
    }

    @GetMapping("/{id}")
    public ResponseChannel responseChannel(@PathVariable("id") Optional<Integer> ID, @RequestParam("userId") Integer userId) {
        if (ID.isPresent()) {
            int id = ID.get();
            Channel channel = channelRepository.findById(id);

            // ユーザーがメンバーかどうか確認
            if (channelUtil.isMember(channel, userId)) {
                return channelService.changeResponseChannel(channel);
            }
        }
        return null; // メンバーでない場合は null を返す
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
}
