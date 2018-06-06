package objects.character.limbs.torsoComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.character.limbs.torsoComps.neckComps.Head;

import java.util.ArrayList;

public class Neck implements Limb {

    private String name;
    Head head;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    int currentHealth;
    int maxHealth;
    private int damageMultiplier = 5;
    private boolean critical = true;

    public Neck(String name, Head head, int maxHealth) {
        this.name = name;
        this.head = head;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
    }

    public Head getHead(){
        return head;
    }


    @Override
    public boolean isCritical() {
        return critical;
    }

    @Override
    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    @Override
    public int getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    public void setDamageMultiplier(int damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
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
        return statuses;
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
        allLimbs.add(head);
        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        for(Limb limb : limbs){
            if(limb instanceof Head){
                head = (Head) limb;
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
