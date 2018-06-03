package objects.character.limbs.torsoComps.armComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.character.limbs.torsoComps.armComps.handComps.Thumb;
import objects.character.limbs.torsoComps.armComps.handComps.Finger;

import java.util.ArrayList;

public class Hand implements Limb {

    private String name;
    private ArrayList<Finger> fingers;
    private ArrayList<Thumb> thumbs;
    private ArrayList<Statuses> statuses = new ArrayList<>();
    int currentHealth;
    int maxHealth;

    public Hand(String name, ArrayList<Finger> fingers, ArrayList<Thumb> thumbs, int maxHealth) {
        this.name = name;
        this.fingers = fingers;
        this.thumbs = thumbs;
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
        ArrayList<Limb> allLimbs = new ArrayList<>();

        allLimbs.addAll(fingers);
        allLimbs.addAll(thumbs);

        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        fingers = new ArrayList<>();
        thumbs = new ArrayList<>();
        for(Limb limb : limbs){
            if(limb instanceof Finger){
                fingers.add((Finger)limb);
            }
            if(limb instanceof Thumb){
                thumbs.add((Thumb)limb);
            }
        }
    }

    public ArrayList<Finger> getFingers() {
        return fingers;
    }

    public void setFingers(ArrayList<Finger> fingers) {
        this.fingers = fingers;
    }

    public ArrayList<Thumb> getThumbs() {
        return thumbs;
    }

    public void setThumbs(ArrayList<Thumb> thumbs) {
        this.thumbs = thumbs;
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
