package org.example.service;

import jakarta.mail.internet.MimeUtility;
import org.assertj.core.api.Assertions;
import org.example.dtos.UserEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Testcontainers
public class KafkaConsumerServiceIntegrationTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("apache/kafka"));

    @Container
    static GenericContainer<?> mailhog = new GenericContainer<>("mailhog/mailhog:latest")
            .withExposedPorts(1025, 8025);

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.mail.host", mailhog::getHost);
        registry.add("spring.mail.port", () -> mailhog.getMappedPort(1025));
        registry.add("spring.mail.properties.mail.smtp.ssl.enable", () -> false);
    }

    @Autowired
    KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Test
    @DisplayName("При создании пользователя отправляется письмо с темой 'Аккаунт создан'")
    void shouldSendCreateEmailWhenUserCreated() {
        UserEvent userEvent = new UserEvent("CREATE", "leshka.kovalev.02@gmail.com", "Alexey");
        sendTestEmail(userEvent);
    }

    @Test
    @DisplayName("При удалении пользователя отправляется письмо с темой 'Аккаунт удалён'")
    void shouldSendDeleteEmailWhenUserDeleted() {
        UserEvent userEvent = new UserEvent("DELETE", "leshka.kovalev.02@gmail.com", "Alexey");
        sendTestEmail(userEvent);
    }

    void sendTestEmail(UserEvent userEvent){
        kafkaTemplate.send("events", userEvent);
        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            HttpResponse<String> response = HttpClient.newHttpClient().send(HttpRequest.newBuilder()
                    .uri(URI.create("http://" + mailhog.getHost() + ":" + mailhog.getMappedPort(8025) + "/api/v2/messages"))
                    .build(), HttpResponse.BodyHandlers.ofString());
            JsonNode json = new ObjectMapper().readTree(response.body());
            JsonNode items = json.get("items");
            Assertions.assertThat(items).isNotEmpty();
            String subjectEncoded = items.get(0).get("Content").get("Headers").get("Subject").get(0).asText();
            String subject = MimeUtility.decodeText(subjectEncoded);
            if (userEvent.operation().equalsIgnoreCase("CREATE")){
                Assertions.assertThat(subject).isEqualTo("Аккаунт создан");
            } else if (userEvent.operation().equalsIgnoreCase("DELETE")) {
                Assertions.assertThat(subject).isEqualTo("Аккаунт удалён");
            }

        });
    }
}
