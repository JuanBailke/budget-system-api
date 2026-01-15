package br.com.ztechnology.budget_system_api.dtos;

import br.com.ztechnology.budget_system_api.entities.enums.PerfilUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Long empresaId,
        PerfilUsuario perfil,
        Boolean ativo
) {
}
