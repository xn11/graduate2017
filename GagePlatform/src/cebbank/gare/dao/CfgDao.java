package cebbank.gare.dao;

import cebbank.gage.pojo.Config;;

public interface CfgDao {
//	public List<Cfg> getAll();
	
//	public int create(Cfg cfg);
	
	public void update(Config cfg);
	
//	public void delete(int id);
	
	public Config getByKey(String key);
	
	public Config getById(int id);

}
