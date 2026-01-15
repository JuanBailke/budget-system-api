package br.com.ztechnology.budget_system_api.mappers;

import br.com.ztechnology.budget_system_api.dtos.ClienteDTO;
import br.com.ztechnology.budget_system_api.entities.Cliente;
import br.com.ztechnology.budget_system_api.entities.Empresa;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNomeOuRazaoSocial(),
                cliente.getCpfOuCnpj(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getEndereco(),
                cliente.getEmpresa().getId()
        );
    }

    public static Cliente toEntity(ClienteDTO dto, Empresa empresa) {
        if (dto == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(dto.id());
        cliente.setNomeOuRazaoSocial(dto.nomeOuRazaoSocial());
        cliente.setCpfOuCnpj(dto.cpfOuCnpj());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(dto.endereco());
        cliente.setEmpresa(empresa);
        return cliente;
    }
}
