package com.genesizant.bonespoker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;


@Component
@Scope("prototype")
public class Player {

    @Autowired
    FiveBonesSet fiveBonesSet;

    private String name;
    private int purseCoin = 100;
    private int betPlayer;

    private String bonesCombinationName;
    private int bonesCombinationPriority;

    public void rollBones() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int randomBones = random.nextInt(6) + 1;
            fiveBonesSet.getBonesHand().add(i, randomBones);
            int oldVal = fiveBonesSet.getFrequencyBones().get(randomBones);
            int newVal = oldVal + 1;
            fiveBonesSet.getFrequencyBones().set(randomBones, newVal);
        }
        fiveBonesSet.getFrequencyBones().remove(0);
    }


    public void dropBonesAndBet() {
        fiveBonesSet.setBonesHand(new ArrayList<>());
        fiveBonesSet.setFrequencyBones(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0)));
        setBetPlayer(0);
    }

    public String getBonesCombinationName() {
        return bonesCombinationName;
    }

    public int getBonesCombinationPriority() {
        return bonesCombinationPriority;
    }

    public void setBonesCombinationName(String bonesCombinationName) {
        this.bonesCombinationName = bonesCombinationName;
    }

    public void setBonesCombinationPriority(int bonesCombinationPriority) {
        this.bonesCombinationPriority = bonesCombinationPriority;
    }

    public String getName() {
        return name;
    }

    public int getPurseCoin() {
        return purseCoin;
    }

    public void setPurseCoin(int purseCoin) {
        this.purseCoin = purseCoin;
    }

    public int getBetPlayer() {
        return betPlayer;
    }

    public void setBetPlayer(int betPlayer) {
        this.betPlayer = betPlayer;
    }

    public void setName(String name) {
        this.name = name;
    }
}
