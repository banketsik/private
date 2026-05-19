package org.user_service.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.user_service.exception.ExceptionHandlers;
import org.user_service.mapper.UserMapper;

@WebMvcTest(UserController.class)
@Import({UserMapper.class, ExceptionHandlers.class})
public class UserControllerTest {
}
