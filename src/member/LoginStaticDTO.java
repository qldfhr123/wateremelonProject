package member;

public class LoginStaticDTO {
	// 정보들을 저장하려고 보통 비번은 저장안함 
	// static이라서 이미 생성되었다고 나와 생성자로는 진행이 안된다함 
	// 로그인 성공했을때 setter, getter값을 넣어주려함
	// static으로 만들어서 어디서든 가져다 쓸 수 있다.
	
	private static String id;
	private static String name;
	private static String pw;
	private static String age;
	private static String RegisterDay;
	
	
	
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		LoginStaticDTO.id = id;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		LoginStaticDTO.name = name;
	}
	public static String getPw() {
		return pw;
	}
	public static void setPw(String pw) {
		LoginStaticDTO.pw = pw;
	}
	public static String getAge() {
		return age;
	}
	public static void setAge(String age) {
		LoginStaticDTO.age = age;
	}
	public static String getRegisterDay() {
		return RegisterDay;
	}
	public static void setRegisterDay(String RegisterDay) {
		LoginStaticDTO.RegisterDay = RegisterDay;
	}
	
	

}
