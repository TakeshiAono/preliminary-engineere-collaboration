package com.api.EngineerCollabo.services;

import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.EngineerCollabo.entities.RequestLogin;
import com.api.EngineerCollabo.entities.RequestUserRegist;
import com.api.EngineerCollabo.entities.ResponseLogin;
import com.api.EngineerCollabo.entities.ResponseUserRegist;
import com.api.EngineerCollabo.entities.ResponseUser;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.util.PasswordUtil;

import jakarta.transaction.Transactional;
// 開発中は認証機能をOFFにしている。
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional

public class UserService {
    // 開発中は認証機能をOFFにしている。
    // public class UserService implements UserDetailsService {

    // リポジトリクラスの依存性注入
    @Autowired
    UserRepository userRepository;

    /**
     * ユーザ登録する情報のDBインサート処理
     * 
     * @param RequestUserRegist ユーザ登録APIのリクエストボディ
     * @return responseUserRegist ユーザ登録APIのレスポンスボディ
     */
    public ResponseUserRegist insertUser(RequestUserRegist requestUserRegist) {
        User user = new User();
        user = CreateUser(requestUserRegist);
        userRepository.save(user);
        ResponseUserRegist responseUserRegist = new ResponseUserRegist();
        responseUserRegist.setId(user.getId());
        responseUserRegist.setName(user.getName());
        responseUserRegist.setEmail(user.getEmail());
        // responseUserRegist.setPassword(user.getPassword());
        return responseUserRegist;
    };

    /**
     * ユーザ登録するユーザ情報の作成処理
     * 
     * @param RequestUserRegist ユーザ登録APIのリクエストボディ
     * @return user ユーザ情報
     */
    private User CreateUser(RequestUserRegist requestUserRegist) {
        String hashPw;
        User user = new User();
        hashPw = PasswordUtil.hashSHA256(requestUserRegist.getPassword());
        // user.setName(requestUserRegist.getName());
        user.setPassword(hashPw);
        user.setEmail(requestUserRegist.getEmail());

        return user;
    };

    /**
     * ログインするユーザ情報の作成処理
     * 
     * @param RequestLogin ログインAPIのリクエストボディ
     * @return responseLogin ログインAPIのレスポンスボディ
     */
    public ResponseLogin login(RequestLogin requestLogin) {
        User loginUser = new User();
        loginUser = CreateUser(requestLogin);
        List<User> userList = new ArrayList<User>();
        userList = userRepository.findByEmail(loginUser.getEmail());

        ResponseLogin responseLogin = new ResponseLogin();
        if (userList.size() == 0) {
            responseLogin.setStatus("error");
        } else if (!(loginUser.getPassword().equals(userList.get(0).getPassword()))) {
            responseLogin.setStatus("error");
        } else {
            responseLogin.setStatus("success");
            responseLogin.setId(userList.get(0).getId());
            responseLogin.setName(userList.get(0).getName());
            responseLogin.setEmail(userList.get(0).getEmail());
            // responseLogin.setPassword(userList.get(0).getPassword());
        }
        return responseLogin;
    };

    /**
     * ログインするユーザ情報の作成処理
     * 
     * @param RequestLogin ログインAPIのリクエストボディ
     * @return user ユーザ情報
     */
    private User CreateUser(RequestLogin requestLogin) {
        String hashPw;
        User user = new User();
        hashPw = PasswordUtil.hashSHA256(requestLogin.getPassword());
        user.setPassword(hashPw);
        user.setEmail(requestLogin.getEmail());

        return user;
    };

    // 開発中は認証機能をOFFにしている。
    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // // データベースからユーザー情報を取得
    // User user = userRepository.findByName(username);
    // // データベースから取得したユーザー情報がnullの場合、例外をスロー
    // if (user == null) {
    // throw new UsernameNotFoundException("User not found");
    // }

    // // UserDetailsオブジェクトに変換して返す
    // return org.springframework.security.core.userdetails.User
    // .withUsername(user.getName())
    // .password(user.getPassword())
    // .roles("USER") // ユーザーのロールを指定
    // .build();
    // }

    public void updateUser(Integer id, User user) {
        if (userRepository.findById(id).get() != null) {
            user.setId(id);
            userRepository.save(user);
        }
    }

    public ResponseUser changeResponseUser(User user) {
        ResponseUser responseUser = new ResponseUser();
        responseUser.setId(user.getId());
        responseUser.setName(user.getName());
        responseUser.setEmail(user.getEmail());
        responseUser.setPassword(user.getPassword());
        responseUser.setIconUrl(user.getIconUrl());
        responseUser.setIntroduce(user.getIntroduce());
        responseUser.setFollowerIds(
            user.getFollowers().stream().map(follower -> follower.getUser().getId()).collect(Collectors.toList()));
        responseUser.setSkillIds(
            user.getSkills().stream().map(skill -> skill.getId()).collect(Collectors.toList()));
        responseUser.setRollIds(
            user.getRolls().stream().map(roll -> roll.getId()).collect(Collectors.toList()));
        responseUser.setChannelIds(
            user.getChannels().stream().map(channel -> channel.getId()).collect(Collectors.toList()));
        responseUser.setMessageIds(
            user.getMessages().stream().map(message -> message.getId()).collect(Collectors.toList()));
        responseUser.setOfferIds(
            user.getOffers().stream().map(offer -> offer.getId()).collect(Collectors.toList()));
        responseUser.setUserNoticeIds(
            user.getUserNotices().stream().map(userNotice -> userNotice.getId()).collect(Collectors.toList()));

        return responseUser;
    }


}