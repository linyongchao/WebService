package service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @Description:为文件上传客户端提供的接口服务
 * @author lin
 * @date 2014-10-10 下午3:42:59
 */
@WebService
public interface IUploadService {

	/**
	 * 客户端登陆方法
	 * 
	 * @param username
	 *            ：用户名
	 * @param pwd
	 *            ：密码
	 * @return：>0 登陆成功，0 登陆失败
	 */
	@WebMethod
	public int login(String username, String pwd);

	/**
	 * 获取客户上传文件的总大小
	 * 
	 * @param id
	 *            ：用户 id
	 * @return：客户上传文件的总大小，单位是B
	 */
	@WebMethod
	public long getSize(int id);

	/**
	 * 获取客户上传文件的总个数
	 * 
	 * @param id
	 *            ：用户 id
	 * @return：客户上传文件的总个数
	 */
	@WebMethod
	public long getNumber(int id);

	/**
	 * 文件上传前的初始化工作
	 * 
	 * @param fileName
	 *            ：文件名
	 * @param md5
	 *            ：文件的 md5 值
	 * @return：返回该文件的唯一标识：dataKey
	 */
	@WebMethod
	public String init(Integer id, String fileName, String md5);

	/**
	 * @param dataKey
	 *            ：文件唯一标示
	 * @param buffer
	 *            ：二进制流
	 * @param length
	 *            ：有效长度
	 * @param position
	 *            ：起始位置
	 * @param blocks
	 *            ：块数
	 * @return：true 上传成功，false 上传失败
	 */
	@WebMethod
	public boolean saveFile(String dataKey, byte[] buffer, int length,
			long position, int blocks);

	/**
	 * @param dataKey
	 *            ：文件唯一标示
	 * @return：true 上传成功，false 上传失败
	 */
	@WebMethod
	public boolean checkMD5(String dataKey);

	/**
	 * 返回版本号
	 * 
	 * @return
	 */
	@WebMethod
	public String getVersion();

	/**
	 * 客户端自动更新
	 * 
	 * @return
	 */
	@WebMethod
	public byte[] getClient();
}