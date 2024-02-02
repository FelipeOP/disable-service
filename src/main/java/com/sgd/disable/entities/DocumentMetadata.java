package com.sgd.disable.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DOCMETA")
public class DocumentMetadata {

    @Id
    @Column(name = "DID")
    private Long did;

    @Column(name = "XDNUMEROCUENTA")
    private String accountNumber;

    @Column(name = "XDMOTIVODESHABILITACION")
    private String disableReason;

}