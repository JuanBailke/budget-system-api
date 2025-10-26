package br.com.ztechnology.budget_system_api.repositories;

import br.com.ztechnology.budget_system_api.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findByAtivoTrue();
}
