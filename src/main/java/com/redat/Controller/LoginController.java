/**
 * Represents login controller
 */
package com.redat.Controller;

import com.redat.Model.Client;
import com.redat.Service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginController {
    // Attributes:
    private final ClientService clientService;

    /**
     * Initialize object
     * @param clientService ClientService
     */
    @Autowired
    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Forward to log in view
     * @return log in view
     */
    @GetMapping
    public String loginView() {
        return "index";
    }

    /**
     * Handle client login
     * @param loginData submitted login data
     * @param session HttpSession
     * @return ResponseEntity
     */
    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String fullName = loginData.get("fullName");
        String password = loginData.get("password");

        if (!fullName.isEmpty() && !password.isEmpty()) {
            // Get client:
            Client client = clientService.findClient(fullName.toLowerCase(), password);

            if (client != null) {
                // Set session:
                session.setAttribute("client", client);

                return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
        return null;
    }
}
