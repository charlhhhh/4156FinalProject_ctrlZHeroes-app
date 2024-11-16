package dev.coms4156.project.finalproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.coms4156.project.finalproject.FinalProjectApplication;
import dev.coms4156.project.finalproject.Item;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test class for the Request class.
 */
@SpringBootTest
@ContextConfiguration
public class RequestUnitTests {
  private Request request;
  private String requestId = "REQ123";
  private List<String> itemIds = Arrays.asList("ITEM001", "ITEM002", "ITEM003");
  private String status = "requested";
  private String priorityLevel = "High";
  private String requesterInfo = "John Doe";

  @BeforeEach
  public void setUp() {
    request = new Request(requestId, itemIds, status, priorityLevel, requesterInfo);
  }

  @Test
  public void testCreateRequest() {
    assertNotNull(request);
    assertEquals(requestId, request.getRequestId());
    assertEquals(status, request.getStatus());
    assertEquals(priorityLevel, request.getPriorityLevel());
    assertEquals(requesterInfo, request.getRequesterInfo());
  }

  @Test
  public void testGetItemIds() {
    List<String> retrievedItemIds = request.getItemIds();
    assertEquals(itemIds, retrievedItemIds, "Item IDs should match the initial setup.");
  }

  @Test
  public void testUpdateStatus() {
    String newStatus = "fulfilled";
    request.updateStatus(newStatus);
    assertEquals(newStatus, request.getStatus(), "Status should be updated to 'fulfilled'.");
  }

  @Test
  public void testUpdatePriority() {
    String newPriority = "Low";
    request.updatePriority(newPriority);
    assertEquals(newPriority, request.getPriorityLevel(), "Priority should be updated to 'Low'.");
  }
}