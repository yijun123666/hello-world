package javacode;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class eventDAO {

//add vname
	public static boolean insertevent(event e) {
		try {
			Connection conn = JDBCTool.getConnection();
			PreparedStatement ps = conn.prepareStatement("insert into event values (?,?,?,?)");
			ps. setString(1, e.getEname());
			ps. setString(2, e.getType());
			ps. setString(3, e.getTime());
			ps. setString(4,e.getVname());
			ps. executeUpdate();
			conn.close();
			return true;
		} catch (SQLException ee) {
			ee.printStackTrace();
			return false;
		}
		
	}
	
	//show all the events in the web page
	public static List<event> getEvents() {
		try {
			List<event> events = new ArrayList<event>();
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM event");
			while(rs.next()) {
				String ename = rs.getString("ename");
				String type = rs.getString("type");
				String time = rs.getString("time");
				String vname=rs.getString("vname");
				event e = new event(ename,type,time,vname);
				events.add(e);
			}
			rs.close();
			st.close();
			conn.close();
			return events;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//show the event according to the specific type:
	public static List<event> getSpecificTypeEvent(String type ) {
		List<event>  events= new ArrayList<event>();
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM event where type ='"+type+"'");
			while(rs.next()) {
				String type1 =rs.getString("type");
				String ename=rs.getString("ename");
				String time=rs.getString("time");
				String vname=rs.getString("vname");
				event e=new event(ename,type1,time,vname);
				events.add(e);
			}
			conn.close();
			return events;
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//what types of our existing events:
	public static List<String> getEventType() {
		List<String> types = new ArrayList<String>();
			
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT distinct type FROM event");
			
			while(rs.next()) {
				String type=rs.getString("type");
				types.add(type);
			}
			rs.close();
			st.close();
			conn.close();
			return types;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	//get the number of events
	/*public static int getEventNumber() {
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT count(*) FROM event");
			rs.next();
			int n=rs.getInt(1);
			rs.close();
			st.close();
			conn.close();
			return n;
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}*/
	
	//get the one event
	public static event getEventbyEname(String ename) {			
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM event where ename='"+ename+"'");
			if(rs.next()){
				String type =rs.getString("type");
				String ename1=rs.getString("ename");
				String time=rs.getString("time");
				String vname=rs.getString("vname");
				event e = new event(ename1,type,time,vname);
				rs.close();
				st.close();
				conn.close();
				return e;
				}
			else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	//get the number of the venue which holds that event
	public static int getNumberofSeat(String ename) {
		try {
			Connection conn = JDBCTool.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT num_seat FROM venue where vname=(SELECT vname FROM event where ename='"+ename+"')");
			rs.next();
			int n=rs.getInt(1);
			rs.close();
			st.close();
			conn.close();
			return n;
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void initialTicket(event e,int num) {
		try {
			Connection conn = JDBCTool.getConnection();
			for(int i=1;i<=num;i++) {
				PreparedStatement ps = conn.prepareStatement("insert into ticket(seat, price,ename) values (?,?,?)");
				ps. setInt(1, i);
				ps. setInt(2,100);
				ps.	setString(3, e.getEname());
				ps. executeUpdate();
			}
			conn.close();
			
			
		} catch (SQLException ee) {
			ee.printStackTrace();
		}
		
	}

}
