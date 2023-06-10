package com.example.one_to_one_mapping.controller;

import com.example.one_to_one_mapping.api_Response.ApiResponse;
import com.example.one_to_one_mapping.dto.AdminDto;
import com.example.one_to_one_mapping.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ApiResponse addUser(@RequestBody AdminDto adminDto) throws Exception {
        logger.info("This is Logger Information");
        return userService.addUser(adminDto);
    }
    @PostMapping(value = "/login")
    public ApiResponse login(@RequestBody AdminDto adminDto) throws Exception {
        return userService.login(adminDto);
    }
}
