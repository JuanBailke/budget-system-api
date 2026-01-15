package br.com.ztechnology.budget_system_api.mappers;

import br.com.ztechnology.budget_system_api.dtos.UsuarioRequestDTO;
import br.com.ztechnology.budget_system_api.dtos.UsuarioResponseDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.Usuario;

public class UsuarioMapper {

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getEmpresa().getId(),
                usuario.getPerfil(),
                usuario.getAtivo()
        );
    }

    public static Usuario toEntity(UsuarioRequestDTO dto, Empresa empresa) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setEmpresa(empresa);
        usuario.setPerfil(dto.perfil());
        usuario.setAtivo(dto.ativo());
        return usuario;
    }
}
