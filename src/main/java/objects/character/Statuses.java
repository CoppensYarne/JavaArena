package objects.character;

public enum Statuses {

    BLEEDING("Bleeding"), POISONED("Poisoned"), PREGNANT("Pregnant"), FROZEN("Frozen"), NONE("Healthy");

    private String name;

    Statuses(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
