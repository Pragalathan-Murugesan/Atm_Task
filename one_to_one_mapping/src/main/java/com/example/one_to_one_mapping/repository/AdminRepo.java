package com.example.one_to_one_mapping.repository;

import com.example.one_to_one_mapping.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
//    @Query(value = "select * from user_table where user_id = :userId", nativeQuery = true)
//    Optional<Admin> updateUserUserId(Long userId);
@Query(value = "select * from admin_table where emailid = :emailID and  role = :role",nativeQuery = true)
    Admin loginApi(String emailID, String role);
}
