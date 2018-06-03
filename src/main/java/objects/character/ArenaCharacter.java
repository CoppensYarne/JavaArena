package objects.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import objects.ArenaObject;
import objects.character.limbs.Limb;
import objects.character.limbs.Torso;
import objects.character.race.Race;
import objects.item.Item;
import objects.item.weapon.Weapon;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

@JsonDeserialize(using = ArenaCharacterDeserializer.class)
public class ArenaCharacter implements ArenaObject {

    Weapon fists = new Weapon("Fists", "punched", "punch", 1, 1, 999, objects.character.Statuses.NONE);
    private String name;
    private int currentHealth;
    private int maxHealth;
    private Torso torso;
    private ArrayList<Limb> lostLimbs = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private Weapon currentWeapon = fists;
    private int position = 0;
    private Gender gender;
    private Race race;
    private boolean isAi;

    public ArenaCharacter() {
        super();
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Torso getTorso() {
        return torso;
    }

    public Gender getGender() {
        return gender;
    }

    public ArrayList<Limb> acquireAllLimbs() {
        ArrayList<Limb> allLimbs = new ArrayList<>();
        allLimbs.add(torso);
        for (Limb torsoLimb : torso.acquireUnderlyingLimbs()) {
            allLimbs.add(torsoLimb);
            for (Limb torsoLimbLimb : torsoLimb.acquireUnderlyingLimbs()) {
                allLimbs.add(torsoLimbLimb);
                for (Limb torsoLimbLimbLimb : torsoLimbLimb.acquireUnderlyingLimbs()) {
                    allLimbs.add(torsoLimbLimbLimb);
                }
            }
        }

        return allLimbs;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public String equipWeapon(Weapon weapon) {
        String toReturnString = "";
        toReturnString += this.name + " found a " + weapon.getName() + ".";
        this.currentWeapon = weapon;
        return toReturnString;
    }

    public String breakWeapon() {
        String toReturnString = "";
        toReturnString += this.name + "'s" + " " + this.currentWeapon.getName() + " broke.";
        this.currentWeapon = fists;
        return toReturnString;
    }

    @Override
    public String toString() {
        return String.format("name: %10s | Current Health: %3s | Max Health: %3s | inventory: %s | Current Weapon: %s | gender: %6s | race: %5s |",
                name, currentHealth, maxHealth, inventory, currentWeapon.getName(), gender, race);
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setTorso(Torso torso) {
        this.torso = torso;
    }

    public ArrayList<Limb> getLostLimbs() {
        return lostLimbs;
    }

    public void setLostLimbs(ArrayList<Limb> lostLimbs) {
        this.lostLimbs = lostLimbs;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public objects.character.race.Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setIsAi(boolean ai) {
        isAi = ai;
    }

    public boolean isAi() {
        return isAi;
    }

    public void setAi(boolean ai) {
        isAi = ai;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public JSONObject toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String toReturnJSON = mapper.writeValueAsString(this);
        return new JSONObject(toReturnJSON);
    }

    public String takeDamage(int amount, Limb chosenLimb) {
        String toReturnString = "";
        this.currentHealth -= amount;
        chosenLimb.getDamaged(amount);
        ArrayList<Limb> newLimbs;
        if (chosenLimb.getCurrentHealth() <= 0) {
            toReturnString += this.name + " lost " + this.gender.getPossession() + " " + chosenLimb + ".";
            if (this.torso.acquireUnderlyingLimbs().contains(chosenLimb)) {
                newLimbs = this.torso.acquireUnderlyingLimbs();
                newLimbs.remove(chosenLimb);
                this.torso.setUnderlyingLimbs(newLimbs);
            } else {
                for (Limb torsoLimb : this.torso.acquireUnderlyingLimbs()) {
                    if (torsoLimb.acquireUnderlyingLimbs().contains(chosenLimb)) {
                        newLimbs = torsoLimb.acquireUnderlyingLimbs();
                        newLimbs.remove(chosenLimb);
                        torsoLimb.setUnderlyingLimbs(newLimbs);
                    } else {
                        for (Limb torsoLimbLimb : torsoLimb.acquireUnderlyingLimbs()) {
                            if (torsoLimbLimb.acquireUnderlyingLimbs().contains(chosenLimb)) {
                                newLimbs = torsoLimbLimb.acquireUnderlyingLimbs();
                                newLimbs.remove(chosenLimb);
                                torsoLimbLimb.setUnderlyingLimbs(newLimbs);
                            } else {
                                for (Limb torsoLimbLimbLimb : torsoLimbLimb.acquireUnderlyingLimbs()) {
                                    if (torsoLimbLimbLimb.acquireUnderlyingLimbs().contains(chosenLimb)) {
                                        newLimbs = torsoLimbLimbLimb.acquireUnderlyingLimbs();
                                        newLimbs.remove(chosenLimb);
                                        torsoLimbLimbLimb.setUnderlyingLimbs(newLimbs);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.lostLimbs.add(chosenLimb);
        }
        return toReturnString;
    }

    public boolean getIsAi() {
        return isAi;
    }

    public String attack(ArenaCharacter defendingCharacter, Limb targetedLimb) {
        String toReturnString = "";

        Limb actualHitLimb = defendingCharacter.acquireRandomLimb(targetedLimb);

        if (actualHitLimb == targetedLimb) {
            toReturnString += this.name + " " + this.currentWeapon.getPastVerb() + " " + defendingCharacter.getName() + " in " + defendingCharacter.getGender().getPossession() + " " + actualHitLimb.getName() +
                    " with " + this.getGender().getPossession() + " " + this.currentWeapon.getName() + ".";
        } else {
            toReturnString += this.name + " wanted to " + this.currentWeapon.getPresentVerb() + " " + defendingCharacter.getName() + " in " + defendingCharacter.getGender().getPossession() + " " + targetedLimb.getName() + " with " + this.getGender().getPossession() + " " + this.currentWeapon.getName() + " but missed" +
                    " and " + this.currentWeapon.getPastVerb() + " " + defendingCharacter.getGender().getPossession() + " " + actualHitLimb.getName() + " instead.";
        }


        toReturnString += defendingCharacter.takeDamage(this.currentWeapon.getDamage(), actualHitLimb);
        if (this.currentWeapon.getToInflictStatus() != objects.character.Statuses.NONE) {
            toReturnString += defendingCharacter.name + " " + defendingCharacter.getGender().getPossession() + " " + actualHitLimb.getName() + " is now " + this.currentWeapon.getToInflictStatus() + ".";
            actualHitLimb.addStatus(this.currentWeapon.getToInflictStatus());
        }
        this.currentWeapon.loseDurability();
        if (this.currentWeapon.getDurability() <= 0) {
            toReturnString += this.breakWeapon();
        }
        return toReturnString;
    }

    //public ArrayList<Statuses> getStatuses() {
    //return statuses;
    //}

    public Limb acquireRandomLimb() {
        Limb randomLimb = torso;

        Random isItOver = new Random();
        int overInt = isItOver.nextInt(2);
        while (overInt == 1 && randomLimb.acquireUnderlyingLimbs() != null) {
            Random randomLimbChooser = new Random();
            int randomLimbIndex = randomLimbChooser.nextInt(randomLimb.acquireUnderlyingLimbs().size());
            randomLimb = randomLimb.acquireUnderlyingLimbs().get(randomLimbIndex);
            isItOver = new Random();
            overInt = isItOver.nextInt(2);
        }
        return randomLimb;
    }

    public Limb acquireRandomLimb(Limb aimedForLimb) {
        Limb randomLimb = aimedForLimb;

        Random isItOver = new Random();
        int overInt = isItOver.nextInt(2);
        while (overInt == 1 && randomLimb.acquireUnderlyingLimbs() != null) {
            Random randomLimbChooser = new Random();
            int randomLimbIndex = randomLimbChooser.nextInt(randomLimb.acquireUnderlyingLimbs().size());
            randomLimb = randomLimb.acquireUnderlyingLimbs().get(randomLimbIndex);
            isItOver = new Random();
            overInt = isItOver.nextInt(2);
        }
        return randomLimb;
    }

    public String acquireFullInfo() {
        String toReturnString = "";

        toReturnString += "Name: " + this.name + "\n";
        toReturnString += "Current Health: " + this.currentHealth + "\n";
        toReturnString += "Max Health: " + this.maxHealth + "\n";
        toReturnString += "Limbs: " + "\n";
        for (Limb limb : acquireAllLimbs()) {
            toReturnString += "-" + limb.acquireFullInfo() + "\n";
        }
        toReturnString += "Lost Limbs: " + this.lostLimbs + "\n";
        toReturnString += "Inventory: " + this.inventory + "\n";
        toReturnString += "Current Weapon: " + this.currentWeapon + "\n";
        toReturnString += "Position: " + this.position + "\n";
        toReturnString += "Gender: " + this.gender + "\n";
        toReturnString += "Race: " + this.race + "\n";
        toReturnString += "Is AI: " + this.isAi + "\n";

        return toReturnString;
    }

    //public void setStatuses(ArrayList<Statuses> statuses) {
    //this.statuses = statuses;
    //}

/*    public String addStatus(Statuses status){
        String toReturnString = "";
        if(!this.statuses.contains(status)){
            this.statuses.add(status);
        }
        toReturnString += this.name + " is " + status + ".";
        return toReturnString;
    }*/

    //public void removeStatus(Statuses status){
    //this.statuses.remove(status);
    //}

}
