package com.devsuperior.dscommerce.controllers.handlers;

import com.devsuperior.dscommerce.dto.CustomError;
import com.devsuperior.dscommerce.dto.ValidationError;
import com.devsuperior.dscommerce.services.exceptions.DatabaseException;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(
        ResourceNotFoundException e,
        HttpServletRequest request
    ){
        var status = HttpStatus.NOT_FOUND;
        var err = new CustomError(
            Instant.now(), status.value(), e.getMessage(), request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> database(
        DatabaseException e,
        HttpServletRequest request
    ){
        var status = HttpStatus.BAD_REQUEST;
        var err = new CustomError(
            Instant.now(), status.value(), e.getMessage(), request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidation(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ){
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var err = new ValidationError(
            Instant.now(), status.value(), "Dados inválidos", request.getRequestURI()
        );
        for(FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

}

/*
    A expressão e.getBindingResult().getFieldErrors() retorna uma
    lista de objetos FieldError. A cada iteração do loop, um desses
    objetos é atribuído à variável f. O loop percorre a lista, e em cada
    iteração, f representa um objeto FieldError diferente da lista.

    Portanto, sim, cada item do retorno da lista e.getBindingResult().getFieldErrors()
    é atribuído a f durante cada passagem do loop. Você pode, então, acessar as
    propriedades e métodos do objeto FieldError através da variável f dentro do
    bloco do loop.
*/