package com.api.EngineerCollabo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import com.api.EngineerCollabo.repositories.ChannelMemberRepository;
import com.api.EngineerCollabo.repositories.ChannelRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.ChannelService;
import com.api.EngineerCollabo.util.ChannelUtil;
import com.api.EngineerCollabo.entities.ResponseChannel;
import com.api.EngineerCollabo.entities.Channel;
import com.api.EngineerCollabo.entities.ChannelMember;
import com.api.EngineerCollabo.entities.DeleteChannelRequest;
import com.api.EngineerCollabo.entities.RequestCreateChannel;

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
    public ResponseChannel createChannel(
        @RequestBody RequestCreateChannel requestChannel
    ) {
        String name = requestChannel.getName();
        Integer ownerId = requestChannel.getOwnerId();
        Integer projectId = requestChannel.getProjectId();
        List<Integer> userIds = requestChannel.getUserIds();

        if(ownerId != null){
            Channel channel = channelService.createChannel(name, ownerId, projectId);
            userIds.stream().forEach(userId -> {
                ChannelMember channelMember = new ChannelMember();
                channelMember.setUserId(userId);
                channelMember.setChannelId(channel.getId());
                channelMemberRepository.save(channelMember);
            });
            return channelService.changeResponseChannel(channel);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'ownerId' parameter is required");
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
    public void deleteChannel(
        @PathVariable("id") Integer channelId,
        @RequestBody DeleteChannelRequest request
    ) {
        Integer ownerId = request.getOwnerId();
        if (channelId == null || ownerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'id' and 'ownerId' are required");
        }
        Channel channel = channelRepository.findById(channelId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found"));
        if (!channelUtil.isOwner(channel, ownerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the channel");
        }
        channelRepository.deleteById(channelId);
    }
}
