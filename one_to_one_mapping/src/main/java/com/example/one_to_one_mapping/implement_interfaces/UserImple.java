package com.example.one_to_one_mapping.implement_interfaces;

import com.example.one_to_one_mapping.api_Response.ApiResponse;
import com.example.one_to_one_mapping.dto.AdminDto;
import com.example.one_to_one_mapping.dto.TransactionDto;
import com.example.one_to_one_mapping.entity.UserProfile;

public interface UserImple {
    ApiResponse addUser(AdminDto adminDto) throws Exception;

    ApiResponse login(AdminDto adminDto) throws Exception;

    ApiResponse changePassword(AdminDto adminDto) throws Exception;

    ApiResponse changePinNumber(Long otpPin,Long pinNumber) throws Exception;

    ApiResponse depositAmount(AdminDto adminDto) throws Exception;

    ApiResponse withdrawAmount(AdminDto adminDto) throws Exception;

    ApiResponse miniStatements(Long numberOfMonths) throws Exception;

//    ApiResponse updateField(UserDto userDto) throws Exception;
//
//    ApiResponse deleteByUserId(UserDto userDto);
}
