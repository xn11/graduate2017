package cebbank.gage.model;

import java.io.Serializable;
import java.util.Date;

/*
 * 资讯
 */
public class News implements Serializable{

	private static final long serialVersionUID = -8115554939230798153L;

    private int id;
    private int companyId;
    private String title;
    private String content;
    private Date createdTime;
    private Date publishTime;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public News() {}
	
	public News(int id, int companyId, String title, String content, Date createdTime, Date publishTime) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.publishTime = publishTime;
	}
	
	public News(int companyId, String title, String content, Date createdTime, Date publishTime) {
		super();
		this.companyId = companyId;
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.publishTime = publishTime;
	}
	
}
