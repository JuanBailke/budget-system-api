package br.com.ztechnology.budget_system_api.controllers;

import br.com.ztechnology.budget_system_api.dtos.ProdutoServicoDTO;
import br.com.ztechnology.budget_system_api.services.ProdutoServicoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/produtos-servicos")
@AllArgsConstructor
public class ProdutoServicoController {

    private final ProdutoServicoService produtoServicoService;

    @PostMapping
    public ResponseEntity<ProdutoServicoDTO> salvarProdutoServico(@Valid @RequestBody ProdutoServicoDTO dto) {
        ProdutoServicoDTO produtoSalvo = produtoServicoService.salvarProdutoServico(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(produtoSalvo.id()).toUri();
        return ResponseEntity.created(uri).body(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoServicoDTO>> listarProdutosServicos(Pageable pageable) {
        return ResponseEntity.ok(produtoServicoService.listarProdutosServicos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoServicoDTO> buscarProdutoServicoPorId(@PathVariable Long id) {
        return produtoServicoService.buscarProdutoServicoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoServicoDTO> atualizarProdutoServico(@PathVariable Long id, @Valid @RequestBody ProdutoServicoDTO dto) {
        ProdutoServicoDTO produtoAtualizado = produtoServicoService.atualizarProdutoServico(id, dto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoServico(@PathVariable Long id) {
        produtoServicoService.deletarProdutoServico(id);
        return ResponseEntity.noContent().build();
    }
}
