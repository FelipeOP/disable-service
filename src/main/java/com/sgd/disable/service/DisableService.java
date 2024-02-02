package com.sgd.disable.service;

import java.util.Collection;
import java.util.List;

import com.sgd.disable.models.DisableRequest;
import com.sgd.disable.models.DocumentDto;

public interface DisableService {

    /**
     * Desactiva todos los documentos especificados en la lista de solicitudes de
     * desactivación.
     * 
     * @implNote
     *           Si hay documentos repetidos, se realiza el siguiente procedimiento:
     *           1. Se ordenan los documentos de forma descendente por fecha de
     *           creación
     *           2. Se habilita el documento con la fecha más reciente
     *           3. Se deshabilitan los restantes.
     * @param disableRequests La lista de solicitudes de desactivación.
     * @return El objeto que contiene información sobre el resultado de la
     *         desactivación.
     */
    long disableAllDocumentsByDisableRequest(List<DisableRequest> disableRequests);

    List<DocumentDto> findByDisableRequest(DisableRequest disableRequest);

    long disableDocumentsInRange(List<Long> didsList, int range);

    long disableDocument(Long did);

    long disableDocuments(Collection<Long> didsList);

    long enableDocument(Long did);

}
