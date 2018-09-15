package com.sep1914.pismo.facade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid payment account")
public class InvalidPaymentAccountException extends RuntimeException {
}
