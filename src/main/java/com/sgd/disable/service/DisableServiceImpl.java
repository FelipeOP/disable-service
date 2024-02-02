package com.sgd.disable.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgd.disable.constants.DisableReason;
import com.sgd.disable.constants.ReleaseState;
import com.sgd.disable.constants.Status;
import com.sgd.disable.models.DisableRequest;
import com.sgd.disable.models.DocumentDto;
import com.sgd.disable.repository.DocumentMetadataRepository;
import com.sgd.disable.repository.DocumentRepository;
import com.sgd.disable.repository.RevisionRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class DisableServiceImpl implements DisableService {

    private final DocumentRepository documentRepository;
    private final DocumentMetadataRepository documentMetadataRepository;
    private final RevisionRepository revisionRepository;

    @Override
    public long disableDocumentsInRange(List<Long> didsList, int range) {
        int total = didsList.size();
        int iterations = total / range;
        if (total % range != 0) {
            iterations++;
        }
        IntStream.range(0, iterations)
                .map(index -> index * range)
                .forEach(index -> {
                    int limit = index + range;
                    if (limit > total) {
                        limit = total;
                    }
                    List<Long> subList = didsList.subList(index, limit);
                    disableDocuments(subList);
                    log.info("[{}] last document processed [{}]", limit, subList.get(subList.size() - 1));
                });
        return total;
    }

    @Override
    public long disableAllDocumentsByDisableRequest(List<DisableRequest> disableRequests) {
        return disableRequests
                .stream()
                .map(this::findByDisableRequest)
                .map(this::disableDocumentsList)
                .count();
    }

    private long disableDocumentsList(List<DocumentDto> documentsToDisable) {
        if (documentsToDisable.isEmpty())
            return 0;
        if (documentsToDisable.size() == 1) {
            Long uniqueDid = documentsToDisable.get(0).getDid();
            disableDocument(uniqueDid);
            return 1;
        }
        DocumentDto firstDocument = documentsToDisable.get(0);
        enableDocument(firstDocument.getDid());
        documentsToDisable.remove(firstDocument);

        Collection<Long> didsList = documentsToDisable
                .stream()
                .map(DocumentDto::getDid)
                .collect(Collectors.toList());

        return disableDocuments(didsList);
    }

    @Override
    public List<DocumentDto> findByDisableRequest(DisableRequest disableRequest) {
        return documentRepository.findByDisableRequest(
            disableRequest.getAccountNumber(),
            disableRequest.getPrimaryFile()
        );
    }

    @Override
    public long disableDocument(Long did) {
        documentMetadataRepository.updateDisableReason(did, DisableReason.RECTIFICATION);
        return revisionRepository.updateRevisionState(did, Status.DISABLE, ReleaseState.HIDDEN);
    }

    @Override
    public long enableDocument(Long did) {
        documentMetadataRepository.updateDisableReason(did, DisableReason.NONE);
        return revisionRepository.updateRevisionState(did, Status.RELEASED, ReleaseState.VISIBLE);
    }

    @Override
    public long disableDocuments(Collection<Long> didsList) {
        documentMetadataRepository.updateDisableReason(didsList, DisableReason.RECTIFICATION);
        return revisionRepository.updateRevisionState(didsList, Status.DISABLE, ReleaseState.HIDDEN);
    }

}
