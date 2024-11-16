package dev.coms4156.project.finalproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains tests for the Resource class.
 */
@SpringBootTest
@ContextConfiguration
public class ResourceUnitTests {

  /**
   * Create variable for testing.
   */
  @BeforeAll
  public static void setupCourseForTesting() {
    testItem = new Item("Food", 10, LocalDate.now().plusDays(5), "Donor123");
    testItem2 = new Item("Medicine", 5, LocalDate.now().minusDays(1), "Donor456");
    testItem3 = new Item("Clothing", 0, null, "Donor789");  // No expiration, but quantity is zero

    testItemMap = new HashMap<>();
    testItemMap.put(testItem.getItemId(), testItem);
    testItemMap.put(testItem2.getItemId(), testItem2);
    //testItemMap.put(testItem3.getItemId(),testItem3);

    testItemMap2 = new HashMap<>();
    testItemMap2.put(testItem.getItemId(), testItem);

    testResource = new Resource(testItemMap, "testResource");
    testResource2 = new Resource(testItemMap2, "testResource");
  }

  @Test
  public void testAddItem() {
    testResource.addItem(testItem3.getItemId(), testItem3);
    assertEquals(testResource.getItemById(testItem3.getItemId()), testItem3);
  }

  @Test
  public void testGetAllItems() {
    testItemMap.put(testItem3.getItemId(), testItem3);
    assertEquals(testResource.getAllItems(), testItemMap);
  }

  @Test
  public void testRemoveItem() {
    testResource.removeItem(testItem3.getItemId());
    assertEquals(testResource.getItemById(testItem3.getItemId()), null);
  }

  @Test
  public void testGetItemById() {
    assertEquals(testResource.getItemById(testItem2.getItemId()), testItem2);
  }

  @Test
  public void testListAvailableItems() {
    testResource.getItemById(testItem3.getItemId()).markAsDispatched();
    testResource.getItemById(testItem2.getItemId()).markAsDispatched();
    String result = "testResource " + testItem.getItemId() + ": " + testItem.toString() + "\n";
    assertEquals(testResource.listAvailableItems(), result);
  }

  @Test
  public void testToString() {
    String result = "testResource " + testItem.getItemId() + ": " + testItem.toString() + "\n";
    assertEquals(testResource2.toString(), result);
  }


  /**
   * The test resource instance used for testing.
   */
  public static Item testItem;
  public static Item testItem2;
  public static Item testItem3;
  public static Map<String, Item> testItemMap;
  public static Map<String, Item> testItemMap2;
  public static Resource testResource;
  public static Resource testResource2;
}
