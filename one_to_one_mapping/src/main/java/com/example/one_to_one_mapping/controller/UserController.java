package com.example.one_to_one_mapping.controller;

import com.example.one_to_one_mapping.api_Response.ApiResponse;
import com.example.one_to_one_mapping.dto.AdminDto;
import com.example.one_to_one_mapping.dto.TransactionDto;
import com.example.one_to_one_mapping.entity.UserProfile;
import com.example.one_to_one_mapping.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("users/api")
public class UserController {
    @Autowired
    private UserService userService;
    @PutMapping(value = "/send/otp")
    public ApiResponse changePassword(@RequestBody AdminDto adminDto) throws Exception {
        return userService.changePassword(adminDto);
    }
    @PutMapping(value = "/change/pin")
    public ApiResponse changePinNumber(@RequestParam Long pinNumber, Long otpPin) throws Exception {
        return userService.changePinNumber(pinNumber,otpPin);
    }
   @PostMapping(value = "/deposit")
    public ApiResponse depositAmount(@RequestBody AdminDto adminDto) throws Exception {
        return userService.depositAmount(adminDto);
   }
    @PostMapping(value = "/withdraw")
    public ApiResponse withdrawAmount(@RequestBody AdminDto adminDto) throws Exception {
        return userService.withdrawAmount(adminDto);
    }
    @GetMapping(value = "/mini/statements")
    public ApiResponse miniStatements(@RequestParam Long numberOfMonths) throws Exception {
        return userService.miniStatements(numberOfMonths);
    }
}
