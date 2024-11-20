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

  @GetMapping("/availableItems")
  @ResponseBody // Explicitly mark this as a response body
  public String retrieveAvailableItems(@RequestParam String resourceId) {
    return clientService.retrieveAvailableItems(resourceId);
  }

  @PatchMapping("/dispatch")
  @ResponseBody // Explicitly mark this as a response body
  public String dispatchItems(@RequestParam String resourceId) {
    return clientService.dispatchItems(resourceId);
  }

  @GetMapping("/retrieveItem")
  @ResponseBody
  public String retrieveItem(@RequestParam String resourceId, @RequestParam String itemId) {
    return clientService.retrieveItem(resourceId, itemId);
  }
}
