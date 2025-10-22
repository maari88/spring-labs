package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.ShoppingCart;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DemoRunner implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    // Ін'єкція ApplicationContext через конструктор
    public DemoRunner(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n\n--- ДЕМОНСТРАЦІЯ ЗАВДАННЯ 5 ---");
        System.out.println("=====================================");

        // 1. Демонстрація @Component, @Service, @Autowired, @Bean
        // (Це вже працює, оскільки цей DemoRunner запустився,
        // а логи з ProductService з'являться при першому запиті до сайту)
        System.out.println("1. @Component, @Service, @Autowired, @Bean: Активовано (див. логи нижче та при запуску).");


        // 2. Демонстрація Singleton vs Prototype
        System.out.println("\n2. Демонстрація Singleton vs Prototype:");

        // Отримуємо Singleton (LoggerComponent)
        LoggerComponent logger1 = applicationContext.getBean(LoggerComponent.class);
        LoggerComponent logger2 = applicationContext.getBean(LoggerComponent.class);
        boolean isSingleton = (logger1 == logger2);
        System.out.println("  - Singleton (LoggerComponent): logger1 == logger2? -> " + isSingleton);
        System.out.println("    (Hash logger1: " + logger1.hashCode() + ")");
        System.out.println("    (Hash logger2: " + logger2.hashCode() + ")");


        // Отримуємо Prototype (ShoppingCart)
        ShoppingCart cart1 = applicationContext.getBean(ShoppingCart.class);
        // Невелика затримка, щоб час створення точно відрізнявся
        Thread.sleep(10);
        ShoppingCart cart2 = applicationContext.getBean(ShoppingCart.class);
        boolean isPrototype = (cart1 != cart2);
        System.out.println("  - Prototype (ShoppingCart): cart1 != cart2? -> " + isPrototype);
        System.out.println("    (Hash cart1: " + cart1.getHash() + ", Створено: " + cart1.getCreationTime() + ")");
        System.out.println("    (Hash cart2: " + cart2.getHash() + ", Створено: " + cart2.getCreationTime() + ")");

        System.out.println("=====================================");
        System.out.println("--- ДЕМОНСТРАЦІЯ ЗАВЕРШЕНА ---\n\n");
    }
}
