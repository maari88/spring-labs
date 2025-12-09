package com.cosmocats.multiverse_market.controller;

import com.cosmocats.multiverse_market.repository.BuyerRepository;
import com.cosmocats.multiverse_market.repository.PlanetRepository;
import com.cosmocats.multiverse_market.repository.SellerRepository;
import com.cosmocats.multiverse_market.service.ProductService;
import com.cosmocats.multiverse_market.service.WelcomeMessageGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketController {

    private final ProductService productService;
    private final SellerRepository sellerRepository;
    private final BuyerRepository buyerRepository;
    private final PlanetRepository planetRepository;
    private final WelcomeMessageGenerator welcomeMessageGenerator;

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
        model.addAttribute("welcomeMessage", welcomeMessageGenerator.getWelcomeMessage());

        model.addAttribute("products", productService.findAll());

        model.addAttribute("sellers", sellerRepository.findAll());
        model.addAttribute("buyers", buyerRepository.findAll());
        model.addAttribute("planets", planetRepository.findAll());

        return "index";
    }
}