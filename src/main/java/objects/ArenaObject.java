package objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

public interface ArenaObject {

    String getName();

    int getPosition();

    void setPosition(int position);

    JSONObject toJSON() throws JsonProcessingException;

}
