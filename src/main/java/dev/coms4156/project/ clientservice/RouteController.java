package dev.coms4156.project.clientservice;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/donation")
    public String createDonation(
        @RequestParam String resourceId,
        @RequestParam String itemType,
        @RequestParam int quantity,
        @RequestParam String expirationDate,
        @RequestParam String donorId) {
        return clientService.createDonation(resourceId, itemType, quantity, expirationDate, donorId);
    }

    @GetMapping("/resource")
    public String retrieveResource(@RequestParam String resourceId) {
        return clientService.retrieveResource(resourceId);
    }
}
