package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:文件操作工具类
 * @author lin
 * @date 2013-7-29 下午7:36:51
 */
public class FileTools {

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            ： 路径格式若为：/home/down/test.txt，
	 *            若路径不存在，则生成home/down文件夹后生成test.txt文件
	 */
	public static void createFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("文件创建异常：" + e);
		}
	}

	/**
	 * 检验该路径是否存在：适用于文件和文件夹
	 * 
	 * @param path
	 * @return： true，存在；false，不存在
	 */
	public static boolean checkPath(String path) {
		return new File(path).exists();
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtName(String fileName) {
		String extName = "";
		if (fileName.lastIndexOf(".") > 0) {
			extName = fileName.substring(fileName.lastIndexOf("."));
		}
		return extName;
	}

	/**
	 * 获取文件大小
	 * 
	 * @param pathStr
	 * @return
	 */
	public static Long getFileSize(String pathStr) {
		Path path = Paths.get(pathStr);
		BasicFileAttributes attributes;
		try {
			attributes = Files.readAttributes(path, BasicFileAttributes.class);
			return attributes.size();
		} catch (IOException e) {
			System.out.println("读取不到文件" + pathStr);
			e.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 文件下载方法
	 * 
	 * @param response
	 *            :HttpServletResponse
	 * @param filePath
	 *            ：带有路径的文件名，如 path/fileName.zip
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static void fileDownLoad(HttpServletResponse response,
			String filePath) {
		int endIndex = 0;
		if (filePath.indexOf("/") != -1) {
			endIndex = filePath.lastIndexOf("/");
		} else {
			endIndex = filePath.lastIndexOf("\\");
		}
		String newFileName = filePath.substring(endIndex + 1);
		File file = new File(filePath);
		response.addHeader("Content-Disposition", "attachment;filename="
				+ newFileName);
		response.setContentType("application/octet-stream");
		FileInputStream is = null;
		ServletOutputStream out = null;
		try {
			is = new FileInputStream(file);
			int length = is.available();
			byte[] content = new byte[length];
			is.read(content);
			out = response.getOutputStream();
			out.write(content);
			out.flush();
			response.setStatus(response.SC_OK);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] getByte(File file) {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			int length = (int) file.length();
			if (length > Integer.MAX_VALUE) {
				System.out.println("this file is max ");
				return new byte[0];
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			try {
				while (offset < bytes.length
						&& (numRead = is.read(bytes, offset, bytes.length
								- offset)) >= 0) {
					offset += numRead;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				System.out.println("file length is error");
				return new byte[0];
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}
}