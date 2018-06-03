package objects.character.limbs.torsoComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.character.limbs.torsoComps.legComps.Foot;

import java.util.ArrayList;

public class Leg implements Limb {

    private String name;
    private Foot foot;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    private int currentHealth;
    private int maxHealth;

    public Leg(String name, Foot foot, int maxHealth) {
        this.name = name;
        this.foot = foot;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
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
        allLimbs.add(foot);
        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        for(Limb limb : limbs){
            if(limb instanceof Foot){
                foot = (Foot) limb;
            }
        }
    }

    public Foot getFoot() {
        return foot;
    }

    public void setFoot(Foot foot) {
        this.foot = foot;
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
