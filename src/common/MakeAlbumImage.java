package common;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class MakeAlbumImage {
	
	public Image musicAlbum(String dbSinger, String dbTitle) {
		ArrayList<File> songsImg = new ArrayList<File>();
		File directorySongImg = new File("musicAlbum");
		File[] fileSongImg = directorySongImg.listFiles();
		
		if (fileSongImg != null) { // 폴더 노래가 없을 때까지 반복
			for (File file : fileSongImg) {
				songsImg.add(file);		
			}
		}
		
		for (File file : songsImg) {
			String imgText = file.getName();
			String[] parts = imgText.split("-|\\."); // ("-")를 기준으로 문자열 분할
			String imgTitle = parts[1]; // 두 번째 제목 호출
//			String imgSinger = parts[0]; // 첫 번째 가수 호출
			if (dbTitle.equals(imgTitle)) {
				// 앨범 이미지 삽입
				String albumURL = dbSinger + "-" + dbTitle + ".jpg";
//				System.out.println(albumURL);
				Image AlbumImage = new Image("/musicAlbum/" + albumURL);
		
				return AlbumImage;
			}
		}
		return null;
	}
	
}
