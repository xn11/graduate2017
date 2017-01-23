package edu.nju.software.dao;

import edu.nju.software.pojo.Cfg;;

public interface CfgDao {
//	public List<Cfg> getAll();
	
//	public int create(Cfg cfg);
	
	public void update(Cfg cfg);
	
//	public void delete(int id);
	
	public Cfg getByKey(String key);
	
	public Cfg getById(int id);

}
