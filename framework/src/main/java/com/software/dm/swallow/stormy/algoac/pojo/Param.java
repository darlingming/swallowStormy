package com.software.dm.swallow.stormy.algoac.pojo;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 * @preserve
 */
public final class Param {


    private int type = 0;// ???????
    private String text = "";
    private Object obj = null;


    /**
     * @return
     * @preserve
     */
    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Object getObj() {
        return obj;
    }

    public Param(int type, String text, Object obj) {
        this.type = type;
        this.text = text;
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Param [type=" + type + ", text=" + text + ", obj=" + obj + "]";
    }

}
