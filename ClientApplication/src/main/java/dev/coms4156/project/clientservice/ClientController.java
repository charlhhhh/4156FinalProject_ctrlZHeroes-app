package dev.coms4156.project.clientservice;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {
  @Autowired
  private ClientService clientService;

  @GetMapping("/")
  public String home() {
    return "index";
  }

  @GetMapping("/createDonationPage")
  public String createDonationPage() {
    return "create_donation.html"; // Thymeleaf automatically looks for create_donation.html in templates/
  }

  @GetMapping("/retrieveItemPage")
  public String retrieveItemPage() {
    return "retrieve_item.html"; // Thymeleaf template
  }

  // Page for retrieving available items
  @GetMapping("/retrieveAvailableItemsPage")
  public String retrieveAvailableItemsPage() {
    return "retrieve_available_items.html"; // Thymeleaf automatically looks for retrieve_available_items.html
  }

  // Page for creating a request
  @GetMapping("/createRequestPage")
  public String createRequestPage() {
    return "create_request.html"; // Thymeleaf automatically looks for create_request.html
  }

  // Page for retrieving dispatched items
  @GetMapping("/retrieveDispatchedItemsPage")
  public String retrieveDispatchedItemsPage() {
    return "retrieve_dispatched_items.html"; // Thymeleaf automatically looks for retrieve_dispatched_items.html
  }

  @PostMapping("/donation")
  @ResponseBody
  public String createDonation(
      @RequestHeader("Authorization") String token,
      @RequestParam String resourceId,
      @RequestParam String itemType,
      @RequestParam int quantity,
      @RequestParam String expirationDate,
      @RequestParam String donorId) {
    if (!isUserAuthenticated(token)) {
      return "Unauthorized access.";
    }
    return clientService.createDonation(resourceId, itemType, quantity, expirationDate, donorId);
  }

  @GetMapping("/retrieveItem")
  @ResponseBody
  public String retrieveItem(@RequestHeader("Authorization") String token, @RequestParam String resourceId, @RequestParam String itemId) {
    if (!isUserAuthenticated(token)) {
      return "Unauthorized access.";
    }
    return clientService.retrieveItem(resourceId, itemId);
  }

  // API for retrieving available items
  @GetMapping("/retrieveAvailableItems")
  @ResponseBody
  public String retrieveAvailableItems(@RequestHeader("Authorization") String token, @RequestParam String resourceId) {
    if (!isUserAuthenticated(token)) {
      return "Unauthorized access.";
    }
    return clientService.retrieveAvailableItems(resourceId);
  }

  // API for creating a request
  @PostMapping("/createRequest")
  @ResponseBody
  public String createRequest(
      @RequestHeader("Authorization") String token,
      @RequestParam String itemIds,
      @RequestParam String priorityLevel,
      @RequestParam String requesterInfo
  ) {
    if (!isUserAuthenticated(token)) {
      return "Unauthorized access.";
    }
    return clientService.createRequest(itemIds, priorityLevel, requesterInfo);
  }

  // API for retrieving dispatched items
  @GetMapping("/retrieveDispatchedItems")
  @ResponseBody
  public String retrieveDispatchedItems(@RequestHeader("Authorization") String token, @RequestParam String resourceId) {
    if (!isUserAuthenticated(token)) {
      return "Unauthorized access.";
    }
    return clientService.retrieveDispatchedItems(resourceId);
  }

  @GetMapping("/loginPage")
  public String loginPage() {
      return "login.html"; // Thymeleaf automatically resolves to login.html in the templates/ directory
  }

  @GetMapping("/registerPage")
  public String registerPage() {
      return "register.html"; // Thymeleaf automatically resolves to login.html in the templates/ directory
  }

  // ================== Firebase Authentication Endpoint ==================

  /**
   * Verifies a Firebase token sent from the client.
   *
   * @param token Firebase ID token sent in the Authorization header
   * @return UID of the authenticated user or an error message
   */
  @PostMapping("/auth/verify-token")
  @ResponseBody
  public String verifyToken(@RequestHeader("Authorization") String token) {
    try {
      // Verify the token using Firebase Auth
      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
      String uid = decodedToken.getUid();
      return "User authenticated successfully. UID: " + uid;
    } catch (FirebaseAuthException e) {
      return "Authentication failed: " + e.getMessage();
    }
  }

  /**
   * Helper method to validate Firebase token.
   */
  private boolean isUserAuthenticated(String token) {
    try {
      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
      return decodedToken != null;
    } catch (FirebaseAuthException e) {
      System.out.println("Authentication failed: " + e.getMessage());
      return false;
    }
  }


}
