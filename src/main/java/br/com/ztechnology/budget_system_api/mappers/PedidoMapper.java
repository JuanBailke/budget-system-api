package br.com.ztechnology.budget_system_api.mappers;

import br.com.ztechnology.budget_system_api.dtos.PedidoRequestDTO;
import br.com.ztechnology.budget_system_api.dtos.PedidoResponseDTO;
import br.com.ztechnology.budget_system_api.entities.Cliente;
import br.com.ztechnology.budget_system_api.entities.Pedido;
import br.com.ztechnology.budget_system_api.entities.Usuario;

import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponseDTO toResponseDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        return new PedidoResponseDTO(
            pedido.getId(),
            pedido.getCliente().getId(),
            pedido.getUsuario().getId(),
            pedido.getDataCriacao(),
            pedido.getValorTotal(),
            pedido.getStatus(),
            pedido.getItens().stream().map(ItemPedidoMapper::toResponseDTO).collect(Collectors.toList())
        );
    }

    public static Pedido toEntity(PedidoRequestDTO dto, Cliente cliente, Usuario usuario) {
        if (dto == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setUsuario(usuario);
        return pedido;
    }
}
