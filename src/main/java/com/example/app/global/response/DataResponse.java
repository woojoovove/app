package com.example.app.global.response;

import com.example.app.global.swagger.SchemaDescriptionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
public class DataResponse<T> {
    @Schema(description = SchemaDescriptionUtils.Response.status, example = "true")
    private boolean success;
    private T data;

    public static<T> DataResponse<T> response(final boolean success, final T data) {
        return DataResponse.<T>builder()
                .success(success)
                .data(data)
                .build();
    }

    public static<T> ResponseEntity<DataResponse<T>> success() {
        return ResponseEntity.ok(response(true, null));
    }

    public static<T> ResponseEntity<DataResponse<T>> success(final T data) {
        return ResponseEntity.ok(response(true, data));
    }
}
