package main;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import member.MemberController;
import member.MypageController;
import member.loginDTO;
import system.SystemController;

public class Opener {
	private Stage primaryStage;
	private Stage regStage;
	private Parent regForm;
	private Stage myStage;
	private loginDTO loginDto;
	private Opener opener;

//	@FXML private BorderPane bp;

//	loginDTO loginDto = new loginDTO();
	MemberController memCon = new MemberController();
	MypageController memCon1 = new MypageController();

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void loginDTO(loginDTO logindto) {
		this.loginDto = logindto;
	}

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	// 로그인창을 실행하는 기능
	public void loginOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/member/login.fxml"));
		Stage loginStage = new Stage();
		regStage = new Stage();
		try {
			Parent regForm = loader.load();
			memCon = loader.getController();
			memCon.setOpener(opener);
			memCon.setmyStage(regStage);
			memCon.setmyForm(regForm);

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			regForm.getStylesheets().add(getClass().getResource("/member/member.css").toExternalForm());

			memCon.setloginDTO(loginDto);

			loginStage.setScene(new Scene(regForm));
			loginStage.setTitle("로그인창");
			loginStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인했을떄 로그인메인으로 바꿔주는 기능
	public void mainOpen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/Lmain.fxml"));
			Parent mainlogin = loader.load();
			Scene scene = new Scene(mainlogin);

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			scene.getStylesheets().add(getClass().getResource("/main/main.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setTitle("watermelon");
			primaryStage.show();

			memCon1.setStage(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 관리자페이지를 열어주는 기능
	public void systemOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/system/SystemManagement.fxml"));

		regStage = new Stage();
		try {
			Parent regForm = loader.load();
			SystemController sysCon = new SystemController();
			sysCon = loader.getController();

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			regForm.getStylesheets().add(getClass().getResource("/system/SystemManagement.css").toExternalForm());

			regStage.setScene(new Scene(regForm));
			regStage.setTitle("관리자창");
			regStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원탈퇴했을떄 비로그인메인창 (아직 미완성)

//		public void NmainOpen() {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/Nmain.fxml"));
//			regStage = new Stage();
//			try {
//				regForm = loader.load();
//				memCon = loader.getController();
//				memCon.setRegStage(regStage);
//				memCon.setRegForm(regForm);
//				
//				regStage.setScene(new Scene(regForm));
//				regStage.setTitle("메인화면1");
//				regStage.show();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	// 회원가입 화면을 실행하는 기능 (로그인창에서 회원가입버튼 클릭)
	public void regOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/member/register.fxml"));

		regStage = new Stage();
		try {
			regForm = loader.load();
			memCon = loader.getController();
			memCon.setRegStage(regStage);
			memCon.setRegForm(regForm);

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			regForm.getStylesheets().add(getClass().getResource("/member/member.css").toExternalForm());

			ComboBox<String> ageCombo = (ComboBox<String>) regForm.lookup("#regAge");
			ageCombo.setValue("연령대선택");
			ageCombo.getItems().addAll("10대미만", "10대", "20대", "30대", "40대", "50대", "60대이상");

			regStage.setScene(new Scene(regForm));
			regStage.setTitle("회원가입 화면");
			regStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 마이페이지 화면을 실행하는 기능 (메인에서 마이페이지버튼을 눌러서 실행하는 창)
	public void myPageOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/member/myPage.fxml"));
		myStage = new Stage();

		try {
			Parent myForm = loader.load();
			memCon1 = loader.getController();
			memCon1.setmyStage(myStage);
			memCon1.setmyForm(myForm);
			memCon1.setMember1(memCon);

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			myForm.getStylesheets().add(getClass().getResource("/member/member.css").toExternalForm());

			myStage.setScene(new Scene(myForm));
			myStage.setTitle("마이페이지");
			myStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원정보수정 화면을 실행하는 기능 (마이페이지에서 회원정보수정버튼을 눌러서 실행하는 창)
	public void myUpdateOpen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/member/myUpdate.fxml"));

		regStage = new Stage();
		try {
			regForm = loader.load();
			MemberController memCon = loader.getController();
			memCon.setRegStage(regStage);
			memCon.setRegForm(regForm);

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			regForm.getStylesheets().add(getClass().getResource("/member/member.css").toExternalForm());

			regStage.setScene(new Scene(regForm));
			regStage.setTitle("회원정보수정");
			regStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 창닫는것
	public static void windowClose(Stage stage) {
		stage.close();
	}

	public static void windowClose(Parent form) {
		Stage stage = (Stage) form.getScene().getWindow();
		stage.close();
	}

	// 메인창닫기 (강제전체종료)
	public void allWinClose() {
		System.exit(0);

	}

	// Alert창 디자인
	public void alertopen(String contextText) {

		// // Alert 창 생성
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("WaterMelon");
		alert.setHeaderText("알림");
		alert.setContentText(contextText);

		// Alert 중간부분 (알림)
		// ------------------------------------------------------------------------------------------------------
		// DialogPane 생성
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle("-fx-background-color: none;");
		Font font1 = Font.loadFont(getClass().getResourceAsStream("/fonts/NotoSansKR-Bold.otf"), 12);
		Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/NotoSansKR-Regular.otf"), 12);

		// 전체창크기
		dialogPane.setPrefWidth(350); // 원하는 가로 크기
		dialogPane.setPrefHeight(180); // 원하는 세로 크기

		// Alert 맨위부분

		dialogPane.getStyleClass().remove("alert"); // 인포메이션 느낌표 삭제!!!!!!!!!

		GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
		grid.setStyle(
				"-fx-background-color: #EFFBF5;" + "-fx-font-family:'Noto Sans KR Bold';" + "-fx-font-size:17px;");

		HBox logoBox = new HBox();
		ImageView imgView = new ImageView("img/수박아이콘.png");
		imgView.setFitWidth(50);
		imgView.setFitHeight(50);
		logoBox.getChildren().add(imgView);

		logoBox.setMaxHeight(24);

		dialogPane.setStyle("-fx-background-color: none;" + "-fx-font-family:'Noto Sans KR';" + "-fx-font-size:15px;");
		logoBox.setAlignment(Pos.CENTER);
		dialogPane.setGraphic(logoBox);

		// Alert 아래부분 버튼바설정

		ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
		buttonBar.setStyle("-fx-font-size: 15px;" + "-fx-background-color: none;");
		buttonBar.getButtons().forEach(b -> {
			b.setStyle("-fx-font-family: \"Andalus\"; -fx-background-color: #CEF6D8;" + "-fx-border-color: #088A4B;"
					+ "-fx-border-radius: 2px;");
		});

		// -------------------------------------------------------------------------------------------------------------------

		// DialogPane을 Alert 창에 설정
		alert.setDialogPane(dialogPane);
		alert.showAndWait();
		// Alert 창을 표시하고 결과를 처리
//		alert.showAndWait().ifPresent(result -> {
//			if (result == ButtonType.OK) {
//				System.out.println("OK button clicked");
//			}
//		});

	}
}
