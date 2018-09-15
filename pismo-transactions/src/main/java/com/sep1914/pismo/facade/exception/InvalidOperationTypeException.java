package com.sep1914.pismo.facade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid transaction operation type")
public class InvalidOperationTypeException extends RuntimeException {
}
