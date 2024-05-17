package com.example.app.global.response;

import com.example.app.global.error.exception.ErrorCode;
import com.example.app.global.swagger.SchemaDescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    @Schema(description = SchemaDescriptionUtils.Response.status, example = "false")
    private boolean success;
    @Schema(description = SchemaDescriptionUtils.Response.errorCode, example = "INVALID_INPUT_VALUE")
    private ErrorCode error;

    private ErrorResponse(final ErrorCode code) {
        this.success = false;
        this.error = code;
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
