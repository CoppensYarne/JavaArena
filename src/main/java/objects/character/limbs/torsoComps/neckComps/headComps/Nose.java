package objects.character.limbs.torsoComps.neckComps.headComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;

import java.util.ArrayList;

public class Nose implements Limb {

    private String name;
    int currentHealth;
    int maxHealth;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    private int damageMultiplier = 1;
    private boolean critical = false;

    public Nose(String name, int maxHealth) {
        this.name = name;
        this.currentHealth = maxHealth;
        this.maxHealth = maxHealth;
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
    public boolean isCritical() {
        return critical;
    }

    @Override
    public void setCritical(boolean critical) {
        this.critical = critical;
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
}
