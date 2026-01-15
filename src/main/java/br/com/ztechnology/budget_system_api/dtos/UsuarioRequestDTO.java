package br.com.ztechnology.budget_system_api.dtos;

import br.com.ztechnology.budget_system_api.entities.enums.PerfilUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String senha,
        @NotNull Long empresaId,
        @NotNull PerfilUsuario perfil,
        @NotNull Boolean ativo
) {
}
