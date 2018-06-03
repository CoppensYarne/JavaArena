package repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import objects.character.ArenaCharacter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CharacterRepository {

    private static ArrayList<ArenaCharacter> allCharacters;

    public CharacterRepository(ArrayList<ArenaCharacter> allCharacters) {
        this.allCharacters = allCharacters;
    }

    public CharacterRepository() throws IOException {
        allCharacters = loadTestCharacters();
    }

    private String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    private ArrayList<ArenaCharacter> loadTestCharacters() throws IOException {
        ArrayList<ArenaCharacter> allCharacters = new ArrayList<>();
        System.out.println();
        JSONArray allCharactersJSON = new JSONArray(readFile(System.getProperty("user.dir") + "\\src\\main\\java\\jsonFiles\\arenaCharacters.json"));

        for(Object characterObject : allCharactersJSON){
            JSONObject characterJSON = (JSONObject) characterObject;
            ObjectMapper mapper = new ObjectMapper();
            ArenaCharacter newCharacter = mapper.readValue(characterJSON.toString(), ArenaCharacter.class);
            allCharacters.add(newCharacter);
        }
        return allCharacters;
    }

    public ArrayList<ArenaCharacter> getAllCharacters(){
        return allCharacters;
    }

}
