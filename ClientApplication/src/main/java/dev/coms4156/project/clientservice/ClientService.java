package dev.coms4156.project.clientservice;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

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

    public String createRequest(List<String> itemIds, List<Integer> itemQuantities, String status, String priorityLevel, String requesterInfo, String resourceId) {
        String requestId = generateRequestId();
        String url = GlobalInfo.BASE_URL + GlobalInfo.CREATE_REQUEST;

        // Construct the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("requestId", requestId);
        requestBody.put("itemIds", itemIds);
        requestBody.put("itemQuantities", itemQuantities);
        requestBody.put("status", status);
        requestBody.put("priorityLevel", priorityLevel);
        requestBody.put("requesterInfo", requesterInfo);
        requestBody.put("resourceId", resourceId);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HTTP entity with headers and body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        try {
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(url, entity, String.class);
        } catch (Exception e) {
            // Handle exceptions (e.g., network errors, bad responses)
            System.err.println("Error creating request: " + e.getMessage());
            return null;
        }
    }

    public String retrieveDispatchedItems(String resourceId) {
        String url = GlobalInfo.BASE_URL + GlobalInfo.RETRIEVE_DISPATCHED_ITEMS + "?resourceId=" + resourceId;
        return restTemplate.getForObject(url, String.class);
    }

    public String retrieveItem(String resourceId, String itemId) {
        String url = GlobalInfo.BASE_URL + "/retrieveItem?resourceId=" + resourceId + "&itemId=" + itemId;
        return restTemplate.getForObject(url, String.class);
    }

    private String generateRequestId() {
        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().substring(0, 8); // Shortened UUID
        return "REQ-" + timestamp + "-" + uuid;
    }

//    private String determinePriorityLevelByType(List<String> itemIds) {
//        String overallPriority = "Low"; // Default priority
//
//        for (String itemId : itemIds) {
//            String itemType = getItemType(itemId); // Mock function to get the item type
//            String priorityForType = PRIORITY_MAP.getOrDefault(itemType, "Low");
//
//            // Upgrade overall priority if needed
//            if (priorityForType.equals("High")) {
//                overallPriority = "High";
//            } else if (priorityForType.equals("Medium") && !overallPriority.equals("High")) {
//                overallPriority = "Medium";
//            }
//        }
//
//        return overallPriority;
//    }
}
