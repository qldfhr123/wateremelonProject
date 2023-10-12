package main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import login.PlaytitleDTO;
import member.LoginStaticDTO;
import system.SystemDAO;

public class MusicPlayController implements Initializable {

	@FXML
	private BorderPane pane;
	@FXML
	private Slider seekBar;
	@FXML
	private Label songLabel, singer, totalTime, playTime;
	@FXML
	private Button previousButton, nextButton, addBtn;
	@FXML
	private ToggleButton playButton, heartBtn;
	@FXML
	private MediaView mediaView;
	@FXML
	private ImageView albumImage, playMediaImage, heartImage;
	private Media media;
	private MediaPlayer mediaPlayer;
	private double totalT, currentT;

	private File directorySong;
	private File[] fileSong;

	private ArrayList<File> songs;
	private int songNumber;

	// 비로그인 노래 재생 1분 제한
	private Thread preMusic;
	private Duration MAX_DURATION = Duration.minutes(1);

	private Image playImage;
	private Image pauseImage;

	// 좋아요 버튼
	private Image heartLine;
	private Image fillHeart;

	private SystemDAO systemDAO = new SystemDAO();
	private ChartDAO Dao;
	private static PlaytitleDTO pDto;
	public ArrayList<ChartDTO> popularList;
	
	String dtoTitle;
	String title;
	
	String songTitle;
	String songSinger;
	
	private Opener opener;
	/*
	 * 메소드 리스트
	 * 
	 * public void musicPlay() // 음약 재생 public void musicPresent() // 재생창에 노래 정보
	 * 출력(제목, 가수, 앨범이미지) public void playMedia() // 노래 재생(슬라이드바 진행) public void
	 * previousMedia() // 이전 노래로 이동 public void nextMedia() // 다음 노래로 이동
	 * 
	 */
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		opener = new Opener();
		pDto = new PlaytitleDTO();
		ChartDAO Dao = new ChartDAO();
		popularList = Dao.popularAll(); //인기차트만 구현함.
//		setPreAndNext(); //안됨
//		popularList = Dao.popularAll();
//		System.out.println("popularList"+popularList);
//		System.out.println("pDto.getSinger()"+PlaytitleDTO.getSinger());
//		System.out.println("initialize Dao " + Dao);
		seekBar.setValueChanging(true); // 외부에서 슬라이더바 조작 가능
		seekBar.setDisable(false);
		musicPlay();

	}
//	public void setChartDAO(ChartDAO Dao) {
//		this.Dao = Dao;
//	}
	
	// 음약 재생 Main
	public void musicPlay() {
		System.out.println("musicPlay Dao " + Dao);
//		for(ChartDTO dto : popularList) {
//			System.out.println(dto.getTitle());
//		}
		songs = new ArrayList<File>();
		directorySong = new File("musicList");
		fileSong = directorySong.listFiles();

		if (fileSong != null) { // 폴더 노래가 없을 때까지 반복
			for (File file : fileSong) {
				songs.add(file);
			}
		}
		for (File file : songs) {
		
			String SongText = file.getName();
			String[] parts = SongText.split("-|\\."); // ("-")를 기준으로 문자열 분할

			String SongTitle = parts[1]; // 두 번째 제목 호출
//			String imgSinger = parts[0]; // 첫 번째 가수 호출
			if (PlaytitleDTO.getTitle().equals(SongTitle)) {
				String path = "file:/C:/Users/hi/git/watermelon/musicList/";
//				String path = "file:/C:/Users/jw/git/watermelon/musicList/";
				System.out.println(PlaytitleDTO.getTitle());
				media = new Media(path+PlaytitleDTO.getSinger()+"-"+PlaytitleDTO.getTitle()+".mp3");
				mediaPlayer = new MediaPlayer(media);
			
				musicPresent();
				playMedia();
				sliderTime();
			}
			
		}
		
	}

	// 재생창에 노래 정보 출력(제목, 가수, 앨범이미지)
	public void musicPresent() {
//		System.out.println("musicPresent Dao " + Dao);
		songs = new ArrayList<File>();
		directorySong = new File("musicAlbum");
		fileSong = directorySong.listFiles();

		if (fileSong != null) { // 폴더 노래가 없을 때까지 반복
			for (File file : fileSong) {
				songs.add(file);
			}
		}
		for (File file : songs) {
			String SongText = file.getName();
			String[] parts = SongText.split("-|\\."); // ("-")를 기준으로 문자열 분할

			String SongTitle = parts[1]; // 두 번째 제목 호출
//			String imgSinger = parts[0]; // 첫 번째 가수 호출
			if (PlaytitleDTO.getTitle().equals(SongTitle)) {
				
				songLabel.setText(PlaytitleDTO.getTitle());
				singer.setText(PlaytitleDTO.getSinger());

				// 앨범 이미지 삽입
				String albumURL = PlaytitleDTO.getSinger() + "-" + PlaytitleDTO.getTitle() + ".jpg";
				Image AlbumImage = new Image("/musicAlbum/" + albumURL);
				albumImage.setImage(AlbumImage);
				
			}
			
		}
		setPreAndNext();
	}

	// 노래 재생(슬라이드바 진행)
	public void playMedia() {
//		setPreAndNext(); // 안됨
		System.out.println("playMedia Dao " + Dao); //여기에서 DAO가 나옴.
		
		
		totalT = (double) mediaPlayer.getTotalDuration().toSeconds(); // 노래가 몇초짜리인지 반환
		int totalMinute = (int) (totalT / 60);
		int totalSecond = (int) (totalT - totalMinute * 60);
		String totalTime1;
		if (totalSecond < 10) {
			totalTime1 = totalMinute + ":0" + totalSecond;
		} else {
			totalTime1 = totalMinute + ":" + totalSecond;
		}
		totalTime.setText(totalTime1);
		System.out.println(totalTime1);

		playImage = new Image("/img/play.png");
		pauseImage = new Image("/img/pause.png");

		playButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == false) {
				playMediaImage.setImage(pauseImage);
				playMediaImage.getParent().requestLayout();
				mediaPlayer.play();
			} else {
				playMediaImage.setImage(playImage);
				playMediaImage.getParent().requestLayout();
				mediaPlayer.pause();
			}
		});

		sliderTime();
		
	}
	
	public void setPreAndNext() {
//		ArrayList<ChartDTO> List = new ArrayList<>();
//		List = Dao.popularAll(); //인기차트 전체 불러오기
		
				// 인기차트만 앞, 뒤 곡 이동 가능.
				for (int i = 1; i < popularList.size() - 1 ; i++) { 
		//			String numStr = Integer.toString(dto.getNum());
					System.out.println(PlaytitleDTO.getTitle());
					if(PlaytitleDTO.getTitle().equals(popularList.get(i).getTitle())) {
						//현재 재생곡의 다음곡 저장
						pDto.setNextTitle(popularList.get(i+1).getTitle());
						pDto.setNextSinger(popularList.get(i+1).getSinger());
						
						//현재 재생곡의 이전곡 저장
						pDto.setPrevTitle(popularList.get(i-1).getTitle());
						pDto.setPrevSinger(popularList.get(i-1).getSinger());
					
					}
			
				}
				System.out.println("getNextTitle"+PlaytitleDTO.getNextTitle());
				System.out.println("getPrevTitle"+PlaytitleDTO.getPrevTitle());
				
				
	}

	public void sliderTime() {
		seekBar.setValueChanging(true);
		seekBar.setSnapToTicks(false);
		// 슬라이더를 움직이기 위한 스레드 생성
		// 스레드가 별개로 돌아가기 때문에 재생하고 창을 종료하면 계속 재생되는 상태이다.
		Thread th = new Thread(() -> {
			seekBar.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (!seekBar.isValueChanging()) {
					// Slider의 값이 변경 중이 아닌 경우에만 실행
					mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
				}
			});
			mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
				if (!seekBar.isValueChanging()) {
					seekBar.setValue(newValue.toSeconds());
				}
			});

			// 슬라이더를 마우스로 드래그하여 값을 조정
			seekBar.setOnMouseClicked(event -> {
				double mouseX = event.getX(); // 클릭한 위치 x값

				System.out.println("mouseX : " + mouseX);

				if (mouseX >= 0 && mouseX <= totalT) {
					seekBar.setValue(mouseX);
					mediaPlayer.seek(Duration.seconds(mouseX - 10));
				}
			});

			// 비로그인 노래 재생 1분 제한
			if(LoginStaticDTO.getId() == null) {
				preMusic = new Thread(() -> {
					while (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
						Duration currentTime = mediaPlayer.getCurrentTime();
						if (currentTime.greaterThanOrEqualTo(MAX_DURATION)) {
							mediaPlayer.stop();
							Platform.runLater(() -> {
								opener.alertopen("1분 미리듣기입니다. 전곡 재생 희망시 로그인하세요.");
							});
							break;
						}
						try {
							Thread.sleep(1000); // 1초마다 재생 시간 체크
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
				preMusic.start();
				
			}

			// 재생시간에 따라 스레드 종료
			while (currentT < totalT) {
				// 음악 재생 시간과 Slider 값 연결
				currentT = (long) mediaPlayer.getCurrentTime().toSeconds();
				Platform.runLater(() -> {
					seekBar.setValue((double) currentT / totalT * 100);

					int currentMinute = (int) (currentT / 60);
					int currentSecond = (int) (currentT - currentMinute * 60);
					String currentTime1;
					if (currentSecond < 10) {
						currentTime1 = currentMinute + ":0" + currentSecond;
					} else {
						currentTime1 = currentMinute + ":" + currentSecond;
					}
					playTime.setText(currentTime1);
				});
				// sleep 0.3초를 주어서 좀 더 안정감있게 slidebar를 제어
				// 없다면 계산이 너무 빠르기 때문에 불안정
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		th.start();
	}

	// 이전 노래로 이동
	public void previousMedia() {
		pDto.setTitle(PlaytitleDTO.getPrevTitle());
		pDto.setSinger(PlaytitleDTO.getPrevSinger());
		// 노래 이동 시 재생 중 표시일 경우, 이미지 변경
		playButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (playMediaImage.getImage() == pauseImage) {
				playMediaImage.setImage(playImage);
				playMediaImage.getParent().requestLayout();
			}
		});
		
		musicPlay();
//		if (songNumber > 0) {
//			songNumber--;
//			mediaPlayer.stop();
//
//			media = new Media(songs.get(songNumber).toURI().toString());
//			mediaPlayer = new MediaPlayer(media);
//			musicPlay();
//		} else {
//			songNumber = songs.size() - 1;
//			mediaPlayer.stop();
//
//			media = new Media(songs.get(songNumber).toURI().toString());
//			mediaPlayer = new MediaPlayer(media);
//			musicPlay();
//		}
	}

	// 다음 노래로 이동
	public void nextMedia() {
		pDto.setTitle(PlaytitleDTO.getNextTitle());
		pDto.setSinger(PlaytitleDTO.getNextSinger());
		// 노래 이동 시 재생 중 표시일 경우, 이미지 변경
		playButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (playMediaImage.getImage() == pauseImage) {
				playMediaImage.setImage(playImage);
				playMediaImage.getParent().requestLayout();
			}
		});
		
		
		// 노래 이동 시 재생 중 표시일 경우, 이미지 변경
		playButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if (playMediaImage.getImage() == pauseImage) {
				playMediaImage.setImage(playImage);
				playMediaImage.getParent().requestLayout();
			}
		});
		
		musicPlay();
//		if (songNumber < songs.size() - 1) {
//			songNumber++;
//			mediaPlayer.stop();
//
//			media = new Media(songs.get(songNumber).toURI().toString()); 
//			mediaPlayer = new MediaPlayer(media);
//			musicPlay();
//		} else {
//			songNumber = 0;
//			mediaPlayer.stop();
//
//			media = new Media(songs.get(songNumber).toURI().toString());
//			mediaPlayer = new MediaPlayer(media);
//			musicPlay();
//		}
	}

	/////////////////////////////////////////////////////////////////////////

	// 담기 버튼
	private boolean isAdd = true;

	public void addMusic() {
		addBtn.setOnMouseClicked(e -> {
			if (isAdd) {
//				opener.alertopen("플레이 리스트에 추가하였습니다.");
				//chartDao.insertMYlist 할 예정

				ChartDAO cdao = new ChartDAO();
				cdao.insertMYlist(PlaytitleDTO.getTitle(), PlaytitleDTO.getSinger());
				
			} 
//			else {
//				opener.alertopen("플레이 리스트에서 삭제되었습니다.");
//			}
			isAdd = !isAdd;
		});
	}

	// 좋아요 버튼
//	int like_count = 0;
	public void clickHeart() {
		if(LoginStaticDTO.getId()!= null) {
			
			heartLine = new Image("/img/heartLine.png");
			fillHeart = new Image("/img/fillHeart.png");
			
			heartBtn.setOnMousePressed(e -> { // 무한 클릭 가능
				heartImage.setImage(fillHeart);
				heartImage.getParent().requestLayout();
				
				// 클릭 시 좋아요 개수 +1 
				
				String music_name = PlaytitleDTO.getTitle();
				
				
//				like_count++;
				
//				System.out.println(like_count);
				ChartDAO cdao = new ChartDAO();
//				cdao.heartCount(like_count,music_name);
				cdao.heartCount(music_name);
				
				
				
			});
			heartBtn.setOnMouseReleased(e -> {
				heartImage.setImage(heartLine);
				heartImage.getParent().requestLayout();
			});
			
		}else {
			opener.alertopen("로그인 후 이용 가능합니다.");
		}
	}

}