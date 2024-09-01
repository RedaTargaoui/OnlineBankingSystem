/**
 * Represents logout controller
 */
package com.redat.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Logout")
public class LogoutController {

    @GetMapping
    public String Logout(HttpSession session) {
        // Invalidate session:
        session.invalidate();

        return "redirect:/";
    }
}
