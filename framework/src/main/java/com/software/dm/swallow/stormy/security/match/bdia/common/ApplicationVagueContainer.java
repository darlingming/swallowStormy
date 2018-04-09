package com.software.dm.swallow.stormy.security.match.bdia.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.esotericsoftware.kryo.Kryo;
import com.software.dm.swallow.stormy.algoac.VagueAnalysisFactroy;
import com.software.dm.swallow.stormy.algoac.impl.VagueStateCharacter;
import com.software.dm.swallow.stormy.algoac.inter.AbstractVagueState;
import com.software.dm.swallow.stormy.algoac.inter.VagueState;
import com.software.dm.swallow.stormy.algoac.pojo.VagueResultEntity;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_basic_type_rel;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_ip_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_basic_type_rel;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_url_rule;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class ApplicationVagueContainer {

    private final Map<String, List<Object>> domainEqualsMap = new HashMap<String, List<Object>>();
    private final VagueAnalysisFactroy afDomain = new VagueAnalysisFactroy();
    private final VagueAnalysisFactroy afRule = new VagueAnalysisFactroy();
    private final Map<T_extract_rule, Pattern> extractPatternRuleMap = new HashMap<T_extract_rule, Pattern>();
    private final Map<T_extract_rule, Pattern> extractDoMainPatternRuleMap = new HashMap<T_extract_rule, Pattern>();
    private final Map<String, T_ip_rule> iPMap = new HashMap<String, T_ip_rule>();

    private final Map<Long, List<T_basic_type_rel>> basicTypeRelMap = new HashMap<Long, List<T_basic_type_rel>>();
    private final Map<Long, List<T_theme_basic_type_rel>> themeBasicTypeRelMap = new HashMap<Long, List<T_theme_basic_type_rel>>();

    private final List<T_url_rule> urlRuleList = new ArrayList<T_url_rule>();
    private final List<T_theme_url_rule> themeUrlRuleList = new ArrayList<T_theme_url_rule>();
    private final List<T_extract_rule> extractRuleList = new ArrayList<T_extract_rule>();

    public ApplicationVagueContainer() {
        // TODO Auto-generated constructor stub
    }

    public void clear() {
        // this.urlRuleList.clear();
        // this.themeUrlRuleList.clear();
        // this.extractRuleList.clear();
        this.basicTypeRelMap.clear();
        this.themeBasicTypeRelMap.clear();
    }

    public static Kryo getKryo() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(true);

        kryo.register(ApplicationVagueContainer.class);
        kryo.register(VagueAnalysisFactroy.class);
        kryo.register(VagueStateCharacter.class);
        kryo.register(HashMap.class);
        kryo.register(ArrayList.class);
        kryo.register(HashSet.class);

        kryo.register(VagueResultEntity.class);
        kryo.register(T_extract_rule.class);
        kryo.register(T_basic_type_rel.class);
        kryo.register(T_theme_url_rule.class);
        kryo.register(T_theme_basic_type_rel.class);
        kryo.register(T_basic_type_rel.class);
        kryo.register(T_url_rule.class);
        kryo.register(T_ip_rule.class);
        kryo.register(AbstractVagueState.class);
        kryo.register(VagueState.class);

        kryo.register(String[].class);
        kryo.register(Integer[].class);

        kryo.register(Pattern.class);
        kryo.register(Pattern[].class);
        return kryo;
    }

    public Map<String, List<Object>> getDomainEqualsMap() {
        return domainEqualsMap;
    }

    public VagueAnalysisFactroy getAfDomain() {
        return afDomain;
    }

    public VagueAnalysisFactroy getAfRule() {
        return afRule;
    }

    // public Map<String, Set<T_extract_rule>> getExtractStringRuleMap() {
    // return extractStringRuleMap;
    // }

    public Map<T_extract_rule, Pattern> getExtractPatternRuleMap() {
        return extractPatternRuleMap;
    }

    public Map<T_extract_rule, Pattern> getExtractDoMainPatternRuleMap() {
        return extractDoMainPatternRuleMap;
    }

    public Map<String, T_ip_rule> getiPMap() {
        return iPMap;
    }

    public Map<Long, List<T_basic_type_rel>> getBasicTypeRelMap() {
        return basicTypeRelMap;
    }

    public Map<Long, List<T_theme_basic_type_rel>> getThemeBasicTypeRelMap() {
        return themeBasicTypeRelMap;
    }

    public List<T_url_rule> getUrlRuleList() {
        return urlRuleList;
    }

    public List<T_theme_url_rule> getThemeUrlRuleList() {
        return themeUrlRuleList;
    }

    public List<T_extract_rule> getExtractRuleList() {
        return extractRuleList;
    }

}
