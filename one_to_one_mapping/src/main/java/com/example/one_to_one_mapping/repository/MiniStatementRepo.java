package com.example.one_to_one_mapping.repository;

import com.example.one_to_one_mapping.entity.MiniStatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniStatementRepo extends JpaRepository<MiniStatements,Long> {
}
