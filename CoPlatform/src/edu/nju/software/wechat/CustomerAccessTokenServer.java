/**
 * 
 */
package edu.nju.software.wechat;

import org.sword.wechat4j.token.Token;
import org.sword.wechat4j.token.server.CustomerServer;

/**
 * @author repo_user
 * @date   2015年1月12日
 */
public class CustomerAccessTokenServer extends CustomerServer {

	/* (non-Javadoc)
	 * @see org.sword.wechat4j.token.DbAccessTokenServer#find()
	 */
	
//	@Autowired
//	private CfgDao cfgDao;
	private static String accessToken= "lDeo8AzsIBYiTwTIFGYssJbAfzAR5JWG48OcnnoVVhSscLuW27VkLaG5DKBYSSyEMWGIuOZrBJE8A5VGf0Q-fRUALCOYRyW26RXSIYa5WWo";
	
	@Override
	public String find() {
//		String accessToken = null;
//		Cfg cfg = cfgDao.getByKey("access_token");
//		String accessToken = cfg.getCfgvalue();
		
		//执行数据库操作
//		String sql = "select cfgValue from cfg where cfg.cfgKey = 'access_token'";
//		accessToken = DBUtil.query(sql);
		
		return accessToken;
	}

	/* (non-Javadoc)
	 * @see org.sword.wechat4j.token.DbAccessTokenServer#save()
	 */
	@Override
	public boolean save(Token accessToken) {
//		Cfg cfg = cfgDao.getByKey("access_token");
//		cfg.setCfgvalue(accessToken.getToken());
//		cfgDao.update(cfg);
		
		//如果没有需要插入，如果有的就更新，假设已经有了数据库配置项
//		String sql = "update cfg set cfg.cfgValue=" + accessToken.getToken() + 
//				" where cfg.cfgKey= 'access_token'";
//		DBUtil.execute(sql);
		CustomerAccessTokenServer.accessToken=accessToken.getToken();
		return true;
	}
}
