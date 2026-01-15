package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.ItemPedidoRequestDTO;
import br.com.ztechnology.budget_system_api.dtos.ItemPedidoResponseDTO;
import br.com.ztechnology.budget_system_api.entities.ItemPedido;
import br.com.ztechnology.budget_system_api.mappers.ItemPedidoMapper;
import br.com.ztechnology.budget_system_api.repositories.ItemPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoResponseDTO atualizarItemPedido(Long id, ItemPedidoRequestDTO dto) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item de pedido não encontrado com id: " + id));
        itemPedido.setDescricao(dto.descricao());
        itemPedido.setQuantidade(dto.quantidade());
        itemPedido.setPrecoUnitario(dto.precoUnitario());
        return ItemPedidoMapper.toResponseDTO(itemPedidoRepository.save(itemPedido));
    }

    public Optional<ItemPedidoResponseDTO> buscarItemPedidoPorId(Long id) {
        return itemPedidoRepository.findById(id).map(ItemPedidoMapper::toResponseDTO);
    }

    public List<ItemPedidoResponseDTO> listarItensPedido() {
        return itemPedidoRepository.findAll().stream().map(ItemPedidoMapper::toResponseDTO).collect(Collectors.toList());
    }

    public void deletarItemPedido(Long id) {
        itemPedidoRepository.deleteById(id);
    }
}
