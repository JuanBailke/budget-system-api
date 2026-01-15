package br.com.ztechnology.budget_system_api.dtos;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        String descricao,
        BigDecimal quantidade,
        BigDecimal precoUnitario
) {
}
