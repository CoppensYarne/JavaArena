package objects.arena;

import objects.ArenaObject;
import objects.character.ArenaCharacter;

import java.util.ArrayList;
import java.util.Random;

public class Arena {

    private String name;
    private ArrayList<ArenaObject> arenaObjects;
    private int length;
    private int height;
    private int squares;
    private Types type;

    public Arena(String name, int length, int height, ArrayList<ArenaObject> arenaObjects, Types type) {
        this.name = name;
        this.length = length;
        this.height = height;
        this.type = type;
        this.squares = length * height;
        this.arenaObjects = new ArrayList<>();
        ArrayList<Integer> filledPos = new ArrayList<>();
        for(ArenaObject arenaObject : arenaObjects){
            int randomPos = acquireRandomPosition();
            while(filledPos.contains(randomPos)){
                randomPos = acquireRandomPosition();
            }
            filledPos.add(randomPos);
            arenaObject.setPosition(randomPos);
        }
        this.arenaObjects = arenaObjects;
    }

    private int acquireRandomPosition(){
        Random randomGen = new Random();
        int randomPos = randomGen.nextInt(squares);
        return randomPos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArenaCharacter acquireCharacterAtPosition(int position){

        for(ArenaObject arenaCharacter : arenaObjects){
            if(arenaCharacter instanceof ArenaCharacter){
                if(arenaCharacter.getPosition() == position){
                    return (ArenaCharacter) arenaCharacter;
                }
            }
        }
        return null;
    }

    public boolean isThereCharacterAtPosition(int position){
        for(ArenaObject arenaCharacter : arenaObjects){
            if(arenaCharacter instanceof ArenaCharacter){
                if(arenaCharacter.getPosition() == position){
                    return true;
                }
            }
        }
        return false;
    }

    public void setArenaObjects(ArrayList<ArenaObject> arenaObjects){
        this.arenaObjects = arenaObjects;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSquares() {
        return squares;
    }

    public void setSquares(int squares) {
        this.squares = squares;
    }

    public Types getType() {
        return type;
    }

    public void removeFromArena(ArenaObject aObject){
        this.arenaObjects.remove(aObject);
    }

    public ArrayList<ArenaObject> getArenaObjects() {
        return arenaObjects;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public String toString() {
        int longestNameLength = 0;
        for(ArenaObject character : this.arenaObjects){
            if(longestNameLength < character.getName().length() && character instanceof ArenaCharacter){
                longestNameLength = character.getName().length();
            }
        }
        String toReturn = "";

        for(int x = 0; x < this.squares; x++){
            toReturn += "[";
            boolean posWithChar = false;
            for(ArenaObject character : arenaObjects){
                if(character.getPosition() == x){
                    posWithChar = true;
                    String toPad;
                    if(character instanceof ArenaCharacter){
                        toPad = character.getName();
                    }else{
                        toPad = "x";
                    }
                    toReturn += String.format("%-"+longestNameLength+"s", toPad);
                }
            }
            if(!posWithChar){
                for(int y = 0; y<longestNameLength; y++){
                    toReturn += ' ';
                }
            }
            toReturn += "]";
            if((x+1)%length == 0){
                toReturn += "\n";
            }
        }
        return toReturn;
    }
}
