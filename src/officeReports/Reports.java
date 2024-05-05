package officeReports;

public class Reports {

    String name;
    String adm;
    String timein;
    String date;

    public Reports() {
        this.name = "";
        this.adm = "";
        this.timein = "";
        this.date = "";
    }

    public Reports(String name, String adm, String timein, String date) {
        this.name = name;
        this.adm = adm;
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

