package com.example.one_to_one_mapping.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@JsonIgnoreProperties({"hibernateLazyInitializer"})

@Table(name = "userProfile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "pinNumber")
    private Long pinNumber;
    @Column(name = "createAt")
    private Long createAt;
    @Column(name = "updateAt")
    private Float updateAt;
    @Column(name = "balance")
    private Long balance;
    @Column(name = "phoneNumber")
    private Long phoneNumber;
    @Column(name = "otpPin")
    private Long otpPin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transactionId",referencedColumnName = "id")
    private MiniStatements miniStatements;
}
