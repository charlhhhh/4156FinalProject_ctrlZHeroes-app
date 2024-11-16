package dev.coms4156.project.finalproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Scheduler class.
 */
@SpringBootTest
@ContextConfiguration
public class SchedulerUnitTests {

  private Scheduler scheduler;
  private Map<String, Item> resourceRepository;
  private List<Request> requests;

  /**
   * Create variable for testing.
   */
  @BeforeEach
  public void setUp() {
    // Initialize the resource repository with some items
    resourceRepository = new HashMap<>();
    resourceRepository.put("water", new Item("water", 10, LocalDate.of(2025, 12, 25), "Donor1"));
    resourceRepository.put("food", new Item("food", 5, LocalDate.of(2025, 12, 25), "Donor2"));

    // Initialize an empty list of requests
    requests = new ArrayList<>();

    // Create the scheduler instance
    scheduler = new Scheduler(requests, resourceRepository);
  }

  @Test
  public void testProcessRequestsSuccessfulDispatch() {
    // Add a valid request where all resources are available
    Request request1 = new Request("REQ1", Arrays.asList("water", "food"), "Pending", "High",
        "John Doe");
    requests.add(request1);

    // Process the requests
    scheduler.processRequests();

    // Assert that the resources have been decremented
    assertEquals(9, resourceRepository.get("water").getQuantity());
    assertEquals(4, resourceRepository.get("food").getQuantity());

    // Assert that the request status has been updated
    assertEquals("Dispatched", request1.getStatus());
  }

  @Test
  public void testProcessRequestsResourceUnavailable() {
    // Add a request where one resource is not available
    Request request2 = new Request("REQ2", Arrays.asList("water", "medicine"), "Pending", "Low",
        "Jane Doe");
    requests.add(request2);

    // Process the requests
    scheduler.processRequests();

    // Assert that the resources remain unchanged
    assertEquals(10, resourceRepository.get("water").getQuantity());
    assertNull(resourceRepository.get("medicine"));

    // Assert that the request status has not changed
    assertEquals("Pending", request2.getStatus());
  }

  @Test
  public void testProcessRequestsInsufficientResourceQuantity() {
    // Modify the resource repository to have insufficient quantity for food
    resourceRepository.put("food", new Item("food", 0, LocalDate.of(2025, 12, 25), "Donor1"));

    // Add a request that requires food
    Request request3 = new Request("REQ3", Arrays.asList("food"), "Pending", "Low", "Jake Doe");
    requests.add(request3);

    // Process the requests
    scheduler.processRequests();

    // Assert that no resources have been decremented due to insufficient quantity
    assertEquals(0, resourceRepository.get("food").getQuantity());

    // Assert that the request status has not changed
    assertEquals("Pending", request3.getStatus());
  }

  @Test
  public void testProcessMultipleRequests() {
    // Add multiple requests to the list
    Request request4 = new Request("REQ4", Arrays.asList("water"), "Pending", "High", "Tom Doe");
    Request request5 = new Request("REQ5", Arrays.asList("food"), "Pending", "Low", "Sara Doe");

    requests.add(request4);
    requests.add(request5);

    // Process the requests
    scheduler.processRequests();

    // Assert that both requests have been processed and dispatched
    assertEquals(9, resourceRepository.get("water").getQuantity());
    assertEquals(4, resourceRepository.get("food").getQuantity());
    assertEquals("Dispatched", request4.getStatus());
    assertEquals("Dispatched", request5.getStatus());
  }

  @Test
  public void testAddRequest() {
    // Create a new request
    Request request6 = new Request("REQ6", Arrays.asList("water"), "Pending", "Medium",
        "Alice Doe");

    // Add the request
    scheduler.addRequest(request6);

    // Assert that the request has been added to the list
    assertEquals(1, requests.size());
    assertEquals("REQ6", requests.get(0).getRequestId());
  }

  @Test
  public void testCheckResourceAvailabilityAllAvailable() {
    // Add a valid request
    Request request7 = new Request("REQ7", Arrays.asList("water", "food"), "Pending", "High",
        "John Doe");

    // Check availability
    boolean available = scheduler.checkResourceAvailability(request7);

    // Assert that resources are available
    assertTrue(available);
  }

  @Test
  public void testCheckResourceAvailabilityNotAvailable() {
    // Add a request where the resource does not exist
    Request request8 = new Request("REQ8", Arrays.asList("medicine"), "Pending", "Low", "Jane Doe");

    // Check availability
    boolean available = scheduler.checkResourceAvailability(request8);

    // Assert that resources are not available
    assertFalse(available);
  }

  @Test
  public void testScheduleDispatchSuccessfulDispatch() {
    // Add a valid request
    Request request9 = new Request("REQ9", Arrays.asList("water"), "Pending", "High", "John Doe");
    requests.add(request9);

    // Schedule the dispatch
    scheduler.scheduleDispatch(request9);

    // Assert that the resource has been decremented
    assertEquals(9, resourceRepository.get("water").getQuantity());

    // Assert that the request status has been updated
    assertEquals("Dispatched", request9.getStatus());
  }

  @Test
  public void testScheduleDispatchResourceUnavailable() {
    // Add a request with a non-existing resource
    Request request10 = new Request("REQ10", Arrays.asList("medicine"), "Pending", "Low",
        "Jane Doe");
    requests.add(request10);

    // Attempt to schedule dispatch
    scheduler.scheduleDispatch(request10);

    // Assert that no dispatch has occurred and the status is still pending
    assertEquals("Pending", request10.getStatus());
  }
}
