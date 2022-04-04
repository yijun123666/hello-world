package javacode;

public class user {

	private String uname;
	private String password;
	private String pubkey;
	private float balance;
	private int tickets;
	
	
	public user(String uname,String password, String pubkey,float balance,int tickets) {
		this.uname=uname;
		this.password=password;
		this.pubkey=pubkey;
		this.balance=balance;
		this.tickets=tickets;
	}
	
	public String getUname() {
		return uname;
	}
	
	public void setUname(String uname) {
		this.uname=uname;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getPubKey() {
		return pubkey;
	}
	
	public void setPubKey(String pubkey) {
		this.pubkey=pubkey;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public void setBalance(float balance) {
		this.balance=balance;
		
	} 
	public int getTicket() {
		return tickets;
	}
	
	public void setTicket(int tickets) {
		this.tickets=tickets;
	}
}
