package br.com.ztechnology.budget_system_api.mappers;

import br.com.ztechnology.budget_system_api.dtos.ProdutoServicoDTO;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.ProdutoServico;

public class ProdutoServicoMapper {

    public static ProdutoServicoDTO toDTO(ProdutoServico produtoServico) {
        if (produtoServico == null) {
            return null;
        }
        return new ProdutoServicoDTO(
                produtoServico.getId(),
                produtoServico.getNome(),
                produtoServico.getDescricao(),
                produtoServico.getValorPadrao(),
                produtoServico.getTipo(),
                produtoServico.getEmpresa().getId()
        );
    }

    public static ProdutoServico toEntity(ProdutoServicoDTO dto, Empresa empresa) {
        if (dto == null) {
            return null;
        }
        ProdutoServico produtoServico = new ProdutoServico();
        produtoServico.setId(dto.id());
        produtoServico.setNome(dto.nome());
        produtoServico.setDescricao(dto.descricao());
        produtoServico.setValorPadrao(dto.valorPadrao());
        produtoServico.setTipo(dto.tipo());
        produtoServico.setEmpresa(empresa);
        return produtoServico;
    }
}
