package com.javacodegeeks.ultimate.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by adr on 11/9/15.
 */
@Entity
@Table(name = "T_ID_CARD")
public class IdCard {
    private Long id;
    private String idNumber;
    private Date issueDate;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ID_NUMBER")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Column(name = "ISSUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}