package objects.item.weapon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import objects.ArenaObject;
import objects.character.Statuses;
import objects.item.Item;
import org.json.JSONObject;

public class Weapon implements Item, ArenaObject {

    private String Name;
    private String PastVerb;
    private String PresentVerb;
    private int Durability;
    private Statuses ToInflictStatus;
    private int Damage;
    private int Range;
    private int Position;

    public Weapon(String name, String pastVerb, String presentVerb, int damage, int range, int durability, Statuses toInflictStatus) {
        this.Name = name;
        this.PastVerb = pastVerb;
        this.PresentVerb = presentVerb;
        this.Durability = durability;
        this.ToInflictStatus = toInflictStatus;
        this.Damage = damage;
        this.Range = range;
    }

    public Weapon(){
        super();
    }

    public String getPresentVerb() {
        return PresentVerb;
    }

    public void setPresentVerb(String presentVerb) {
        PresentVerb = presentVerb;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        this.Range = range;
    }

    public String getName() {
        return Name;
    }

    @Override
    public int getPosition() {
        return Position;
    }

    @Override
    public void setPosition(int position) {
        this.Position = position;
    }

    @Override
    public JSONObject toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String toReturnJSON = mapper.writeValueAsString(this);
        return new JSONObject(toReturnJSON);
    }

    public void setPastVerb(String pastVerb) {
        PastVerb = pastVerb;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getDurability() {
        return Durability;
    }

    public void setDurability(int durability) {
        this.Durability = durability;
    }

    public Statuses getToInflictStatus() {
        return ToInflictStatus;
    }

    public void setToInflictStatus(Statuses toInflictStatus) {
        this.ToInflictStatus = toInflictStatus;
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int damage) {
        this.Damage = damage;
    }

    public void loseDurability(){
        this.Durability -= 1;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "Name='" + Name + '\'' +
                ", Durability=" + Durability +
                ", Damage=" + Damage +
                ", Range=" + Range +
                '}';
    }

    public String getPastVerb() {
        return PastVerb;
    }
}
