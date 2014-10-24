package sdo;

import java.util.Date;

/**
 * @ClassName: User
 * @Description: (用户的数据服务对象)
 * @author summer
 * @date 2012-6-19 下午02:45:47
 * 
 */
public class User {
    private int userId;// 用户编号
    private String username;// 用户名
    private String password;// 密码
    private String email;// 邮箱
    private Date createDate;// 创建日期
    private String truename;// 真实姓名
	private int state = 0;// 是否删除

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}