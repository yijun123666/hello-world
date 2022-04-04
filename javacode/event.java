package javacode;

public class event {
	
	private String ename;
	private String type;
	private String time;
	private String vname;

	
	
	public event(String ename,String type, String time,String vname) {
		this.ename = ename;
		this.type = type;
		this.time = time;
		this.vname=vname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
	}

	
}





	