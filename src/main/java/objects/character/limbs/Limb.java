package objects.character.limbs;

import objects.character.Statuses;

import java.util.ArrayList;

public interface Limb {

    int getCurrentHealth();
    int getMaxHealth();
    String getName();

    ArrayList<Statuses> getStatuses();
    void setStatuses(ArrayList<Statuses> statuses);

    void addStatus(Statuses status);
    void removeStatus(Statuses status);

    void getDamaged(int amount);

    ArrayList<Limb> acquireUnderlyingLimbs();
    void setUnderlyingLimbs(ArrayList<Limb> limbs);

    String toString();

    String acquireFullInfo();

    int getDamageMultiplier();

    void setDamageMultiplier(int damageMultiplier);

    void setCritical(boolean critical);
    boolean isCritical();

}
