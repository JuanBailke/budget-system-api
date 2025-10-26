package br.com.ztechnology.budget_system_api.repositories;

import br.com.ztechnology.budget_system_api.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
