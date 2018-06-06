package objects.character.limbs;

import objects.character.Statuses;
import objects.character.limbs.torsoComps.Arm;
import objects.character.limbs.torsoComps.Leg;
import objects.character.limbs.torsoComps.Neck;

import java.util.ArrayList;

public class Torso implements Limb {

    private String name;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    private ArrayList<Neck> neck;
    private ArrayList<Arm> arms;
    private ArrayList<Leg> legs;
    private int currentHealth;
    private int maxHealth;
    private int damageMultiplier = 3;
    private boolean critical = true;

    public Torso(String name, ArrayList<Neck> neck, ArrayList<Arm> arms, ArrayList<Leg> legs, int maxHealth) {
        this.name = name;
        this.neck = neck;
        this.arms = arms;
        this.legs = legs;
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
    public void getDamaged(int amount) {
        this.currentHealth -= amount;
    }

    @Override
    public ArrayList<Limb> acquireUnderlyingLimbs() {
        ArrayList<Limb> allLimbs = new ArrayList<>();
        allLimbs.addAll(neck);
        allLimbs.addAll(arms);
        allLimbs.addAll(legs);

        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        neck = new ArrayList<>();
        arms = new ArrayList<>();
        legs = new ArrayList<>();
        for(Limb limb : limbs){
            if(limb instanceof Neck){
                neck.add((Neck)limb);
            }
            if(limb instanceof Arm){
                arms.add((Arm)limb);
            }
            if(limb instanceof Leg){
                legs.add((Leg) limb);
            }
        }
    }

    public ArrayList<Arm> getArms() {
        return arms;
    }

    public void setArms(ArrayList<Arm> arms) {
        this.arms = arms;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public ArrayList<Neck> getNeck() {
        return neck;
    }

    public void setNeck(ArrayList<Neck> neck) {
        this.neck = neck;
    }

    @Override
    public String getName() {
        return name;
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
    public String toString() {
        return name;
    }

    @Override
    public String acquireFullInfo() {
        return "Name: " + name + "\n Statuses: " + statuses;
    }

}
