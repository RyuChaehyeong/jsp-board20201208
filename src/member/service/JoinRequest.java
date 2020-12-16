package member.service;

import java.util.Map;

public class JoinRequest {
	private String id;
	private String name;
	private String password;
	private String confirmPassword;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public void validate(Map<String, Boolean> errors) {
		//id값이 잘 들어왔는지? 
		checkEmpty(errors, id, "id");
		//name이 잘 들어왔는지? 
		checkEmpty(errors, name, "name");
		//pw는 잘 들어왔는지? 
		checkEmpty(errors, password, "password");
		//confirmPw 잘 들어왔는지?
		checkEmpty(errors, confirmPassword, "confirmPassword");
		//어디다 담아서 잘 들어왔는지 볼거냐면 파라미터로 넘어온 map객체에 담기로 결정
		//즉, 문제가 있을 때만 map 객체에 담기로 결정
		
		//입력한 pw와 확인 pw가 같은지 확인 -> 메소드로 따로 뺌
		//"confirmPassword"라는 이름이로 에러가 등록되지 않았을 때 실행
		if (!errors.containsKey("confirmPassword")) {
			if (!isPasswordEqualToConfirm()) {
				errors.put("notMatch", Boolean.TRUE);
			}
		}
	}
	
	public boolean isPasswordEqualToConfirm() {
		return password != null && password.equals(confirmPassword);
	}
	
	private void checkEmpty(Map<String, Boolean> errors, String value, String fieldName) {
		if (value == null || value.isEmpty()) {
			//문제발생!
			//value가 비어있으면 map에 fieldName으로 map에 넣음 
			errors.put(fieldName, Boolean.TRUE);
		}
	}
}
