package system;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import main.ChartDAO;
import main.Opener;

public class SystemController implements Initializable {

	@FXML
	private Label systemTitle, label1, label2, label3, label4;
	
	@FXML
	private TableView<SystemDTO> SmemberList;
	@FXML
	private TableColumn<SystemDTO, String> SystemId, SystemPw, SystemName, SystemAge, SystemRegister;

	@FXML
	private TableView<SystemDTO> SmusicList;
	@FXML
	private TableColumn<SystemDTO, String> SsongTitle, Ssinger;
	@FXML
	private TableColumn<SystemDTO, Integer> Sheart;

	@FXML
	private TextField SmusicUpdate, SmusicDelete;
	@FXML
	private Button SmusicUpBtn, SmusicUpBtn1, SmusicDelBtn, SmusicDelBtn1;

	private SystemDAO systemDAO;
	private ArrayList<SystemDTO> musicList;
	private ArrayList<SystemDTO> memberList;

	private File chooseFile;
	private Stage primaryStage;

	private Opener opener;
	
	public SystemController() {
		systemDAO = new SystemDAO();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		opener = new Opener();
		// 데이터베이스 연결
		systemDAO.connection();

		SmemberList();
		SmusicList();
	}

	// 관리자 모드 - 회원정보 리스트
	@SuppressWarnings("unchecked")
	public void SmemberList() {
		ObservableList<SystemDTO> OmemberList = FXCollections.observableArrayList();
		memberList = systemDAO.selectMember();

		for (SystemDTO systemDTO : memberList) {
			SystemDTO dto = new SystemDTO();
			String id = systemDTO.getId();
			String pw = systemDTO.getPw();
			String name = systemDTO.getName();
			String age = systemDTO.getAge();
			String registerTime = systemDTO.getRegisterTime();

			dto.setId(id);
			dto.setPw(pw);
			dto.setName(name);
			dto.setAge(age);
			dto.setRegisterTime(registerTime);

			OmemberList.add(dto);
		}

		TableColumn<SystemDTO, String> SystemId = new TableColumn<>("아이디"); // SceneBuilder 칼럼명
		TableColumn<SystemDTO, String> SystemPw = new TableColumn<>("비밀번호");
		TableColumn<SystemDTO, String> SystemName = new TableColumn<>("이름");
		TableColumn<SystemDTO, String> SystemAge = new TableColumn<>("연령대");
		TableColumn<SystemDTO, String> SystemRegister = new TableColumn<>("등록시간");
		SystemId.setCellValueFactory(new PropertyValueFactory<>("id")); // DB 칼럼명
		SystemPw.setCellValueFactory(new PropertyValueFactory<>("pw"));
		SystemName.setCellValueFactory(new PropertyValueFactory<>("name"));
		SystemAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		SystemRegister.setCellValueFactory(new PropertyValueFactory<>("registerTime"));

		SystemId.setMinWidth(90);
		SystemPw.setMinWidth(90);
		SystemName.setMinWidth(50);
		SystemAge.setMinWidth(35);
		SystemRegister.setMinWidth(150);
		
//		SystemId.setStyle("-fx-background-color: #CCCCCC;");
//		SystemPw.setStyle("-fx-background-color: #CCCCCC;");
//		SystemName.setStyle("-fx-background-color: #CCCCCC;");
//		SystemAge.setStyle("-fx-background-color: #CCCCCC;");
//		SystemRegister.setStyle("-fx-background-color: #CCCCCC;");

		System.out.println("관리자 모드 - 회원정보 리스트 출력");
		SmemberList.setItems(OmemberList);
		SmemberList.getColumns().addAll(SystemId, SystemPw, SystemName, SystemAge, SystemRegister);
	}

	// 관리자 모드 - 음악 리스트
	@SuppressWarnings("unchecked")
	public void SmusicList() {
		ObservableList<SystemDTO> OmusicList = FXCollections.observableArrayList();
		musicList = systemDAO.selectMusic();

		for (SystemDTO systemDTO : musicList) {
			SystemDTO dto = new SystemDTO();
			String title = systemDTO.getTitle();
			String singer = systemDTO.getSinger();
			int heart = systemDTO.getHeart();

			dto.setSinger(singer);
			dto.setTitle(title);
			dto.setHeart(heart);

			OmusicList.add(dto);
		}

		TableColumn<SystemDTO, String> SsongTitle = new TableColumn<>("제목"); // SceneBuilder 칼럼명
		TableColumn<SystemDTO, String> Ssinger = new TableColumn<>("가수");
		TableColumn<SystemDTO, Integer> Sheart = new TableColumn<>("좋아요");
		SsongTitle.setCellValueFactory(new PropertyValueFactory<>("title")); // DB 칼럼명
		Ssinger.setCellValueFactory(new PropertyValueFactory<>("singer"));
		Sheart.setCellValueFactory(new PropertyValueFactory<>("heart"));

		SsongTitle.setMinWidth(210);
		Ssinger.setMinWidth(110);
		Sheart.setMinWidth(36);

		System.out.println("관리자 모드 - 음악리스트 출력");
		SmusicList.setItems(OmusicList);
		SmusicList.getColumns().addAll(SsongTitle, Ssinger, Sheart);
	}

	// 음악 등록
	public void openFileUp(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		// 기본 디렉토리 경로 설정
		fileChooser.setInitialDirectory(new File("C:\\Users\\hi\\git\\watermelon\\music\\"));
//		fileChooser.setInitialDirectory(new File("C:\\Users\\jw\\git\\watermelon\\music\\"));
		// 파일 옵션 표시
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("음악파일 : Audio Files(*.mp3)", "*.mp3"));

		// 현재 화면에 띄울수 없으므로 위에서 선언한 stage를 사용
		chooseFile = fileChooser.showOpenDialog(primaryStage);

		if (chooseFile != null) {
			SmusicUpdate.setText(chooseFile.getName()); // 선택한 파일명 출력
		} else {
			SmusicUpdate.setText("아무것도 지정하지 않았습니다.");

		}
	}

	public void saveFile(ActionEvent e) {
		if (chooseFile != null) {
			File saveFile = new File("C:\\Users\\hi\\git\\watermelon\\musicList\\" + chooseFile.getName()); // 저장 경로
//			File saveFile = new File("C:\\Users\\jw\\git\\watermelon\\musicList\\" + chooseFile.getName()); // 저장 경로
			String SongText = chooseFile.getName();
			String[] parts = SongText.split("-|\\."); // ("-")를 기준으로 문자열 분할
			
			String SongTitle = parts[1]; // 두 번째 제목 호출
			String SongSinger = parts[0]; // 첫 번째 가수 호출
			ChartDAO chartDao = new ChartDAO();
			chartDao.insertMusic(SongTitle, SongSinger);
			try {
				// 파일 복사
				System.out.println(chooseFile.toPath());
				Files.copy(chooseFile.toPath(), saveFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				opener.alertopen("파일을 저장했습니다.");
				System.out.println("파일이 성공적으로 저장되었습니다.");
			} catch (IOException c) {
				opener.alertopen("파일 저장을 실패했습니다.");
				System.out.println("파일 저장 중 오류가 발생했습니다: " + c.getMessage());
			}
		} else {
			System.out.println("파일 선택이 취소되었습니다.");
		}
	}

	public void openFileDel(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		// 기본 디렉토리 경로 설정
		fileChooser.setInitialDirectory(new File("C:\\Users\\hi\\git\\watermelon\\musicList\\"));
//		fileChooser.setInitialDirectory(new File("C:\\Users\\jw\\git\\watermelon\\musicList\\"));

		// 파일 옵션 표시
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("음악파일 : Audio Files(*.mp3)", "*.mp3"));

		// 현재 화면에 띄울수 없으므로 위에서 선언한 stage를 사용
		chooseFile = fileChooser.showOpenDialog(primaryStage);

		if (chooseFile != null) {
			SmusicDelete.setText(chooseFile.getName()); // 선택한 파일명 출력
		} else {
			SmusicDelete.setText("아무것도 지정하지 않았습니다.");

		}
	}

	public void deleteFile(ActionEvent e) {
		if (chooseFile != null) {
			String SongText = chooseFile.getName();
			String[] parts = SongText.split("-|\\."); // ("-")를 기준으로 문자열 분할
			
			String SongTitle = parts[1]; // 두 번째 제목 호출
//			String SongSinger = parts[0]; // 첫 번째 가수 호출
			ChartDAO chartDao = new ChartDAO();
			chartDao.deleteMusic(SongTitle);
			try {
				Files.deleteIfExists(chooseFile.toPath());
				opener.alertopen("파일을 삭제했습니다.");
				System.out.println("파일이 성공적으로 삭제되었습니다.");
			} catch (IOException c) {
				opener.alertopen("파일 삭제를 실패했습니다.");
				System.out.println("파일 삭제 중 오류가 발생했습니다: " + c.getMessage());
			}
		} else {
			System.out.println("삭제할 파일이 선택되지 않았습니다.");
		}
	}
}
