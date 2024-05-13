package com.example.app.response;

import com.example.app.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private boolean success;
    private ErrorCode error;

    private ErrorResponse(final ErrorCode code) {
        this.success = false;
        this.error = code;
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
