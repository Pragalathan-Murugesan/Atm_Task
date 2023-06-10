package com.example.one_to_one_mapping.dto;

import com.example.one_to_one_mapping.entity.MiniStatements;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionDto {
    private Long id;
    private Long withDrawAmount;
    private  Long depositAmount;
    private String withdrawAt;
    private String depositAt;
    private String location;
    private String bankName;
    private MiniStatements miniStatements;
    private Long pinNumber;
}
