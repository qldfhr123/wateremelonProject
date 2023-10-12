package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class HomeController implements Initializable {

	@FXML
	private BorderPane bp;

	@FXML
	private Pane homePane;

	@FXML
	private VBox homeVBox;

	@FXML
	private VBox TodayVideoBox;

	@FXML
	private ScrollPane RecentVideoBox;

	@FXML
	private ScrollPane PopularVideoBox;
	
	@FXML
	private Label NhomeLabel1, NhomeLabel2, NhomeLabel3;

	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(homeVBox);
		System.out.println(TodayVideoBox);

		TodayVideoBox.getChildren().add(recomendMusic(550.0, 250.0, "/video/세븐틴_April_shower.mp4"));

		RecentVideoBox.setContent(recentMusicBox());
		PopularVideoBox.setContent(recentMusicBox());

	}

	private StackPane recomendMusic(double width, double height, String video) {

		StackPane videoBox = new StackPane();
		videoBox.setPrefSize(width, height);
		videoBox.setStyle("-fx-background-color: white;");
		videoBox.setAlignment(Pos.CENTER);

		// 동영상 파일 경로
		String videoPath = getClass().getResource(video).toExternalForm();

		// Media 객체 생성
		Media media = new Media(videoPath);

		// MediaPlayer 객체 생성
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setMute(true); // 소리는 안들리게일단 설정함.

		ImageView playPauseBtn = new ImageView();
		Image playPauseImg = new Image("/img/videoPlayButton.png");
		playPauseBtn.setImage(playPauseImg);
		playPauseBtn.setFitHeight(40.0);
		playPauseBtn.setFitWidth(40.0);
		playPauseBtn.setVisible(false);

		// MediaView 객체 생성 및 MediaPlayer 설정
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(width); // 너비 설정
		mediaView.setFitHeight(height); // 높이 설정

		videoBox.setOnMouseEntered(e -> playPauseBtn.setVisible(true));
		videoBox.setOnMouseExited(e -> playPauseBtn.setVisible(false));

		videoBox.getChildren().addAll(mediaView, playPauseBtn);

		mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
//			System.out.println("observable "+observable);
//			System.out.println("oldValue "+oldValue);
//			System.out.println("newValue "+newValue);

			if (newValue == MediaPlayer.Status.READY) {

				// 동영상 준비가 완료된 상태일 때

				mediaPlayer.setOnEndOfMedia(() -> {
					// 동영상을 처음으로 되감기
					mediaPlayer.seek(Duration.ZERO);
				});

				playPauseBtn.setImage(new Image("/img/videoPlayButton.png"));
				playPauseBtn.setOnMouseClicked(e -> {
					mediaPlayer.seek(Duration.ZERO);
					mediaPlayer.play();
				});
				mediaPlayer.play();
			}
			if (newValue == MediaPlayer.Status.PAUSED) { // 멈췄을 때, play버튼이 나오도록 설정.
				playPauseBtn.setImage(new Image("/img/videoPlayButton.png"));
				playPauseBtn.setOnMouseClicked(e -> mediaPlayer.play());
			} else if (newValue == MediaPlayer.Status.PLAYING) { // 재생할 때,pause버튼이 나오도록 설정.
				playPauseBtn.setImage(new Image("/img/videoPauseButton.png"));
				playPauseBtn.setOnMouseClicked(e -> mediaPlayer.pause());
			}

		});

		return videoBox;

	}

	private StackPane MVplayer(double width, double height, String video, Boolean autoPlay) {

		StackPane videoBox = new StackPane();
		videoBox.setPrefSize(200, 200);
		videoBox.setStyle("-fx-background-color: white;");
//		videoBox.setStyle("-fx-border-color: black;");
		videoBox.setAlignment(Pos.CENTER);

		// 동영상 파일 경로
//		video = "/video/10CM_그라데이션.mp4";
		String videoPath = getClass().getResource(video).toExternalForm();

		// Media 객체 생성
		Media media = new Media(videoPath);

		// MediaPlayer 객체 생성
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(autoPlay); // 재생 안 함으로 설정
		mediaPlayer.setMute(true); // 소리는 안들리게일단 설정함.

		ImageView playPauseBtn = new ImageView();
		Image playPauseImg = new Image("/img/videoPlayButton.png");
		playPauseBtn.setImage(playPauseImg);
		playPauseBtn.setFitHeight(40.0);
		playPauseBtn.setFitWidth(40.0);
		playPauseBtn.setVisible(false);

		// MediaView 객체 생성 및 MediaPlayer 설정
		MediaView mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(width); // 너비 설정
		mediaView.setFitHeight(height); // 높이 설정
//		mediaView.setPreserveRatio(false); // 비율 유지 비활성화
		
		videoBox.setOnMouseEntered(e -> playPauseBtn.setVisible(true));
		videoBox.setOnMouseExited(e -> playPauseBtn.setVisible(false));

//		mediaView.setClip(videoBox.getClip());
//		videoBox.widthProperty().addListener((obs, oldVal, newVal) -> {
//			mediaView.setFitWidth(newVal.doubleValue()); // 가로 크기에 맞게 설정
//			mediaView.setFitHeight(newVal.doubleValue()); // 세로 크기에 맞게 설정
//		});

		videoBox.heightProperty().addListener((obs, oldVal, newVal) -> {
//			mediaView.setFitWidth(newVal.doubleValue()); // 가로 크기에 맞게 설정
			mediaView.setFitHeight(newVal.doubleValue()); // 세로 크기에 맞게 설정
		});

		videoBox.getChildren().addAll(mediaView, playPauseBtn);

		mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
//			System.out.println("observable "+observable);
//			System.out.println("oldValue "+oldValue);
//			System.out.println("newValue "+newValue);

			if (newValue == MediaPlayer.Status.READY) {

				// 동영상 준비가 완료된 상태일 때
				mediaPlayer.seek(Duration.seconds(90)); // 1분 30초로 이동.(임의로 정한 썸네)

				mediaPlayer.setOnEndOfMedia(() -> {
					// 동영상을 처음으로 되감기
					mediaPlayer.seek(Duration.ZERO);
				});

				playPauseBtn.setImage(new Image("/img/videoPlayButton.png"));
				playPauseBtn.setOnMouseClicked(e -> {
					mediaPlayer.seek(Duration.ZERO);
					mediaPlayer.play();
				});
//				mediaPlayer.play();
			}
			else if (newValue == MediaPlayer.Status.PAUSED) { // 멈췄을 때, play버튼이 나오도록 설정.
				playPauseBtn.setImage(new Image("/img/videoPlayButton.png"));
				playPauseBtn.setOnMouseClicked(e -> mediaPlayer.play());
			} else if (newValue == MediaPlayer.Status.PLAYING) { // 재생할 때,pause버튼이 나오도록 설정.
				playPauseBtn.setImage(new Image("/img/videoPauseButton.png"));
				playPauseBtn.setOnMouseClicked(e -> mediaPlayer.pause());
			}

		});

		return videoBox;

	}

	private HBox recentMusicBox() {
		HBox recent = new HBox();
		recent.setStyle("-fx-background-color: white;");
//		recent.setMaxHeight(220.0);

		recent.getChildren().addAll(
				MVplayer(200.0, 100.0, "/video/10CM_그라데이션.mp4", false),
				MVplayer(200.0, 100.0, "/video/세븐틴_April_shower.mp4", false),
				MVplayer(200.0, 100.0, "/video/세븐틴_April_shower.mp4", false)
//				MVplayer(200.0, 100.0, "/video/세븐틴_April_shower.mp4", false),
//				MVplayer(200.0, 100.0, "/video/세븐틴_April_shower.mp4", false)
				);
		recent.setSpacing(10);

		return recent;
	}



}