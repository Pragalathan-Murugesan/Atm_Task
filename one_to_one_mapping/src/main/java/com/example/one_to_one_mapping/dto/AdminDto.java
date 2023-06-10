package com.example.one_to_one_mapping.dto;

import com.example.one_to_one_mapping.entity.MiniStatements;
import com.example.one_to_one_mapping.entity.UserProfile;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminDto {
    private Long id;
    private String name;
    private String emailID;
    private String password;
    private String city;
    private UserProfile userProfile;
    private Long userId;
    private  String role;
    private Long loginAt;
    private Float createAt;
    private Long phoneNumber;
    private Long withDrawAmount;
    private  Long depositAmount;
    private Long withdrawAt;
    private Long depositAt;
    private String location;
    private String bankName;
    private Long pinNumber;
    private Long numberOfMonths;
    private MiniStatements miniStatements;
    private Long date;
}
