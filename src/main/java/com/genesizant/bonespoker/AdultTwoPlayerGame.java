package com.genesizant.bonespoker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AdultTwoPlayerGame {

    @Autowired
    BonesCombinations bonesCombinations;

    @Autowired
    Player playerOne;

    @Autowired
    Player playerTwo;

    private static final Scanner scanner = new Scanner(System.in);
    private static int betBank;
    private static boolean betTrue = true;
    private static int lastCallAllIn = 0;

    public void startGameTwoPlayer() {

        System.out.println("*".repeat(50));
        System.out.println("Это игра - \"Покер на костях\". Версия на двоих и ставками\nКак зовут первого игрока?");

        String namePlayerOne = scanner.next();
        playerOne.setName(namePlayerOne);
        System.out.println("Привет, " + playerOne.getName() + "!\nТеперь познакомимся со вторым участником! Как тебя зовут?"); // + "Чтобы бросить кубики - нажми 1!");
        String namePlayerTwo = scanner.next();
        playerTwo.setName(namePlayerTwo);
        System.out.println("Привет, " + playerTwo.getName() + "! ");


        while (playerOne.getPurseCoin() > 0 && playerTwo.getPurseCoin() > 0) {
            System.out.println(playerOne.getName() + " у тебя в кошельке - " + playerOne.getPurseCoin() + " монеток");
            System.out.println(playerTwo.getName() + " у тебя в кошельке - " + playerTwo.getPurseCoin() + " монеток");
            System.out.println("Ну что, сделаем ставки?");

            toPlaceBet(playerOne);
            toPlaceBet(playerTwo);
            while (playerOne.getBetPlayer() != playerTwo.getBetPlayer()) {
                checkingBet(playerOne, playerTwo);
                playerOne.setBetPlayer(0);
                playerTwo.setBetPlayer(0);
            }


            System.out.println("Банк составляет - " + betBank + " Чтобы бросить кубики - нажмите 1!");

            int choose = 0;

            while (choose != 1) {
                if (scanner.hasNextInt()) {
                    choose = scanner.nextInt();
                    if (choose != 1) {
                        System.out.println("Я же сказал - 1 (один)!");
                    }
                } else {
                    System.out.println("Попробуй ввести цифру...");
                    scanner.next();
                }
            }

            createIntrigue();
            System.out.println();

            playerOne.rollBones();
            playerTwo.rollBones();

            bonesCombinations.getCombinationsPlayer(playerOne);
            bonesCombinations.getCombinationsPlayer(playerTwo);

            System.out.println(playerOne.getName() + ", у тебя выпало " + playerOne.fiveBonesSet.getBonesHand());
            System.out.println(playerTwo.getName() + ", у тебя выпало " + playerTwo.fiveBonesSet.getBonesHand());

            doWantRaise(playerOne);
            doWantRaise(playerTwo);
            while (playerOne.getBetPlayer() != playerTwo.getBetPlayer()) {
                checkingBet(playerOne, playerTwo);
                playerOne.setBetPlayer(0);
                playerTwo.setBetPlayer(0);
            }
            System.out.println();

            playerOne.fiveBonesSet.setBonesHand(changeBonesHand(playerOne.fiveBonesSet.getBonesHand(), playerOne));
            playerTwo.fiveBonesSet.setBonesHand(changeBonesHand(playerTwo.fiveBonesSet.getBonesHand(), playerTwo));
            playerOne.fiveBonesSet.frequencyCount(playerOne);
            playerTwo.fiveBonesSet.frequencyCount(playerTwo);
            bonesCombinations.getCombinationsPlayer(playerOne);
            bonesCombinations.getCombinationsPlayer(playerTwo);

            if (playerOne.getBonesCombinationPriority() > playerTwo.getBonesCombinationPriority()) {
                System.out.println("Победил " + playerOne.getName() + "!\nТвоя комбинация " + playerOne.getBonesCombinationName() + " больше комбинации "
                        + playerTwo.getName() + " - " + playerTwo.getBonesCombinationName());
                if (playerOne.getPurseCoin() == 0 && lastCallAllIn > 0) {
                    playerOne.setPurseCoin(lastCallAllIn * 2);
                    playerTwo.setPurseCoin(betBank - lastCallAllIn * 2);
                }
                playerOne.setPurseCoin(playerOne.getPurseCoin() + betBank);
            } else if (playerOne.getBonesCombinationPriority() == playerTwo.getBonesCombinationPriority()) {
                System.out.println("Ничья по комбинациям! У " + playerOne.getName() + " - " + playerOne.getBonesCombinationName() + ", у "
                        + playerTwo.getName() + " - " + playerTwo.getBonesCombinationName());
                checkDraw(playerOne, playerTwo);
            } else {
                System.out.println("Победил " + playerTwo.getName() + "!\nТвоя комбинация " + playerTwo.getBonesCombinationName() + " Это больше комбинации "
                        + playerOne.getName() + " - " + playerOne.getBonesCombinationName());
                if (playerTwo.getPurseCoin() == 0 && lastCallAllIn > 0) {
                    playerTwo.setPurseCoin(lastCallAllIn * 2);
                    playerOne.setPurseCoin(betBank - lastCallAllIn * 2);
                }
                playerTwo.setPurseCoin(playerTwo.getPurseCoin() + betBank);
            }
            playerOne.dropBonesAndBet();
            playerTwo.dropBonesAndBet();
            betBank = 0;
        }

        if (playerOne.getPurseCoin() == 0) {
            System.out.println("В игре \"Покер на костях\" победил " + playerTwo.getName() + "!!!");
        } else if (playerTwo.getPurseCoin() == 0) {
            System.out.println("В игре \"Покер на костях\" победил " + playerOne.getName() + "!!!");
        }

        System.out.println("*".repeat(50));
    }

    private static void doWantRaise(Player player) {
        System.out.println(player.getName() + ", будешь повышать?\nЕсли да нажми - 1\nЕсли нет нажми - 0");
        int chooseRise = -1;
        while (chooseRise != 0) {
            if (scanner.hasNextInt()) {
                chooseRise = scanner.nextInt();
                if (chooseRise == 1) {
                    if (player.getPurseCoin() > 0) {
                        toPlaceBet(player);
                        chooseRise = 0;
                    } else {
                        System.out.println("Куда тебе еще повышать! У тебя сейчас - " + player.getPurseCoin());
                    }
                }
            } else {
                System.out.println("Попробуй ввести цифру...");
                scanner.next();
            }
        }
    }

    private static void checkingBet(Player playerOne, Player playerTwo) {
        if (playerOne.getBetPlayer() > playerTwo.getBetPlayer()) {
            System.out.println(playerTwo.getName() + " ты поставил меньше, тебе нужно добавить еще - " + (playerOne.getBetPlayer() - playerTwo.getBetPlayer()));
            if ((playerOne.getBetPlayer() - playerTwo.getBetPlayer()) < playerTwo.getPurseCoin()) {
                int raiseBet = scanner.nextInt();
                while (playerTwo.getBetPlayer() + raiseBet != playerOne.getBetPlayer()) {
                    System.out.println(playerTwo.getName() + " твоя ставка все еще меньше! Поставь еще - " + (playerOne.getBetPlayer() - (playerTwo.getBetPlayer() + raiseBet)));
                    raiseBet += scanner.nextInt();
                }
                playerTwo.setPurseCoin(playerTwo.getPurseCoin() - raiseBet);
                playerTwo.setBetPlayer(playerTwo.getBetPlayer() + raiseBet);
                betBank += raiseBet;
                System.out.println(playerTwo.getName() + " уровнял. У тебя осталось - " + playerTwo.getPurseCoin());
            } else {
                playerTwo.setBetPlayer(playerTwo.getBetPlayer() + playerTwo.getPurseCoin());
                lastCallAllIn = playerTwo.getBetPlayer();
                betBank += playerTwo.getPurseCoin();
                playerTwo.setPurseCoin(0);
                System.out.println(playerTwo.getName() + " поставил все");
            }
        } else {
            System.out.println(playerOne.getName() + " ты поставил меньше, тебе нужно добавить еще - " + (playerTwo.getBetPlayer() - playerOne.getBetPlayer()));
            if ((playerTwo.getBetPlayer() - playerOne.getBetPlayer()) < playerOne.getPurseCoin()) {
                int raiseBet = scanner.nextInt();
                while (playerOne.getBetPlayer() + raiseBet != playerTwo.getBetPlayer()) {
                    System.out.println(playerOne.getName() + " твоя ставка все еще меньше! Поставь еще - " + (playerTwo.getBetPlayer() - (playerOne.getBetPlayer() + raiseBet)));
                    raiseBet += scanner.nextInt();
                }
                playerOne.setPurseCoin(playerOne.getPurseCoin() - raiseBet);
                playerOne.setBetPlayer(playerOne.getBetPlayer() + raiseBet);
                betBank += raiseBet;
                System.out.println(playerOne.getName() + " уровнял. У тебя осталось - " + playerOne.getPurseCoin());
            } else {
                playerOne.setBetPlayer(playerOne.getBetPlayer() + playerOne.getPurseCoin());
                lastCallAllIn = playerTwo.getBetPlayer();
                betBank += playerOne.getPurseCoin();
                playerOne.setPurseCoin(0);
                System.out.println(playerOne.getName() + " поставил все");
            }
        }
    }

    public static void toPlaceBet(Player player) {
        System.out.println(player.getName() + " сколько хочешь поставить? У тебя сейчас - " + player.getPurseCoin());
        player.setBetPlayer(player.getBetPlayer() + scanner.nextInt());
        while (betTrue) {
            if (player.getBetPlayer() <= player.getPurseCoin()) {
                player.setPurseCoin(player.getPurseCoin() - player.getBetPlayer());
                System.out.println(player.getName() + " ставка принята, у тебя осталось - " + player.getPurseCoin());
                betTrue = false;
            } else {
                System.out.println("У тебя столько нет, попробуй еще раз");
                player.setBetPlayer(scanner.nextInt());
            }
        }
        betTrue = true;
        betBank += player.getBetPlayer();
    }

    public static void createIntrigue() {
        String[] intrigueVoice = {"Трясем кубики", "Дуем в кулачек", "Загадываем лучшую комбинацию",
                "Ахалай Махалай!", "Ну пожаааалуйста...", "Танцуем с бубном", "Луна дай мне силу!"};

        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(intrigueVoice[(int) (Math.random() * 6)]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void checkDraw(Player playerOne, Player playerTwo) {
        List<Integer> bonesHandOne = playerOne.fiveBonesSet.getBonesHand();
        List<Integer> bonesHandTwo = playerTwo.fiveBonesSet.getBonesHand();

        int sumBonesPlayerOne = bonesHandOne.stream().mapToInt(Integer::intValue).sum();
        int sumBonesPlayerTwo = bonesHandTwo.stream().mapToInt(Integer::intValue).sum();

        if (sumBonesPlayerOne > sumBonesPlayerTwo) {
            System.out.println("Победил " + playerOne.getName() + "! За счет старшей кости");
            if (playerOne.getPurseCoin() == 0 && lastCallAllIn > 0) {
                playerOne.setPurseCoin(lastCallAllIn * 2);
                playerTwo.setPurseCoin(betBank - lastCallAllIn * 2);
            }
            playerOne.setPurseCoin(playerOne.getPurseCoin() + betBank);
        } else {
            System.out.println("Победил " + playerTwo.getName() + "! За счет старшей кости");
            if (playerTwo.getPurseCoin() == 0 && lastCallAllIn > 0) {
                playerTwo.setPurseCoin(lastCallAllIn * 2);
                playerOne.setPurseCoin(betBank - lastCallAllIn * 2);
            }
            playerTwo.setPurseCoin(playerTwo.getPurseCoin() + betBank);
        }

    }

    public static List<Integer> changeBonesHand(List<Integer> list, Player player) {
        int wishChange = -1;
        int numberBones;
        int revers = 0;

        System.out.println(player.getName() + " сколько кубиков ты хочешь поменять?" + " У тебя сейчас так: " + player.fiveBonesSet.getBonesHand());

        while (wishChange < 0 || wishChange > list.size()) {
            if (scanner.hasNextInt()) {
                wishChange = scanner.nextInt();
                revers = wishChange;
                if (wishChange < 0 || wishChange > list.size()) {
                    System.out.println("Не понял?! Сколько-сколько кубиков хочешь поменять? Их вообще то " + list.size());
                }
            } else {
                System.out.println("Хмм... и сколько я должен менять кубиков? Попробуй ввести цифру :-)");
                scanner.next();
            }
        }

        if (wishChange != 0) {
            System.out.println("Кубики идут по порядку, какой хочешь поменять? Выбирай по-одному!");
            for (int i = 0; i < wishChange; i++) {
                numberBones = scanner.nextInt();
                while (numberBones < 0 || numberBones > list.size()) {
                    if (scanner.hasNextInt()) {
                        if (numberBones < 0 || numberBones > list.size()) {
                            System.out.println("Не понял?! Какой-какой кубик хочешь поменять? Их вообще то " + list.size());
                            numberBones = scanner.nextInt();
                        }
                    } else {
                        System.out.println("Хмм... и какой кубик я должен менять? Попробуй ввести цифру :-)");
                        scanner.next();
                    }
                }
                System.out.println("Убрали кубик под номером - " + numberBones + ". Тебе нужно поменять еще - " + --revers);
                list.remove(numberBones - 1);
                System.out.println(list);
            }
            System.out.println("Тааак! Внимание! Бросаем новые кубики в количестве - " + wishChange + " шт.!");
            createIntrigue();
            System.out.println();

            for (int i = 0; i < wishChange; i++) {
                list.add((int) (Math.random() * 6 + 1));
            }
            System.out.println("Итого у тебя выпало:" + list);
            player.fiveBonesSet.frequencyCount(player);
        }
        return list;
    }

}
