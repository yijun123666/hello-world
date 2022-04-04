package javacode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ticketDAO {
	//scan all the seat status from that event
	public static int[] getSeatofTicketStatus(String ename) {
		int[]  seatstatus= new int[100];	
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT seat,status FROM ticket where ename='"+ename+"'");
			while(rs.next()) {
				int status=rs.getInt("status");
				int seat=rs.getInt("seat");
				seatstatus[seat]=status;
			}
			rs.close();
			st.close();
			conn.close();
			return seatstatus;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	
}
