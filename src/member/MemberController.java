package member;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Opener;

public class MemberController implements Initializable {

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

	private Stage regStage;
	private Stage primaryStage;
	private Parent regForm;
	private Stage updateStage;
	private Parent updateForm;
	private MemberDAO memberDao;
	private MemberDTO memberDto;
	private Opener opener;
	private loginDTO loginDto;
	private MypageController m2;
	private String systemID;
	

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

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setloginDTO(loginDTO loginDto) {
		this.loginDto = loginDto;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		memberDao = new MemberDAO();
		memberDto = new MemberDTO();
		opener = new Opener();
	}

	// ==================================================== 로그인

	// 로그인창에서 로그인버튼을 눌렀을때 진행
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

	// 메인창에서 마이페이지버튼 눌렀을때
	public void myPP() {

		Stage primaryStage = new Stage();
		Opener opener = new Opener();
		setOpener(opener);
		opener.setPrimaryStage(primaryStage);

		opener.myPageOpen();

	}

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
