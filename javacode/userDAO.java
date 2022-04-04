package javacode;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javacode.wallet;
import java.util.Map;

public class userDAO {
	
	//cancel the ticket
	   public static void userconfirmcancel(String uname,int tickets) throws Exception {
	  //获得公钥
	     wallet.extractUserWallet(uname);
	     String pubkey=wallet.keyMap.get(0);
	     //用私钥加密一个秘文
	     String ciphertext= wallet.encryptByPrivateKey("Cancel");
	    
	     //帮我得到这些信息
	     String info=ClientApp.main(new String[] {"queryTicket",String.valueOf(tickets)});
	     String ID=wallet.getTicketID(info);
	     float  price=Float.parseFloat(wallet.getTicketPrice(info));
	     int seat=Integer.valueOf(wallet.getTicketSeat(info));
	     String ename=wallet.getTicketename(info);
	       //调取函数
	     cancelDAO.cancelTicket(uname,ename,seat,tickets,price,ciphertext);
	    }
	
	//log-in:
	public static user Login(String uname, String password, String pubkey,float balance,int tickets) {
		Connection conn=null;
		try {
			conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM user WHERE uname='"+uname+"' AND password='"+password+"'");
			if(rs.next()) {
				String uname1 = rs.getString("username");
				String password1 = rs.getString("password");
				String pubkey1 = rs.getString("pubkey");
				float balance1=rs.getFloat("balance");
				int tickets1=rs.getInt("tickets");
				user u = new user(uname1,password1,pubkey1,balance1,tickets1);
				return u;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return null;
	}
	//register the user account
	public static boolean CreateUser(String uname,String password) {
		Connection conn = null;
		try {
			conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM user WHERE uname='"+uname+"'");
			if(rs.next()) {
				return false;
				}
			else {
				rs.close();
				st.close();
				PreparedStatement ps = conn.prepareStatement("insert into user values (?,?,?)");
				ps.setString(1,uname);
				ps.setString(2,password);
				//ps.setString(3,generate.genKeyPair().get(0));
				ps.executeUpdate();
				return true;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		finally {
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return false;
	}
	
	//increase the balance
		public static void increaseBalance(int num,String uname) {	
			try {
				Connection conn = JDBCTool.getConnection();
				PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance+? where uname=?");
				ps.setInt(1, num);
				ps.setString(2, uname);
				ps.executeUpdate();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	//
		public static void userconfirmtransfer(String olduname,String newuname,int tickets) throws Exception {
			//获得公钥
			wallet.extractUserWallet(olduname);
			String pubkey=wallet.keyMap.get(0);
			//用私钥加密一个秘文
			String ciphertext= wallet.encryptByPrivateKey("Owner");
			//调用transfer函数
			transferDAO.transferTicket(ciphertext,pubkey,tickets,olduname,newuname);
		}
	
	
	//这个function先废掉不管
	/*public static user getUser(String uname) {			
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT balance FROM user where uname='"+uname+"'");
			if(rs.next()){
				String pubkey1 =rs.getString("pubkey");
				String uname1=rs.getString("uname");
				String password=rs.getString("password");
				float balance=rs.getFloat("balance");
				int tickets=rs.getInt("tickets");
				user u = new user(uname1,password,pubkey1,balance,tickets);
				rs.close();
				st.close();
				conn.close();
				return u;
				}
			else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	
	
}
