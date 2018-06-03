package objects.character.limbs.torsoComps.neckComps;

import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.character.limbs.torsoComps.neckComps.headComps.Ear;
import objects.character.limbs.torsoComps.neckComps.headComps.Eye;
import objects.character.limbs.torsoComps.neckComps.headComps.Mouth;
import objects.character.limbs.torsoComps.neckComps.headComps.Nose;

import java.util.ArrayList;

public class Head implements Limb {

    private String name;
    private ArrayList<Eye> eyes;
    private ArrayList<Nose> noses;
    private ArrayList<Ear> ears;
    private ArrayList<Mouth> mouths;
    int currentHealth;
    int maxHealth;
    private ArrayList<Statuses> statuses = new ArrayList<>();

    public Head(String name, ArrayList<Eye> eyes, ArrayList<Nose> noses, ArrayList<Ear> ears, ArrayList<Mouth> mouths, int maxHealth) {
        this.name = name;
        this.eyes = eyes;
        this.noses = noses;
        this.ears = ears;
        this.mouths = mouths;
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

        allLimbs.addAll(eyes);
        allLimbs.addAll(noses);
        allLimbs.addAll(ears);
        allLimbs.addAll(mouths);

        return allLimbs;
    }

    @Override
    public void setUnderlyingLimbs(ArrayList<Limb> limbs) {
        eyes = new ArrayList<>();
        noses = new ArrayList<>();
        ears = new ArrayList<>();
        mouths = new ArrayList<>();

        for(Limb limb : limbs){
            if(limb instanceof Eye){
                eyes.add((Eye)limb);
            }
            if(limb instanceof Nose){
                noses.add((Nose)limb);
            }
            if(limb instanceof Ear){
                ears.add((Ear) limb);
            }
            if(limb instanceof Mouth){
                mouths.add((Mouth)limb);
            }
        }
    }

    public ArrayList<Eye> getEyes() {
        return eyes;
    }

    public void setEyes(ArrayList<Eye> eyes) {
        this.eyes = eyes;
    }

    public ArrayList<Nose> getNoses() {
        return noses;
    }

    public void setNoses(ArrayList<Nose> noses) {
        this.noses = noses;
    }

    public ArrayList<Ear> getEars() {
        return ears;
    }

    public void setEars(ArrayList<Ear> ears) {
        this.ears = ears;
    }

    public ArrayList<Mouth> getMouths() {
        return mouths;
    }

    public void setMouths(ArrayList<Mouth> mouths) {
        this.mouths = mouths;
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
