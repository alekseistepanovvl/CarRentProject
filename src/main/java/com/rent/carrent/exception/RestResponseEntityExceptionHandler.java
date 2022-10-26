package com.rent.carrent.exception;

import com.rent.carrent.dto.error.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Exception!", ex);
        StringBuilder builder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            builder.append(fieldName).append(":").append(errorMessage).append("\n");
        });
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(builder.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(CarCreationRequestValidationException.class)
    public ResponseEntity<ErrorDto> handleCarValidationException(CarCreationRequestValidationException ex) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCarNotFoundException(CarNotFoundException ex) {
        ErrorDto errorDto = new ErrorDto();
        String message = ex.getMessage();
        Object[] params = ex.getParams();
        errorDto.setErrorMessage(String.format(message, params));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error("Exception!", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage("Unexpected exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }
}
