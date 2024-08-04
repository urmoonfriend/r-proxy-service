package kz.company.r_proxy_service.stream;

import kz.company.r_proxy_service.clients.models.request.GetAllUnitsRequest;
import kz.company.r_proxy_service.clients.models.request.GetOneUnitRequest;
import kz.company.r_proxy_service.clients.models.response.GetAllUnitsResponse;
import kz.company.r_proxy_service.clients.models.response.GetOneUnitResponse;
import kz.company.r_proxy_service.models.dto.ResultMessage;
import kz.company.r_proxy_service.models.dto.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
@RequiredArgsConstructor
public class StreamTransmitter {
    public static final String ONE_STUDENT_REQUEST = "storageOneStudentRequest";
    public static final String ALL_STUDENTS_REQUEST = "storageAllStudentsRequest";
    private final StreamBridge streamBridge;
    private final ConcurrentHashMap<String, CompletableFuture<ResultMessage<Student>>> oneStudentFutures = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CompletableFuture<ResultMessage<List<Student>>>> allStudentsFutures = new ConcurrentHashMap<>();
    private static final String S_OUT_0 = "%s-out-0";

    private void sendRabbitMqMessage(String exchangePrefix, Object message) {
        streamBridge.send(String.format(S_OUT_0, exchangePrefix), message);
    }

    public GetOneUnitResponse sendOneStudentRequest(GetOneUnitRequest request) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<ResultMessage<Student>> future = new CompletableFuture<>();
        oneStudentFutures.put(correlationId, future);
        request.setCorrelationId(correlationId);
        sendRabbitMqMessage(ONE_STUDENT_REQUEST, request);
        try {
            ResultMessage<Student> result = future.get(30, TimeUnit.SECONDS);
            GetOneUnitResponse response = new GetOneUnitResponse()
                    .setReturnResponse(new GetOneUnitResponse.GetOneUnitReturn()
                            .setSuccess(result.isSuccess())
                            .setStudent(result.getMessage())
                            .setError(result.getError() != null ? result.getError().getDescription() : null));
            log.info("sendOneStudentRequest method response: [{}]", result);
            return response;
        } catch (TimeoutException e) {
            throw new RuntimeException("No response received within the timeout period", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while waiting for response", e);
        } finally {
            oneStudentFutures.remove(correlationId);
        }
    }

    public GetAllUnitsResponse sendAllStudentsRequest(GetAllUnitsRequest request) {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<ResultMessage<List<Student>>> future = new CompletableFuture<>();
        allStudentsFutures.put(correlationId, future);
        request.setCorrelationId(correlationId);
        sendRabbitMqMessage(ALL_STUDENTS_REQUEST, request);
        try {
            ResultMessage<List<Student>> result = future.get(30, TimeUnit.SECONDS);
            GetAllUnitsResponse response = new GetAllUnitsResponse()
                    .setReturnResponse(new GetAllUnitsResponse.GetAllUnitsReturn()
                            .setSuccess(result.isSuccess()).setStudents(result.getMessage()));
            log.info("sendAllStudentsRequest method response: [{}]", result);
            return response;
        } catch (TimeoutException e) {
            throw new RuntimeException("No response received within the timeout period", e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while waiting for response", e);
        } finally {
            allStudentsFutures.remove(correlationId);
        }
    }

    public void handleOneStudentResponse(ResultMessage<Student> response, String correlationId) {
        CompletableFuture<ResultMessage<Student>> future = oneStudentFutures.remove(correlationId);
        if (future != null) {
            future.complete(response);
        } else {
            log.warn("No CompletableFuture found for correlationId: {}", correlationId);
        }
    }

    public void handleAllStudentsResponse(ResultMessage<List<Student>> response, String correlationId) {
        CompletableFuture<ResultMessage<List<Student>>> future = allStudentsFutures.remove(correlationId);
        if (future != null) {
            future.complete(response);
        } else {
            log.warn("No CompletableFuture found for correlationId: {}", correlationId);
        }
    }
}
