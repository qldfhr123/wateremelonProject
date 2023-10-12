package system;

public class SystemDTO {
	// 회원관리 DTO
	private String id, pw, name, age, registerTime;
	// 음악관리 DTO
	private String title, singer;
	private Integer heart;

	
	
	// set
	public void setId(String id) {
		this.id = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public void setHeart(Integer heart) {
		this.heart = heart;
	}
	
	 
	// get
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getName() {
		return name;
	}
	public String getAge() {
		return age;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public String getTitle() {
		return title;
	}
	public String getSinger() {
		return singer;
	}
	public Integer getHeart() {
		return heart;
	}
 
	 
}
