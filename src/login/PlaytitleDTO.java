package login;



public class PlaytitleDTO {
	
	private static String singer;
	private static String title;
	private static String nextTitle;
	private static String nextSinger;
	private static String prevTitle;
	private static String prevSinger;
	
	public static String getPrevSinger() {
		return prevSinger;
	}
	public  void setPrevSinger(String prevSinger) {
		this.prevSinger = prevSinger;
	}
	
	public static String getNextSinger() {
		return nextSinger;
	}
	public  void setNextSinger(String nextSinger) {
		this.nextSinger = nextSinger;
	}
	
	
	public static String getNextTitle() {
		return nextTitle;
	}
	public  void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}
	public static String getPrevTitle() {
		return prevTitle;
	}
	public  void setPrevTitle(String prevTitle) {
		this.prevTitle = prevTitle;
	}
	
	public static String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public static String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
}
