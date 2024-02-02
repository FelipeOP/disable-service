package com.sgd.disable.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sgd.disable.models.DocumentDto;
import com.sgd.disable.repository.DocumentRepository;

@ActiveProfiles("qa")
@SpringBootTest
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void findByDisableRequest_whenValidRequest_returnDocument() {
        String accountNumber = "93000000031432";
        String primaryFile = "Contrato.pdf.pdf";

        List<DocumentDto> documentsFound = documentRepository.findByDisableRequest(accountNumber, primaryFile);
        assertThat(documentsFound).isNotEmpty();
    }

    @Test
    void findByDisableRequest_whenInvalidRequest_returnEmpty() {
        String accountNumber = "0";
        String primaryFile = "Contrato.pdf";

        List<DocumentDto> documentsFound = documentRepository.findByDisableRequest(accountNumber, primaryFile);
        assertThat(documentsFound).isEmpty();
    }

    @Test
    void findByDisableRequest_whenValidRequest_returnDocumentWithExpectedValues() {
        String accountNumber = "93000000031432";
        String primaryFile = "Contrato.pdf.pdf";
        long expectedDid = 13433L;

        List<DocumentDto> documentsFound = documentRepository.findByDisableRequest(accountNumber, primaryFile);
        DocumentDto document = documentsFound.get(0);
        assertThat(document.getDid()).isEqualTo(expectedDid);
    }

    @Test
    void findByDisableRequest_whenValidRequest_returnSortedDescendingDocuments() {
        String accountNumber = "100100100111";
        String primaryFile = "nameDoc100.pdf";
        Comparator<DocumentDto> comparator = Comparator.comparing(DocumentDto::getCreateDate).reversed();

        List<DocumentDto> documentsFound = documentRepository.findByDisableRequest(accountNumber, primaryFile);
        assertThat(documentsFound).isSortedAccordingTo(comparator);
    }

}
