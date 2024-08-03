package kz.company.r_proxy_service.clients;

import kz.company.r_proxy_service.clients.models.response.GetAllUnitsResponse;
import kz.company.r_proxy_service.clients.models.response.GetOneUnitResponse;

public interface StorageClient {
    GetOneUnitResponse getOneUnit(String recordBookNumber);

    GetAllUnitsResponse getAllUnits();
}
