package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.UsuarioRequestDTO;
import br.com.ztechnology.budget_system_api.dtos.UsuarioResponseDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.Usuario;
import br.com.ztechnology.budget_system_api.mappers.UsuarioMapper;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import br.com.ztechnology.budget_system_api.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        Usuario usuario = UsuarioMapper.toEntity(dto, empresa);
        return UsuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        Usuario usuario = UsuarioMapper.toEntity(dto, empresa);
        usuario.setId(id);
        return UsuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponseDTO> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper::toResponseDTO);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(UsuarioMapper::toResponseDTO).collect(Collectors.toList());
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
