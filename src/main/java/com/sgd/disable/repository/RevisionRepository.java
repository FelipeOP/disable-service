package com.sgd.disable.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sgd.disable.entities.Revision;

public interface RevisionRepository extends JpaRepository<Revision, Long> {

        @Modifying
        @Query(value = "UPDATE Revision r "
                        + "SET r.status = :status, r.releaseState = :releaseState "
                        + "WHERE r.did = :did")
        public int updateRevisionState(
                        @Param("did") Long did,
                        @Param("status") String status,
                        @Param("releaseState") String releaseState);

        @Modifying
        @Query(value = "UPDATE Revision r "
                        + "SET r.status = :status, r.releaseState = :releaseState "
                        + "WHERE r.did IN :didsList")
        public int updateRevisionState(
                        @Param("didsList") Collection<Long> didsList,
                        @Param("status") String status,
                        @Param("releaseState") String releaseState);

}
