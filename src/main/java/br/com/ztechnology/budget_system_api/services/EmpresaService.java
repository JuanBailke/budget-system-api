package br.com.ztechnology.budget_system_api.services;

import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public Empresa salvarEmpresa(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public List<Empresa> listarEmpresas(){
        return empresaRepository.findAll();
    }

    public List<Empresa> listarEmpresasAtivas(){
        return empresaRepository.findByAtivoTrue();
    }
}
