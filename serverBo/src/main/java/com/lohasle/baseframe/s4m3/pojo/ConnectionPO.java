package com.lohasle.baseframe.s4m3.pojo;

/**
 * 拖动配置条目pojo
 * @author ganmin
 * @value host 主机地址
 * @value port 端口号
 * @value dbName 数据库名称
 * @value user 用户名
 * @value pwd 密码
 */
public class ConnectionPO {
	private String host;
	private String port;
	private String dbName;
	private String user;
	private String pwd;

	/** default constructor */
	public ConnectionPO() {
	}

	/** full constructor */
	public ConnectionPO(String host, String port, String dbName,
	    String user, String pwd) {
	    super();
	    this.host = host;
	    this.port = port;
	    this.dbName = dbName;
	    this.user = user;
	    this.pwd = pwd;
	}
	
	public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
