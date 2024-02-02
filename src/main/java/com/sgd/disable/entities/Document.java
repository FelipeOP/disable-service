package com.sgd.disable.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "DOCUMENTS")
public class Document {

    @Id
    @Column(name = "DID")
    private Long did;

    @Column(name = "DORIGINALNAME")
    private String originalName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DID", referencedColumnName = "did")
    private DocumentMetadata documentMetadata;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DID", referencedColumnName = "did")
    private Revision revision;
}