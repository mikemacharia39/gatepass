package gatereports;

public class Reports {

	String name;
	String adm;
	String timein;
	String date;
	String type;
	
	public Reports()
	{
		this.name="";
		this.adm="";
		this.timein="";
		this.date="";
		this.type="";
	}
	public Reports(String name, String adm, String type, String timein, String date)
	{
		this.name=name;
		this.adm= adm;
		this.timein= timein;
		this.date=date;
		this.type=type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

