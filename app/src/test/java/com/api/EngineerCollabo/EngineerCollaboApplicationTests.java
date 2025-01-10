package com.api.EngineerCollabo;

import com.api.EngineerCollabo.services.UserService;
import com.api.EngineerCollabo.repositories.UserRepository;
import com.api.EngineerCollabo.util.JwtUtil;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
@ActiveProfiles("test")
class EngineerCollaboApplicationTests {

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private JwtUtil jwtUtil;

	@Test
	void contextLoads() {
	}

}
