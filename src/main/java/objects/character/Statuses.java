package objects.character;

public enum Statuses {

    BLEEDING("Bleeding"),
    POISONED("Poisoned"),
    PREGNANT("Pregnant"),
    FROZEN("Frozen"),
    BURNING("Burning"),
    BLINDED("Blinded"),
    BEDAZZLED("Bedazzled"),
    MARRIED("Married"),
    NONE("Healthy");

    private String name;

    Statuses(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
