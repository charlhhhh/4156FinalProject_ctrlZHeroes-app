package dev.coms4156.project.clientservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClientService clientService;

  @InjectMocks
  private ClientController clientController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // --- Unit Tests for ClientController Methods ---
  @Test
  public void testHomePage() {
    String viewName = clientController.home();
    assertEquals("index", viewName);
  }

  @Test
  public void testCreateDonationPage() {
    String viewName = clientController.createDonationPage();
    assertEquals("create_donation.html", viewName);
  }

  @Test
  public void testRetrieveItemPage() {
    String viewName = clientController.retrieveItemPage();
    assertEquals("retrieve_item.html", viewName);
  }

  @Test
  public void testRetrieveAvailableItemsPage() {
    String viewName = clientController.retrieveAvailableItemsPage();
    assertEquals("retrieve_available_items.html", viewName);
  }

  @Test
  public void testCreateRequestPage() {
    String viewName = clientController.createRequestPage();
    assertEquals("create_request.html", viewName);
  }

  @Test
  public void testRetrieveDispatchedItemsPage() {
    String viewName = clientController.retrieveDispatchedItemsPage();
    assertEquals("retrieve_dispatched_items.html", viewName);
  }

  @Test
  public void testRetrieveItem() {
    when(clientService.retrieveItem(anyString(), anyString())).thenReturn("Item Retrieved");

    String response = clientController.retrieveItem("1", "item1");
    assertEquals("Item Retrieved", response);
  }

  @Test
  public void testRetrieveAvailableItems() {
    when(clientService.retrieveAvailableItems(anyString())).thenReturn("Available Items");

    String response = clientController.retrieveAvailableItems("1");
    assertEquals("Available Items", response);
  }

  @Test
  public void testCreateRequest() {
    when(clientService.createRequest(anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn("Request Created");

    String response = clientController.createRequest("req1", "item1,item2", "open", "high", "requester123");
    assertEquals("Request Created", response);
  }

  @Test
  public void testRetrieveDispatchedItems() {
    when(clientService.retrieveDispatchedItems(anyString())).thenReturn("Dispatched Items");

    String response = clientController.retrieveDispatchedItems("1");
    assertEquals("Dispatched Items", response);
  }

  // --- API Tests for ClientController Endpoints ---
  @Test
  public void testCreateDonationEndpoint() throws Exception {
    when(clientService.createDonation("1", "food", 10, "2024-12-31", "donor123"))
        .thenReturn("Donation Created");

    mockMvc.perform(post("/client/donation")
            .param("resourceId", "1")
            .param("itemType", "food")
            .param("quantity", "10")
            .param("expirationDate", "2024-12-31")
            .param("donorId", "donor123"))
        .andExpect(status().isOk())
        .andExpect(content().string("Donation Created"));
  }

  @Test
  public void testRetrieveItemEndpoint() throws Exception {
    when(clientService.retrieveItem("1", "item1")).thenReturn("Item Retrieved");

    mockMvc.perform(get("/client/retrieveItem")
            .param("resourceId", "1")
            .param("itemId", "item1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Item Retrieved"));
  }

  @Test
  public void testRetrieveAvailableItemsEndpoint() throws Exception {
    when(clientService.retrieveAvailableItems("1")).thenReturn("Available Items");

    mockMvc.perform(get("/client/retrieveAvailableItems")
            .param("resourceId", "1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Available Items"));
  }

  @Test
  public void testCreateRequestEndpoint() throws Exception {
    when(clientService.createRequest("req1", "item1,item2", "open", "high", "requester123"))
        .thenReturn("Request Created");

    mockMvc.perform(post("/client/createRequest")
            .param("requestId", "req1")
            .param("itemIds", "item1,item2")
            .param("status", "open")
            .param("priorityLevel", "high")
            .param("requesterInfo", "requester123"))
        .andExpect(status().isOk())
        .andExpect(content().string("Request Created"));
  }

  @Test
  public void testRetrieveDispatchedItemsEndpoint() throws Exception {
    when(clientService.retrieveDispatchedItems("1")).thenReturn("Dispatched Items");

    mockMvc.perform(get("/client/retrieveDispatchedItems")
            .param("resourceId", "1"))
        .andExpect(status().isOk())
        .andExpect(content().string("Dispatched Items"));
  }
}
