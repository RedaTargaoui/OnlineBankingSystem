/**
 * Represents dashboard controller
 */
package com.redat.Controller;

import com.redat.Model.Account;
import com.redat.Model.Client;
import com.redat.Model.Transaction;
import com.redat.Model.TransactionType;
import com.redat.Service.AccountService;
import com.redat.Service.ClientService;
import com.redat.Service.TransactionService;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Dashboard/")
public class DashboardController {
    // Attributes:
    private final AccountService accountService;
    private final ClientService clientService;
    private final TransactionService transactionService;
    private List<Transaction> transactions;

    /**
     * Initialize object
     * @param accountService AccountService
     * @param clientService ClientService
     * @param transactionService TransactionService
     */
    @Autowired
    public DashboardController(AccountService accountService, ClientService clientService, TransactionService transactionService) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.transactionService = transactionService;
    }

    /**
     * Get dashboard view
     * @param session HttpSession
     * @param model Get client from session:
     * @return dashboard view
     */
    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        // Get client from session:
        Client client = (Client) session.getAttribute("client");

        if (client != null) {
            model.addAttribute("client", client);

            // Get client's account:
            Account account = (Account) session.getAttribute("account");

            if (account == null) {
                // Get from database:
                account = accountService.getAccount(client);
                // Set in session:
                session.setAttribute("account", account);
            }

            // Get transactions from session:
            transactions = (List<Transaction>) session.getAttribute("transactions");

            if (transactions == null) {
                // Get from database:
                transactions = transactionService.getTransactions(account);
                // Set in session:
                session.setAttribute("transactions", transactions);
            }

            model.addAttribute("account", account);
            model.addAttribute("transactions", transactions);
        } else {
            return "redirect:/";
        }

        return "dashboard";
    }

    /**
     * Handle client deposit
     * @param depositData submitted deposit data
     * @param session HttpSession
     * @return ResponseEntity
     */
    @PostMapping("/api/deposit")
    public ResponseEntity<Object> deposit(@RequestBody Map<String, String> depositData, HttpSession session) {
        // Get amount:
        double amount = Double.parseDouble(depositData.get("amount"));

        // Get account:
        Account account = (Account) session.getAttribute("account");

        if (account != null) {
            // Update account balance:
            account.setBalance(account.getBalance() + amount);

            // Update in database:
            accountService.updateAccount(account);

            // Save transaction:
            Transaction newTransaction = new Transaction(TransactionType.DEPOSIT, amount,
                    Date.valueOf(LocalDate.now()), account, null);
            transactionService.saveTransaction(newTransaction);
            transactions.add(newTransaction);

            return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
        }

        return null;
    }

    /**
     * Handle client withdraw
     * @param withdrawData submitted withdraw data
     * @param session HttpSession
     * @return ResponseEntity
     */
    @PostMapping("/api/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody Map<String, String> withdrawData, HttpSession session) {
        // Get amount:
        double amount = Double.parseDouble(withdrawData.get("amount"));

        // Get account:
        Account account = (Account) session.getAttribute("account");

        if (account != null) {
            if (account.getBalance() - amount >= 0) {
                // Update account balance:
                account.setBalance(account.getBalance() - amount);

                // Update in database:
                accountService.updateAccount(account);

                // Save transaction:
                Transaction newTransaction = new Transaction(TransactionType.WITHDRAWAL, amount,
                        Date.valueOf(LocalDate.now()), account, null);
                transactionService.saveTransaction(newTransaction);
                transactions.add(newTransaction);

                return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }

        return null;
    }

    /**
     * Handle transfer operation
     * @param transferData transfer data
     * @param session HttpSession
     * @return ResponseEntity
     */
    @PostMapping("/api/transfer")
    public ResponseEntity<Object> transfer(@RequestBody Map<String, String> transferData, HttpSession session) {
        // Get data:
        double amount = Double.parseDouble(transferData.get("amount"));
        String recipient = transferData.get("recipient");

        // Get sender account:
        Account senderAccount = (Account) session.getAttribute("account");

        if (senderAccount != null) {
            if (senderAccount.getBalance() - amount >= 0) {
                // Check if recipient's account exists:
                Account recipientAccount = accountService.getAccount(clientService.findClient(recipient.toLowerCase()));

                if (recipientAccount != null) {
                    // Make the transfer:
                    senderAccount.setBalance(senderAccount.getBalance() - amount);
                    recipientAccount.setBalance(recipientAccount.getBalance() + amount);

                    // Save in database:
                    accountService.updateAccount(senderAccount);
                    accountService.updateAccount(recipientAccount);

                    // Save transaction:
                    Transaction newTransaction = new Transaction(TransactionType.TRANSFER, amount,
                            Date.valueOf(LocalDate.now()), senderAccount, recipientAccount);
                    transactionService.saveTransaction(newTransaction);
                    transactions.add(newTransaction);

                    return new ResponseEntity<>(Map.of("success", true), HttpStatus.OK);
                }

                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }

        return null;
    }
}
