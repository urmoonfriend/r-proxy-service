package kz.company.r_proxy_service.clients.impl;

import kz.company.r_proxy_service.clients.StorageClient;
import kz.company.r_proxy_service.clients.models.request.GetAllUnitsRequest;
import kz.company.r_proxy_service.clients.models.request.GetOneUnitRequest;
import kz.company.r_proxy_service.clients.models.response.GetAllUnitsResponse;
import kz.company.r_proxy_service.clients.models.response.GetOneUnitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@RequiredArgsConstructor
public class StorageClientImpl implements StorageClient {
    private final WebServiceTemplate webServiceTemplate;
    @Value("${storage.client.uri}")
    private String uri;

    @Override
    public GetOneUnitResponse getOneUnit(String recordBookNumber) {
        GetOneUnitRequest request = new GetOneUnitRequest()
                .setRecordBookNumber(recordBookNumber);
        return (GetOneUnitResponse) webServiceTemplate.marshalSendAndReceive(uri, request);
    }

    @Override
    public GetAllUnitsResponse getAllUnits() {
        GetAllUnitsRequest request = new GetAllUnitsRequest();
        return (GetAllUnitsResponse) webServiceTemplate.marshalSendAndReceive(uri, request);
    }
}