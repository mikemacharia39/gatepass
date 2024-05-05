package logs;

public class LogClass {

    String name;
    String level;
    String indatetime;
    String outdatetime;


    public LogClass() {
        this.name = "";
        this.level = "";
        this.indatetime = "";
        this.outdatetime = "";

    }

    public LogClass(String name, String level, String indatetime, String outdatetime) {
        this.name = name;
        this.level = level;
        this.indatetime = indatetime;
        this.outdatetime = outdatetime;
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

    public String getIndatetime() {
        return indatetime;
    }

    public void setIndatetime(String indatetime) {
        this.indatetime = indatetime;
    }

    public String getOutdatetime() {
        return outdatetime;
    }

    public void setOutdatetime(String outdatetime) {
        this.outdatetime = outdatetime;
    }
}
