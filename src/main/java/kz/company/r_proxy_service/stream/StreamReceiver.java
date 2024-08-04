package kz.company.r_proxy_service.stream;

import kz.company.r_proxy_service.enums.ResultCode;
import kz.company.r_proxy_service.models.dto.ResultMessage;
import kz.company.r_proxy_service.models.dto.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class StreamReceiver {

    private final StreamTransmitter streamTransmitter;

    @Bean
    public Consumer<kz.company.r_proxy_service.clients.models.response.ResultMessage> receiveStorageOneStudentResponse() {
        return response -> {
            log.info("Received JSON response for OneStudent: {}", response);
            try {
                ResultMessage<Student> resultMessage = ResultMessage.error(ResultCode.UNKNOWN_ERROR);
                if (response.isSuccess()) {
                    resultMessage = new ResultMessage<Student>()
                            .setSuccess(response.isSuccess())
                            .setMessage(response.getStudent());
                }
                streamTransmitter.handleOneStudentResponse(resultMessage, response.getCorrelationId());

            } catch (Exception e) {
                log.error("Deserialization error: {}", e.getMessage(), e);
                throw new RuntimeException("Error deserializing JSON response", e);
            }
        };
    }

    @Bean
    public Consumer<kz.company.r_proxy_service.clients.models.response.ResultMessage> receiveStorageAllStudentsResponse() {
        return response -> {
            log.info("Received JSON response for AllStudents: {}", response);
            try {
                ResultMessage<List<Student>> resultMessage = ResultMessage.error(ResultCode.UNKNOWN_ERROR);
                if (response.isSuccess()) {
                    resultMessage = new ResultMessage<List<Student>>()
                            .setSuccess(response.isSuccess())
                            .setMessage(response.getStudents());
                }
                streamTransmitter.handleAllStudentsResponse(resultMessage, response.getCorrelationId());
            } catch (Exception e) {
                log.error("Deserialization error: {}", e.getMessage(), e);
                throw new RuntimeException("Error deserializing JSON response", e);
            }
        };
    }

}