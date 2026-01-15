package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.ProdutoServicoDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.ProdutoServico;
import br.com.ztechnology.budget_system_api.mappers.ProdutoServicoMapper;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import br.com.ztechnology.budget_system_api.repositories.ProdutoServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProdutoServicoService {

    private final ProdutoServicoRepository produtoServicoRepository;
    private final EmpresaRepository empresaRepository;

    public ProdutoServicoDTO salvarProdutoServico(ProdutoServicoDTO dto) {
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        ProdutoServico produtoServico = ProdutoServicoMapper.toEntity(dto, empresa);
        return ProdutoServicoMapper.toDTO(produtoServicoRepository.save(produtoServico));
    }

    public ProdutoServicoDTO atualizarProdutoServico(Long id, ProdutoServicoDTO dto) {
        produtoServicoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto/Serviço não encontrado com id: " + id));
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        ProdutoServico produtoServico = ProdutoServicoMapper.toEntity(dto, empresa);
        produtoServico.setId(id);
        return ProdutoServicoMapper.toDTO(produtoServicoRepository.save(produtoServico));
    }

    public Optional<ProdutoServicoDTO> buscarProdutoServicoPorId(Long id) {
        return produtoServicoRepository.findById(id).map(ProdutoServicoMapper::toDTO);
    }

    public List<ProdutoServicoDTO> listarProdutosServicos() {
        return produtoServicoRepository.findAll().stream().map(ProdutoServicoMapper::toDTO).collect(Collectors.toList());
    }

    public void deletarProdutoServico(Long id) {
        produtoServicoRepository.deleteById(id);
    }
}
