package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.CommonService;
import login.MYplayListDTO;
import member.LoginStaticDTO;

public class ChartDAO {
	private Connection con;
	private ChartDTO dto;
	private MYplayListDTO mydto;
	ArrayList<MYplayListDTO> list = new ArrayList<>();
	
	private Opener opener;

	public ChartDAO() {
		opener = new Opener();
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
	

	//관리자 음악 추가
	public void insertMusic(String title, String singer) { 
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 중복확인
		String music_Insert;
		music_Insert = "INSERT INTO System_musicList (singer, title, heart) VALUES(?, ?, 10)"; //임의로 좋아요 초기 10으로 설정.
			try {
				
				ps = con.prepareStatement(music_Insert);
				ps.setString(1,singer );	
				ps.setString(2,title );	
				ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//관리자 음악 삭제
	public void deleteMusic(String title) { 
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 중복확인
		String music_Insert;
		music_Insert = "DELETE FROM System_musicList where title = ? ";
			try {
				
				ps = con.prepareStatement(music_Insert);
				ps.setString(1,title );	
				ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void insertplaylist(String title, String Mname, String userID, String heart) {
		PreparedStatement ps_InsertPlaylist = null;
		
		String sql;
		sql = "  INSERT INTO User_musicList (title, singer, heart, user_id) VALUES(?, ?, ?, ?)";
 
		try {
			userID = LoginStaticDTO.getId();
			ps_InsertPlaylist = con.prepareStatement(sql);
		
			ps_InsertPlaylist.setString(1, title);
			ps_InsertPlaylist.setString(2, Mname);
			ps_InsertPlaylist.setString(3, heart);
			ps_InsertPlaylist.setString(4, userID);
			
			ps_InsertPlaylist.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
//	public ArrayList<ChartDTO> selectAll() { // 최신음악
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		ArrayList<ChartDTO> list = new ArrayList<>();
//		String sql;
//		sql = "SELECT  DISTINCT  num, USER_ID ,TITLE, SINGER FROM musicList ORDER BY num ASC";
//		try {
//			ps = con.prepareStatement(sql);
//			rs = ps.executeQuery();
//
//			while (rs.next()) {
//				dto = new ChartDTO();
//				dto.setNum(rs.getInt("num"));
//				dto.setLike(rs.getInt("heart"));
//				dto.setSinger(rs.getString("singer"));
//				dto.setTitle(rs.getString("title"));
//				list.add(dto);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}
	
	public ArrayList<ChartDTO> MusicplayList () { //재생목록
		
		ArrayList<ChartDTO> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		String sql;
		sql = "SELECT  DISTINCT singer,title FROM User_musicList where user_id = ?  ORDER BY title ASC";
		try {
			ps = con.prepareStatement(sql);
			String id2 = LoginStaticDTO.getId();
			ps.setString(1, id2);	
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ChartDTO();
				
				dto.setSinger(rs.getString("singer"));
				dto.setTitle(rs.getString("title"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ChartDTO> popularAll() { // 인기 차트
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<ChartDTO> list = new ArrayList<>();
		String sql;
		sql = "SELECT * FROM System_musicList ORDER BY heart DESC";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ChartDTO();
				dto.setNum(rs.getInt("num"));
				dto.setLike(rs.getInt("heart"));
				dto.setSinger(rs.getString("singer"));
				dto.setTitle(rs.getString("title"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<ChartDTO> recentAll() { // 최신 목록
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<ChartDTO> list = new ArrayList<>();
		String sql;
		sql = "SELECT * FROM System_musicList ORDER BY num DESC";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new ChartDTO();
				dto.setNum(rs.getInt("num"));
				dto.setLike(rs.getInt("heart"));
				dto.setSinger(rs.getString("singer"));
				dto.setTitle(rs.getString("title"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	//
	public void insertMYlist(String mylist_title, String mylist_singer) { // 플레이리스트 담기
		PreparedStatement ps_Insert = null;
		PreparedStatement ps_Mname = null;
		ResultSet rs_Mname = null;

		// 중복확인
		String same_music;
		same_music = "SELECT * FROM MYmusicList  WHERE (user_id = ?)";
			// where user_id
			try {
				
				ps_Mname = con.prepareStatement(same_music);
				
				String id2 = LoginStaticDTO.getId();
				ps_Mname.setString(1, id2);	
				rs_Mname = ps_Mname.executeQuery();

			String name_check1 = "";// 중복검사를위해 이름을 담을 변수 선언
			while (rs_Mname.next()) {
				name_check1 = rs_Mname.getString("title");// DB에서 노래이름 불러오기
				
			}
			if (mylist_title.equals(name_check1)) {// playlistController 에서 불러드린 플레이리스트에 담을 가수와 노래제목을 가져와서 비교
				opener.alertopen("이미 플레이리스트에 등록되어있어 담을수 없습니다!");
				System.out.println("중복 오류");
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

		// 담는 부분
		String sql;
		sql = "  INSERT INTO MYmusicList (singer, title, user_id) VALUES(?, ?, ?)";
 
		try {
			String User_id = LoginStaticDTO.getId();
			ps_Insert = con.prepareStatement(sql);
			
			ps_Insert.setString(1, mylist_singer);
			ps_Insert.setString(2, mylist_title);
			ps_Insert.setString(3, User_id);
			
			System.out.println("담을 노래 : " + mylist_title);
			ps_Insert.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public ArrayList<MYplayListDTO> selectMYplaylist() { // 플레이리스트
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql;
		sql = "SELECT DISTINCT USER_ID ,TITLE,SINGER FROM MYmusicList WHERE USER_ID = ?";
		// MYplaylist에 담은 칼럼 이름 지정하고 다시 값들을 지정한다
		//(값들을 ROW_NUMBER함수를 사용 - title별로 user_id최신 순으로 번호를 정한다 중복이 되면 1씩증가) 
		//조건문으로 숫가자 1인 것만 출력 중복인 데이터를 표시하지않고 출력할수있다
		try {
			
			ps = con.prepareStatement(sql);
			String id2 = LoginStaticDTO.getId();
			ps.setString(1, id2);	
			
			rs = ps.executeQuery();

			while (rs.next()) {
				mydto = new MYplayListDTO();
				mydto.setSinger(rs.getString("singer"));
				mydto.setTitle(rs.getString("title"));

				list.add(mydto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

//	public void heartCount(int count, String music_name) { 
		public void heartCount(String music_name) { 
		int recentHeart = 0;
		PreparedStatement ps_c = null;
		ResultSet rs_c = null;
		String sql_c;
		sql_c = "SELECT * FROM System_musicList  WHERE title = ? ";
		try {
			ps_c = con.prepareStatement(sql_c);
			ps_c.setString(1, music_name);
			rs_c = ps_c.executeQuery();

			while (rs_c.next()) {
				recentHeart = rs_c.getInt("heart");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement ps = null;

		String count_sql;

		count_sql = "UPDATE System_musicList SET heart = ?  WHERE title = ?";
		try {
			ps = con.prepareStatement(count_sql);

			ps.setLong(1, recentHeart + 1);
			ps.setString(2, music_name);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
