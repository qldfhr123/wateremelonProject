package member;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.Opener;


public class MypageController implements Initializable {
	

	@FXML private TextField id;
	@FXML private PasswordField pw;
	@FXML private Button loginBtn;
	@FXML private Button regCancel;
	@FXML private Button regBtn;
	@FXML private Label myP; 
	@FXML private Label nameLabel;
	@FXML private TextField regID;
	@FXML private PasswordField regPW;
	
	@FXML private TextField regName;
	@FXML private ComboBox<String> regAge;
	@FXML private Button memUpdate;
	@FXML private Button memDelete;
	@FXML private HBox nameBox;
	@FXML private HBox userId;
	@FXML private HBox userAge;
	
	
	private ObservableList<Node> la; 
	private ArrayList<MemberDTO> useridlist;
	private ArrayList<MemberDTO> memlist;


	private Stage myStage;
	private Parent myForm;
	private MemberDAO memberDao;
	private MemberDTO memberDto;
	private Opener opener;
	private loginDTO loginDto;
	  private Stage stage;
	
	
	private MemberController m1; 
	

	
	
	 public void setStage(Stage stage) {
		    this.stage = stage;
	}

	
	public void setmyStage(Stage myStage) {
		this.myStage = myStage;
	}
	
	public void setmyForm(Parent myForm) {
		this.myForm = myForm;
	}
	
	public void setOpener(Opener opener) {
		this.opener = opener;
	}
	
	
	
	public void setMember1(MemberController m1) {
		this.m1 = m1;
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		memberDao = new MemberDAO();
		memberDto = new MemberDTO();
		opener = new Opener();

		nameLabel(loginDto);
		userId(loginDto);
		userAge(loginDto);
	
	}
	


	//메인창에서 마이페이지버튼 눌렀을때 화면
	
			public void nameLabel(loginDTO loginDto) {
				
		
				String userName = LoginStaticDTO.getName();
				System.out.println("이름1:"+userName);
				
				Label l1 = new Label();
				l1.setText(userName);
				System.out.println(userName);
			
				nameBox.setAlignment(Pos.CENTER_LEFT);
				nameBox.getChildren().add(l1);
				
		 	
			}
			
			public void userId(loginDTO loginDto) {
				
				
				String userID = LoginStaticDTO.getId();
				System.out.println("아이디:"+userID);
				
				Label l2 = new Label();
				l2.setText(userID);
				System.out.println(userID);
			
				userId.setAlignment(Pos.CENTER_LEFT);
				userId.getChildren().add(l2);
				
		 	
			}
			
			public void userAge(loginDTO loginDto) {
				
				
				String userage = LoginStaticDTO.getAge();
				System.out.println("연령대:"+userage);
				
				Label l3 = new Label();
				l3.setText(userage);
				System.out.println(userage);
			
				userAge.setAlignment(Pos.CENTER_LEFT);
				userAge.getChildren().add(l3);
				
		 	
			}
			
			
		
		// 마이페이지창에서 회원정보수정버튼을 눌렀을때 
			public void myPUProc() {
			
			opener.myUpdateOpen();
			Opener.windowClose(memUpdate);
			
		}
	

	// 마이페이지창에서 회원탈퇴버튼을 눌렀을때 
	
	public void myDelete() {
		
		String user_id = LoginStaticDTO.getId();
		
		memberDao.delete(user_id);

		opener.alertopen("회원탈퇴되었습니다.");

		

		
		
//		Opener.windowClose(memDelete); //회원가입창삭제
//		opener.NmainOpen();
//		Opener.windowClose(stage); // 오프너에서 스테이지를 가져와야 삭제가능... 
		opener.allWinClose(); //시스템종료
		
		
	}
}
	


	



