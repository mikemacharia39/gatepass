package attendance;

public class AttClass {

    String name;
    String adm;
    String type;
    String timein;
    String date;


    public AttClass() {
        this.name = "";
        this.adm = "";
        this.type = "";
        this.timein = "";
        this.date = "";

    }

    public AttClass(String name, String adm, String type, String date, String timein) {
        this.name = name;
        this.adm = adm;
        this.type = type;
        this.timein = timein;
        this.date = date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimein() {
        return timein;
    }

    public void setTimein(String timein) {
        this.timein = timein;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}