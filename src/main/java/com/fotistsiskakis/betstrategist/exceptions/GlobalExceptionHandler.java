package com.fotistsiskakis.betstrategist.exceptions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Operation(summary = "Validation Exception", description = "Handles validation errors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "{\"error\": \"Bad Request error\"}")
                    })
            })
    })
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> {
            errors.put("error", error.getDefaultMessage());
        });
        return errors;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Operation(summary = "Data Integrity Violation Exception", description = "Handles data integrity violation errors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "A unique constraint was violated. Please provide a different value.")
                    })
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "An error occurred. Please try again later.")
                    })
            }),
    })
    public ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        // Check if the message contains a unique constraint violation error code
        if (ex.getMessage().contains("unique constraint")) {
            return new ResponseEntity<>("A unique constraint was violated. Please provide a different value.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @Operation(summary = "Entity Not Found Exception", description = "Handles entity not found errors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "")
                    })
            })
    })
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Operation(summary = "Illegal Argument Exception", description = "Handles illegal argument errors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = "{\"error\": \"Bad Request Error\"}")
                    })
            }),
    })
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    @ExceptionHandler(RuntimeException.class)
    @Operation(summary = "Handle Runtime Exception")
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(mediaType = "application/json", examples = {
                    @ExampleObject(value = "An unexpected error occurred. Please try again later.")
            })
    })
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
