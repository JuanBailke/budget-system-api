package br.com.ztechnology.budget_system_api.model;

import br.com.ztechnology.budget_system_api.model.enums.StatusEmpresa;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_empresas")
public class Empresa{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = true)
    private String logoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmpresa status;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

}
