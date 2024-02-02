package com.sgd.disable.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "REVISIONS")
public class Revision {

    @Id
    @Column(name = "DID")
    private Long did;

    @Column(name = "DSTATUS")
    private String status;

    @Column(name = "DRELEASESTATE")
    private String releaseState;

    @Column(name = "DCREATEDATE")
    private Date createDate;

}