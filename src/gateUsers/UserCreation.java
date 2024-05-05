package gateUsers;

public class UserCreation {

    String name;
    String level;

    public UserCreation() {
        this.name = "";
        this.level = "";
    }

    public UserCreation(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
