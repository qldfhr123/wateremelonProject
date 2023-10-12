package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LChartController implements Initializable {
	private ChartDAO Dao;
	private ArrayList<ChartDTO> musicList;
//	private Parent form;
	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

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
	private Label Mhome, Mpopular, Mrecent, Mplaylist, M_Myplaylist;

	// footer icon 모음.
	@FXML
	private ImageView homeIcon, popularIcon, searchIcon, myListIcon;
	
	
	// 위쪽 페이지 이동 버튼

	private Parent homePage;
	private Parent LrecentPage;
	private Parent LpopularPage;
	private Parent Lplaylist;
	private Parent Lmusiclist;
	private Opener opener;

	public void setOpener(Opener opener) {
		this.opener = opener;
	}

	@FXML
	private void logo_home(MouseEvent event) {
//        loadPage("Lmain");
		loadPage(homePage);
		Mhome.setStyle("-fx-background-color:#01DF74");
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:transparent");
		Mplaylist.setStyle("-fx-background-color:transparent");
		M_Myplaylist.setStyle("-fx-background-color:transparent");
	}

	@FXML
	private void HomeClick(MouseEvent event) {
		loadPage(homePage);
		Mhome.setStyle("-fx-background-color:#01DF74");
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:transparent");
		Mplaylist.setStyle("-fx-background-color:transparent");
		M_Myplaylist.setStyle("-fx-background-color:transparent");
	}

	@FXML
	private void popularClick(MouseEvent event) {
//		loadPage(LpopularPage);
		try {
			LpopularPage = FXMLLoader.load(getClass().getResource("/login/Lpopular.fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bp.setCenter(LpopularPage);
		Mhome.setStyle("-fx-background-color:transparent");
		Mpopular.setStyle("-fx-background-color:#01DF74");/**/
		Mrecent.setStyle("-fx-background-color:transparent");
		Mplaylist.setStyle("-fx-background-color:transparent");
		M_Myplaylist.setStyle("-fx-background-color:transparent");
	}

	@FXML
	private void recentClick(MouseEvent event) {
//		loadPage(LrecentPage);
		try {
			LrecentPage = FXMLLoader.load(getClass().getResource("/login/Lrecent.fxml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bp.setCenter(LrecentPage);
		Mhome.setStyle("-fx-background-color:transparent");
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:#01DF74");/**/
		Mplaylist.setStyle("-fx-background-color:transparent");
		M_Myplaylist.setStyle("-fx-background-color:transparent");
	}

	@FXML
	private void playlistClick(MouseEvent event) { //재생목록
//		loadPage(Lmusiclist);
//		 Parent LmusicList;
			try {
				Lmusiclist = FXMLLoader.load(getClass().getResource("/login/LmusicList.fxml"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bp.setCenter(Lmusiclist);
		Mhome.setStyle("-fx-background-color:transparent");
		Mpopular.setStyle("-fx-background-color:transparent");
		Mrecent.setStyle("-fx-background-color:transparent");
		Mplaylist.setStyle("-fx-background-color:#01DF74");/**/
		M_Myplaylist.setStyle("-fx-background-color:transparent");
	}

	@FXML
    private void MyplaylistClick(MouseEvent event) { //플레이리스트
//	 Parent Lplaylist;
	try {
		Lplaylist = FXMLLoader.load(getClass().getResource("/login/LplayList.fxml"));
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	bp.setCenter(Lplaylist);
//        loadPage(Lmusiclist);
        Mhome.setStyle("-fx-background-color:transparent");
        Mpopular.setStyle("-fx-background-color:transparent");
        Mrecent.setStyle("-fx-background-color:transparent");
        Mplaylist.setStyle("-fx-background-color:transparent");
        M_Myplaylist.setStyle("-fx-background-color:#01DF74");/**/
    }

	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("연결 테스트");
		Dao = new ChartDAO();
//		musicList = Dao.selectAll();
		// 미리 로딩을 시켜놔야 메뉴 이동시 버퍼링이 거의 없음.
		try {
			homePage = FXMLLoader.load(getClass().getResource("/main/home.fxml"));
//			LrecentPage = FXMLLoader.load(getClass().getResource("/login/Lrecent.fxml"));
//			LpopularPage = FXMLLoader.load(getClass().getResource("/login/Lpopular.fxml"));
//			Lmusiclist = FXMLLoader.load(getClass().getResource("/login/LmusicList.fxml"));
//			Lplaylist = FXMLLoader.load(getClass().getResource("/login/LplayList.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bp.setCenter(homePage);
		iconChange();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), (ActionEvent event) -> {
			reLoadPage();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

	}

	private void loadPage(Parent root) {

		bp.setCenter(root);

	}
	
	private void reLoadPage() {

		try {
			LrecentPage = FXMLLoader.load(getClass().getResource("/login/Lrecent.fxml"));
			LpopularPage = FXMLLoader.load(getClass().getResource("/login/Lpopular.fxml"));
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

//        loadPage("Nhome");
			loadPage(homePage);

		});
		popularIcon.setOnMouseClicked(event -> {
			// 클릭 시 이미지 변경
			Image newImage = new Image("img/trophy(on).png");
			popularIcon.setImage(newImage);
			homeIcon.setImage(homeOff);
			searchIcon.setImage(searchOff);
			myListIcon.setImage(myListOff);

//        loadPage("Npopular");
			loadPage(LpopularPage);

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

	// 메인창에서 마이페이지버튼 눌렀을때
	public void myPP() {

		Stage primaryStage = new Stage();
		Opener opener = new Opener();
		setOpener(opener);
		opener.setPrimaryStage(primaryStage);

		opener.myPageOpen();

	}

}