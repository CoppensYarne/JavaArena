package objects.character;

public enum Gender {
    Male("Male", "his", "him"), Female("Female", "her", "her");


    String name;
    String possession;
    String person;

    Gender(String name, String possession, String person) {
        this.name = name;
        this.possession = possession;
        this.person = person;
    }

    public String getName(){
        return this.name;
    }

    public String getPossession(){
        return this.possession;
    }

    public String getPerson(){
        return this.person;
    }

}

