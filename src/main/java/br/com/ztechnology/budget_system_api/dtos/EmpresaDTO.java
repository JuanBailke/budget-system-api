package br.com.ztechnology.budget_system_api.dtos;

import br.com.ztechnology.budget_system_api.entities.enums.StatusEmpresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmpresaDTO(
        Long id,
        @NotBlank String nome,
        @NotBlank @Size(min = 14, max = 14) String cnpj,
        String endereco,
        @NotNull StatusEmpresa status
) {
}
