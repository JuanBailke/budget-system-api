package br.com.ztechnology.budget_system_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
        Long id,
        @NotBlank String nomeOuRazaoSocial,
        @NotBlank @Size(min = 11, max = 14) String cpfOuCnpj,
        @NotBlank @Email String email,
        String telefone,
        String endereco,
        @NotNull Long empresaId
) {
}
