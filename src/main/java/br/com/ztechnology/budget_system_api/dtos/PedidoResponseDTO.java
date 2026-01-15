package br.com.ztechnology.budget_system_api.dtos;

import br.com.ztechnology.budget_system_api.entities.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        Long clienteId,
        Long usuarioId,
        LocalDateTime dataCriacao,
        BigDecimal valorTotal,
        StatusPedido status,
        List<ItemPedidoResponseDTO> itens
) {
}
