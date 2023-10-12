package common;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.PlaytitleDTO;
import main.ChartDAO;
import main.ChartDTO;
import main.MusicPlayController;

public class HMakeMusicBox {

	/*
	 * 하트가 있는 노래 박스 만드는 class
	 * 
	 * 비로그인 인기차트(NpopularController), 로그인 인기차트(LpopularController)
	 */

	private MakeAlbumImage MAImage;
	public ChartDAO Dao;
	public ArrayList<ChartDTO> musicList;
	public MusicPlayController MPCon;
	
	
	
//	public void setPopularList(ArrayList<ChartDTO> musicList) { // 인기차트 목록 모두 불러오기
//		this.musicList = musicList;
//		
//	}

	public HMakeMusicBox() {
		MAImage = new MakeAlbumImage();
		MPCon = new MusicPlayController();
//		System.out.println("HMakeMusicBox ChartDAO" + Dao);
//		MPCon.setChartDAO(Dao);
//		System.out.println("musicList"+ musicList);
	}
	
	public void setChartDAO(ChartDAO Dao) {
		this.Dao = Dao;
	}

	public HBox eachSong(String dbSinger, String dbTitle, String heart, boolean plusButton) {

		HBox songContainer = new HBox();
		songContainer.setPrefWidth(550.0);
		songContainer.setMaxHeight(50.0);
		songContainer.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
		songContainer.setStyle("-fx-background-color: white;");

		VBox heartBox = new VBox();
		heartBox.setPrefWidth(20);
		heartBox.setMaxHeight(100);
		heartBox.setAlignment(Pos.CENTER);
		ImageView heartImg = new ImageView("img/heartLine.png");
		heartImg.setFitHeight(24);
		heartImg.setFitWidth(29);
		Label likeCount = new Label(heart);
		heartBox.getChildren().addAll(heartImg, likeCount);

		VBox AlbumCoverBox = new VBox();
		AlbumCoverBox.setPrefSize(100.0, 200.0);
		AlbumCoverBox.setAlignment(Pos.CENTER);
		ImageView coverImg = new ImageView(MAImage.musicAlbum(dbSinger, dbTitle));
		coverImg.setFitHeight(50);
		coverImg.setFitWidth(52);
		AlbumCoverBox.getChildren().add(coverImg);

		VBox songInfo = new VBox();
		songInfo.setPrefSize(297.0, 50.0);
		songInfo.setSpacing(5.0);
		songInfo.setAlignment(Pos.CENTER_LEFT);
		Label title = new Label(dbTitle);
		Label singer = new Label(dbSinger);
		songInfo.getChildren().addAll(title, singer);

		VBox playBox = new VBox();
		ImageView playBtn = new ImageView("img/playButton.png");
		playBox.setPrefSize(51.0, 50.0);
		playBtn.setFitHeight(24.0);
		playBtn.setFitWidth(21.0);
		playBox.setAlignment(Pos.CENTER);
		playBox.getChildren().add(playBtn);

		EventHandler<Event> play = e -> {
			System.out.println("play 클릭");
			PlaytitleDTO pdto = new PlaytitleDTO();
			pdto.setTitle(dbTitle);
			pdto.setSinger(dbSinger);
			
//			MPCon.setPopularList(musicList);
//			System.out.println("HMakeMusicBox ChartDAO" + Dao);
			
//			MPCon.setChartDAO(Dao);
//		mpc.setPDTO(pdto);

			// 재생창 오픈
			playOpen();
		};
		playBtn.setOnMouseClicked(play);

		VBox AddSongBox = new VBox();
		ImageView addSongBtn = new ImageView("img/담기.png");
		AddSongBox.setPrefSize(51.0, 50.0);
		addSongBtn.setFitHeight(24.0);
		addSongBtn.setFitWidth(21.0);
		AddSongBox.setAlignment(Pos.CENTER);
		AddSongBox.getChildren().addAll(addSongBtn);
		AddSongBox.setVisible(plusButton);

		EventHandler<Event> myplaylist = e -> {
			System.out.println("담기 클릭");
			Dao.insertMYlist(dbTitle, dbSinger);
		};
		addSongBtn.setOnMouseClicked(myplaylist);

		songContainer.getChildren().addAll(heartBox, AlbumCoverBox, songInfo, playBox, AddSongBox);

		return songContainer;
	}

	private void playOpen() {
		FXMLLoader playPageLoader = new FXMLLoader(getClass().getResource("/main/musicPlay.fxml"));
		Stage primaryStage = new Stage();
		try {
			Parent playPage = playPageLoader.load();

			System.setProperty("prism.lcdtext", "false"); // 폰트파일 로드전에 실행(폰트 테두리 선명하게 = 안티앨러싱) ///////////////// 추가
			playPage.getStylesheets().add(getClass().getResource("/main/main.css").toExternalForm());
			
			MPCon = playPageLoader.getController();
//			MPCon.setChartDAO(Dao);
			
			primaryStage.setScene(new Scene(playPage));
			primaryStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
