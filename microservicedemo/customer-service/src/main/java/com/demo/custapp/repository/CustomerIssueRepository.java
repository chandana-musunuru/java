package com.demo.custapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.custapp.entity.CustomerIssue;

@Repository
public interface CustomerIssueRepository extends JpaRepository<CustomerIssue, Integer> {

}
