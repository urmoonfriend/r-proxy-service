package kz.company.r_proxy_service.services.impl;

import kz.company.r_proxy_service.clients.StorageClient;
import kz.company.r_proxy_service.clients.models.response.GetAllUnitsResponse;
import kz.company.r_proxy_service.clients.models.response.GetOneUnitResponse;
import kz.company.r_proxy_service.enums.ResultCode;
import kz.company.r_proxy_service.exceptions.StudentNotFoundException;
import kz.company.r_proxy_service.models.dto.ResultMessage;
import kz.company.r_proxy_service.models.dto.Student;
import kz.company.r_proxy_service.services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {
    private final StorageClient storageClient;

    @Override
    public Optional<ResultMessage<Student>> getByRecordBookNumber(String recordBookNumber) {
        Optional<ResultMessage<Student>> resultMessage = Optional.of(
                ResultMessage.error(ResultCode.INTERNAL_SERVER_ERROR));
        try {
            GetOneUnitResponse result = storageClient.getOneUnit(recordBookNumber);
            if (result != null && result.getReturnResponse() != null && result.getReturnResponse().isSuccess()) {
                resultMessage = Optional.of(ResultMessage.success(result.getReturnResponse().getStudent()));
            } else {
                resultMessage = Optional.of(ResultMessage.error(new ResultMessage.ErrorDto()
                        .setCode(ResultCode.BAD_REQUEST.getCode())
                        .setName(ResultCode.BAD_REQUEST.name())
                        .setDescription(new StudentNotFoundException(recordBookNumber).getMessage())));
            }
        } catch (Exception e) {
            log.error("StorageServiceImpl.getByRecordBookNumber error: [{}]", e.getMessage(), e);
        }
        return resultMessage;
    }

    @Override
    public Optional<ResultMessage<List<Student>>> getAll() {
        Optional<ResultMessage<List<Student>>> resultMessage = Optional.of(
                ResultMessage.error(ResultCode.INTERNAL_SERVER_ERROR));
        try {
            GetAllUnitsResponse result = storageClient.getAllUnits();
            log.info("StorageServiceImpl.getAll() [{}]", result);
            if (result != null && result.getReturnResponse() != null && result.getReturnResponse().isSuccess()) {
                resultMessage = Optional.of(ResultMessage.success(result.getReturnResponse().getStudents()));
            }
        } catch (Exception e) {
            log.error("StorageServiceImpl.getByRecordBookNumber error: [{}]", e.getMessage(), e);
        }
        return resultMessage;
    }
}
