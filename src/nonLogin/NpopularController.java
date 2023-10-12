package nonLogin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.HMakeMusicBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import main.ChartDAO;
import main.ChartDTO;

public class NpopularController implements Initializable {
	
	public ChartDAO Dao;
	private ArrayList<ChartDTO> musicList;
	private ObservableList<Node> fp;
	
	@FXML
	private FlowPane listPane;

	public void initialize(URL location, ResourceBundle resources) {
		Dao = new ChartDAO();
		HMakeMusicBox HMMB = new HMakeMusicBox();
		HMMB.setChartDAO(Dao);
		
		
		musicList = Dao.popularAll(); // oracle에서 인기차트 전체 리스트 가져오기.
		fp = listPane.getChildren();
		for (ChartDTO dto : musicList) {
//			String numStr = Integer.toString(dto.getNum());
			String singer = dto.getSinger();
			String dbtitle = dto.getTitle();
			String heart = Integer.toString(dto.getLike());
			fp.add(HMMB.eachSong(singer, dbtitle, heart, false));
//			fp.add(eachSong(numStr, singer, dbtitle, heart));
		}
	}

}