package br.com.ztechnology.budget_system_api.repositories;

import br.com.ztechnology.budget_system_api.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
