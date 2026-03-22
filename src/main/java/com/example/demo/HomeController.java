package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final CardService cardService;

    public HomeController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/")
    public String homepage(HttpSession session, Model model) {
        List<String> collection = getCollection(session);
        List<Card> cards = collection.stream()
                .map(cardService::getById)
                .filter(card -> card != null)
                .toList();
        model.addAttribute("cards", cards);
        return "index";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @PostMapping("/search")
    public String searchCards(@RequestParam String name, Model model) {
        List<Card> results = cardService.searchByName(name);
        model.addAttribute("results", results);
        model.addAttribute("query", name);
        return "search";
    }

    @PostMapping("/collection/add")
    public String addCard(@RequestParam String cardId, HttpSession session) {
        List<String> collection = getCollection(session);
        if (!collection.contains(cardId)) {
            collection.add(cardId);
            session.setAttribute("collection", collection);
        }
        return "redirect:/";
    }

    @PostMapping("/collection/remove")
    public String removeCard(@RequestParam String cardId, HttpSession session) {
        List<String> collection = getCollection(session);
        collection.remove(cardId);
        session.setAttribute("collection", collection);
        return "redirect:/";
    }

    @GetMapping("/card/{id}")
    public String cardDetail(@PathVariable String id, Model model) {
        Card card = cardService.getById(id);
        model.addAttribute("card", card);
        return "card-detail";
    }

    @SuppressWarnings("unchecked")
    private List<String> getCollection(HttpSession session) {
        List<String> collection = (List<String>) session.getAttribute("collection");
        if (collection == null) {
            collection = new ArrayList<>();
            session.setAttribute("collection", collection);
        }
        return collection;
    }
}
