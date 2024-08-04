package kz.company.r_proxy_service.clients.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kz.company.r_proxy_service.models.dto.Student;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMessage {
    private boolean success;
    private Student student;
    private List<Student> students;
    private String error;
    private String correlationId;
}
