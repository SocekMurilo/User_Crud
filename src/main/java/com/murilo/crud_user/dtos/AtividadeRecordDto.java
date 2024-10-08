package com.murilo.crud_user.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskRecordDto(
    @NotBlank String tarefa,
    @NotBlank String descricao,
    @NotBlank String status,
    @NotBlank String prioridade
) {}