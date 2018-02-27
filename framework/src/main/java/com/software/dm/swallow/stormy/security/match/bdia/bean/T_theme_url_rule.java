package com.software.dm.swallow.stormy.security.match.bdia.bean;

import java.util.List;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * 
 */
public final class T_theme_url_rule {
	
	public static final int ACTION_THEME = 1;
	
	
	private long n_id;
	private String domain_comp;
	private String rule;
	private int rule_type;
	private List<T_theme_basic_type_rel> themeTypes;

	public T_theme_url_rule(long n_id, String domain_comp, String rule, int rule_type, List<T_theme_basic_type_rel> themeTypes) {
		this.n_id = n_id;
		this.domain_comp = domain_comp;
		this.rule = rule;
		this.rule_type = rule_type;
		this.themeTypes = themeTypes;
	}

	public long getN_id() {
		return n_id;
	}

	public String getDomain_comp() {
		return domain_comp;
	}

	public String getRule() {
		return rule;
	}

	public int getRule_type() {
		return rule_type;
	}

	public List<T_theme_basic_type_rel> getThemeTypes() {
		return themeTypes;
	}

}
