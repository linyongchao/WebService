package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import sdo.Client;
import sdo.Data;
import sdo.User;
import service.IUploadService;
import utils.DataUtil;
import utils.FileTools;
import utils.PropertiesUtil;
import utils.SQLUtils;

@WebService(endpointInterface = "service.IUploadService")
public class UploadServiceImpl implements IUploadService {
	Logger log = Logger.getLogger(UploadServiceImpl.class);
	private SQLUtils sql = new SQLUtils();
	private String path = PropertiesUtil.dataPath;

	@Override
	public int login(String username, String pwd) {
		log.info("用户：" + username + "尝试登陆");
		User user = sql.login(username, pwd);
		if (user == null) {
			log.info("用户：" + username + "登陆失败");
			return 0;
		} else {
			log.info("用户：" + username + "登陆成功");
			return user.getUserId();
		}
	}

	@Override
	public long getSize(int id) {
		return sql.getDataSize(id);
	}

	@Override
	public long getNumber(int id) {
		return sql.getDataNum(id);
	}

	@Override
	public String init(Integer id, String fileName, String md5) {
		List<String> dataKeyList = sql.getAllDataKey();
		String dataKey = DataUtil.getNewDataKey();
		while (dataKeyList.contains(dataKey)) {
			dataKey = DataUtil.getNewDataKey();
		}
		Data data = new Data();
		data.setUserId(id);
		// 只允许字母和数字
		String regEx = "[^\\w\\.\\_\\-\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(fileName);
		data.setName(m.replaceAll("").trim());
		String newName = dataKey + FileTools.getExtName(fileName);
		data.setDataKey(dataKey);
		data.setPath(path + newName);
		data.setMd5(md5);
		sql.addData(data);
		log.info("为用户：" + id + "返回" + newName);
		return newName;
	}

	@Override
	public boolean saveFile(String dataKey, byte[] buffer, int length,
			long position, int blocks) {
		String filePath = path + dataKey;
		File file = new File(filePath);
		if (!file.exists()) {
			FileTools.createFile(filePath);
		}
		boolean isTrue = true;
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.seek(position);
			if (buffer.length == length) {
				raf.write(buffer);
			} else {
				byte[] temp = new byte[length];
				System.arraycopy(buffer, 0, temp, 0, length);
				raf.write(temp);
			}
		} catch (FileNotFoundException e) {
			isTrue = false;
			e.printStackTrace();
		} catch (IOException e) {
			isTrue = false;
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					isTrue = false;
					e.printStackTrace();
				}
			}
		}
		return isTrue;
	}

	@Override
	public boolean checkMD5(String dataKey) {
		String key = dataKey.substring(0, dataKey.indexOf("."));
		File file = new File(path + dataKey);
		if (!file.exists()) {
			return false;
		}
		FileInputStream fis = null;
		boolean isTrue = true;
		try {
			fis = new FileInputStream(file);
			String md5 = DigestUtils.md5Hex(fis);
			Data data = sql.getDataByKey(key);
			String save = data.getMd5();
			isTrue = md5.equals(save) ? true : false;
		} catch (FileNotFoundException e) {
			isTrue = false;
			e.printStackTrace();
		} catch (IOException e) {
			isTrue = false;
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					isTrue = false;
					e.printStackTrace();
				}
			}
		}
		if (isTrue) {
			Data data = new Data();
			data.setSize(FileTools.getFileSize(path + dataKey));
			data.setDataKey(key);
			sql.updateData(data);
			log.info("文件：" + dataKey + "上传成功");
		} else {
			log.info("文件：" + dataKey + "上传失败");
		}
		return isTrue;
	}

	@Override
	public String getVersion() {
		log.info("获取最新的版本号");
		Client client = sql.getClient();
		return client.getVersion();
	}

	@Override
	public byte[] getClient() {
		log.info("获取客户端");
		String courseFile = null;
		try {
			courseFile = new File("").getCanonicalPath().replace("bin",
					"webapps");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Client client = sql.getClient();
		String path = courseFile + "/celloud/client/" + client.getName();
		System.out.println("client path:" + path);
		byte[] bytes = null;
		try {
			bytes = FileTools.getByte(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
}