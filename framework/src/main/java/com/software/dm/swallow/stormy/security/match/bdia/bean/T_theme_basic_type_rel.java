package com.software.dm.swallow.stormy.security.match.bdia.bean;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class T_theme_basic_type_rel {

    private String subject_id;
    private long n_id;
    private String type_id;
    private int type_level;
    private String all_name;

    public T_theme_basic_type_rel(String subject_id, long n_id, String type_id, int type_level, String all_name) {
        this.subject_id = subject_id;
        this.n_id = n_id;
        this.type_id = type_id;
        this.type_level = type_level;
        this.all_name = all_name;
    }

    public int getType_level() {
        return type_level;
    }

    public String getSubject_id() {
        return subject_id;
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
