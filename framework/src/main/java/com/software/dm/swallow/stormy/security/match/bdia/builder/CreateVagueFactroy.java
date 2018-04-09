package com.software.dm.swallow.stormy.security.match.bdia.builder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.software.dm.swallow.stormy.hadoop.tools.Constant;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_basic_type_rel;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_ip_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_basic_type_rel;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.common.ApplicationVagueContainer;
import com.software.dm.swallow.stormy.security.match.bdia.common.RepoConstant;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class CreateVagueFactroy {

    public CreateVagueFactroy() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @return
     * @throws IOException
     */
    private Properties getProperties(String filePath) throws IOException {
        InputStream inputStream = null;
        if (null != filePath) {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                inputStream = new FileInputStream(f);
            }

        } else {
            inputStream = CreateFactroy.class.getResourceAsStream(RepoConstant.REPO_NAME);
        }

        Properties prop = new Properties();
        prop.load(inputStream);
        inputStream.close();
        this.println(prop.toString().replaceAll(", ", "\r\n"));
        return prop;
    }

    /**
     * BASICAndApp
     *
     * @param urlRulePath
     * @param basicRefPath
     * @param btrMap
     * @param urlRuleList
     * @throws IOException
     */
    private void initUrlRule(String urlRulePath, String basicRefPath, Map<Long, List<T_basic_type_rel>> btrMap, List<T_url_rule> urlRuleList) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(basicRefPath), "utf8"));
        String line = null;
        String[] values = null;
        while (null != (line = br.readLine())) {
            values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);
            try {
                this.trim(values);
                long nid = Long.valueOf(values[0]);
                T_basic_type_rel tbtr = new T_basic_type_rel(nid, values[1], values[2]);
                List<T_basic_type_rel> btrSet = btrMap.get(nid);
                if (null == btrSet) {
                    btrSet = new ArrayList<T_basic_type_rel>();
                    btrSet.add(tbtr);
                    btrMap.put(nid, btrSet);
                } else {
                    btrSet.add(tbtr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.println(line);
            }
        }
        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream(urlRulePath), "utf8"));

        while (null != (line = br.readLine())) {
            if (line.isEmpty())
                continue;
            values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);
            try {
                this.trim(values);
                long nid = Long.valueOf(values[0]);
                T_url_rule tbtr = new T_url_rule(//
                        Long.valueOf(nid), //
                        values[1], //
                        values[2], //
                        values[3], //
                        values[4], //
                        values[5], //
                        values[6].isEmpty() ? -1 : Integer.valueOf(values[6]), //
                        values[7].isEmpty() ? -1 : Integer.valueOf(values[7]), //
                        values[8].isEmpty() ? -1 : Integer.valueOf(values[8]), //
                        values[9], //
                        values[10], //
                        btrMap.get(nid));
                urlRuleList.add(tbtr);
            } catch (Exception e) {
                e.printStackTrace();
                this.println(line);
            }
        }
        br.close();
    }

    /**
     * theme
     *
     * @param themeRulePath
     * @param themeRefPath
     * @param tbtrMap
     * @param themeUrlRuleList
     * @throws IOException
     */
    private void initThemeRule(String themeRulePath, String themeRefPath, Map<Long, List<T_theme_basic_type_rel>> tbtrMap, List<T_theme_url_rule> themeUrlRuleList) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(themeRefPath), "utf8"));
        String line = null;
        String[] values = null;
        while (null != (line = br.readLine())) {
            values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);
            try {
                this.trim(values);
                long nid = Long.valueOf(values[1]);
                T_theme_basic_type_rel tbtr = new T_theme_basic_type_rel(values[0], nid, values[2], values[3].isEmpty() ? -1 : Integer.valueOf(values[3]), values[4]);
                List<T_theme_basic_type_rel> tbtrSet = tbtrMap.get(nid);
                if (null == tbtrSet) {
                    tbtrSet = new ArrayList<T_theme_basic_type_rel>();
                    tbtrSet.add(tbtr);
                    tbtrMap.put(nid, tbtrSet);
                } else {
                    tbtrSet.add(tbtr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.println(line);
            }
        }
        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream(themeRulePath), "utf8"));
        while (null != (line = br.readLine())) {
            values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);
            try {
                this.trim(values);
                Long nid = Long.valueOf(values[0]);
                List<T_theme_basic_type_rel> tbtrSet = tbtrMap.get(nid);
                if (null == tbtrSet || tbtrSet.isEmpty()) {
                    this.println("T_theme_url_rule is not ref :" + line);
                    continue;
                }
                T_theme_url_rule ttur = new T_theme_url_rule(//
                        nid, //
                        values[1], //
                        values[2], //
                        values[3].isEmpty() ? -1 : Integer.valueOf(values[3]), //
                        tbtrSet);
                themeUrlRuleList.add(ttur);
            } catch (Exception e) {
                e.printStackTrace();
                this.println(line);
            }
        }
        br.close();
    }

    /**
     * @param iPRulePath
     * @param iPMap
     * @throws IOException
     */
    private void initIPRule(String iPRulePath, Map<String, T_ip_rule> iPMap) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(iPRulePath), "utf8"));
        String line = null;
        String[] values = null;
        while (null != (line = br.readLine())) {
            values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);
            this.trim(values);
            try {
                if (!values[1].isEmpty() && !values[2].isEmpty()) {
                    long nid = Long.valueOf(values[0]);
                    T_ip_rule tipr = new T_ip_rule(nid, values[1], values[2], values[3].isEmpty() ? -1 : Integer.valueOf(values[3]), values[4], values[5]);
                    iPMap.put(values[1] + values[2], tipr);
                } else {
                    this.println("T_IP_RULE is error:" + line);
                }

            } catch (Exception e) {
                e.printStackTrace();
                this.println(line);
            }
        }
        br.close();

    }

    /**
     * @param msg
     */
    private void println(String msg) {
        System.out.println(msg);
    }

    /**
     * @param extractPath
     * @param extractRuleList
     * @throws IOException
     */
    private void initExtract(String extractPath, List<T_extract_rule> extractRuleList) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(extractPath), "utf8"));
        String line = null;
        String[] values = null;
        while (null != (line = br.readLine())) {
            values = line.split(RepoConstant.REPO_SPLIT_FLAG_PLAIN, -1);
            try {
                this.trim(values);
                T_extract_rule extract = new T_extract_rule(//
                        values[0].isEmpty() ? -1 : Integer.valueOf(values[0]), //
                        values[1].isEmpty() ? -1 : Integer.valueOf(values[1]), //
                        values[2].isEmpty() ? -1 : Integer.valueOf(values[2]), //
                        values[3], //
                        values[4].isEmpty() ? -1 : Integer.valueOf(values[4]), //
                        values[5], //
                        values[6], //
                        values[7], //
                        values[8], //
                        values[9], //
                        values[10], //
                        values[11].isEmpty() ? -1 : Integer.valueOf(values[11])

                );
                if (!extract.getContent_rule().isEmpty() && !extract.getUncode().isEmpty()) {
                    String[] rules = extract.getContent_rule().split(Constant.PUB_FIELD_STR, -1);
                    String[] uncodes = extract.getUncode().split(Constant.PUB_COMMA, -1);
                    if (uncodes.length != rules.length) {
                        this.println("rules:" + rules.length + "uncodes:" + uncodes.length + " not equals " + line);
                        continue;
                    }
                    Pattern[] ps = new Pattern[rules.length];
                    Map<Integer, Integer[]> uncodesMap = new HashMap<Integer, Integer[]>();
                    for (int j = 0; j < rules.length; j++) {
                        if (!rules[j].isEmpty())
                            ps[j] = Pattern.compile(rules[j], Pattern.CASE_INSENSITIVE);
                        String[] codes = uncodes[j].split(RepoConstant.DECODING_REPEAT_SEPARATOR);
                        Integer[] ints = new Integer[codes.length];
                        for (int i = 0; i < codes.length; i++) {
                            ints[i] = Integer.valueOf(codes[i]);
                        }
                        uncodesMap.put(j, ints);
                    }

                    extract.setParamRegexPatternArray(ps);
                    extract.setUncodes(uncodesMap);
                }

                extractRuleList.add(extract);

            } catch (Exception e) {
                e.printStackTrace();
                this.println(line);
            }
        }
        br.close();

    }

    /**
     * @param ac
     * @param domain
     * @param obj
     */
    private void loadDomainEquals(ApplicationVagueContainer ac, String domain, Object obj) {
        List<Object> setObj = ac.getDomainEqualsMap().get(domain);
        if (null == setObj) {
            setObj = new ArrayList<Object>();
            setObj.add(obj);
            ac.getDomainEqualsMap().put(domain, setObj);
        } else {
            setObj.add(obj);
        }
    }

    /**
     * @param ac
     * @param type
     * @param domain
     * @param obj
     */
    private void loadAfDomain(ApplicationVagueContainer ac, int type, String domain, Object obj) {
        ac.getAfDomain().addData(domain, obj);
    }

    /**
     * @param ac
     * @param type
     * @param rule
     * @param obj
     */
    private void loadAfRule(ApplicationVagueContainer ac, int type, String rule, Object obj) {
        ac.getAfRule().addData(rule, obj);
    }

    /**
     * @param ac
     */
    private void builderBasicAndApp(ApplicationVagueContainer ac) {
        // basicAndApp
        List<T_url_rule> urlRuleList = ac.getUrlRuleList();
        for (T_url_rule t_url_rule : urlRuleList) {
            int ruleType = t_url_rule.getRule_type();
            // 1������ֵ��ƥ�� ������ƥ�䣩
            // 2������ģ��ƥ�� ������ƥ�䣩
            // 3������ģ��ƥ�� ��������ƥ�䣩
            // 4������ֵ�ȣ�����ģ��ƥ��
            // 5������ģ��������ģ��ƥ��

            switch (ruleType) {
                case 1:
                    this.loadDomainEquals(ac, t_url_rule.getDomain_comp(), t_url_rule);
                    break;
                case 2:
                    this.loadAfDomain(ac, T_url_rule.ACTION_BASIC_APP, t_url_rule.getDomain_comp(), t_url_rule);
                    break;
                case 3:
                    this.loadAfRule(ac, T_url_rule.ACTION_BASIC_APP, t_url_rule.getRule(), t_url_rule);
                    break;
                case 4:
                    this.loadDomainEquals(ac, t_url_rule.getDomain_comp(), t_url_rule);
                    this.loadAfRule(ac, T_url_rule.ACTION_BASIC_APP, t_url_rule.getRule(), t_url_rule);
                    break;
                case 5:
                    this.loadAfDomain(ac, T_url_rule.ACTION_BASIC_APP, t_url_rule.getDomain_comp(), t_url_rule);
                    this.loadAfRule(ac, T_url_rule.ACTION_BASIC_APP, t_url_rule.getRule(), t_url_rule);
                    break;
            }
        }
    }

    /**
     * @param ac
     */
    private void builderTheme(ApplicationVagueContainer ac) {
        List<T_theme_url_rule> themeUrlRuleList = ac.getThemeUrlRuleList();
        for (T_theme_url_rule t_theme_url_rule : themeUrlRuleList) {
            int ruleType = t_theme_url_rule.getRule_type();
            // 1������ֵ��ƥ�� ������ƥ�䣩
            // 2������ģ��ƥ�� ������ƥ�䣩
            // 3������ģ��ƥ�� ��������ƥ�䣩
            // 4������ֵ�ȣ�����ģ��ƥ��
            // 5������ģ��������ģ��ƥ��
            switch (ruleType) {
                case 1:
                    this.loadDomainEquals(ac, t_theme_url_rule.getDomain_comp(), t_theme_url_rule);
                    break;
                case 2:
                    this.loadAfDomain(ac, T_theme_url_rule.ACTION_THEME, t_theme_url_rule.getDomain_comp(), t_theme_url_rule);
                    break;
                case 3:
                    this.loadAfRule(ac, T_theme_url_rule.ACTION_THEME, t_theme_url_rule.getRule(), t_theme_url_rule);
                    break;
                case 4:
                    this.loadDomainEquals(ac, t_theme_url_rule.getDomain_comp(), t_theme_url_rule);
                    this.loadAfRule(ac, T_theme_url_rule.ACTION_THEME, t_theme_url_rule.getRule(), t_theme_url_rule);
                    break;
                case 5:
                    this.loadAfDomain(ac, T_theme_url_rule.ACTION_THEME, t_theme_url_rule.getDomain_comp(), t_theme_url_rule);
                    this.loadAfRule(ac, T_theme_url_rule.ACTION_THEME, t_theme_url_rule.getRule(), t_theme_url_rule);
                    break;
            }
        }

    }

    /**
     * 1������ƥ�䣬��������ƥ�䣩 2��ģ��ƥ�䣨������ƥ�䣩 3������ģ��������ƥ�� 4������ģ����ģ��ƥ�� 5������ֵ������ƥ��
     * 6������ֵ��ģ��ƥ��
     *
     * @param ac
     */
    private void builderExtract(ApplicationVagueContainer ac) {
        List<T_extract_rule> extractRuleList = ac.getExtractRuleList();
        for (T_extract_rule t_extract_rule : extractRuleList) {
            int ruleType = t_extract_rule.getRule_type();
            // 1������ƥ�䣬��������ƥ�䣩
            // 2��ģ��ƥ�䣨������ƥ�䣩
            // 3������ģ��������ƥ��
            // 4������ģ����ģ��ƥ��
            // 5������ֵ������ƥ��
            // 6������ֵ��ģ��ƥ��
            String rule = t_extract_rule.getRule();

            // String context_rule = t_extract_rule.getContent_rule();
            // if (!context_rule.isEmpty()) {
            // try {
            // String[] rules = context_rule.split("\u0001");
            // for (String string : rules) {
            // Pattern.compile(string);
            // }
            // t_extract_rule.setParamRegexs(rules);
            // } catch (Exception e) {
            // e.printStackTrace();
            // System.out.println(t_extract_rule);
            // continue;
            // }
            // }
            switch (ruleType) {
                case 1:
                    Pattern ruleP = null;
                    try {
                        ruleP = Pattern.compile(rule);
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.println(t_extract_rule.toString());
                        continue;
                    }
                    ac.getExtractPatternRuleMap().put(t_extract_rule, ruleP);
                    break;
                case 2:
                    this.loadAfRule(ac, T_extract_rule.ACTION_EXTRACT, rule, t_extract_rule);
                    break;
                case 3:
                    try {
                        ruleP = Pattern.compile(rule);
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.println(t_extract_rule.toString());
                        continue;
                    }
                    this.loadAfDomain(ac, T_extract_rule.ACTION_EXTRACT, t_extract_rule.getFullDomain(), t_extract_rule);
                    ac.getExtractDoMainPatternRuleMap().put(t_extract_rule, ruleP);
                    break;
                case 4:
                    this.loadAfDomain(ac, T_extract_rule.ACTION_EXTRACT, t_extract_rule.getFullDomain(), t_extract_rule);
                    this.loadAfRule(ac, T_extract_rule.ACTION_EXTRACT, t_extract_rule.getRule(), t_extract_rule);
                    break;
                case 5:
                    try {
                        ruleP = Pattern.compile(rule);
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.println(t_extract_rule.toString());
                        continue;
                    }
                    this.loadDomainEquals(ac, t_extract_rule.getFullDomain(), t_extract_rule);
                    ac.getExtractDoMainPatternRuleMap().put(t_extract_rule, ruleP);
                    break;
                case 6:
                    this.loadDomainEquals(ac, t_extract_rule.getFullDomain(), t_extract_rule);
                    this.loadAfRule(ac, T_extract_rule.ACTION_EXTRACT, rule, t_extract_rule);
                    break;

            }
        }
    }

    /**
     * @param ac
     */
    private void builder(ApplicationVagueContainer ac) {
        // basicAndApp
        this.builderBasicAndApp(ac);
        // theme
        this.builderTheme(ac);
        // extract
        this.builderExtract(ac);
    }

    /**
     *
     */
    public void exec(String configFilePath, String serialName) {

        ApplicationVagueContainer avc = new ApplicationVagueContainer();
        try {
            Properties prop = getProperties(configFilePath);
            // basicAndApp
            String urlRulePath = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_URL_RULE_PATCH);
            String basicRefPath = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_BASIC_TYPE_REL_PATCH);
            this.initUrlRule(urlRulePath, basicRefPath, avc.getBasicTypeRelMap(), avc.getUrlRuleList());
            // IP
            String iPRulePath = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_IP_RULE_PATCH);
            this.initIPRule(iPRulePath, avc.getiPMap());
            // theme
            String themeRulePath = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_THEME_URL_RULE_PATCH);
            String themeRefPath = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_THEME_BASIC_TYPE_REL_PATCH);
            this.initThemeRule(themeRulePath, themeRefPath, avc.getThemeBasicTypeRelMap(), avc.getThemeUrlRuleList());
            // extract
            String extractPath = prop.getProperty(RepoConstant.REPO_ROOT_PATCH) + File.separatorChar + prop.getProperty(RepoConstant.REPO_EXTRACT_RULE_PATCH);
            this.initExtract(extractPath, avc.getExtractRuleList());

            this.builder(avc);
            println("builder is ok!");

            println("BasicUrlRule size is " + avc.getUrlRuleList().size());
            println("AppIPRule size is " + avc.getiPMap().size());
            println("ThemeUrlRule size is " + avc.getThemeUrlRuleList().size());
            println("ExtractUrlRule size is " + avc.getExtractRuleList().size());
            println("ExtractPatternRule size is " + avc.getExtractPatternRuleMap().size());
            println("ExtractDoMainPatternRule size is " + avc.getExtractDoMainPatternRuleMap().size());

            avc.clear();
            this.serial(prop, avc, serialName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * @param prop
     * @param ac
     * @throws Exception
     */
    private void serial(Properties prop, ApplicationVagueContainer ac, String serialName) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Kryo kryo = ApplicationVagueContainer.getKryo();
        serialName = serialName == null ? df.format(new Date()) + ".serial" : serialName;
        File file = new File(prop.getProperty(RepoConstant.REPO_SERIAL_PATCH), serialName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        Output out = new Output(os, 10240);
        kryo.writeObject(out, ac);
        out.flush();
        os.close();

    }

    /**
     * @param trims
     */
    private void trim(String[] values) {
        if (null != values)
            for (int i = 0; i < values.length; i++) {
                //
                int len = values[i].length();
                int st = 0;
                char[] val = values[i].toCharArray();

                while ((st < len) && (val[st] <= ' ') && (val[st] != '\001')) {
                    st++;
                }
                while ((st < len) && (val[len - 1] <= ' ') && (val[len - 1] != '\001')) {
                    len--;
                }
                values[i] = ((st > 0) || (len < val.length)) ? values[i].substring(st, len) : values[i];
            }
    }

}
