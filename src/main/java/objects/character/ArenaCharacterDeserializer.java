package objects.character;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import objects.character.race.Human;

import java.io.IOException;

public class ArenaCharacterDeserializer extends JsonDeserializer<ArenaCharacter> {

    @Override
    public ArenaCharacter deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        ArenaCharacter newCharacter = new ArenaCharacter();

        newCharacter.setName(node.get("name").asText());
        newCharacter.setMaxHealth(node.get("maxHealth").asInt());
        newCharacter.setCurrentHealth(node.get("maxHealth").asInt());
        newCharacter.setGender(Gender.valueOf(node.get("gender").asText()));
        if(node.get("race").asText().equals("Human")){
            newCharacter.setRace(new Human());
        }
        newCharacter.setIsAi(node.get("isAi").asBoolean());
        newCharacter.setTorso(newCharacter.getRace().getLimbs());

        return newCharacter;
    }
}
