package dev.coms4156.project.clientservice;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ClientService {
    // private final RestTemplate restTemplate = new RestTemplate();
    private final RestTemplate restTemplate;

    // Default constructor for production
    public ClientService() {
        this.restTemplate = new RestTemplate();
    }

    // Constructor for testing
    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createDonation(String resourceId, String itemType, int quantity, String expirationDate, String donorId) {
        String url = GlobalInfo.BASE_URL + GlobalInfo.CREATE_DONATION
            + "?resourceId=" + resourceId
            + "&itemType=" + itemType
            + "&quantity=" + quantity
            + "&expirationDate=" + expirationDate
            + "&donorId=" + donorId;
        System.out.println(url);
        return restTemplate.postForObject(url, null, String.class);
    }

    public String retrieveAvailableItems(String resourceId) {
        String url = GlobalInfo.BASE_URL + GlobalInfo.RETRIEVE_AVAILABLE_ITEMS + "?resourceId=" + resourceId;
        return restTemplate.getForObject(url, String.class);
    }

    public String createRequest(String requestId, String itemIds, String status, String priorityLevel, String requesterInfo) {
        String url = GlobalInfo.BASE_URL + GlobalInfo.CREATE_REQUEST
            + "?requestId=" + requestId
            + "&itemIds=" + itemIds
            + "&status=" + status
            + "&priorityLevel=" + priorityLevel
            + "&requesterInfo=" + requesterInfo;
        return restTemplate.postForObject(url, null, String.class);
    }

    public String retrieveDispatchedItems(String resourceId) {
        String url = GlobalInfo.BASE_URL + GlobalInfo.RETRIEVE_DISPATCHED_ITEMS + "?resourceId=" + resourceId;
        return restTemplate.getForObject(url, String.class);
    }

    public String retrieveItem(String resourceId, String itemId) {
        String url = GlobalInfo.BASE_URL + "/retrieveItem?resourceId=" + resourceId + "&itemId=" + itemId;
        return restTemplate.getForObject(url, String.class);
    }
}
