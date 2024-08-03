package kz.company.r_proxy_service.exceptions;

public class StudentNotFoundException extends Throwable {
    public StudentNotFoundException(String recordBookNumber) {
        super(String.format("Student with recordBookNumber: %s not found", recordBookNumber));
    }
}
