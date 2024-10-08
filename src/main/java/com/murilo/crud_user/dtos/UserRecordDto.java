package com.murilo.crud_user.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(
    @NotBlank String login, 
    @NotBlank String senha, 
    @NotBlank String email
) {}