package kz.company.r_proxy_service.exceptions;

import static java.lang.String.format;

public class MethodNotFoundException extends Throwable {
    public MethodNotFoundException(String message) {
        super(format("Method: %s not found", message));
    }
}
