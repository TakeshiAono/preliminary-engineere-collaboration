package com.api.EngineerCollabo.controllers;

import com.api.EngineerCollabo.entities.Project;
import com.api.EngineerCollabo.entities.User;
import com.api.EngineerCollabo.repositories.ProjectRepository;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoutingControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private RoutingController routingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndexGet() {
        // モックの設定
        Project mockProject = new Project();
        when(projectRepository.findById(1)).thenReturn(mockProject);

        // 実行
        String viewName = routingController.indexGet(model);

        // 検証
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("indexc", viewName);
    }
}
