package com.cosmocats.multiverse_market.controller;

import com.cosmocats.multiverse_market.repository.BuyerRepository;
import com.cosmocats.multiverse_market.repository.PlanetRepository;
import com.cosmocats.multiverse_market.repository.SellerRepository;
import com.cosmocats.multiverse_market.service.ProductService;
import com.cosmocats.multiverse_market.service.WelcomeMessageGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 1. Анотація @Controller (частина завдання 5)
// Вказує, що це бін рівня представлення
@Controller
public class MarketController {

    // --- Залежності ---
    // (Ми залишаємо WelcomeMessageGenerator, тому що це гарна "фіча", а не "демо-код")
    private final ProductService productService;
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final PlanetRepository planetRepository;
    private final WelcomeMessageGenerator welcomeMessageGenerator;

    // --- Ін'єкція через конструктор ---
    // (Spring автоматично підставить сюди всі потрібні біни)
    public MarketController(ProductService productService,
                            SellerRepository sellerRepository,
                            BuyerRepository buyerRepository,
                            PlanetRepository planetRepository,
                            WelcomeMessageGenerator welcomeMessageGenerator) {
        this.productService = productService;
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
        this.planetRepository = planetRepository;
        this.welcomeMessageGenerator = welcomeMessageGenerator;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {

        // --- ДЕМОНСТРАЦІЮ СКОУПІВ ПРИБРАНО ЗВІДСИ ---
        // (Тепер цим займається DemoRunner.java)

        // 1. Отримуємо привітання (з нашого @Bean)
        model.addAttribute("welcomeMessage", welcomeMessageGenerator.getWelcomeMessage());

        // 2. Отримуємо дані для таблиць
        // (Цей метод тепер викликає logger (Field Injection) та shippingService (Setter Injection))
        model.addAttribute("products", productService.getAllProductsWithLogs());
        model.addAttribute("sellers", sellerRepository.findAll());
        model.addAttribute("buyers", buyerRepository.findAll());
        model.addAttribute("planets", planetRepository.findAll());

        // Повертаємо ім'я HTML-шаблону
        return "index";
    }
}

