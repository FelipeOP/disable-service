package com.sgd.disable.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sgd.disable.constants.DisableReason;
import com.sgd.disable.entities.DocumentMetadata;
import com.sgd.disable.repository.DocumentMetadataRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("qa")
@SpringBootTest
@Transactional
class DocumentMetadataTest {

    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;

    @Test
    void updateDisableReason_whenDocumentExits_returnExpectedDisableReason() {
        Long did = 13433L;

        documentMetadataRepository.updateDisableReason(did, DisableReason.RECTIFICATION);
        DocumentMetadata documentMetadata = documentMetadataRepository.findById(did).get();

        assertThat(documentMetadata.getDisableReason()).isEqualTo(DisableReason.RECTIFICATION);
    }

    @Test
    void updateDisableReason_whenDocumentNotExits_returnZero() {
        Long did = 0L;
        int rowsAffected = documentMetadataRepository.updateDisableReason(did, DisableReason.RECTIFICATION);
        assertThat(rowsAffected).isZero();
    }

    @Test
    void updateDisableReason_whenDocumentsExits_returnExpectedDisableReason() {
        List<Long> didsList = Arrays.asList(13433L, 13434L);

        documentMetadataRepository.updateDisableReason(didsList, DisableReason.RECTIFICATION);
        List<DocumentMetadata> documentMetadataList = documentMetadataRepository.findAllById(didsList);

        assertThat(documentMetadataList)
                .extracting(DocumentMetadata::getDisableReason)
                .contains(DisableReason.RECTIFICATION);
    }

    @Test
    void updateDisableReason_whenDocumentsNotExits_returnZeroRowsAffected() {
        List<Long> didsList = Arrays.asList(-2L, -1L);
        int rowsAffected = documentMetadataRepository.updateDisableReason(didsList, DisableReason.RECTIFICATION);
        assertThat(rowsAffected).isZero();
    }
}
