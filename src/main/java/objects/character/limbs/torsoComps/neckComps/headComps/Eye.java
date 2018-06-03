package objects.character.limbs.torsoComps.neckComps.headComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;

import java.util.ArrayList;

public class Eye implements Limb {

    private String name;
    int currentHealth;
    int maxHealth;
    private ArrayList<Statuses> statuses = new ArrayList<>();

    public Eye(String name, int maxHealth) {
        this.name = name;
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
    public void getDamaged(int amount) {
        this.currentHealth -= amount;
    }

    @Override
    public ArrayList<Limb> acquireUnderlyingLimbs() {
        return null;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {

    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String acquireFullInfo() {
        return "Name: " + name + "\n Statuses: " + statuses;
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

}
