package dev.coms4156.project.clientservice;

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
  @ResponseBody // Explicitly mark this as a response body
  public String createDonation(
      @RequestParam String resourceId,
      @RequestParam String itemType,
      @RequestParam int quantity,
      @RequestParam String expirationDate,
      @RequestParam String donorId) {
    return clientService.createDonation(resourceId, itemType, quantity, expirationDate, donorId);
  }

  @GetMapping("/retrieveItem")
  @ResponseBody
  public String retrieveItem(@RequestParam String resourceId, @RequestParam String itemId) {
    return clientService.retrieveItem(resourceId, itemId);
  }

  // API for retrieving available items
  @GetMapping("/retrieveAvailableItems")
  @ResponseBody
  public String retrieveAvailableItems(@RequestParam String resourceId) {
    return clientService.retrieveAvailableItems(resourceId);
  }

  // API for creating a request
  @PostMapping("/createRequest")
  @ResponseBody
  public String createRequest(
      @RequestParam String requestId,
      @RequestParam String itemIds,
      @RequestParam String status,
      @RequestParam String priorityLevel,
      @RequestParam String requesterInfo
  ) {
    return clientService.createRequest(requestId, itemIds, status, priorityLevel, requesterInfo);
  }

  // API for retrieving dispatched items
  @GetMapping("/retrieveDispatchedItems")
  @ResponseBody
  public String retrieveDispatchedItems(@RequestParam String resourceId) {
    return clientService.retrieveDispatchedItems(resourceId);
  }

}
