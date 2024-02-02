package com.sgd.disable.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import com.sgd.disable.exception.ExceptionMessage;
import com.sgd.disable.exception.InvalidFileException;
import com.sgd.disable.models.DisableRequest;

public class ExcelHelper {

    ExcelHelper() {
    }

    protected static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    protected static final Map<String, Integer> HEADERS = new HashMap<>();

    static {
        HEADERS.put("PRIMARYFILE", 0);
        HEADERS.put("XDNUMEROCUENTA", 1);
    }

    public static boolean isXlsxFile(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<DisableRequest> readExcelFile(MultipartFile xlsxFile) throws IOException, InvalidFileException {
        try (Workbook workbook = new XSSFWorkbook(xlsxFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            Row headerRow = sheet.getRow(0);
            if (!hasValidHeaders(headerRow)){
                throw new InvalidFileException(
                    String.format(ExceptionMessage.INVALID_HEADERS, Arrays.toString(HEADERS.keySet().toArray())), 
                    HttpStatus.BAD_REQUEST
                );
            }
            sheet.removeRow(headerRow);
            return extractDisableRequests(sheet);
        }
    }

    public static boolean hasValidHeaders(Row headerRow) {
        for (Cell cell : headerRow) {
            String header = cell.getStringCellValue().toUpperCase();
            if (!HEADERS.containsKey(header))
                return false;
            HEADERS.put(header, cell.getColumnIndex());
        }
        return true;
    }

    private static List<DisableRequest> extractDisableRequests(Sheet sheet){
        List<DisableRequest> disableRequestsList = new ArrayList<>();
        for (Row row : sheet) {
            DisableRequest disableRequest = new DisableRequest();
            for (Cell cell : row) {
                String text = cell.getStringCellValue();
                if (cell.getColumnIndex() == HEADERS.get("PRIMARYFILE"))
                    disableRequest.setPrimaryFile(text);
                if (cell.getColumnIndex() == HEADERS.get("XDNUMEROCUENTA"))
                    disableRequest.setAccountNumber(text);
            }
            disableRequestsList.add(disableRequest);
        }
        return disableRequestsList;
    }

}
