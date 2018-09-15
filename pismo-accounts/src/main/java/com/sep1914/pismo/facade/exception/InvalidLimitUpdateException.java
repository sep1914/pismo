package com.sep1914.pismo.facade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid values for credit and withdrawal limits")
public class InvalidLimitUpdateException extends RuntimeException {
}
