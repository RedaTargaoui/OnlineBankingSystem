/**
 * Represents profile controller
 */
package com.redat.Controller;

import com.redat.Model.Account;
import com.redat.Model.Client;
import com.redat.Service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/Profile")
public class ProfileController {
    // Attributes:
    private final ClientService clientService;

    /**
     * Initialize object
     * @param clientService ClientService
     */
    @Autowired
    public ProfileController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Get profile view
     * @param session HttpSession
     * @param model Model
     * @return profile view
     */
    @GetMapping
    public String profile(HttpSession session, Model model) {
        // Get client:
        Client client = (Client) session.getAttribute("client");

        // Get account:
        Account account = (Account) session.getAttribute("account");

        if (client == null) {
            return "redirect:/";
        }

        // Add to model:
        model.addAttribute("client", client);
        model.addAttribute("account", account);

        return "profile";
    }

    /**
     * Change password
     * @param passwordData password
     * @param session HttpSession
     * @return ResponseEntity
     */
    @PostMapping("/api/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody Map<String, String> passwordData, HttpSession session) {
        // Get client:
        Client client = (Client) session.getAttribute("client");

        if (client != null) {
            // Get password:
            String newPassword = passwordData.get("newPassword");

            // Update client's password:
            client.setPassword(newPassword);

            // Save in database:
            clientService.saveClient(client);

            return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
        }

        return null;
    }
}
