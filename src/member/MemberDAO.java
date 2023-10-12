package member;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

 public class MemberDAO {
	 private Connection con;
	 
	public MemberDAO() {
		connection();
	}
	
	public void connection() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "oracle";
		String password = "oracle";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// 로그인, 비밀번호확인 후 로그인
	public String login(String id) {
		String sql = "SELECT pw FROM member WHERE id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("pw");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	// id중복확인
	public int idcheck(String id) {
		String sql = "SELECT count(id) FROM member WHERE id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next())
				return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	
	
	// db에 고객정보 저장(1)
	
	MemberDTO memberDto;
	
	public MemberDTO insert(String id, String pw, String name, String age, String memberday) {
		String sql = "INSERT INTO member VALUES(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, age);
			ps.setString(5, memberday);

			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return memberDto;
	}
	
	// db에 고객정보 저장(2)
	public void add(MemberDTO memberDto) {	
		String sql = "INSERT INTO member VALUES(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, memberDto.getId());
			ps.setString(2, memberDto.getPw());
			ps.setString(3, memberDto.getName());
			ps.setString(4, memberDto.getAge());
			ps.setString(5, memberDto.getRegisterDay());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
	// DB에서 고객정보삭제 
	
	public int delete(String id) {
		String sql = "DELETE FROM member WHERE id=?";
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	// db에서 고객정보수정 (비밀번호만 수정예정)
	public void update(loginDTO loginDto) {
		
		String sql = "UPDATE member SET pw = ? WHERE id = ? ";
		PreparedStatement ps = null;
		
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, loginDto.getPw());
			ps.setString(2, loginDto.getId());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 전체회원 정보를 볼 수 있는 
	
	public ArrayList<MemberDTO> selectAll() {
		
		ArrayList<MemberDTO> memlist = new ArrayList <>();
		String sql = "SELECT * FROM member ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setName(rs.getString("name"));
				dto.setId(rs.getString("id"));
				dto.setAge(rs.getString("age"));
				memlist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memlist;
	}
	
	
	
	
	// 한사람정보만 확인하기 
	public ArrayList<MemberDTO> myselect(String id) {
		
		ArrayList<MemberDTO> memlist = new ArrayList <>();
		
		String sql = "SELECT * FROM member WHERE id = ? ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				MemberDTO dto = new MemberDTO();
				
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getString("age"));
				dto.setRegisterDay(rs.getString("memberday"));
				
				memlist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memlist;
	}
	
	
 
}
 
