package kz.company.r_proxy_service.clients.models.response;

import jakarta.xml.bind.annotation.*;
import kz.company.r_proxy_service.models.dto.Student;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@XmlRootElement(name = "getAllUnitsResponse", namespace = "http://service.ws.sample/")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllUnitsResponse {
    @XmlElement(name = "return", namespace = "")
    private GetAllUnitsReturn returnResponse;

    @Data
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class GetAllUnitsReturn {
        private boolean success;
        @XmlElementWrapper(name = "students")
        @XmlElement(name = "student")
        private List<Student> students;
    }
}