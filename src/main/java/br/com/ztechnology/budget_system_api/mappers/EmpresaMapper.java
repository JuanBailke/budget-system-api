package br.com.ztechnology.budget_system_api.mappers;

import br.com.ztechnology.budget_system_api.dtos.EmpresaDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;

public class EmpresaMapper {

    public static EmpresaDTO toDTO(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        return new EmpresaDTO(
                empresa.getId(),
                empresa.getNomeFantasia(),
                empresa.getCnpj(),
                empresa.getEndereco(),
                empresa.getStatus()
        );
    }

    public static Empresa toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(dto.id());
        empresa.setNomeFantasia(dto.nome());
        empresa.setCnpj(dto.cnpj());
        empresa.setEndereco(dto.endereco());
        empresa.setStatus(dto.status());
        return empresa;
    }
}
