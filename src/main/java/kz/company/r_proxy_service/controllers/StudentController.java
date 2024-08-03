package kz.company.r_proxy_service.controllers;

import kz.company.r_proxy_service.enums.ResultCode;
import kz.company.r_proxy_service.models.dto.ResultMessage;
import kz.company.r_proxy_service.models.dto.Student;
import kz.company.r_proxy_service.services.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/students")
public class StudentController {

    private final StorageService storageService;

    @GetMapping("/{recordBookNumber}")
    public ResponseEntity<ResultMessage<Student>> getStudent(@PathVariable String recordBookNumber) {
        log.info("StudentController.getStudent request: [{}]", recordBookNumber);
        Optional<ResultMessage<Student>> resultMessage = storageService.getByRecordBookNumber(recordBookNumber);
        log.info("StudentController.getStudent response: [{}]", resultMessage);
        return resultMessage
                .filter(ResultMessage::isSuccess)
                .map(ResponseEntity::ok)
                .or(() -> resultMessage
                        .filter(rm -> ResultCode.BAD_REQUEST.name().equals(rm.getError().getName()))
                        .map(rm -> ResponseEntity.badRequest().body(rm))
                )
                .orElseGet(() -> resultMessage
                        .map(rm -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rm))
                        .orElseGet(() -> ResponseEntity.notFound().build())
                );
    }

    @GetMapping()
    public ResponseEntity<ResultMessage<List<Student>>> getStudents() {
        log.info("StudentController.getStudents request");
        Optional<ResultMessage<List<Student>>> resultMessage = storageService.getAll();
        log.info("StudentController.getStudents response: [{}]", resultMessage);
        return resultMessage
                .filter(ResultMessage::isSuccess)
                .map(ResponseEntity::ok)
                .orElseGet(() -> resultMessage
                        .map(rm -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rm))
                        .orElseGet(() -> ResponseEntity.notFound().build())
                );
    }
}
