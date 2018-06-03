package main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private Arena arena;
    @JsonIgnore
    private static WeaponRepository wr;
    @JsonIgnore
    private static CharacterRepository cr;
    private ArrayList<ArenaCharacter> allCharacters;
    private ArrayList<Weapon> allWeapons;
    private String gameEvents;

    public Game() throws IOException {
        wr = new WeaponRepository();
        allWeapons = wr.getAllWeapons();
        cr = new CharacterRepository();
        allCharacters = cr.getAllCharacters();
        startGame();
    }

    public Game(int arenaLength, int arenaHeight) throws IOException {
        wr = new WeaponRepository();
        allWeapons = wr.getAllWeapons();
        cr = new CharacterRepository();
        allCharacters = cr.getAllCharacters();
        startGame(arenaLength, arenaHeight);
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

    public ArenaCharacter acquireRandomCharacter() {
        Random randomGen = new Random();
        int index = randomGen.nextInt(allCharacters.size());
        return allCharacters.get(index);
    }

    public void moveCharactersRandom() {
        for (ArenaCharacter character : allCharacters) {
            int toTryPosition = character.getPosition();
            boolean positionTaken;

            ArrayList<Integer> toTryPositions = new ArrayList<>() {{
                add(toTryPosition + 1);
                add(toTryPosition + arena.getLength());
                add(toTryPosition - 1);
                add(toTryPosition - arena.getLength());
                add(toTryPosition - arena.getLength() + 1);
                add(toTryPosition - arena.getLength() - 1);
                add(toTryPosition + arena.getLength() + 1);
                add(toTryPosition + arena.getLength() - 1);
            }};

            Collections.shuffle(toTryPositions);

            for (Integer newPosition : toTryPositions) {
                positionTaken = false;
                for (ArenaCharacter arenaCharacter : allCharacters) {
                    if (character != arenaCharacter && arenaCharacter.getPosition() == newPosition) {
                        positionTaken = true;
                    }
                }

                if (!(newPosition < 0) && !(newPosition > arena.getSquares()) && !(positionTaken) && (!(newPosition % arena.getLength() == 0)||arena.getLength() == 1)) {
                    character.setPosition(newPosition);
                }
            }

            /*while (newPosition == character.getPosition() || newPosition < 0 || newPosition > arena.getSquares() || positionTaken || newPosition % arena.getLength() == 0) {
                positionTaken = false;
                newPosition = character.getPosition();
                Random random = new Random();
                int randomInt = random.nextInt(8);
                switch (randomInt) {
                    case 0:
                        newPosition += 1;
                        break;
                    case 1:
                        newPosition += arena.getLength();
                        break;
                    case 2:
                        newPosition -= 1;
                        break;
                    case 3:
                        newPosition -= arena.getLength();
                        break;
                    case 4:
                        newPosition -= arena.getLength() + 1;
                    case 5:
                        newPosition -= arena.getLength() - 1;
                    case 6:
                        newPosition += arena.getLength() + 1;
                    case 7:
                        newPosition += arena.getLength() - 1;
                }
                for (ArenaCharacter arenaCharacter : allCharacters) {
                    if (character != arenaCharacter && arenaCharacter.getPosition() == newPosition) {
                        positionTaken = true;
                    }
                }
            }
            character.setPosition(newPosition);
        }*/
        }
    }


        public String attackAdjacentCharacters () {
            String toReturnString = "";
            for (ArenaCharacter arenaCharacter : allCharacters) {
                ArrayList<Integer> allPossiblePositions = new ArrayList<>() {{
                    add(arenaCharacter.getPosition() + 1);
                    add(arenaCharacter.getPosition() - 1);
                    add(arenaCharacter.getPosition() + arena.getLength());
                    add(arenaCharacter.getPosition() - arena.getLength());
                    add(arenaCharacter.getPosition() + arena.getLength() + 1);
                    add(arenaCharacter.getPosition() + arena.getLength() - 1);
                    add(arenaCharacter.getPosition() - arena.getLength() + 1);
                    add(arenaCharacter.getPosition() - arena.getLength() - 1);
                }};
                ArrayList<Integer> allAdjacentFilledPositions = new ArrayList<>();
                for (Integer position : allPossiblePositions) {
                    if (arena.isThereCharacterAtPosition(position)) {
                        allAdjacentFilledPositions.add(position);
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


        public String nextTurn () {
            String toReturnString = "";
            ArrayList<ArenaCharacter> charactersToAttack = new ArrayList<>(cr.getAllCharacters());
            Collections.shuffle(charactersToAttack);

            for (ArenaCharacter character : allCharacters) {
                for (Limb limb : character.getTorso().acquireUnderlyingLimbs()) {
                    if (limb.getStatuses().contains(Statuses.BLEEDING)) {
                        toReturnString += character.takeDamage(5, limb);
                    }
                    if (limb.getStatuses().contains(Statuses.POISONED)) {
                        toReturnString += character.takeDamage(10, limb);
                    }
                }
            }

            toReturnString += "\n";

            moveCharactersRandom();

            toReturnString += attackAdjacentCharacters();

            toReturnString += "\n";

            ArrayList<ArenaCharacter> deadCharacters = getNewDeadCharacters();
            if (deadCharacters.size() != 0) {
                toReturnString += deadCharacters;
            }

            toReturnString += "\n";

            for (ArenaCharacter deadCharacter : getNewDeadCharacters()) {
                toReturnString += deadCharacter.getName() + " died.";
                int characterPosition = deadCharacter.getPosition();
                if (!deadCharacter.getCurrentWeapon().getName().equals("Fists")) {
                    deadCharacter.getCurrentWeapon().setPosition(characterPosition);
                }
                allCharacters.remove(deadCharacter);
            }

            toReturnString += "\n";

            for (ArenaCharacter character : allCharacters) {
                for (Weapon weapon : allWeapons) {
                    if (weapon.getPosition() == character.getPosition()) {
                        toReturnString += character.equipWeapon(weapon);
                        weapon.setPosition(arena.getSquares() + 1);
                    }
                }
            }

            toReturnString += "\n";

            updateArena();

            toReturnString = toReturnString.replace(".", ".\n\n");

            gameEvents = toReturnString;
            return toReturnString;
        }

        public ArrayList<ArenaCharacter> getNewDeadCharacters () {
            ArrayList<ArenaCharacter> newDeadCharacters = new ArrayList<>();

            for (ArenaCharacter character : allCharacters) {
                if (character.getCurrentHealth() <= 0) {

                    newDeadCharacters.add(character);
                }
            }

            return newDeadCharacters;
        }

        public Weapon acquireRandomWeapon() {
            Random randomGen = new Random();
            int index = randomGen.nextInt(allWeapons.size());
            return allWeapons.get(index);
        }

        public void updateArena () {
            ArrayList<ArenaObject> allObjects = new ArrayList<>();

            for (ArenaObject weapon : allWeapons) {
                allObjects.add(weapon);
            }

            for (ArenaObject character : allCharacters) {
                allObjects.add(character);
            }

            arena.setArenaObjects(allObjects);

        }

        private boolean acquireRandomChance ( int chancePercent){
            Random randomGen = new Random();
            int randomInt = randomGen.nextInt(100);
            if (randomInt < chancePercent) {
                return true;
            } else {
                return false;
            }
        }

        public boolean isGameOver () {
            if (allCharacters.size() == 1) {
                return true;
            } else {
                return false;
            }
        }

        public ArrayList<ArenaCharacter> getAllCharacters () {
            return allCharacters;
        }

        public void setAllCharacters (ArrayList < ArenaCharacter > allCharacters) {
            this.allCharacters = allCharacters;
        }

        public ArrayList<Weapon> getAllWeapons () {
            return allWeapons;
        }

        public void setAllWeapons (ArrayList < Weapon > allWeapons) {
            this.allWeapons = allWeapons;
        }

        public Arena generateArena () {
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

        public Arena generateArena ( int length, int height){
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

        public Arena getArena () {
            return arena;
        }

        public JSONObject toJSON () throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObject = new JSONObject(mapper.writeValueAsString(this));
            return jsonObject;
        }


    }
