package com.Soo_Shinsa.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InternalServerException extends RuntimeException {

    private final ErrorCode errorCode;
}
