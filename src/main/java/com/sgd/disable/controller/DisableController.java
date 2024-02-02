package com.sgd.disable.controller;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sgd.disable.exception.ExceptionMessage;
import com.sgd.disable.exception.InvalidFileException;
import com.sgd.disable.helper.CsvHelper;
import com.sgd.disable.helper.ExcelHelper;
import com.sgd.disable.models.DisableRequest;
import com.sgd.disable.models.DisableResponse;
import com.sgd.disable.models.DocumentDto;
import com.sgd.disable.service.DisableService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("sgd/api/services/disable")
@AllArgsConstructor
@Validated
public class DisableController {

    private final DisableService disableService;

    @PostMapping("/uploadXlsxFile")
    public ResponseEntity<DisableResponse> uploadXlsxFile(@RequestParam("xlsxFile") MultipartFile xlsxFile)
            throws InvalidFileException {
        if (xlsxFile == null || xlsxFile.isEmpty()) {
            throw new InvalidFileException(ExceptionMessage.NULL_FILE, HttpStatus.BAD_REQUEST);
        }
        if (!ExcelHelper.isXlsxFile(xlsxFile)) {
            throw new InvalidFileException(
                String.format(ExceptionMessage.INVALID_FILE_TYPE, ".xlsx"),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
            );
        }

        DisableResponse disableResponse = new DisableResponse();
        try {
            List<DisableRequest> disableRequests = ExcelHelper.readExcelFile(xlsxFile);
            if (disableRequests.isEmpty()) {
                throw new InvalidFileException(ExceptionMessage.EMPTY_CONTENT, HttpStatus.BAD_REQUEST);
            } 

            long disabledCount = disableService.disableAllDocumentsByDisableRequest(disableRequests);
            disableResponse.setDisableCount(disabledCount);
        } catch (IOError | IOException e) {
            log.error(e.getMessage(), e);
            throw new InvalidFileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(disableResponse);
    }

    @PostMapping("/uploadCsvFile")
    public ResponseEntity<DisableResponse> uploadCsvFile(@RequestParam("csvFile") MultipartFile csvFile)
            throws InvalidFileException {
        if (csvFile == null || csvFile.isEmpty()){
            throw new InvalidFileException(ExceptionMessage.NULL_FILE, HttpStatus.BAD_REQUEST);
        }
        if (!CsvHelper.isCsvFile(csvFile)){
            throw new InvalidFileException(
                String.format(ExceptionMessage.INVALID_FILE_TYPE, ".csv"),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE
            );
        }

        DisableResponse disableResponse = new DisableResponse();
        try {
            List<Long> didsList = CsvHelper.getDidsFromCsv(csvFile.getInputStream());
            if (didsList.isEmpty()) {
                throw new InvalidFileException(ExceptionMessage.EMPTY_CONTENT, HttpStatus.BAD_REQUEST);
            }

            long disabledCount= disableService.disableDocuments(didsList);
            disableResponse.setDisableCount(disabledCount);
        } catch (IOError | IOException e) {
            log.error(e.getMessage(), e);
            throw new InvalidFileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(disableResponse);
    }

    @GetMapping("/findDocuments/{accountNumber}/{primaryFile}")
    public ResponseEntity<List<DocumentDto>> findByDisableRequest(
            @PathVariable("accountNumber") String accountNumber,
            @PathVariable("primaryFile") String primaryFile) {
                
        DisableRequest disableRequest = new DisableRequest(accountNumber, primaryFile);
        List<DocumentDto> documentsToDisable = disableService.findByDisableRequest(disableRequest);
        if (documentsToDisable.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(documentsToDisable);
    }

}
