package kz.company.r_proxy_service.clients.models.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@XmlRootElement(name = "getAllUnits", namespace = "http://service.ws.sample/")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllUnitsRequest {
    private String correlationId;
}