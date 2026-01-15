package br.com.ztechnology.budget_system_api.repositories;

import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.enums.StatusEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Page<Empresa> findByStatus(StatusEmpresa status, Pageable pageable);
}
