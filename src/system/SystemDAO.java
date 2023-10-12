package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SystemDAO {
private Connection con;  
	
	public SystemDAO() {  // 생성자 호출
		connection();
	} 
	 
	// 연결
	public void connection() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
//		String url = "jdbc:oracle:thin:@192.168.0.23:1521:xe";
		String user = "oracle";
		String password = "oracle";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	 
	// 연결 해제
	public void disconnection() {
		try {
			if(con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//회원 리스트 전체 출력
	
		public ArrayList<SystemDTO> selectMember() {
			ArrayList<SystemDTO> list = new ArrayList<>();
			String sql = "SELECT * FROM member";
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					SystemDTO systemDTO = new SystemDTO();
					systemDTO.setId(rs.getString("id"));
					systemDTO.setPw(rs.getString("pw"));
					systemDTO.setName(rs.getString("name"));
					systemDTO.setAge(rs.getString("age"));
					systemDTO.setRegisterTime(rs.getString("memberDay"));
					list.add(systemDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
	
	// 리스트 전체 출력
	public ArrayList<SystemDTO> selectMusic() {
		ArrayList<SystemDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM System_musicList";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				SystemDTO systemDTO = new SystemDTO();
				systemDTO.setTitle(rs.getString("title"));
				systemDTO.setSinger(rs.getString("singer"));
				systemDTO.setHeart(rs.getInt("heart"));
				list.add(systemDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// DB 노래제목 검색
	public String getTitle() {
		String sql = "SELECT * FROM System_musicList WHERE title=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("title");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// DB 노래 저장
	public void insertMusic() {
		String sql = "INSERT INTO System_musicList VALUES(?,?,?,?)";  // 좋아요, 제목, 가수
		PreparedStatement ps = null;
	
		try {
			ps =con.prepareStatement(sql);
//			ps.setString(1, DBHeart);
//			ps.setString(2, pw);
//			ps.setString(3, name);
//			ps.setString(4, gender);
//			ps.setString(5, ageScope);
//			ps.setString(6, hobbys);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
