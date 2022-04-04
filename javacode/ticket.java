package javacode;

public class ticket {

	private int status;
	private float price;
	private int seat;
	private int key;
	private String ename=null;
	private String time=null;
	
	public ticket(int status, float price, int seat, int key) {
		this.status=status;
		this.price=price;
		this.seat=seat;
		this.key=key;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatues(int status) {
		this.status=status;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price=price;
	}
	
	public int getSeat() {
		return seat;
	}
	
	public void setSeat(int seat) {
		this.seat=seat;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key=key;
	}
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time=time;
	}
	
	public String getEname() {
		return ename;
	}
	
	public void setEname(String ename) {
		this.ename=ename;
	}
}
