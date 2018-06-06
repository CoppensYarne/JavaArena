package objects.character.limbs.torsoComps.legComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.character.limbs.torsoComps.legComps.footComps.Toe;

import java.util.ArrayList;

public class Foot implements Limb {

    private String name;
    private ArrayList<Toe> toes;
    private int currentHealth;
    private int maxHealth;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    private int damageMultiplier = 2;
    private boolean critical = false;

    public Foot(String name, ArrayList<Toe> toes, int maxHealth) {
        this.name = name;
        this.toes = toes;
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
    public void getDamaged(int amount) {
        this.currentHealth -= amount;
    }

    @Override
    public ArrayList<Limb> acquireUnderlyingLimbs() {
        ArrayList<Limb> allLimbs = new ArrayList<>();
        allLimbs.addAll(toes);
        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        toes = new ArrayList<>();
        for(Limb limb : limbs){
            if(limb instanceof Toe){
                toes.add((Toe)limb);
            }
        }
    }

    public ArrayList<Toe> getToes() {
        return toes;
    }

    public void setToes(ArrayList<Toe> toes) {
        this.toes = toes;
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
