package com.sgd.disable.exception;

public class ExceptionMessage {

    ExceptionMessage() {
    }

    public static final String INVALID_HEADERS = "Encabezados inválidos, solo se acepta %s";
    public static final String NULL_FILE = "No se envió el archivo o está corrupto. Verifique la integridad del archivo.";
    public static final String EMPTY_CONTENT = "El archivo enviado está vacío.";
    public static final String INVALID_FILE_TYPE = "El tipo de archivo no es válido, solo se acepta %s";
    public static final String ERROR_READING_FILE = "Error al leer el archivo";
}
