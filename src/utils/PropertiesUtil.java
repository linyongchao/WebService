package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	static Logger logger = Logger.getLogger(PropertiesUtil.class);
	public static String dataPath;
	static {
		Properties prop = new Properties();
		InputStream inStream = ConnectManager.class.getClassLoader()
				.getResourceAsStream("system.properties");
		try {
			prop.load(inStream);
			dataPath = prop.getProperty("dataPath");
		} catch (IOException e) {
			logger.info("读取jdbc配置文件失败");
		}
	}
}
