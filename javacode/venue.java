package javacode;

public class venue {

	private String vname;
	private int num_seat;
	private String address;
	
	public venue(String vname, int num_seat,String address) {
		this.vname=vname;
		this.num_seat=num_seat;
		this.address=address;
	}
	
	public String getVname() {
		return vname;
	}
	
	public void setVname(String vname) {
		this.vname=vname;
	}
	
	public int getNumSeat() {
		return num_seat;
	}
	
	public void setNumSeat(int num_seat) {
		this.num_seat=num_seat;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address){
		this.address=address;
	}
}
