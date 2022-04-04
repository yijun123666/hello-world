package javacode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class buyDAO {
	public static void buyTicket(String vname,int seatnum, String ename,String uname) throws Exception {
		float tprice=PriceofTicket(ename,seatnum);
		decreaseBalance(tprice,uname);
		setTicketStatus(seatnum,ename);
		createTicket(vname,ename,seatnum,uname);
	}
		
	   //decrease the balance
		public static void decreaseBalance(float tprice,String uname) {	
			try {
				Connection conn = JDBCTool.getConnection();
				PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance-? where uname=?");
				ps.setFloat(1, tprice);
				ps.setString(2, uname);
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		
	//when buy the ticket then change its status
	public static void setTicketStatus(int seatnum,String ename) {	
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE ticket SET status = 0 where (seat=? AND ename=?)");
			ps.setInt(1, seatnum);
			ps.setString(2, ename);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	//insert the hash value into user's tickets
	public static void insertTicketstoUser(String uname,int hash) {
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT into tickets values (?,?)");
			ps.setString(1, uname);
			ps.setInt(2, hash);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	//get the time of the event
	public static String TimeofTicket(String ename) {
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT time FROM event where ename='"+ename+"'");
			rs.next();
			String time=rs.getString("time");
			rs.close();
			st.close();
			conn.close();
			return time;
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//get the price of event
	public static float PriceofTicket(String ename,int seatnum) {
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT price FROM ticket where ename='"+ename+"' AND seat="+seatnum);
			rs.next();
			float price=rs.getFloat("price");
			rs.close();
			st.close();
			conn.close();
			return price;
			
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	//get the key
	
	//get the ID
	
	//invoke the blockchain to creatTicket
	public static void createTicket(String vname,String ename,int seatnum,String uname) throws Exception {
		int hash=wallet.ticketHash(getTicket(seatnum,ename));
		insertTicketstoUser(uname,hash);
		String ID=wallet.encryptByPrivateKey(String.valueOf(hash));
		System.out.println("ID!!!!!"+ID);
	    String price=String.valueOf(PriceofTicket(ename,seatnum));
		System.out.println("price!!!!!"+price);
	    String time=TimeofTicket(ename);
		System.out.println("time!!!!!"+time);
		ClientApp.main(new String[] {"createTicket",String.valueOf(hash),ID,ename,time,String.valueOf(seatnum),price,uname});
}
	
	public static ticket getTicket(int seatnum,String ename) {	
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ticket where ename='"+ename+"' AND seat="+seatnum);
			if(rs.next()){
				int status =rs.getInt("status");
				float price=rs.getFloat("price");
				int seat =rs.getInt("seat");
				int key=rs.getInt("key");
				ticket t = new ticket(status,price,seat,key);
				t.setEname(ename);
				t.setTime(TimeofTicket(ename));
				rs.close();
				st.close();
				conn.close();
				return t;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
		
	}


