package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.ResponseUserNotice;
import com.api.EngineerCollabo.entities.UserNotice;
import com.api.EngineerCollabo.repositories.UserNoticeRepository;
import com.api.EngineerCollabo.services.UserNoticeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserNoticeControllerTest {

    @Mock
    private UserNoticeRepository userNoticeRepository;

    @Mock
    private UserNoticeService userNoticeService;

    @InjectMocks
    private UserNoticeController userNoticeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserNotice() {
        // モックの設定
        UserNotice requestUserNotice = new UserNotice();
        requestUserNotice.setLog("Test Log");
        requestUserNotice.setUserId(1);

        // 実行
        userNoticeController.createUserNotice(requestUserNotice);

        // 検証
        verify(userNoticeService, times(1)).createUserNotice("Test Log", 1);
    }

    @Test
    void testResponseUserNotice() {
        // モックの設定
        int userNoticeId = 1;
        UserNotice userNotice = new UserNotice();
        userNotice.setId(userNoticeId);
        userNotice.setLog("Test Log");

        when(userNoticeRepository.findById(userNoticeId)).thenReturn(userNotice);
        when(userNoticeService.changResponseUserNotice(userNotice)).thenReturn(new ResponseUserNotice());

        // 実行
        ResponseUserNotice responseUserNotice = userNoticeController.responseUserNotice(Optional.of(userNoticeId));

        // 検証
        assertEquals(new ResponseUserNotice(), responseUserNotice);
    }

    @Test
    void testPutUserNotice() {
        // モックの設定
        int userNoticeId = 1;
        UserNotice userNotice = new UserNotice();
        userNotice.setId(userNoticeId);
        userNotice.setLog("Test Log");

        UserNotice requestUserNotice = new UserNotice();
        requestUserNotice.setLog("Updated Log");

        when(userNoticeRepository.findById(userNoticeId)).thenReturn(userNotice);

        // 実行
        userNoticeController.putUserNotice(Optional.of(userNoticeId), requestUserNotice);

        // 検証
        assertEquals("Updated Log", userNotice.getLog());
        verify(userNoticeRepository, times(1)).save(userNotice);
    }

    @Test
    void testDeleteUserNotice() {
        // モックの設定
        int userNoticeId = 1;

        // 実行
        userNoticeController.deleteUserNotice(Optional.of(userNoticeId));

        // 検証
        verify(userNoticeRepository, times(1)).deleteById(userNoticeId);
    }
}
