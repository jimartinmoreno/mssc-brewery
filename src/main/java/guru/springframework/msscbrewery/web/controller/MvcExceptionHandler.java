package guru.springframework.msscbrewery.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j //Lombok annotation
public class MvcExceptionHandler {

    /**
     * Para a gestion de excepciones
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String[]> validationErrorHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation ->
                errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage())
        );
        log.info("validationErrorHandler - errors: " + errors);
        return new ResponseEntity<>(errors.toArray(new String[e.getConstraintViolations().size()]), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> handleBindException(BindException ex){
        return new ResponseEntity<>(ex.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Para a gestion de excepciones
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String[]> validationErrorHandler(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>(e.getErrorCount());
        e.getFieldErrors().forEach(fieldError ->
                errors.add(fieldError.getField() + " : " + fieldError.getDefaultMessage())
        );
        log.info("validationErrorHandler - errors: " + errors);
        return new ResponseEntity<>(errors.toArray(new String[e.getErrorCount()]), HttpStatus.BAD_REQUEST);
    }
}
