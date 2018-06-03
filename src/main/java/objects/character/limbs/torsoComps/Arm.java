package objects.character.limbs.torsoComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.character.limbs.torsoComps.armComps.Hand;

import java.util.ArrayList;

public class Arm implements Limb {

    private String name;
    Hand hand;
    int currentHealth;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    int maxHealth;

    public Arm(String name, Hand hand, int maxHealth) {
        this.name = name;
        this.hand = hand;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }

    public Hand getHand(){
        return hand;
    }

    @Override
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ArrayList<Statuses> getStatuses() {
        return this.statuses;
    }

    @Override
    public void setStatuses(ArrayList<Statuses> statuses) {
        this.statuses = statuses;
    }

    @Override
    public void addStatus(Statuses status) {
        this.statuses.add(status);
    }

    @Override
    public void removeStatus(Statuses status) {
        this.statuses.remove(status);
    }

    @Override
    public void getDamaged(int amount) {
        this.currentHealth -= amount;
    }

    @Override
    public ArrayList<Limb> acquireUnderlyingLimbs() {
        ArrayList<Limb> allLimbs = new ArrayList<>();
        allLimbs.add(hand);
        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        for(Limb limb : limbs){
            if(limb instanceof Hand){
                hand = (Hand) limb;
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String acquireFullInfo() {
        return "Name: " + name + "\n Statuses: " + statuses;
    }
}
