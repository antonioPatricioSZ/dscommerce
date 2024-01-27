package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.UserRole;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record RegisterDto (
    String name,
    String email,
    String phone,
    LocalDate birthDate,
    String password,
    UserRole role
){

}
