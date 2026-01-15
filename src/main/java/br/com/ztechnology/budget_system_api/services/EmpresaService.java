package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.EmpresaDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.enums.StatusEmpresa;
import br.com.ztechnology.budget_system_api.mappers.EmpresaMapper;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaDTO salvarEmpresa(EmpresaDTO dto){
        Empresa empresa = EmpresaMapper.toEntity(dto);
        return EmpresaMapper.toDTO(empresaRepository.save(empresa));
    }

    public EmpresaDTO atualizarEmpresa(EmpresaDTO dto) {
        Empresa empresa = EmpresaMapper.toEntity(dto);
        return EmpresaMapper.toDTO(empresaRepository.save(empresa));
    }

    public Optional<EmpresaDTO> buscarEmpresaPorId(Long id) {
        return empresaRepository.findById(id).map(EmpresaMapper::toDTO);
    }

    public List<EmpresaDTO> listarEmpresas(){
        return empresaRepository.findAll().stream().map(EmpresaMapper::toDTO).collect(Collectors.toList());
    }

    public List<EmpresaDTO> listarEmpresasAtivas(){
        return empresaRepository.findByStatus(StatusEmpresa.ATIVA).stream().map(EmpresaMapper::toDTO).collect(Collectors.toList());
    }

    public void deletarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }
}
