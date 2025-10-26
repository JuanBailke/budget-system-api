package br.com.ztechnology.budget_system_api.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeOuRazaoSocial;

    @Column(nullable = false, unique = true, length = 14)
    private String cpfOuCnpj;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String telefone;

    @Column(nullable = true)
    private String endereco;
}
