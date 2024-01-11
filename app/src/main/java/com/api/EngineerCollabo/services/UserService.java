package com.api.EngineerCollabo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

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
}

// @Service
// public class UserService {
// @Autowired
// private UserRepository userRepository;
//
// @Autowired
// private OfferRepository offerRepository;
//
// public List<List<User>> getScoutedUser(User user) {
// List<Offer> offers = offerRepository.findByUser(user);
// List<List<User>> users = new ArrayList<>();
// offers.forEach(offer -> {
// System.out.println("aaa");
// System.out.println(offer);
// users.add(userRepository.findByScoutedOffers(offer));
// });
// return users;
// }
// }