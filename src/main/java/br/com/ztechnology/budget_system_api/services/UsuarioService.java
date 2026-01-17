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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        Usuario usuario = UsuarioMapper.toEntity(dto, empresa);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return UsuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        Usuario usuario = UsuarioMapper.toEntity(dto, empresa);
        usuario.setId(id);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return UsuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponseDTO> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).map(UsuarioMapper::toResponseDTO);
    }

    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(UsuarioMapper::toResponseDTO);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public UsuarioResponseDTO desativarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
        usuario.setAtivo(false);
        return UsuarioMapper.toResponseDTO(usuarioRepository.save(usuario));
    }
}
