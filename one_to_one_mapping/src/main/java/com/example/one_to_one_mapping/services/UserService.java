package com.example.one_to_one_mapping.services;

import com.example.one_to_one_mapping.api_Response.ApiResponse;
import com.example.one_to_one_mapping.dto.AdminDto;
import com.example.one_to_one_mapping.entity.Admin;
import com.example.one_to_one_mapping.entity.MiniStatements;
import com.example.one_to_one_mapping.entity.UserProfile;
import com.example.one_to_one_mapping.implement_interfaces.UserImple;
import com.example.one_to_one_mapping.jwttokens.GenerateTokens;
import com.example.one_to_one_mapping.repository.AdminRepo;
import com.example.one_to_one_mapping.repository.MiniStatementRepo;
import com.example.one_to_one_mapping.repository.UserProfileRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
public class UserService implements UserImple {
    @Autowired
    private UserProfileRepo userProfileRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private GenerateTokens generateToken;
    @Autowired
    private ApiResponse apiResponse;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
     private MiniStatementRepo miniStatementRepo;
    String encryptPwd;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public ApiResponse addUser(AdminDto adminDto) throws Exception {
        try {
            Admin admin  = new Admin();
            admin.setName(adminDto.getName());
            admin.setEmailID(adminDto.getEmailID());
            encryptPwd = bCryptPasswordEncoder.encode(adminDto.getPassword());
            admin.setPassword(encryptPwd);
            admin.setRole(adminDto.getRole());
            admin.setUserProfile(adminDto.getUserProfile());
            userProfileRepo.save(admin.getUserProfile());
            adminRepo.save(admin);
            apiResponse.setStatus(HttpStatus.CREATED.value());
            apiResponse.setMessage("User Added Successfully");
            apiResponse.setData(admin);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return apiResponse;
    }

    @Override
    public ApiResponse login(AdminDto adminDto) throws Exception {
        try {
            Admin admin = adminRepo.loginApi(adminDto.getEmailID(), adminDto.getRole());
            if (admin == null&&admin.getPassword()!=(adminDto.getPassword())) {
                apiResponse.setMessage("Login Failed");
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return apiResponse;
            }
            if (bCryptPasswordEncoder.matches(adminDto.getPassword(), admin.getPassword())) {
                Long epochTime = Instant.now().getEpochSecond();
                admin.setLoginAt(epochTime);
                adminRepo.save(admin);
                String token = generateToken.generateTokens(adminDto);
                HashMap<String, Object> data = new HashMap<>();
                data.put("token", token);
                apiResponse.setStatus(HttpStatus.ACCEPTED.value());
                apiResponse.setData(data);
                apiResponse.setMessage("Login Successfully");
                return apiResponse;
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return apiResponse;
    }
    @Transactional
    public ApiResponse changePassword(AdminDto adminDto) throws Exception {
        try {
            String changePinQuery = "SELECT e FROM UserProfile e WHERE e.phoneNumber = :phoneNumber";
            TypedQuery<UserProfile> query = entityManager.createQuery(changePinQuery, UserProfile.class);
            query.setParameter("phoneNumber",adminDto.getPhoneNumber());
            UserProfile userProfile = query.getSingleResult();
            if (userProfile==null&&userProfile.getPhoneNumber()!=adminDto.getPhoneNumber()) {
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setData(null);
                apiResponse.setMessage("UnAuthorized Access This is Not You");
            }
                Random random = new Random();
                String num = String.valueOf(random.nextLong(1100000000));
                Long otpPin = Long.valueOf(num);
                userProfile.setOtpPin(otpPin);
                Long epochTime = Instant.now().getEpochSecond();
                userProfile.setUpdateAt(epochTime);
                userProfileRepo.save(userProfile);
                apiResponse.setStatus(HttpStatus.CREATED.value());
                apiResponse.setData(otpPin);
                apiResponse.setMessage("The Opt Is:");

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return apiResponse;
    }

    @Transactional
    public ApiResponse changePinNumber(Long pinNumber,Long otpPin) throws Exception {
        try {
            String changePinQuery = "SELECT e FROM UserProfile e WHERE e.otpPin = :otpPin";
            TypedQuery<UserProfile> query = entityManager.createQuery(changePinQuery, UserProfile.class);
            query.setParameter("otpPin",otpPin);
            UserProfile userProfile = query.getSingleResult();
            if (otpPin!=userProfile.getPhoneNumber()) {
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setData(null);
                apiResponse.setMessage("OtpPin Is Not Valid");
            }
            Long epochTime = Instant.now().getEpochSecond();
            userProfile.setUpdateAt(epochTime);
            userProfile.setPinNumber(pinNumber);
            userProfileRepo.save(userProfile);
            apiResponse.setStatus(HttpStatus.CREATED.value());
            apiResponse.setData(null);
            apiResponse.setMessage("4 Digits Secret PinNumber Changed Successfully ");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return apiResponse;
    }

    @Transactional
    public ApiResponse depositAmount(AdminDto adminDto) throws Exception {
           try {
               String changePinQuery = "SELECT e FROM UserProfile e WHERE e.pinNumber = :pinNumber" ;
               TypedQuery<UserProfile> query = entityManager.createQuery(changePinQuery, UserProfile.class);
               query.setParameter("pinNumber",adminDto.getPinNumber());
               query.getSingleResult();
               UserProfile userProfile = query.getSingleResult();
               userProfile.setMiniStatements(adminDto.getMiniStatements());
               Long updateAt = Instant.now().getEpochSecond();
               userProfile.getMiniStatements().setDepositAt(updateAt);
               LocalDate currentDate = LocalDate.now();
               userProfile.getMiniStatements().setDate(currentDate);

               Long balance = userProfile.getBalance();
               Long amount = adminDto.getMiniStatements().getDepositAmount();

               Long balance1 = balance + amount;
               userProfile.setBalance(balance1);
               userProfileRepo.save(userProfile);
               miniStatementRepo.save(userProfile.getMiniStatements());
               apiResponse.setMessage("Deposited Successfully");
               apiResponse.setData(null);
               apiResponse.setStatus(HttpStatus.OK.value());
           }catch (Exception e){
               throw new Exception(e.getMessage());
           }
        return apiResponse;
    }

    @Transactional
    public ApiResponse withdrawAmount(AdminDto adminDto) throws Exception {
        try {
            String changePinQuery = "SELECT e FROM UserProfile e WHERE e.pinNumber = :pinNumber" ;
            TypedQuery<UserProfile> query = entityManager.createQuery(changePinQuery, UserProfile.class);
            query.setParameter("pinNumber",adminDto.getPinNumber());
            query.getSingleResult();
            UserProfile userProfile = query.getSingleResult();
            userProfile.setMiniStatements(adminDto.getMiniStatements());
            Long updateAt = Instant.now().getEpochSecond();
            userProfile.getMiniStatements().setWithdrawAt(updateAt);
            LocalDate currentDate = LocalDate.now();
            userProfile.getMiniStatements().setDate(currentDate);
            Long balance = userProfile.getBalance();
            Long amount = userProfile.getMiniStatements().getWithDrawAmount();
            if (balance <= 500) {
                apiResponse.setMessage("Insufficient Balance");
                apiResponse.setData("null");
                apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                return apiResponse;
            }
            if (balance >= 500) {

                Long balance1 = balance - amount;
                userProfile.setBalance(balance1);
                userProfileRepo.save(userProfile);
                miniStatementRepo.save(userProfile.getMiniStatements());
                apiResponse.setMessage("Withdraw Successfully And Below Is Your Account Balance");
                apiResponse.setData(balance1);
                apiResponse.setStatus(HttpStatus.OK.value());
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return apiResponse;
    }

    @Transactional
    public ApiResponse miniStatements(Long numberOfMonths) throws Exception {
        try {
            Long numMonths = numberOfMonths;
            if (numMonths!=null) {
                LocalDate currentDate = LocalDate.now();
                LocalDate endDate = currentDate.withDayOfMonth(2).minusDays(1);
                LocalDate startDate = endDate.minusMonths(numMonths - 1).withDayOfMonth(1);
                String jpql = "SELECT t FROM MiniStatements t WHERE t.date BETWEEN :startDate AND :endDate";
                Query query = entityManager.createQuery(jpql);
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
                query.getResultList();
                apiResponse.setData(query.getResultList());
                apiResponse.setStatus(HttpStatus.OK.value());
                apiResponse.setMessage("This Is Mini Statements");
                return apiResponse;
            }else {
                apiResponse.setData(null);
                apiResponse.setStatus(HttpStatus.NOT_FOUND);
                apiResponse.setMessage("This Is Not Valid Value");
                return apiResponse;
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
