package com.software.dm.swallow.stormy.security.match.bdia.bean;

import java.io.Serializable;
import java.util.List;

public final class T_url_rule implements Serializable {

    public static final int ACTION_BASIC_APP = 0;

    private long n_id;
    private String domain_one;
    private String brand;
    private String domain_comp;
    private String service_name;
    private String rule;
    private int rule_type;
    private int rule_type_level;
    private int is_app_url;
    private String bqz_id;
    private String bqz_name;

    private List<T_basic_type_rel> basicTypes;

    public T_url_rule(long n_id, String domain_one, String brand, String domain_comp, String service_name, String rule, int rule_type, int rule_type_level, int is_app_url, String bqz_id, String bqz_name, List<T_basic_type_rel> basicTypes) {

        this.n_id = n_id;
        this.domain_one = domain_one;
        this.brand = brand;
        this.domain_comp = domain_comp;
        this.service_name = service_name;
        this.rule = rule;
        this.rule_type = rule_type;
        this.rule_type_level = rule_type_level;
        this.is_app_url = is_app_url;
        this.bqz_id = bqz_id;
        this.bqz_name = bqz_name;
        this.basicTypes = basicTypes;
    }

    public long getN_id() {
        return n_id;
    }

    public String getDomain_one() {
        return domain_one;
    }

    public String getBrand() {
        return brand;
    }

    public String getDomain_comp() {
        return domain_comp;
    }

    public String getService_name() {
        return service_name;
    }

    public String getRule() {
        return rule;
    }

    public int getRule_type() {
        return rule_type;
    }

    public int getRule_type_level() {
        return rule_type_level;
    }

    public int getIs_app_url() {
        return is_app_url;
    }

    public String getBqz_id() {
        return bqz_id;
    }

    public String getBqz_name() {
        return bqz_name;
    }

    public List<T_basic_type_rel> getBasicTypes() {
        return basicTypes;
    }

}
