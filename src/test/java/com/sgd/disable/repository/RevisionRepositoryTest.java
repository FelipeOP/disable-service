package com.sgd.disable.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sgd.disable.constants.ReleaseState;
import com.sgd.disable.constants.Status;
import com.sgd.disable.entities.Revision;
import com.sgd.disable.repository.RevisionRepository;

@ActiveProfiles("qa")
@SpringBootTest()
@Transactional
class RevisionRepositoryTest {

    @Autowired
    private RevisionRepository revisionRepository;

    @Test
    void updateRevisionState_whenDocumentExits_returnExpectedStatus() {
        Long did = 13433L;

        revisionRepository.updateRevisionState(did, Status.DISABLE, ReleaseState.HIDDEN);
        Revision revision = revisionRepository.findById(did).get();

        assertThat(revision.getStatus()).isEqualTo(Status.DISABLE);
        assertThat(revision.getReleaseState()).isEqualTo(ReleaseState.HIDDEN);
    }

    @Test
    void updateRevisionState_whenDocumentNotExits_returnZero() {
        Long did = 0L;
        long rowsAffected = revisionRepository.updateRevisionState(did, Status.DISABLE, ReleaseState.HIDDEN);
        assertThat(rowsAffected).isZero();
    }

    @Test
    void updateRevisionState_whenDocumentsExits_returnExpectedStatusAndReleaseState() {
        List<Long> didsList = Arrays.asList(13433L, 13434L);

        revisionRepository.updateRevisionState(didsList, Status.DISABLE, ReleaseState.HIDDEN);
        List<Revision> revisions = revisionRepository.findAllById(didsList);

        assertThat(revisions).extracting(Revision::getStatus).contains(Status.DISABLE);
        assertThat(revisions).extracting(Revision::getReleaseState).contains(ReleaseState.HIDDEN);
    }

    @Test
    void updateRevisionState_whenDocumentsNotExits_returnZeroRowsAffected() {
        List<Long> didsList = Arrays.asList(-2L, -1L);
        long rowsAffected = revisionRepository.updateRevisionState(didsList, Status.DISABLE, ReleaseState.HIDDEN);
        assertThat(rowsAffected).isZero();
    }

}
