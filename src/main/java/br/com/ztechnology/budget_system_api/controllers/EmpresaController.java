package br.com.ztechnology.budget_system_api.controllers;

import br.com.ztechnology.budget_system_api.dtos.EmpresaDTO;
import br.com.ztechnology.budget_system_api.services.EmpresaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/empresas")
@AllArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaDTO> salvarEmpresa(@Valid @RequestBody EmpresaDTO dto) {
        EmpresaDTO empresaSalva = empresaService.salvarEmpresa(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(empresaSalva.id()).toUri();
        return ResponseEntity.created(uri).body(empresaSalva);
    }

    @GetMapping
    public ResponseEntity<Page<EmpresaDTO>> listarEmpresas(Pageable pageable) {
        return ResponseEntity.ok(empresaService.listarEmpresas(pageable));
    }

    @GetMapping("/ativas")
    public ResponseEntity<Page<EmpresaDTO>> listarEmpresasAtivas(Pageable pageable) {
        return ResponseEntity.ok(empresaService.listarEmpresasAtivas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> buscarEmpresaPorId(@PathVariable Long id) {
        return empresaService.buscarEmpresaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> atualizarEmpresa(@PathVariable Long id, @Valid @RequestBody EmpresaDTO dto) {
        EmpresaDTO empresaAtualizada = empresaService.atualizarEmpresa(id, dto);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}
