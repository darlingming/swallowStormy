package com.software.dm.swallow.stormy.security.match.bdia.bean;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class T_extract_rule {

    public static final int ACTION_EXTRACT = 2;

    private int rid;
    private int theme_id;
    private int plat_id;
    private String prod_id;
    private int action_id;
    private String content_type;
    private String fullDomain;
    private String rule;
    private String content_rule;
    private String uncode;
    private Map<Integer, Integer[]> uncodes;
    private String spiderType;
    private int rule_type;
    // private String[] paramRegexs;
    private Pattern[] paramRegexPatternArray;

    public T_extract_rule(int rid, int theme_id, int plat_id, String prod_id, int action_id, String content_type, String fullDomain, String rule, String content_rule, String uncode, String spiderType, int rule_type) {

        this.rid = rid;
        this.theme_id = theme_id;
        this.plat_id = plat_id;
        this.prod_id = prod_id;
        this.action_id = action_id;
        this.content_type = content_type;
        this.fullDomain = fullDomain;
        this.rule = rule;
        this.content_rule = content_rule;
        this.uncode = uncode;
        this.spiderType = spiderType;
        this.rule_type = rule_type;
    }

    public Pattern[] getParamRegexPatternArray() {
        return paramRegexPatternArray;
    }

    public void setParamRegexPatternArray(Pattern[] paramRegexPatternArray) {
        this.paramRegexPatternArray = paramRegexPatternArray;
    }

    public int getRid() {
        return rid;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public int getPlat_id() {
        return plat_id;
    }

    public String getProd_id() {
        return prod_id;
    }

    public int getAction_id() {
        return action_id;
    }

    public String getContent_type() {
        return content_type;
    }

    public String getFullDomain() {
        return fullDomain;
    }

    public String getRule() {
        return rule;
    }

    public String getContent_rule() {
        return content_rule;
    }

    public String getUncode() {
        return uncode;
    }

    public String getSpiderType() {
        return spiderType;
    }

    public int getRule_type() {
        return rule_type;
    }

    public Map<Integer, Integer[]> getUncodes() {
        return uncodes;
    }

    public void setUncodes(Map<Integer, Integer[]> uncodes) {
        this.uncodes = uncodes;
    }

    @Override
    public String toString() {
        return "T_extract_rule [rid=" + rid + ", theme_id=" + theme_id + ", plat_id=" + plat_id + ", prod_id=" + prod_id + ", action_id=" + action_id + ", content_type=" + content_type + ", fullDomain=" + fullDomain + ", rule=" + rule + ", content_rule=" + content_rule + ", uncode=" + uncode + ", spiderType=" + spiderType + ", rule_type=" + rule_type + "]";
    }

}
