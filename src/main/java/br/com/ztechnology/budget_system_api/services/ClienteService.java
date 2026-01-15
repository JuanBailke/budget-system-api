package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.ClienteDTO;
import br.com.ztechnology.budget_system_api.entities.Cliente;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.mappers.ClienteMapper;
import br.com.ztechnology.budget_system_api.repositories.ClienteRepository;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public ClienteDTO salvarCliente(ClienteDTO dto){
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        Cliente cliente = ClienteMapper.toEntity(dto, empresa);
        return ClienteMapper.toDTO(clienteRepository.save(cliente));
    }

    public ClienteDTO atualizarCliente(Long id, ClienteDTO dto) {
        clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id: " + id));
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com id: " + dto.empresaId()));
        Cliente cliente = ClienteMapper.toEntity(dto, empresa);
        cliente.setId(id);
        return ClienteMapper.toDTO(clienteRepository.save(cliente));
    }

    public Optional<ClienteDTO> buscarClientePorId(Long id) {
        return clienteRepository.findById(id).map(ClienteMapper::toDTO);
    }

    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(ClienteMapper::toDTO);
    }

    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
