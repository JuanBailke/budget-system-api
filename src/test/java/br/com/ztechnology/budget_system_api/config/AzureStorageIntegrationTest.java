package br.com.ztechnology.budget_system_api.config;

import com.azure.storage.blob.BlobServiceClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Teste de integração para verificar a conexão com o Azurite.
 * Este teste requer que o contêiner do Azurite esteja em execução.
 */
@SpringBootTest
@ActiveProfiles("dev")
public class AzureStorageIntegrationTest {

    @Autowired
    private BlobServiceClient blobServiceClient;

    private final String containerName = "test-container-" + System.currentTimeMillis();

    @AfterEach
    void tearDown() {
        // Limpa o container após o teste para não deixar lixo
        blobServiceClient.getBlobContainerClient(containerName).deleteIfExists();
    }

    @Test
    void shouldConnectToAzuriteAndCreateBlobContainer() {
        // Ação: Tenta criar um container de blob
        blobServiceClient.createBlobContainerIfNotExists(containerName);

        // Verificação: Confirma que o container agora existe no Azurite
        assertTrue(blobServiceClient.getBlobContainerClient(containerName).exists(),
                "O container de blob deveria ter sido criado no Azurite.");
    }
}