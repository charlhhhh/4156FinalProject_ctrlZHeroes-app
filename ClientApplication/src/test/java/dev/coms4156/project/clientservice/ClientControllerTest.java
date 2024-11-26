package dev.coms4156.project.clientservice;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    private MockedStatic<FirebaseApp> firebaseAppMockedStatic;
    private MockedStatic<FirebaseAuth> firebaseAuthMockedStatic;

    @BeforeEach
    public void setUp() throws FirebaseAuthException {
        MockitoAnnotations.openMocks(this);

        // Mock FirebaseApp
        firebaseAppMockedStatic = mockStatic(FirebaseApp.class);
        firebaseAppMockedStatic.when(FirebaseApp::initializeApp).thenReturn(null);

        // Mock FirebaseAuth
        firebaseAuthMockedStatic = mockStatic(FirebaseAuth.class);
        FirebaseAuth mockAuth = mock(FirebaseAuth.class);
        firebaseAuthMockedStatic.when(FirebaseAuth::getInstance).thenReturn(mockAuth);

        // Mock FirebaseToken
        FirebaseToken mockToken = mock(FirebaseToken.class);
        when(mockAuth.verifyIdToken(anyString())).thenReturn(mockToken);
        when(mockToken.getUid()).thenReturn("mock-uid");
    }

    @AfterEach
    public void tearDown() {
        // Close static mocks
        firebaseAppMockedStatic.close();
        firebaseAuthMockedStatic.close();
    }

    @Test
    public void testCreateDonationEndpoint() throws Exception {
        when(clientService.createDonation("1", "food", 10, "2024-12-31", "donor123"))
            .thenReturn("Donation Created");

        mockMvc.perform(post("/client/donation")
                .header("Authorization", "mock-token")
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
                .header("Authorization", "mock-token")
                .param("resourceId", "1")
                .param("itemId", "item1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Item Retrieved"));
    }

    @Test
    public void testRetrieveAvailableItemsEndpoint() throws Exception {
        when(clientService.retrieveAvailableItems("1")).thenReturn("Available Items");

        mockMvc.perform(get("/client/retrieveAvailableItems")
                .header("Authorization", "mock-token")
                .param("resourceId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Available Items"));
    }

    @Test
    public void testCreateRequestEndpoint() throws Exception {
        when(clientService.createRequest("req1", "item1,item2", "open", "high", "requester123"))
            .thenReturn("Request Created");

        mockMvc.perform(post("/client/createRequest")
                .header("Authorization", "mock-token")
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
                .header("Authorization", "mock-token")
                .param("resourceId", "1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Dispatched Items"));
    }
}
