package com.genesizant.bonespoker;

import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class BonesCombinations {

    public void getCombinationsPlayer(Player player) {
        Collections.sort(player.fiveBonesSet.getFrequencyBones());
        Collections.reverse(player.fiveBonesSet.getFrequencyBones());

            if (player.fiveBonesSet.getFrequencyBones().get(0) == 5) {
                player.setBonesCombinationName("Покер!");
                player.setBonesCombinationPriority(9);
            } else if (player.fiveBonesSet.getFrequencyBones().get(0) == 4) {
                player.setBonesCombinationName("Каре!");
                player.setBonesCombinationPriority(8);
            } else if (player.fiveBonesSet.getFrequencyBones().get(0) == 3) {
                if (player.fiveBonesSet.getFrequencyBones().get(1) == 2) {
                    player.setBonesCombinationName("Фул-хаус!");
                    player.setBonesCombinationPriority(7);
                } else {
                    player.setBonesCombinationName("Тройка!");
                    player.setBonesCombinationPriority(4);
                }
            } else if (player.fiveBonesSet.getFrequencyBones().get(0) == 2) {
                if (player.fiveBonesSet.getFrequencyBones().get(1) == 2) {
                    player.setBonesCombinationName("Две пары!");
                    player.setBonesCombinationPriority(3);
                } else {
                    player.setBonesCombinationName("Пара!");
                    player.setBonesCombinationPriority(2);
                }
            } else {
                Collections.sort(player.fiveBonesSet.getBonesHand());
                int sumBones = 0;
                for (int j = 0; j < player.fiveBonesSet.getBonesHand().size(); j++) {
                    sumBones += player.fiveBonesSet.getBonesHand().get(j);
                }
                if (player.fiveBonesSet.getBonesHand().get(0) == 1 && sumBones == 15) {
                    player.setBonesCombinationName("Большой стрит!");
                    player.setBonesCombinationPriority(6);
                }
                if (player.fiveBonesSet.getBonesHand().get(0) == 2 && sumBones == 20) {
                    player.setBonesCombinationName("Малый стрит!");
                    player.setBonesCombinationPriority(5);
                }
                int seniorBone = player.fiveBonesSet.getBonesHand().get(0);
                for (Integer integer : player.fiveBonesSet.getBonesHand()) {
                    if (integer > seniorBone) {
                        seniorBone = integer;
                    }
                }
                player.setBonesCombinationName("старшая кость");
                player.setBonesCombinationPriority(1);
            }
    }
}