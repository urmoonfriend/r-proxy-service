package kz.company.r_proxy_service.services;

import kz.company.r_proxy_service.models.dto.ResultMessage;
import kz.company.r_proxy_service.models.dto.Student;

import java.util.List;
import java.util.Optional;

public interface StorageService {
    Optional<ResultMessage<Student>> getByRecordBookNumber(String recordBookNumber);

    Optional<ResultMessage<List<Student>>> getAll();
}
