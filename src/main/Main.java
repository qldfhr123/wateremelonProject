package main;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

import javafx.animation.FadeTransition;
public class Main extends Application {

	private Stage primaryStage;
	private Parent loadingPage;
	private Parent mainPage;
	private Parent homePage;
	private Parent loginPage;

	private BorderPane mainBp;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
//        System.out.println(bp);
		loadPages();

		showLoadingPage();
	}

	private void loadPages() throws Exception {
		FXMLLoader loadingPageLoader = new FXMLLoader(getClass().getResource("startPage.fxml"));
		loadingPage = loadingPageLoader.load();

		FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/main/Nmain.fxml"));
		mainPage = mainPageLoader.load();
		

		FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("/member/login.fxml"));
		loginPage = loginPageLoader.load();
		loginPageLoader.getController();

		NChartController ncc = mainPageLoader.getController();

		Opener opener = new Opener();
		opener.setPrimaryStage(primaryStage);
		ncc.setOpener(opener);

		// 비로그인 첫 화면에 나오는 인기차트, 동영상 페이지.
		FXMLLoader NhomeLoader = new FXMLLoader(getClass().getResource("/main/home.fxml"));
		homePage = NhomeLoader.load();
		mainBp = (BorderPane) mainPage.lookup("#bp"); // Nhome.fxml 넣어주려고 가져옴
		mainBp.setCenter(homePage);

	}

	private void showLoadingPage() {
		primaryStage.setScene(new Scene(loadingPage));
		primaryStage.show();

		ImageView logo = (ImageView) loadingPage.lookup("#logo");
		shakeLogo(logo);

		// 2초 후에 메인 페이지로 전환
		FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(1), loadingPage);
		fadeOutTransition.setFromValue(1.0);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished(event -> showMainPage());
		fadeOutTransition.setDelay(Duration.seconds(2));
		fadeOutTransition.play();

	}

	private void showMainPage() {
		System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱)      /////////////////  추가
		mainPage.getStylesheets().add(getClass().getResource("/main/main.css").toExternalForm());
		
		primaryStage.setTitle("watermelon");
		primaryStage.setScene(new Scene(mainPage));

	}

	private void shakeLogo(ImageView logo) {
		RotateTransition rotateTransition = new RotateTransition(Duration.millis(100), logo);
		rotateTransition.setFromAngle(-5); // 회전 시작 각도
		rotateTransition.setToAngle(5); // 회전 종료 각도
		rotateTransition.setCycleCount(20); // 회전 횟수
		rotateTransition.setAutoReverse(true); // 자동으로 반대 방향으로 회전
		rotateTransition.play();
	}
}