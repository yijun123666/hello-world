package javacode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class transferDAO {
	public static void transferTicket(String ciphertext,String pubkey,int hash,String unameofoldowner,String unameofnewowner) throws NumberFormatException,Exception {
		//查询区块链 提起ID出来
		String info=ClientApp.main(new String[] {"queryTicket",String.valueOf(hash)});
		String ID=wallet.getTicketID(info);//先这样不让变红 之后去掉
		String price=null;//抽一下price好扣钱
		//String ID=getID(info); Use the function from MIU
		
		String de=wallet.decryptByPublicKey(ciphertext);
		if(de.indexOf("Owner")!=-1) {
			//contains the owner 
			String dehash=wallet.decryptByPublicKey(ID);//解密调取的从json里已经被加密的ID
			if(dehash.equals(String.valueOf(hash))) {
			ClientApp.main(new String[] {"changeTicketOwne",String.valueOf(hash),unameofnewowner});//在区块链加个块
			decreaseBalance(Float.valueOf(price),unameofnewowner);//扣新的所有者的钱
			increaseBalance(Float.valueOf(price),unameofoldowner);//加原来有票人的钱
			insertTicketstoUser(unameofnewowner,hash);//在mysql数据库中给tickets表中写入新的拥有者及这个票的hash
			deleteTicketstoUser(unameofoldowner,hash);//在mysql数据库中给tickets表中去掉旧的拥有者及这个票的hash
			}
			else {
			System.out.println("error");
			}
		}
		else {
			System.out.println("error");
		}
	}
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
	public static void deleteTicketstoUser(String uname,int hash) {
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE from tickets values (?,?)");
			ps.setString(1, uname);
			ps.setInt(2, hash);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void increaseBalance(float num,String uname) {	
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance+? where uname=?");
			ps.setFloat(1, num);
			ps.setString(2, uname);
			ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void decreaseBalance(float tprice,String unameofnewowner) {	
	try {
		Connection conn = JDBCTool.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance-? where uname=?");
		ps.setFloat(1, tprice);
		ps.setString(2, unameofnewowner);
		ps.executeUpdate();
		ps.close();
		conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}	
}
}
