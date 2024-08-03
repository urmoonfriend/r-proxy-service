package kz.company.r_proxy_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    UNKNOWN_ERROR("Неизвестная ошибка", 100),
    INTERNAL_SERVER_ERROR("Ошибка сервера", 500),
    BAD_REQUEST("BAD_REQUEST", 400);
    private final String description;
    private final Integer code;
}
