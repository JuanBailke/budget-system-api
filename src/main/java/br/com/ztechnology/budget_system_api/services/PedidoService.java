package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.dtos.PedidoRequestDTO;
import br.com.ztechnology.budget_system_api.dtos.PedidoResponseDTO;
import br.com.ztechnology.budget_system_api.entities.Cliente;
import br.com.ztechnology.budget_system_api.entities.ItemPedido;
import br.com.ztechnology.budget_system_api.entities.Pedido;
import br.com.ztechnology.budget_system_api.entities.Usuario;
import br.com.ztechnology.budget_system_api.entities.enums.StatusPedido;
import br.com.ztechnology.budget_system_api.mappers.ItemPedidoMapper;
import br.com.ztechnology.budget_system_api.mappers.PedidoMapper;
import br.com.ztechnology.budget_system_api.repositories.ClienteRepository;
import br.com.ztechnology.budget_system_api.repositories.PedidoRepository;
import br.com.ztechnology.budget_system_api.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponseDTO salvarPedido(PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com id: " + dto.clienteId()));
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + dto.usuarioId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setUsuario(usuario);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.ORCAMENTO);

        List<ItemPedido> itens = dto.itens().stream()
                .map(ItemPedidoMapper::toEntity)
                .peek(item -> item.setPedido(pedido))
                .collect(Collectors.toList());
        pedido.setItens(itens);

        BigDecimal valorTotal = itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(item.getQuantidade()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setValorTotal(valorTotal);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return PedidoMapper.toResponseDTO(pedidoSalvo);
    }

    public Optional<PedidoResponseDTO> buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).map(PedidoMapper::toResponseDTO);
    }

    public Page<PedidoResponseDTO> listarPedidos(Pageable pageable) {
        return pedidoRepository.findAll(pageable).map(PedidoMapper::toResponseDTO);
    }

    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
