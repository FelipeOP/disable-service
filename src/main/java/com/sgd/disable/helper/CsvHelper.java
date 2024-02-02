package com.sgd.disable.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.web.multipart.MultipartFile;

public class CsvHelper {

    CsvHelper() {
    }

    protected static final String TYPE = "text/csv";

    public static boolean isCsvFile(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Long> getDidsFromCsv(InputStream inputStream) throws IOException {
        try (
                BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8)
                );
                CSVParser csvParser = new CSVParser(
                    fileReader,
                    CSVFormat.DEFAULT
                            .builder()
                            .setHeader("DID")
                            .setSkipHeaderRecord(true)
                            .setTrim(true)
                            .build()
                );
            ) 
            {
                return csvParser.getRecords()
                        .stream()
                        .map(csvRecord -> csvRecord.get("DID"))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            }
    }
}
