package taaewoo.RiotDataPipeline.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonResponse {private HttpStatus code;
    private String message;
    private Object data;

    public CommonResponse(HttpStatus code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
