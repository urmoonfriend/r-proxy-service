package kz.company.r_proxy_service.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import kz.company.r_proxy_service.configs.LocalDateTimeAdapter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlElement(name = "id")
    private Long id;
    @XmlElement(name = "firstName")
    private String firstName;
    @XmlElement(name = "lastName")
    private String lastName;
    @XmlElement(name = "fatherName")
    private String fatherName;
    @XmlElement(name = "file")
    private byte[] file;
    @XmlElement(name = "fileName")
    private String fileName;
    @XmlElement(name = "contentType")
    private String contentType;
    @XmlElement(name = "age")
    private Integer age;
    @XmlElement(name = "recordBookNumber")
    private String recordBookNumber;
    @XmlElement(name = "createdAt")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime createdAt;
    @XmlElement(name = "updatedAt")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime updatedAt;
}
