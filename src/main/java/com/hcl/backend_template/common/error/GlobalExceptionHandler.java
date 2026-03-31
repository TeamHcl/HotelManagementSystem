package com.hcl.backend_template.common.error;

import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Validation failed for one or more fields");
    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty("timestamp", OffsetDateTime.now());
    problemDetail.setProperty(
        "errors",
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    fieldError -> fieldError.getField(),
                    fieldError ->
                        fieldError.getDefaultMessage() == null
                            ? "Invalid value"
                            : fieldError.getDefaultMessage(),
                    (first, second) -> first)));

    return ResponseEntity.badRequest().body(problemDetail);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors =
        ex.getConstraintViolations().stream()
            .collect(
                Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    violation -> violation.getMessage(),
                    (first, second) -> first));

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Request constraint validation failed");
    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty("timestamp", OffsetDateTime.now());
    problemDetail.setProperty("errors", errors);

    return ResponseEntity.badRequest().body(problemDetail);
  }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
        HttpStatus resolvedStatus = status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status;
        String detail = ex.getReason() == null ? "Request failed" : ex.getReason();

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(resolvedStatus, detail);
        problemDetail.setTitle(resolvedStatus.getReasonPhrase());
        problemDetail.setProperty("timestamp", OffsetDateTime.now());

        return ResponseEntity.status(resolvedStatus).body(problemDetail);
    }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception ex) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    problemDetail.setTitle("Internal Server Error");
    problemDetail.setProperty("timestamp", OffsetDateTime.now());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
  }
}
