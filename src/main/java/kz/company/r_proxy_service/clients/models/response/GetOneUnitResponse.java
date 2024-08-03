package kz.company.r_proxy_service.clients.models.response;

import jakarta.xml.bind.annotation.*;
import kz.company.r_proxy_service.models.dto.Student;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@XmlRootElement(name = "getOneUnitResponse", namespace = "http://service.ws.sample/")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetOneUnitResponse {
    @XmlElement(name = "return")
    private GetOneUnitReturn returnResponse;

    @Data
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class GetOneUnitReturn {
        private boolean success;
        private String error;
        private Student student;
    }
}