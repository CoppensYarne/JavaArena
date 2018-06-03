package repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import objects.item.weapon.Weapon;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WeaponRepository {

    ArrayList<Weapon> allWeapons;

    public WeaponRepository() throws IOException {
        this.allWeapons = loadAllWeapons();
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

    private ArrayList<Weapon> loadAllWeapons() throws IOException {
        ArrayList<Weapon> allWeapons = new ArrayList<>();
        JSONArray allWeaponsJSON = new JSONArray(readFile(System.getProperty("user.dir") + "/src/main/java/jsonFiles/weapons.json"));

        for(Object weaponObject : allWeaponsJSON){
            JSONObject weaponJSON = (JSONObject) weaponObject;
            ObjectMapper mapper = new ObjectMapper();
            Weapon newWeapon = mapper.readValue(weaponJSON.toString(), Weapon.class);
            allWeapons.add(newWeapon);
        }
        return allWeapons;
    }

    public ArrayList<Weapon> getAllWeapons(){
        return this.allWeapons;
    }



}
