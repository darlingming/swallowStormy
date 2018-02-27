package com.software.dm.swallow.stormy.security.match.bdia.bean;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * 
 */
public final class T_ip_rule {
	public static final int ACTION_IP = 4;

	private long n_id;
	private String ip;
	private String port;
	private int rule_type;
	private String bqz_id;
	private String bqz_name;

	public T_ip_rule(long n_id, String ip, String port, int rule_type, String bqz_id, String bqz_name) {
		this.n_id = n_id;
		this.ip = ip;
		this.port = port;
		this.rule_type = rule_type;
		this.bqz_id = bqz_id;
		this.bqz_name = bqz_name;
	}

	public long getN_id() {
		return n_id;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public int getRule_type() {
		return rule_type;
	}

	public String getBqz_id() {
		return bqz_id;
	}

	public String getBqz_name() {
		return bqz_name;
	}

}
