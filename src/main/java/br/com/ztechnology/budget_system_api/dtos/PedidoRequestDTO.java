package br.com.ztechnology.budget_system_api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull Long clienteId,
        @NotNull Long usuarioId,
        @NotEmpty @Valid List<ItemPedidoRequestDTO> itens
) {
}
