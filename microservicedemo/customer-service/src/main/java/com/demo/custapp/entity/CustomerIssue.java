package com.demo.custapp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="CustomerIssue")
public class CustomerIssue {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="issueid")
  private Integer issueId;
  @Column(name="bookid" , length = 50)
  private String bookId;
  @Column(name="issuedate")
  private LocalDate issueDate;
  @Column(name="returndate")
  private LocalDate returnDate;
  @ManyToOne
  @JoinColumn(name="cid")
  private Customer customer;
  private String status;
   
  
	
}
