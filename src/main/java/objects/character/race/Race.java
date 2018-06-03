package objects.character.race;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import objects.character.limbs.Limb;
import objects.character.limbs.Torso;

import java.util.ArrayList;

public interface Race {

    @JsonIgnore
    Torso getLimbs();

    String getName();

}
