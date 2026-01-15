package br.com.ztechnology.budget_system_api.dtos;

import br.com.ztechnology.budget_system_api.entities.enums.TipoProduto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoServicoDTO(
        Long id,
        @NotBlank String nome,
        String descricao,
        @NotNull @DecimalMin("0.01") BigDecimal valorPadrao,
        @NotNull TipoProduto tipo,
        @NotNull Long empresaId
) {
}
