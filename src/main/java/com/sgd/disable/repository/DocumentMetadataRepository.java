package com.sgd.disable.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgd.disable.entities.DocumentMetadata;

public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {

    @Modifying
    @Query(value = "UPDATE DocumentMetadata dm "
            + "SET dm.disableReason = :disableReason "
            + "WHERE dm.did = :did")
    int updateDisableReason(
            @Param("did") Long did,
            @Param("disableReason") String disableReason);

    @Modifying
    @Query(value = "UPDATE DocumentMetadata dm SET "
            + "dm.disableReason = :disableReason "
            + "WHERE dm.did IN :didsList")
    int updateDisableReason(
            @Param("didsList") Collection<Long> didsList,
            @Param("disableReason") String disableReason);
}
