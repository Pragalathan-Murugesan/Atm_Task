package com.example.one_to_one_mapping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
//@JsonIgnoreProperties({"hibernateLazyInitializer"})

@Getter
@Table(name = "miniStatement")

public class MiniStatements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "withDrawAmount")
    private Long withDrawAmount;
    @Column(name = "depositAmount")
    private Long depositAmount;
    @Column(name = "withdrawAt")
    private Long withdrawAt;
    @Column(name = "depositAt")
    private Long depositAt;
    @Column(name = "location")
    private String location;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Long date;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserProfile userProfile = new UserProfile();
}