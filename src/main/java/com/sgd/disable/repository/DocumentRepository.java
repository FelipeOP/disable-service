package com.sgd.disable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgd.disable.entities.Document;
import com.sgd.disable.models.DocumentDto;

public interface DocumentRepository extends JpaRepository<Document, Long> {

        @Query(value = "SELECT NEW com.sgd.disableservice.models.DocumentDto"
                        + "(d.did, dm.accountNumber, d.originalName, r.status, r.releaseState, r.createDate, dm.disableReason) "
                        + "FROM Document d "
                        + "JOIN d.documentMetadata dm JOIN d.revision r "
                        + "WHERE dm.accountNumber = :accountNumber AND d.originalName = :primaryFile "
                        + "ORDER BY r.createDate DESC")
        public List<DocumentDto> findByDisableRequest(
                        @Param("accountNumber") String accountNumber,
                        @Param("primaryFile") String primaryFile);

}
