package dev.coms4156.project.finalproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * This class contains unit tests for the Item class.
 */
@SpringBootTest
@ContextConfiguration
public class ItemUnitTests {

  /** Test items for validation. */
  public static Item validItem;
  public static Item expiredItem;
  public static Item zeroQuantityItem;
  public static Item testItem;

  /**
   * Set up the items to be tested before all tests.
   */
  @BeforeAll
  public static void setupItemsForTesting() {
    // Setting up items with different properties for validation testing
    validItem = new Item("Food", 10, LocalDate.now().plusDays(5), "Donor123");
    expiredItem = new Item("Medicine", 5, LocalDate.now().minusDays(1), "Donor456");
    zeroQuantityItem = new Item("Clothing", 0, null, "Donor789"); 
    testItem = new Item("Drink", 90, LocalDate.now().plusDays(100), "Donor123");
  }

  /**
   * Test the creation of a valid item.
   */
  @Test
  public void testItemCreation() {
    assertNotNull(validItem.getItemId()); // Ensure the item ID is generated
    assertEquals("Food", validItem.getItemType());
    assertEquals(10, validItem.getQuantity());
    assertEquals("available", validItem.getStatus());
    assertEquals("Donor123", validItem.getDonorId());
  }

  /**
   * Test marking an item as dispatched.
   */
  @Test
  public void testMarkAsDispatched() {
    validItem.markAsDispatched();
    assertEquals("dispatched", validItem.getStatus());
  }

  /**
   * Test the attribute validation for a valid item.
   */
  @Test
  public void testValidateAttributes_ValidItem() {
    assertTrue(validItem.validateAttributes()); 
  }

  /**
   * Test validation for an expired item.
   */
  @Test
  public void testValidateAttributes_ExpiredItem() {
    assertFalse(expiredItem.validateAttributes()); // This should fail because the item is expired
  }

  /**
   * Test validation for an item with zero quantity.
   */
  @Test
  public void testValidateAttributes_ZeroQuantityItem() {
    assertFalse(zeroQuantityItem.validateAttributes()); 
  }

  /**
   * Test that unique item IDs are generated.
   */
  @Test
  public void testGenerateUniqueItemId() {
    String itemId1 = Item.generateUniqueItemId();
    String itemId2 = Item.generateUniqueItemId();
    assertNotEquals(itemId1, itemId2); // Ensure the IDs are unique
  }

  /**
   * Test the toString method output.
   */
  @Test
  public void testToString() {
    String expectedString =
        "Item[ID=" + validItem.getItemId() + ", Type=Food, Quantity=10, Expiration=" 
        + validItem.getExpirationDate() + ", Status=available, DonorID=Donor123]";
    assertEquals(expectedString, validItem.toString());
  }

  /**
   * Test marking an item as unknown.
   */
  @Test
  public void testMarkAsUnknown() {
    // Assuming markAsUnknown() sets the status to "unknown"
    testItem.markAsUnknown();
    assertEquals("unknown", testItem.getStatus(), 
        "The status should be 'unknown' after calling markAsUnknown()");
  }

  /**
   * Test setting the quantity of an item.
   */
  @Test
  public void testSetQuantity() {
    testItem.setQuantity(15); // Change quantity to 15
    assertEquals(15, testItem.getQuantity(), "The quantity should be updated to 15");

    // Test invalid case (e.g., negative quantity, if your class supports that check)
    testItem.setQuantity(-5);
    assertEquals(-5, testItem.getQuantity(), 
        "The quantity should be updated to -5 even though it's invalid (unless validation exists)");
  }

  /**
   * Test setting the expiration date of an item.
   */
  @Test
  public void testSetExpirationDate() {
    LocalDate newDate = LocalDate.now().plusDays(10);
    testItem.setExpirationDate(newDate);
    assertEquals(newDate, testItem.getExpirationDate(), 
        "The expiration date should be updated to the new date");

    // Test setting a null expiration date (optional)
    testItem.setExpirationDate(null);
    assertNull(testItem.getExpirationDate(), "The expiration date should be null");
  }

  /**
   * Test setting the status of an item.
   */
  @Test
  public void testSetStatus() {
    testItem.setStatus("dispatched");
    assertEquals("dispatched", testItem.getStatus(), 
        "The status should be updated to 'dispatched'");

    testItem.setStatus("available");
    assertEquals("available", testItem.getStatus(), "The status should be updated to 'available'");

    // Test invalid status (optional, depending on how your class handles invalid inputs)
    testItem.setStatus("invalidStatus");
    assertEquals("invalidStatus", testItem.getStatus(), 
        "The status should be set to 'invalidStatus' unless validation is applied");
  }

  /**
   * Test validation for an item with a valid quantity but a null expiration date.
   */
  @Test
  public void testValidateAttributes_NullExpirationDate() {
    // Creating an item with a null expiration date and valid quantity
    Item nullExpirationItem = new Item("Water", 5, null, "DonorXYZ");
      
    // This should pass since the expiration date is allowed to be null, and the quantity is valid
    assertTrue(nullExpirationItem.validateAttributes(), 
        "The item should be valid when the expiration date is null but the quantity is positive");
  }
}
