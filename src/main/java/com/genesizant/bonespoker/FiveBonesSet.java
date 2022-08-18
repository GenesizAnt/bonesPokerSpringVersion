package com.genesizant.bonespoker;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Scope("prototype")
public class FiveBonesSet {

    private List<Integer> bonesHand = new ArrayList<>();
    private List<Integer> frequencyBones = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0));

    public void frequencyCount(Player player) {
        int repeatedOne = 0;
        int repeatedTwo = 0;
        int repeatedThree = 0;
        int repeatedFour = 0;
        int repeatedFive = 0;
        int repeatedSix = 0;

        for (int i = 0; i < player.fiveBonesSet.getBonesHand().size(); i++) {
            if (player.fiveBonesSet.getBonesHand().get(i) == 1) {
                repeatedOne++;
            } else if (player.fiveBonesSet.getBonesHand().get(i) == 2) {
                repeatedTwo++;
            } else if (player.fiveBonesSet.getBonesHand().get(i) == 3) {
                repeatedThree++;
            } else if (player.fiveBonesSet.getBonesHand().get(i) == 4) {
                repeatedFour++;
            } else if (player.fiveBonesSet.getBonesHand().get(i) == 5) {
                repeatedFive++;
            } else if (player.fiveBonesSet.getBonesHand().get(i) == 6) {
                repeatedSix++;
            }
        }
        ArrayList<Integer> bonesCombinationsPrint = new ArrayList<>();
        bonesCombinationsPrint.add(repeatedOne);
        bonesCombinationsPrint.add(repeatedTwo);
        bonesCombinationsPrint.add(repeatedThree);
        bonesCombinationsPrint.add(repeatedFour);
        bonesCombinationsPrint.add(repeatedFive);
        bonesCombinationsPrint.add(repeatedSix);
        Collections.sort(bonesCombinationsPrint);
        Collections.reverse(bonesCombinationsPrint);
        player.fiveBonesSet.setFrequencyBones(bonesCombinationsPrint);
    }

    public List<Integer> getBonesHand() {
        return bonesHand;
    }

    public void setBonesHand(List<Integer> bonesHand) {
        this.bonesHand = bonesHand;
    }

    public List<Integer> getFrequencyBones() {
        return frequencyBones;
    }

    public void setFrequencyBones(List<Integer> frequencyBones) {
        this.frequencyBones = frequencyBones;
    }
}
