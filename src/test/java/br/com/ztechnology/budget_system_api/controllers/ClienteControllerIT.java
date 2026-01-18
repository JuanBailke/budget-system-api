package br.com.ztechnology.budget_system_api.controllers;

import br.com.ztechnology.budget_system_api.dtos.ClienteDTO;
import br.com.ztechnology.budget_system_api.dtos.TokenDTO;
import br.com.ztechnology.budget_system_api.entities.Cliente;
import br.com.ztechnology.budget_system_api.entities.Empresa;
import br.com.ztechnology.budget_system_api.entities.Usuario;
import br.com.ztechnology.budget_system_api.entities.enums.PerfilUsuario;
import br.com.ztechnology.budget_system_api.entities.enums.StatusEmpresa;
import br.com.ztechnology.budget_system_api.repositories.ClienteRepository;
import br.com.ztechnology.budget_system_api.repositories.EmpresaRepository;
import br.com.ztechnology.budget_system_api.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ClienteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminToken;
    private Empresa empresa;

    @BeforeEach
    void setUp() throws Exception {
        // Limpa os repositórios para garantir a independência dos testes
        clienteRepository.deleteAll();
        usuarioRepository.deleteAll();
        empresaRepository.deleteAll();

        // Cria uma empresa padrão
        this.empresa = new Empresa();
        this.empresa.setNomeFantasia("Empresa Teste");
        this.empresa.setRazaoSocial("Razão Social Teste");
        this.empresa.setCnpj("12345678901234");
        this.empresa.setDataCadastro(LocalDateTime.now());
        this.empresa.setStatus(StatusEmpresa.ATIVA);
        empresaRepository.save(this.empresa);

        // Cria um usuário administrador para obter o token de autenticação
        Usuario admin = new Usuario();
        admin.setNome("Admin User");
        admin.setEmail("admin@test.com");
        admin.setSenha(passwordEncoder.encode("password"));
        admin.setEmpresa(this.empresa);
        admin.setPerfil(PerfilUsuario.ADMIN);
        admin.setAtivo(true);
        usuarioRepository.save(admin);

        // Obtém o token de autenticação
        this.adminToken = getAuthToken("admin@test.com", "password");

        // Cria dados de teste (clientes)
        clienteRepository.save(createCliente("Cliente C", "33333333333", "c@test.com", this.empresa));
        clienteRepository.save(createCliente("Cliente A", "11111111111", "a@test.com", this.empresa));
        clienteRepository.save(createCliente("Cliente B", "22222222222", "b@test.com", this.empresa));
    }

    private Cliente createCliente(String nome, String cpf, String email, Empresa empresa) {
        Cliente cliente = new Cliente();
        cliente.setNomeOuRazaoSocial(nome);
        cliente.setCpfOuCnpj(cpf);
        cliente.setEmail(email);
        cliente.setEmpresa(empresa);
        return cliente;
    }

    private String getAuthToken(String email, String password) throws Exception {
        String loginJson = String.format("{\"email\":\"%s\",\"senha\":\"%s\"}", email, password);

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        TokenDTO tokenDTO = objectMapper.readValue(responseString, TokenDTO.class);
        return tokenDTO.token();
    }

    @Test
    void listarClientes_comPaginacao_deveRetornarPaginaDeClientesOrdenada() throws Exception {
        mockMvc.perform(get("/api/clientes")
                        .header("Authorization", "Bearer " + adminToken)
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "nomeOuRazaoSocial,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].nomeOuRazaoSocial").value("Cliente A"))
                .andExpect(jsonPath("$.content[1].nomeOuRazaoSocial").value("Cliente B"));
    }

    @Test
    void criarCliente_comDadosValidos_deveRetornarClienteCriado() throws Exception {
        // Assumindo que ClienteDTO é um record ou classe com construtor/setters correspondentes.
        // O ID da empresa é pego da instância criada no método setUp.
        ClienteDTO novoClienteDTO = new ClienteDTO(
                null, // ID é nulo para criação
                "Novo Cliente de Teste",
                "98765432109", // CPF/CNPJ único
                "novo.cliente@teste.com",
                "11987654321",
                "Rua dos Testes, 123",
                this.empresa.getId()
        );

        String novoClienteJson = objectMapper.writeValueAsString(novoClienteDTO);

        mockMvc.perform(post("/api/clientes")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(novoClienteJson))
                .andExpect(status().isCreated()) // Espera 201 Created
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nomeOuRazaoSocial").value("Novo Cliente de Teste"))
                .andExpect(jsonPath("$.cpfOuCnpj").value("98765432109"));
    }

    @Test
    void criarCliente_comDadosInvalidos_deveRetornarBadRequest() throws Exception {
        // DTO com nome nulo, que deve ser inválido (assumindo anotação @NotBlank no DTO)
        String jsonInvalido = String.format(
            "{\"nomeOuRazaoSocial\":null, \"cpfOuCnpj\":\"12345678901\", \"email\":\"email@invalido.com\", \"empresaId\": %d}",
            this.empresa.getId()
        );

        mockMvc.perform(post("/api/clientes")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest()) // Espera 400 Bad Request
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    void desativarUsuario_comUsuarioAdmin_deveRetornarStatusOkEUsuarioInativo() throws Exception {
        // Arrange: Cria um usuário comum para ser desativado
        Usuario usuarioComum = new Usuario();
        usuarioComum.setNome("Common User");
        usuarioComum.setEmail("common@test.com");
        usuarioComum.setSenha(passwordEncoder.encode("password"));
        usuarioComum.setEmpresa(this.empresa);
        usuarioComum.setPerfil(PerfilUsuario.USER);
        usuarioComum.setAtivo(true);
        Usuario usuarioSalvo = usuarioRepository.save(usuarioComum);

        // Act & Assert: Admin desativa o usuário comum
        mockMvc.perform(patch("/api/usuarios/{id}/desativar", usuarioSalvo.getId())
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioSalvo.getId()))
                .andExpect(jsonPath("$.ativo").value(false));
    }

    @Test
    void desativarUsuario_comUsuarioNaoAdmin_deveRetornarForbidden() throws Exception {
        // Arrange
        // Cria um usuário que será o alvo da desativação
        Usuario usuarioParaDesativar = new Usuario();
        usuarioParaDesativar.setNome("User To Deactivate");
        usuarioParaDesativar.setEmail("deactivate@test.com");
        usuarioParaDesativar.setSenha(passwordEncoder.encode("password"));
        usuarioParaDesativar.setEmpresa(this.empresa);
        usuarioParaDesativar.setPerfil(PerfilUsuario.USER);
        usuarioParaDesativar.setAtivo(true);
        Usuario usuarioSalvo = usuarioRepository.save(usuarioParaDesativar);

        // Cria um outro usuário comum (não admin) que tentará fazer a ação
        Usuario usuarioComum = new Usuario();
        usuarioComum.setNome("Common User Action");
        usuarioComum.setEmail("common-action@test.com");
        usuarioComum.setSenha(passwordEncoder.encode("password123"));
        usuarioComum.setEmpresa(this.empresa);
        usuarioComum.setPerfil(PerfilUsuario.USER);
        usuarioComum.setAtivo(true);
        usuarioRepository.save(usuarioComum);
        String commonUserToken = getAuthToken("common-action@test.com", "password123");

        // Act & Assert
        mockMvc.perform(patch("/api/usuarios/{id}/desativar", usuarioSalvo.getId())
                        .header("Authorization", "Bearer " + commonUserToken))
                .andExpect(status().isForbidden());
    }
}
