package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sdo.Client;
import sdo.Data;
import sdo.User;
import constant.DataState;

/**
 * @Description:为webservice提供数据库查询的工具类
 * @author lin
 * @date 2014-10-13 上午10:40:43
 */
public class SQLUtils {
	public User login(String username, String password) {
		System.out.println("用户" + username + "尝试登录");
		User user = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from tb_user where (username=? or email=?) and password=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, MD5Util.getMD5(password));
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setCreateDate(rs.getDate("create_date"));
				user.setTruename(rs.getString("truename"));
				System.out.println("用户" + username + "登陆成功！");
			} else {
				System.out.println("用户" + username + "登录失败");
			}
		} catch (SQLException e) {
			System.out.println("查询用户" + username + "信息失败" + e);
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return user;
	}

	public Long getDataSize(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long dataNum = 0L;
		String sql = "select sum(size) as c from tb_file f where user_id=? and state=?;";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, DataState.ACTIVE);
			rs = ps.executeQuery();
			if (rs.next()) {
				dataNum = rs.getLong("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataNum;
	}

	public Long getDataNum(int userId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long dataNum = 0L;
		String sql = "select count(*) from tb_file f where user_id=? and state=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, DataState.ACTIVE);
			rs = ps.executeQuery();
			if (rs.next()) {
				dataNum = rs.getLong("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataNum;
	}

	public List<String> getAllDataKey() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> dataKeyList = new ArrayList<String>();
		String sql = "select data_key from tb_file";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dataKeyList.add(rs.getString("data_key"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return dataKeyList;
	}

	public int addData(Data data) {
		Connection conn = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "insert into tb_file(user_id,data_key,name,create_date,path,md5,state) values(?,?,?,now(),?,?,?)";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, data.getUserId());
			ps.setString(2, data.getDataKey());
			ps.setString(3, data.getName());
			ps.setString(4, data.getPath());
			ps.setString(5, data.getMd5());
			ps.setInt(6, DataState.DEELTED);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return result;
	}

	public Data getDataByKey(String dataKey) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Data data = new Data();
		String sql = "select * from tb_file where data_key=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dataKey);
			rs = ps.executeQuery();
			while (rs.next()) {
				data.setDataId(rs.getInt("file_id"));
				data.setName(rs.getString("file_name"));
				data.setMd5(rs.getString("md5"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return data;
	}

	public int updateData(Data data) {
		int flag = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tb_file set size=?,state=? where data_key=?";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, data.getSize());
			ps.setInt(2, DataState.ACTIVE);
			ps.setString(3, data.getDataKey());
			ps.executeUpdate();
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, null);
		}
		return flag;
	}

	public Client getClient() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Client client = null;
		try {
			conn = ConnectManager.getConnection();
			String sql = "select * from tb_client order by id desc limit 1";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				client = new Client();
				client.setId(rs.getInt("id"));
				client.setName(rs.getString("name"));
				client.setVersion(rs.getString("version"));
				client.setCreateDate(rs.getDate("create_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
		return client;
	}
}