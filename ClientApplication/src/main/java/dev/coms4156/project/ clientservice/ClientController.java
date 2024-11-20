package dev.coms4156.project.clientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/donation")
    @ResponseBody
    public String createDonation(
        @RequestParam String resourceId,
        @RequestParam String itemType,
        @RequestParam int quantity,
        @RequestParam String expirationDate,
        @RequestParam String donorId,
        @RequestHeader("Authorization") String idToken) {
        try {
            // Verify the token and extract the UID
            String uid = FirebaseAuthUtil.verifyToken(idToken);
            System.out.println("Verified UID: " + uid);
            return clientService.createDonation(resourceId, itemType, quantity, expirationDate, donorId);
        } catch (Exception e) {
            // Handle authentication failure
            return "Authentication failed: " + e.getMessage();
        }
    }

    @GetMapping("/availableItems")
    @ResponseBody
    public String retrieveAvailableItems(@RequestParam String resourceId, @RequestHeader("Authorization") String idToken) {
        try {
            // Verify the token
            String uid = FirebaseAuthUtil.verifyToken(idToken);
            System.out.println("Verified UID: " + uid);
            return clientService.retrieveAvailableItems(resourceId);
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
    }

    @PatchMapping("/dispatch")
    @ResponseBody
    public String dispatchItems(@RequestParam String resourceId, @RequestHeader("Authorization") String idToken) {
        try {
            // Verify the token
            String uid = FirebaseAuthUtil.verifyToken(idToken);
            System.out.println("Verified UID: " + uid);
            return clientService.dispatchItems(resourceId);
        } catch (Exception e) {
            return "Authentication failed: " + e.getMessage();
        }
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerUser(@RequestParam String email, @RequestParam String uid) {
        // Optionally store user details in your database
        try {
            // Simulate saving the user in the database (example)
            System.out.println("User registered: Email=" + email + ", UID=" + uid);
            return "User registered successfully!";
        } catch (Exception e) {
            return "Registration failed: " + e.getMessage();
        }
    }


    
}
