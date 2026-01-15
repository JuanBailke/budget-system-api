package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.EmpresaDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.enums.StatusEmpresa;
import br.com.ztechnology.budget_system_api.mappers.EmpresaMapper;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaDTO salvarEmpresa(EmpresaDTO dto){
        Empresa empresa = EmpresaMapper.toEntity(dto);
        return EmpresaMapper.toDTO(empresaRepository.save(empresa));
    }

    public EmpresaDTO atualizarEmpresa(Long id, EmpresaDTO dto) {
        empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + id));
        Empresa empresa = EmpresaMapper.toEntity(dto);
        empresa.setId(id);
        return EmpresaMapper.toDTO(empresaRepository.save(empresa));
    }

    public Optional<EmpresaDTO> buscarEmpresaPorId(Long id) {
        return empresaRepository.findById(id).map(EmpresaMapper::toDTO);
    }

    public Page<EmpresaDTO> listarEmpresas(Pageable pageable){
        return empresaRepository.findAll(pageable).map(EmpresaMapper::toDTO);
    }

    public Page<EmpresaDTO> listarEmpresasAtivas(Pageable pageable){
        return empresaRepository.findByStatus(StatusEmpresa.ATIVA, pageable).map(EmpresaMapper::toDTO);
    }

    public void deletarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
}
