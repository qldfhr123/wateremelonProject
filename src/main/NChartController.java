package main;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import member.LoginStaticDTO;
import member.MemberDAO;
import member.MemberDTO;
import member.MypageController;
import member.loginDTO;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;

public class NChartController implements Initializable {
	private ChartDAO Dao;
	private ArrayList<ChartDTO> musicList;
//	private Parent form;
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

//	public void setForm(Parent form) {
//		this.form = form;
//	}

	@FXML
	private TextField id;
	@FXML
	private PasswordField pw;
	@FXML
	private Button loginBtn;
	@FXML
	private Button regCancel;
	@FXML
	private Button regBtn;
	@FXML
	private Label myP;
	@FXML
	private Label logIN;
	@FXML
	private TextField regID;
	@FXML
	private PasswordField regPW;
	@FXML
	private TextField regName;
	@FXML
	private ComboBox<String> regAge;
	@FXML
	private Button memUpdate;
	@FXML
	private Button memDelete;
	@FXML
	private HBox nameBox;
	@FXML
	private Button upCancelBtn;
	@FXML
	private PasswordField currentPW;
	@FXML
	private PasswordField newPW;
	@FXML
	private Button upBtn;

	@FXML
	private BorderPane bp;

	@FXML
	private MediaView media;

	@FXML
	private Pane centerPane;

	@FXML
	private HBox footerBar;

	// 메뉴 label
	@FXML
	private Label Mhome, Mpopular, Mrecent;

	// footer icon 모음.
	@FXML
	private ImageView homeIcon, popularIcon, searchIcon, myListIcon;

	// 위쪽 페이지 이동 버튼

	private Parent NhomePage;
	private Parent NrecentPage;
	private Parent NpopularPage;
	private Stage regStage;
	private Parent regForm;
	private Stage updateStage;
	private Parent updateForm;
	private MemberDAO memberDao;
	private MemberDTO memberDto;
	private loginDTO loginDto;
	private MypageController m2;
	private String systemID;

	// 민경
	private Opener opener;

	public void setRegStage(Stage regStage) {
		this.regStage = regStage;
	}

	public void setRegForm(Parent regForm) {
		this.regForm = regForm;
	}

	public void setupdateStage(Stage updateStage) {
		this.updateStage = updateStage;
	}

	public void setupdateForm(Parent updateForm) {
		this.updateForm = updateForm;
	}

	public void setmyStage(Stage updateStage) {
		this.updateStage = updateStage;
	}

	public void setmyForm(Parent updateForm) {
		this.updateForm = updateForm;
	}

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	public void setloginDTO(loginDTO loginDto) {
		this.loginDto = loginDto;
	}

	@FXML
	private void logo_home(MouseEvent event) {
		System.out.println("HomeChlick");
		loadPage(NhomePage);

		primaryStage.setTitle("홈화면");
		Mhome.setStyle("-fx-background-color:#01DF74");
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:transparent");
	}

	@FXML
	private void recentClick(MouseEvent event) {
//		loadPage("Nrecent");
//		loadPage(NrecentPage);
		try {
			NrecentPage = FXMLLoader.load(getClass().getResource("/nonLogin/Nrecent.fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bp.setCenter(NrecentPage);
		Mhome.setStyle("-fx-background-color:transparent");
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:#01DF74");
	}

	@FXML
	private void popularClick(MouseEvent event) {
//		loadPage("Npopular");
//		loadPage(NpopularPage);
		try {
			NpopularPage = FXMLLoader.load(getClass().getResource("/nonLogin/Npopular.fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bp.setCenter(NpopularPage);
		Mhome.setStyle("-fx-background-color:transparent");
		Mpopular.setStyle("-fx-background-color:#01DF74");/**/
		Mrecent.setStyle("-fx-background-color:transparent");
	}

	@FXML
	private void homeClick(MouseEvent event) {
//		loadPage("Nhome");
		loadPage(NhomePage);
		Mhome.setStyle("-fx-background-color:#01DF74");/**/
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:transparent");

	}

	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("연결 테스트");
		Dao = new ChartDAO();
//		musicList = Dao.selectAll();
		// 미리 로딩을 시켜놔야 메뉴 이동시 버퍼링이 거의 없음.
		try {
			NhomePage = FXMLLoader.load(getClass().getResource("/main/home.fxml"));
			NrecentPage = FXMLLoader.load(getClass().getResource("/nonLogin/Nrecent.fxml"));
			NpopularPage = FXMLLoader.load(getClass().getResource("/nonLogin/Npopular.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		iconChange();

//		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30), (ActionEvent event) -> {
//			reLoadPage();
//		}));
//		timeline.setCycleCount(Timeline.INDEFINITE);
//		timeline.play();

	}

	private void loadPage(Parent root) {

//			Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
		bp.setCenter(root);

	}

	private void reLoadPage() {

		try {
			NrecentPage = FXMLLoader.load(getClass().getResource("/nonLogin/Nrecent.fxml"));
			NpopularPage = FXMLLoader.load(getClass().getResource("/nonLogin/Npopular.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void iconChange() {
		Image homeOff = new Image("img/home(off).png");
		Image trophyOff = new Image("img/trophy(off).png");
		Image searchOff = new Image("img/search(off).png");
		Image myListOff = new Image("img/보관함(off).png");

		homeIcon.setOnMouseClicked(event -> {
			// 클릭 시 이미지 변경
			Image newImage = new Image("img/home(on).png");
			homeIcon.setImage(newImage);
			popularIcon.setImage(trophyOff);
			searchIcon.setImage(searchOff);
			myListIcon.setImage(myListOff);

//            loadPage("Nhome");
			loadPage(NhomePage);

		});
		popularIcon.setOnMouseClicked(event -> {
			// 클릭 시 이미지 변경
			Image newImage = new Image("img/trophy(on).png");
			popularIcon.setImage(newImage);
			homeIcon.setImage(homeOff);
			searchIcon.setImage(searchOff);
			myListIcon.setImage(myListOff);

//            loadPage("Npopular");
			loadPage(NpopularPage);

		});
		searchIcon.setOnMouseClicked(event -> {
			// 클릭 시 이미지 변경
			Image newImage = new Image("img/search(on).png");
			searchIcon.setImage(newImage);
			homeIcon.setImage(homeOff);
			popularIcon.setImage(trophyOff);
			myListIcon.setImage(myListOff);
		});

		myListIcon.setOnMouseClicked(event -> {
			// 클릭 시 이미지 변경
			Image newImage = new Image("img/보관함(on).png");
			myListIcon.setImage(newImage);
			homeIcon.setImage(homeOff);
			popularIcon.setImage(trophyOff);
			searchIcon.setImage(searchOff);
		});
	}

	public void loginProc() {

		String dbPw = memberDao.login(id.getText());

		if (pw.getText().isEmpty()) {
			opener.alertopen("비밀번호를 입력하세요");
		} else if (id.getText().isEmpty()) {
			opener.alertopen("아이디를 입력하세요");
		} else if (dbPw != null && dbPw.equals(pw.getText())) {
			opener.alertopen("로그인 성공");

			// 관리자아이디로 로그인시 관리자페이지로

			systemID = "system";

			if (id.getText().equals(systemID)) {
				opener.systemOpen();
				Opener.windowClose(loginBtn);
			} else {

				String user_id = "";
				user_id = id.getText();

				ArrayList<MemberDTO> memlist = memberDao.myselect(user_id);
				for (MemberDTO m : memlist) {

					System.out.println("아이디 : " + m.getId());
					System.out.println("비밀번호 : " + m.getPw());
					System.out.println("이름 : " + m.getName());
					System.out.println("연령대 : " + m.getAge());
					System.out.println("가입일 : " + m.getRegisterDay());
					System.out.println("==========================");

					LoginStaticDTO.setId(m.getId());
					LoginStaticDTO.setPw(m.getPw());
					LoginStaticDTO.setName(m.getName());
					LoginStaticDTO.setAge(m.getAge());
					LoginStaticDTO.setRegisterDay(m.getRegisterDay());

				}

				Opener.windowClose(loginBtn);
				opener.mainOpen();

			}
		} else {
			opener.alertopen("로그인 실패");

		}

	}

	// 메인에서 로그인버튼 누르면 - 로그인창 새창열림

	public void loginPP() {

		opener.setOpener(opener);
		opener.loginOpen();

	}

	// 로그인창에서 회원가입버튼 누를때 실행 - 회원가입창 새창으로
	public void regProc() {
		opener.regOpen();
		Opener.windowClose(loginBtn);
	}

	// 회원가입창에서 등록을 눌렀을때
	public void regin() {

		System.out.println("테스트");

		if (regID.getText().isEmpty()) {
			opener.alertopen("id를 입력하세요");
		} else if (regPW.getText().isEmpty()) {
			opener.alertopen("비밀번호를 입력하세요");
		} else if (regName.getText().isEmpty()) {
			opener.alertopen("이름을 입력하세요");
		} else {

			int idCheck = memberDao.idcheck(regID.getText());

			if (idCheck == 0) {

				opener.alertopen("회원가입이 완료되었습니다.");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String format = sdf.format(new Date());

				memberDao.insert(regID.getText(), regPW.getText(), regName.getText(), regAge.getValue(), format);

				Stage primaryStage = new Stage();

				Opener opener = new Opener();
				setOpener(opener);
				opener.setPrimaryStage(primaryStage);

				Opener.windowClose(regCancel);
			} else if (idCheck >= 1) {
				opener.alertopen("중복아이디입니다.");
			}
		}

	}

	// 회원가입창에서 취소버튼 눌렀을때
	public void regcancel() {

		Stage primaryStage = new Stage();
		Opener opener = new Opener();
		setOpener(opener);
		opener.setPrimaryStage(primaryStage);

		Opener.windowClose(regCancel);

	}

	// ========================================================= 마이페이지

	// 회원정보수정창에서 비밀번호 바꾸고 수정버튼 누를때

	public void myupdateP() {

		String user_id = LoginStaticDTO.getId();

		String dbPw = memberDao.login(user_id);

		if (currentPW.getText().isEmpty()) {
			opener.alertopen("현재비밀번호를 입력하세요");
		} else if (newPW.getText().isEmpty()) {
			opener.alertopen("수정할 비밀번호를 입력하세요");
		} else if (dbPw != null && dbPw.equals(currentPW.getText())) {
			String newPw = newPW.getText();

			opener.alertopen("회원정보가 수정되었습니다");

			loginDTO loginDto = new loginDTO();
			loginDto.setPw(newPw);
			loginDto.setId(user_id);
			memberDao.update(loginDto);

			Opener.windowClose(upBtn);

		} else {
			opener.alertopen("비밀번호를 확인하세요");
		}

	}

	// 회원정보수정창에서 취소를 눌렀을때

	public void muUpCancel() {

		Stage primaryStage = new Stage();
		Opener opener = new Opener();
		setOpener(opener);
		opener.setPrimaryStage(primaryStage);

		Opener.windowClose(upCancelBtn);

	}
}