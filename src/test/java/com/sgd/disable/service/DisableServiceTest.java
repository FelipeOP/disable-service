package com.sgd.disable.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.sgd.disable.models.DocumentDto;
import com.sgd.disable.repository.DocumentRepository;

@SpringBootTest
public class DisableServiceTest {

    @Autowired
    private IDisableService disableService;
    @MockBean
    private IDocumentRepository mockDocumentRepository;

    @Test
    public void findByDisableRequest_whenValidRequest_returnDocument() {
        String accountNumber = "93000000031432";
        String primaryFile = "Contrato.pdf.pdf";

        List<DocumentDto> documentsFound = disableService.findByDisableRequest(accountNumber, primaryFile);
        
        assertThat(documentsFound).isNotEmpty();
    }

}
