package com.example.app.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // common error
    INVALID_INPUT_VALUE(400, "C0001", "Invalid input value"),
    METHOD_NOT_ALLOWED(405, "C0002", "Method not allowed"),
    ENTITY_NOT_FOUND(400, "C0003", "Cannot found entity"),
    INTERNAL_SERVER_ERROR(500, "C0004", "Internal server error"),
    // group error
    DUPLICATE_GROUP_NAME(400, "G0001", "Duplicate group name"),
    GROUP_NOT_FOUND(400, "G0002", "Group not found"),
    //user error
    USER_NOT_FOUND(400, "U0001", "User not found"),
    // IO exception
    MESSAGE_SERIALIZE_EXCEPTION(500,"I0001", "Message serialization failed"),
    INVALID_REQUEST_JSON(400, "C0004", "Invalid request json"),
    MEMBERSHIP_NOT_FOUND(400, "M0001", "Membership not found");


    @JsonIgnore
    private final int status;
    private final String code;
    private final String message;

}
