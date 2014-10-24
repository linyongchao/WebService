package sdo;

import java.util.Date;

/**
 * @Description:客户端信息表
 * @author lin
 * @date 2014-10-13 上午10:55:07
 */
public class Client {
	private int id;
	private String version;
	private String name;
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
