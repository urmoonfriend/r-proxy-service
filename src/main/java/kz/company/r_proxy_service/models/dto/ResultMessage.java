package kz.company.r_proxy_service.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kz.company.r_proxy_service.enums.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultMessage<T> {
    private boolean success;
    private T message;
    private ErrorDto error;

    public static <T> ResultMessage<T> success(T message) {
        return new <T>ResultMessage<T>()
                .setSuccess(true)
                .setMessage(message);
    }

    public static <T> ResultMessage<T> error(String error) {
        return new <T>ResultMessage<T>()
                .setSuccess(false)
                .setError(new ErrorDto()
                        .setName(ResultCode.INTERNAL_SERVER_ERROR.name())
                        .setDescription(error)
                        .setCode(ResultCode.INTERNAL_SERVER_ERROR.getCode()));
    }

    public static <T> ResultMessage<T> error(ErrorDto error) {
        return new <T>ResultMessage<T>()
                .setSuccess(false)
                .setError(error);
    }

    public static <T> ResultMessage<T> error(ResultCode error) {
        return new <T>ResultMessage<T>()
                .setSuccess(false)
                .setError(new ErrorDto()
                        .setName(error.name())
                        .setDescription(error.getDescription())
                        .setCode(error.getCode()));
    }

    @Data
    @Accessors(chain = true)
    public static class ErrorDto {
        private String name;
        private Integer code;
        private String description;
    }
}
