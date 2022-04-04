package javacode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class cancelDAO {
	public static void cancelTicket(String uname,String ename,int seat,int hash,float price,String ciphertext) throws Exception {
		//第一种 检测有没有票 这个不完全  我觉得要用第二个
		//ClientApp.main(new String[] {"ticketExists",String.valueOf(hash)});
		//第二种 采用transfer的认证模式 即 有票且是你的
		String info=ClientApp.main(new String[] {"queryTicket",String.valueOf(hash)});
		String ID=wallet.getTicketID(info);
		String de=wallet.decryptByPublicKey(ciphertext);
		if(de.indexOf("Cancel")!=-1) {
		//contains the cancel
		String dehash=wallet.decryptByPublicKey(ID);//解密调取的从json里已经被加密的ID
			if(dehash.equals(String.valueOf(hash))) {
				//通过认证开始操作退票
				setTicketStatustobeone(seat,ename);//释放位置可以被选；
				increaseBalanceaftercancel(price,uname);//退钱但扣了1的服务费
				deleteTicketstoUser(uname,hash);//删除原来有票人持有该票的信息
				ClientApp.main(new String[] {"deleteTicket",ID});//区块链中加个块去去掉
			}
			else{
				System.out.println("error");
			}
		}else{
			System.out.println("error");
		}
		
	}
	public static void setTicketStatustobeone(int seatnum,String ename) {	
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE ticket SET status = 1 where (seat=? AND ename=?)");
			ps.setInt(1, seatnum);
			ps.setString(2, ename);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public static void increaseBalanceaftercancel(float num,String uname) {	
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("UPDATE user SET balance = balance+? -1 where uname=?");
			ps.setFloat(1, num);
			ps.setString(2, uname);
			ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
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
	
}
