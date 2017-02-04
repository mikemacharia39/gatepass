package officeentry;

public class OfficeClass {

	String no;
	String name;
	String adm;
	String timein;
	String date;
	
public OfficeClass()
{
	this.name="";
	this.adm="";
	this.no="";
	this.timein="";
	this.date="";
	
}
public OfficeClass(String no, String name, String adm,  String timein, String date)
{
	this.name=name;
	this.adm= adm;
	this.no=no;
	this.timein= timein;
	this.date=date;
	
}
public String getNo() {
	return no;
}
public void setNo(String no) {
	this.no = no;
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