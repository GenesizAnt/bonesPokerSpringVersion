package com.genesizant.bonespoker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartGameOnePlayer {

    @Autowired
    BonesCombinations bonesCombinations;

    @Autowired
    Player player;

    @Autowired
    FiveBonesSet fiveBonesSet;

    public void startGame() {
        System.out.println("*".repeat(50));
        System.out.println("Это игра - \"Покер на костях\". Как тебя зовут?");
        Scanner scannerName = new Scanner(System.in);
        String namePlayer = scannerName.next();
        player.setName(namePlayer);
        player.rollBones();

        System.out.println("Привет, " + player.getName() + "! " + "Чтобы бросить кубики - нажми 1!");

        Scanner scannerStartGame = new Scanner(System.in);
        int choose = 0;

        while (choose != 1) {
            if (scannerStartGame.hasNextInt()) {
                choose = scannerStartGame.nextInt();
                if (choose != 1) {
                    System.out.println("Я же сказал - 1 (один)!");
                }
            } else {
                System.out.println("Попробуй ввести цифру...");
                scannerStartGame.next();
            }
        }

        System.out.println();

        System.out.println(player.getName() + ", у тебя выпало " + player.fiveBonesSet.getBonesHand());
        System.out.println();
        bonesCombinations.getCombinationsPlayer(player);
        System.out.println(player.getBonesCombinationName());
        System.out.println("*".repeat(50));
    }
}
