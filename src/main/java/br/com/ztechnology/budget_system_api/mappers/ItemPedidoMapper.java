package br.com.ztechnology.budget_system_api.mappers;

import br.com.ztechnology.budget_system_api.dtos.ItemPedidoRequestDTO;
import br.com.ztechnology.budget_system_api.dtos.ItemPedidoResponseDTO;
import br.com.ztechnology.budget_system_api.entities.ItemPedido;

public class ItemPedidoMapper {

    public static ItemPedidoResponseDTO toResponseDTO(ItemPedido itemPedido) {
        if (itemPedido == null) {
            return null;
        }
        return new ItemPedidoResponseDTO(
                itemPedido.getId(),
                itemPedido.getDescricao(),
                itemPedido.getQuantidade(),
                itemPedido.getPrecoUnitario()
        );
    }

    public static ItemPedido toEntity(ItemPedidoRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setDescricao(dto.descricao());
        itemPedido.setQuantidade(dto.quantidade());
        itemPedido.setPrecoUnitario(dto.precoUnitario());
        return itemPedido;
    }
}
