package com.software.dm.swallow.stormy.security.match.bdia.bean;

import java.io.Serializable;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class T_basic_type_rel implements Serializable {

    private long n_id;
    private String type_id;
    private String all_name;

    public T_basic_type_rel(long n_id, String type_id, String all_name) {

        this.n_id = n_id;
        this.type_id = type_id;
        this.all_name = all_name;
    }

    public long getN_id() {
        return n_id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getAll_name() {
        return all_name;
    }

}
