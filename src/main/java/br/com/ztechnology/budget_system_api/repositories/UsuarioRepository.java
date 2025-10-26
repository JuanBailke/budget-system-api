package br.com.ztechnology.budget_system_api.repositories;

import br.com.ztechnology.budget_system_api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
