package dev.coms4156.project.clientservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClientService {

    private final WebClient webClient;

    public ClientService(@Value("${backend.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public String createDonation(String resourceId, String itemType, int quantity, String expirationDate, String donorId) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/createDonation")
                        .queryParam("resourceId", resourceId)
                        .queryParam("itemType", itemType)
                        .queryParam("quantity", quantity)
                        .queryParam("expirationDate", expirationDate)
                        .queryParam("donorId", donorId)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String retrieveResource(String resourceId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/retrieveResource")
                        .queryParam("resourceId", resourceId)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
