package dev.coms4156.project.clientservice;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServiceTest {

  @Mock
  private RestTemplate restTemplate;

  private ClientService clientService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    clientService = new ClientService(restTemplate);
  }

  @Test
  public void testCreateDonation() {
    String url = GlobalInfo.BASE_URL + GlobalInfo.CREATE_DONATION
        + "?resourceId=1&itemType=food&quantity=10&expirationDate=2024-12-31&donorId=donor123";
    String expectedResponse = "Donation Created";

    // Mocking the RestTemplate response
    when(restTemplate.postForObject(eq(url), any(), eq(String.class))).thenReturn(expectedResponse);
    System.out.println(expectedResponse);
    String result = clientService.createDonation("1", "food", 10, "2024-12-31", "donor123");
    System.out.println(result);
    assertEquals(expectedResponse, result);
  }

  @Test
  public void testRetrieveAvailableItems() {
    String url = GlobalInfo.BASE_URL + GlobalInfo.RETRIEVE_AVAILABLE_ITEMS + "?resourceId=1";
    String expectedResponse = "Available Items List";

    // Mocking the RestTemplate response
    when(restTemplate.getForObject(eq(url), eq(String.class))).thenReturn(expectedResponse);

    String result = clientService.retrieveAvailableItems("1");

    assertEquals(expectedResponse, result);
  }

  @Test
  public void testCreateRequest() {
    List<String> itemIds = Arrays.asList("item1", "item2");
    List<Integer> itemQuantities = Arrays.asList(1, 2);
    String status = "Pending";
    String priorityLevel = "high";
    String requesterInfo = "requester123";
    String resourceId = "R_TEST";

    String expectedResponse = "Request Created";

    // Mock the RestTemplate response
    when(restTemplate.postForObject(any(String.class), any(), eq(String.class))).thenReturn(expectedResponse);

    String result = clientService.createRequest(itemIds, itemQuantities, status, priorityLevel, requesterInfo, resourceId);

    assertEquals(expectedResponse, result);
  }

  @Test
  public void testRetrieveDispatchedItems() {
    String url = GlobalInfo.BASE_URL + GlobalInfo.RETRIEVE_DISPATCHED_ITEMS + "?resourceId=1";
    String expectedResponse = "Dispatched Items List";

    // Mocking the RestTemplate response
    when(restTemplate.getForObject(eq(url), eq(String.class))).thenReturn(expectedResponse);

    String result = clientService.retrieveDispatchedItems("1");

    assertEquals(expectedResponse, result);
  }

  @Test
  public void testRetrieveItem() {
    String url = GlobalInfo.BASE_URL + "/retrieveItem?resourceId=1&itemId=item1";
    String expectedResponse = "Item Retrieved";

    // Mocking the RestTemplate response
    when(restTemplate.getForObject(eq(url), eq(String.class))).thenReturn(expectedResponse);

    String result = clientService.retrieveItem("1", "item1");

    assertEquals(expectedResponse, result);
  }
}



