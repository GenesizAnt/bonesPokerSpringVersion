package com.genesizant.bonespoker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        AdultTwoPlayerGame game = context.getBean("adultTwoPlayerGame", AdultTwoPlayerGame.class);
        game.startGameTwoPlayer();

        context.close();
    }
}
