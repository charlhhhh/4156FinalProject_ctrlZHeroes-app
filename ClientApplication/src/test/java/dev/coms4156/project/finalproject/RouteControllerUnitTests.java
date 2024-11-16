package dev.coms4156.project.finalproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for the Resource class.
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

  /**
   * Create variable for testing.
   */
  @BeforeEach
  public void setupTestDatabase() {
    testRouteController = new RouteController();

    Resource resource1 = new Resource(new HashMap<String, Item>(), "R_TEST");
    Map<String, Resource> resourceMapping = new HashMap<>();
    resourceMapping.put("R_TEST", resource1);
    Scheduler scheduler = new Scheduler(new ArrayList<Request>());

    testDatabase = new MyFileDatabase(false, null, null);
    testDatabase.setResources(resourceMapping);
    testDatabase.setRequests(scheduler);
    FinalProjectApplication.overrideDatabase(testDatabase);
  }

  @Test
  public void testCreateDonation() {
    Item item = new Item("Food", 10, LocalDate.now().plusDays(7), "Robert");
    ResponseEntity<?> response = testRouteController.createDonation("R_TEST", "Food", 10, 
        LocalDate.now().plusDays(7), "Robert");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(
        item.toString().substring(45), 
        ((String) response.getBody()).substring(45) // Take substring to ignore itemId.
    );

    response = testRouteController.createDonation("R_TEST", "Food", -1, 
      LocalDate.now().plusDays(7), "Robert");
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void testCreateRequest() {
    Request request = new Request("REQ1", Arrays.asList("ABCD", "EFGH"), 
        "Pending", "High", "John Doe");
    ResponseEntity<?> response = testRouteController.createRequest("REQ1", 
        Arrays.asList("ABCD", "EFGH"), "Pending", "High", "John Doe");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(request.getRequestId(), response.getBody());
  }

  @Test
  public void testRetrieveResource() {
    Item item1 = new Item("Food", 10, LocalDate.now().plusDays(7), "Robert");
    Map<String, Item> items = new HashMap<>();
    items.put(item1.getItemId(), item1);
    Resource resource1 = new Resource(items, "R_TEST");
    Map<String, Resource> resourceMapping = new HashMap<>();
    resourceMapping.put("R_TEST", resource1);
    testDatabase.setResources(resourceMapping);

    ResponseEntity<?> response = testRouteController.retrieveResource("R_TEST");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(resource1.toString(), response.getBody());
  }

  @Test
  public void testRetrieveItem() {
    Item item1 = new Item("Food", 10, LocalDate.now().plusDays(7), "Robert");
    Item item2 = new Item("Clothing", 5, LocalDate.now().plusDays(180), "Charlie");
    Map<String, Item> items = new HashMap<>();
    items.put(item1.getItemId(), item1);
    items.put(item2.getItemId(), item2);
    Resource resource1 = new Resource(items, "R_TEST");
    Map<String, Resource> resourceMapping = new HashMap<>();
    resourceMapping.put("R_TEST", resource1);
    testDatabase.setResources(resourceMapping);

    ResponseEntity<?> response = testRouteController.retrieveItem("R_TEST", item1.getItemId());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(item1.toString(), response.getBody());

    response = testRouteController.retrieveItem("R_TEST", "ABCD");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testRetrieveAvailableItems() {
    Item item1 = new Item("Food", 10, LocalDate.now().plusDays(7), "Robert");
    Item item2 = new Item("Clothing", 5, LocalDate.now().plusDays(180), "Charlie");
    item2.markAsDispatched();
    Map<String, Item> items = new HashMap<>();
    items.put(item1.getItemId(), item1);
    items.put(item2.getItemId(), item2);
    Resource resource1 = new Resource(items, "R_TEST");
    Map<String, Resource> resourceMapping = new HashMap<>();
    resourceMapping.put("R_TEST", resource1);
    testDatabase.setResources(resourceMapping);

    ResponseEntity<?> response = testRouteController.retrieveAvailableItems("R_TEST");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(item1.getItemId() + ": " + item1.toString() + "\n", response.getBody());

    item1.markAsDispatched();
    response = testRouteController.retrieveAvailableItems("R_TEST");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void testRetrieveDispatchedItems() {
    Item item1 = new Item("Food", 10, LocalDate.now().plusDays(7), "Robert");
    Item item2 = new Item("Clothing", 5, LocalDate.now().plusDays(180), "Charlie");
    Map<String, Item> items = new HashMap<>();
    items.put(item1.getItemId(), item1);
    items.put(item2.getItemId(), item2);
    Resource resource1 = new Resource(items, "R_TEST");
    Map<String, Resource> resourceMapping = new HashMap<>();
    resourceMapping.put("R_TEST", resource1);
    testDatabase.setResources(resourceMapping);

    ResponseEntity<?> response = testRouteController.retrieveDispatchedItems("R_TEST");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    item1.markAsDispatched();
    response = testRouteController.retrieveDispatchedItems("R_TEST");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(item1.getItemId() + ": " + item1.toString() + "\n", response.getBody());
  }

  @Test
  public void testRetrieveItemsByDonor() {
    Item item1 = new Item("Food", 10, LocalDate.now().plusDays(7), "Robert");
    Item item2 = new Item("Clothing", 5, LocalDate.now().plusDays(180), "Charlie");
    Map<String, Item> items = new HashMap<>();
    items.put(item1.getItemId(), item1);
    items.put(item2.getItemId(), item2);
    Resource resource1 = new Resource(items, "R_TEST");
    Map<String, Resource> resourceMapping = new HashMap<>();
    resourceMapping.put("R_TEST", resource1);
    testDatabase.setResources(resourceMapping);

    ResponseEntity<?> response = testRouteController.retrieveItemsByDonor("R_TEST", "Robert");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(item1.getItemId() + ": " + item1.toString() + "\n", response.getBody());

    response = testRouteController.retrieveItemsByDonor("R_TEST", "Somebody");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /** The test resource instance used for testing. */
  public static RouteController testRouteController;
  public static MyFileDatabase testDatabase;
}
