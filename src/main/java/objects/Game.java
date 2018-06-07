package objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ArenaTooSmallException;
import objects.arena.Arena;
import objects.arena.Types;
import objects.ArenaObject;
import objects.character.ArenaCharacter;
import objects.character.Statuses;
import objects.character.limbs.Limb;
import objects.item.weapon.Weapon;
import org.json.JSONObject;
import repositories.CharacterRepository;
import repositories.WeaponRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

    private String gameOwner;
    private Arena arena;
    @JsonIgnore
    private static WeaponRepository wr;
    @JsonIgnore
    private static CharacterRepository cr;
    private ArrayList<ArenaCharacter> allCharacters;
    private ArrayList<ArenaCharacter> graveyard = new ArrayList<>();
    private ArrayList<Weapon> allWeapons;
    private String gameEvents;
    private String errorMessage = "";
    private boolean gameOver = false;

    public Game() throws IOException {
        wr = new WeaponRepository();
        allWeapons = wr.getAllWeapons();
        cr = new CharacterRepository();
        allCharacters = cr.getAllCharacters();
        startGame();
    }

    public Game(String ownerName, int arenaLength, int arenaHeight) throws IOException {
        try {
            gameOwner = ownerName;
            wr = new WeaponRepository();
            allWeapons = wr.getAllWeapons();
            cr = new CharacterRepository();
            allCharacters = cr.getAllCharacters();
            if ((arenaLength * arenaHeight) < (allCharacters.size() + allWeapons.size())) {
                throw new ArenaTooSmallException("User " + gameOwner + " requested a size of " + arenaLength + "x" + arenaHeight + "=" + (arenaLength * arenaHeight +
                        " but it needs to be at least " + ((allCharacters.size() + allWeapons.size()))));
            }
            startGame(arenaLength, arenaHeight);
        } catch (ArenaTooSmallException ex) {
            errorMessage = ex.getMessage();
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getGameOwner() {
        return gameOwner;
    }

    public void setGameOwner(String gameOwner) {
        this.gameOwner = gameOwner;
    }

    public void startGame() {
        this.arena = generateArena();
    }

    public void startGame(int length, int height) {
        this.arena = generateArena(length, height);
    }

    public String getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(String gameEvents) {
        this.gameEvents = gameEvents;
    }

    public ArrayList<ArenaCharacter> getGraveyard() {
        return graveyard;
    }

    public void setGraveyard(ArrayList<ArenaCharacter> graveyard) {
        this.graveyard = graveyard;
    }

    public ArenaCharacter acquireRandomCharacter() {
        Random randomGen = new Random();
        int index = randomGen.nextInt(allCharacters.size());
        return allCharacters.get(index);
    }

    public void moveCharactersRandom() {
        for (ArenaCharacter character : allCharacters) {
            int toTryPosition = character.getPosition();
            boolean positionTaken;

            ArrayList<Integer> toTryPositions = new ArrayList<Integer>() {{
                if (character.getPosition() % arena.getLength() != 0) {
                    add(toTryPosition + 1);
                }

                if ((character.getPosition() + 1) % arena.getLength() != 0) {
                    add(toTryPosition - 1);
                }

                if (character.getPosition() < arena.getSquares() - arena.getLength()) {
                    add(toTryPosition + arena.getLength());

                    if ((toTryPosition + arena.getLength()) % arena.getLength() != 0) {
                        add(toTryPosition + arena.getLength() + 1);
                    }

                    if ((toTryPosition + arena.getLength() + 1) % arena.getLength() != 0) {
                        add(toTryPosition + arena.getLength() - 1);
                    }

                }

                if (character.getPosition() > arena.getLength()) {
                    add(toTryPosition - arena.getLength());

                    if ((toTryPosition - arena.getLength()) % arena.getLength() != 0) {
                        add(toTryPosition - arena.getLength() + 1);
                    }

                    if ((toTryPosition - arena.getLength() + 1) % arena.getLength() != 0) {
                        add(toTryPosition - arena.getLength() - 1);
                    }

                }
            }};

            Collections.shuffle(toTryPositions);

            for (Integer newPosition : toTryPositions) {
                positionTaken = false;
                for (ArenaCharacter arenaCharacter : allCharacters) {
                    if (character != arenaCharacter && arenaCharacter.getPosition() == newPosition) {
                        positionTaken = true;
                    }
                }

                if (!(newPosition < 0) && !(newPosition > arena.getSquares()) && !(positionTaken)) {
                    character.setPosition(newPosition);
                }
            }
        }
    }


    public String attackAdjacentCharacters() {
        String toReturnString = "";
        for (ArenaCharacter arenaCharacter : allCharacters) {
            ArrayList<Integer> allPossiblePositions = new ArrayList<Integer>();

            for (int x = 1; x <= arenaCharacter.getCurrentWeapon().getRange(); x++) {
                allPossiblePositions.add(arenaCharacter.getPosition() + x);
                allPossiblePositions.add(arenaCharacter.getPosition() - x);
                allPossiblePositions.add(arenaCharacter.getPosition() + arena.getLength() * x);
                allPossiblePositions.add(arenaCharacter.getPosition() - arena.getLength() * x);
                allPossiblePositions.add(arenaCharacter.getPosition() + arena.getLength() * x + x);
                allPossiblePositions.add(arenaCharacter.getPosition() + arena.getLength() * x - x);
                allPossiblePositions.add(arenaCharacter.getPosition() - arena.getLength() * x + x);
                allPossiblePositions.add(arenaCharacter.getPosition() - arena.getLength() * x - x);
            }

            ArrayList<Integer> allAdjacentFilledPositions = new ArrayList<>();
            for (Integer position : allPossiblePositions) {
                if (arena.isThereCharacterAtPosition(position)) {
                    if (!(arenaCharacter.getTorso().getStatuses().contains(Statuses.MARRIED) && arena.acquireCharacterAtPosition(position).getTorso().getStatuses().contains(Statuses.MARRIED))) {
                        allAdjacentFilledPositions.add(position);
                    }
                }
            }
            if (allAdjacentFilledPositions.size() != 0) {
                Random random = new Random();
                int randomTargetPosition = allAdjacentFilledPositions.get(random.nextInt(allAdjacentFilledPositions.size()));
                ArenaCharacter defendingCharacter = arena.acquireCharacterAtPosition(randomTargetPosition);
                Limb targetedLimb = defendingCharacter.acquireRandomLimb();
                toReturnString += arenaCharacter.attack(defendingCharacter, targetedLimb);
            }
        }
        return toReturnString;
    }


    public String nextTurn() {
        String toReturnString = "";
        ArrayList<ArenaCharacter> charactersToAttack = new ArrayList<>(cr.getAllCharacters());
        Collections.shuffle(charactersToAttack);

        moveCharactersRandom();


        for (ArenaCharacter character : allCharacters) {
            for (Weapon weapon : allWeapons) {
                if (weapon.getPosition() == character.getPosition()) {
                    if(character.getCurrentWeapon().getName().equals("Fists")) {
                        toReturnString += character.equipWeapon(weapon);
                    }else{
                        toReturnString += character.putInInventory(weapon);
                    }
                    weapon.setPosition(arena.getSquares() + 1);
                }
            }
        }


        for(ArenaCharacter character : allCharacters){
            if(character.getCurrentWeapon().getName().equals("Fists") && character.getInventory().size() != 0){
                if(character.getInventory().get(0) instanceof Weapon){
                    Weapon toEquipWeapon = (Weapon) character.getInventory().get(0);
                    toReturnString += character.equipFromInventory(toEquipWeapon);
                }
            }
        }

        toReturnString += attackAdjacentCharacters();

        toReturnString += "\n";

        ArrayList<ArenaCharacter> deadCharacters = acquireNewDeadCharacters();

        for (ArenaCharacter deadCharacter : deadCharacters) {
            toReturnString += deadCharacter.getName() + " died.";

            if (deadCharacter.getTorso().getStatuses().contains(Statuses.MARRIED)){
                ArenaCharacter marriedChar = null;
                for(ArenaCharacter arenaCharacter : allCharacters){
                    if(arenaCharacter.getTorso().getStatuses().contains(Statuses.MARRIED)){
                        arenaCharacter.setCurrentHealth(0);
                        toReturnString += arenaCharacter.getName() + " died of sadness.";
                        marriedChar = arenaCharacter;
                        break;
                    }
                }
                allCharacters.remove(marriedChar);
                graveyard.add(marriedChar);
            }

            int characterPosition = deadCharacter.getPosition();
            if (!deadCharacter.getCurrentWeapon().getName().equals("Fists")) {
                deadCharacter.getCurrentWeapon().setPosition(characterPosition);
            }
            allCharacters.remove(deadCharacter);
        }

        updateArena();

        //toReturnString = toReturnString.replace(".", ".\n\n");

        if (allCharacters.size() == 1 || (allCharacters.size() == 2 && (allCharacters.get(0).getTorso().getStatuses().contains(Statuses.MARRIED)&&allCharacters.get(1).getTorso().getStatuses().contains(Statuses.MARRIED)))) {
            gameOver = true;
        }

        gameEvents = toReturnString;
        return toReturnString;
    }

    public ArrayList<ArenaCharacter> acquireNewDeadCharacters() {
        ArrayList<ArenaCharacter> newDeadCharacters = new ArrayList<>();

        for (ArenaCharacter character : allCharacters) {
            if (character.getCurrentHealth() <= 0) {
                newDeadCharacters.add(character);
                graveyard.add(character);
            }
        }

        return newDeadCharacters;
    }

    public Weapon acquireRandomWeapon() {
        Random randomGen = new Random();
        int index = randomGen.nextInt(allWeapons.size());
        return allWeapons.get(index);
    }

    public void updateArena() {
        ArrayList<ArenaObject> allObjects = new ArrayList<>();

        for (ArenaObject weapon : allWeapons) {
            allObjects.add(weapon);
        }

        for (ArenaObject character : allCharacters) {
            allObjects.add(character);
        }

        arena.setArenaObjects(allObjects);

    }

    private boolean acquireRandomChance(int chancePercent) {
        Random randomGen = new Random();
        int randomInt = randomGen.nextInt(100);
        if (randomInt < chancePercent) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ArrayList<ArenaCharacter> getAllCharacters() {
        return allCharacters;
    }

    public void setAllCharacters(ArrayList<ArenaCharacter> allCharacters) {
        this.allCharacters = allCharacters;
    }

    public ArrayList<Weapon> getAllWeapons() {
        return allWeapons;
    }

    public void setAllWeapons(ArrayList<Weapon> allWeapons) {
        this.allWeapons = allWeapons;
    }

    public Arena generateArena() {
        ArrayList<ArenaCharacter> allCharacters = this.allCharacters;
        ArrayList<Weapon> allWeapons = this.allWeapons;

        ArrayList<ArenaObject> allObjects = new ArrayList<>();

        for (ArenaObject aObject : allCharacters) {
            allObjects.add(aObject);
        }

        for (ArenaObject aObject : allWeapons) {
            allObjects.add(aObject);
        }

        Arena arena = new Arena("TestArena", 10, 10, allObjects, Types.DESERT);
        return arena;
    }

    public Arena generateArena(int length, int height) {
        ArrayList<ArenaCharacter> allCharacters = this.allCharacters;
        ArrayList<Weapon> allWeapons = this.allWeapons;

        ArrayList<ArenaObject> allObjects = new ArrayList<>();

        for (ArenaObject aObject : allCharacters) {
            allObjects.add(aObject);
        }

        for (ArenaObject aObject : allWeapons) {
            allObjects.add(aObject);
        }

        Arena arena = new Arena("TestArena", length, height, allObjects, Types.DESERT);
        return arena;
    }

    public Arena getArena() {
        return arena;
    }

    public JSONObject toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(this));
        return jsonObject;
    }


}
