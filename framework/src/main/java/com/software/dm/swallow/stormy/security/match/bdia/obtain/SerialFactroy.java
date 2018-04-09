package com.software.dm.swallow.stormy.security.match.bdia.obtain;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.software.dm.swallow.stormy.algoac.pojo.Param;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_ip_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_theme_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_url_rule;
import com.software.dm.swallow.stormy.security.match.bdia.common.ApplicationContainer;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class SerialFactroy {

    private T_url_rule basicAppResult = null;
    private final List<T_theme_url_rule> themeResultList = new ArrayList<T_theme_url_rule>();
    private final List<T_extract_rule> extractResultList = new ArrayList<T_extract_rule>();
    private T_ip_rule ipResult = null;
    private ApplicationContainer applicationContainer;

    public SerialFactroy(String serialPath) throws IOException {
        this.deSerial(serialPath);
    }

    public SerialFactroy(InputStream is) throws IOException {
        this.deSerial(is);
    }

    /**
     * @param serialPath
     * @return
     * @throws IOException
     */
    private ApplicationContainer deSerial(String serialPath) throws IOException {
        Kryo kryo = ApplicationContainer.getKryo();

        InputStream is = new BufferedInputStream(new FileInputStream(serialPath));
        // InputStream is = new BufferedInputStream(new
        // GZIPInputStream(ClassLoader.getSystemResourceAsStream(Constant.REPO_SERIAL)));
        ApplicationContainer applicationContainer = kryo.readObject(new Input(is, 10240), ApplicationContainer.class);
        is.close();
        Map<T_extract_rule, Pattern> extractPatternRuleMap = applicationContainer.getExtractPatternRuleMap();
        // System.out.println(extractPatternRuleMap.entrySet().iterator().next().getKey());
        this.applicationContainer = applicationContainer;
        return applicationContainer;
    }

    /**
     * @param is
     * @return
     * @throws IOException
     */
    private ApplicationContainer deSerial(InputStream is) throws IOException {
        Kryo kryo = ApplicationContainer.getKryo();

        ApplicationContainer applicationContainer = kryo.readObject(new Input(is, 10240), ApplicationContainer.class);
        is.close();

        Map<T_extract_rule, Pattern> extractPatternRuleMap = applicationContainer.getExtractPatternRuleMap();
        for (Map.Entry<T_extract_rule, Pattern> elem : extractPatternRuleMap.entrySet()) {
            T_extract_rule ter = elem.getKey();
            Pattern p = elem.getValue();
            extractPatternRuleMap.put(ter, Pattern.compile(p.pattern(), Pattern.CASE_INSENSITIVE));
        }
        Map<T_extract_rule, Pattern> extractDoMainPatternRuleMap = applicationContainer.getExtractDoMainPatternRuleMap();
        for (Map.Entry<T_extract_rule, Pattern> elem : extractDoMainPatternRuleMap.entrySet()) {
            T_extract_rule ter = elem.getKey();
            Pattern p = elem.getValue();
            extractDoMainPatternRuleMap.put(ter, Pattern.compile(p.pattern(), Pattern.CASE_INSENSITIVE));
        }

        List<T_extract_rule> extractRuleList = applicationContainer.getExtractRuleList();
        for (T_extract_rule t_extract_rule : extractRuleList) {
            Pattern[] ps = t_extract_rule.getParamRegexPatternArray();
            if (null != ps) {
                for (int i = 0; i < ps.length; i++) {
                    if (null != ps[i])
                        ps[i] = Pattern.compile(ps[i].pattern(), Pattern.CASE_INSENSITIVE);
                }
            }
        }
        this.applicationContainer = applicationContainer;
        return applicationContainer;
    }

    /**
     * @param tur
     */
    private void checkBasic(T_url_rule tur) {
        if (null != this.basicAppResult) {
            if (this.basicAppResult.getRule_type_level() < tur.getRule_type_level()) {
                this.basicAppResult = tur;
            }
        } else {
            this.basicAppResult = tur;
        }
    }

    /**
     *
     */
    private final Set<Object> tempResultSet = new HashSet<Object>();

    private void clear() {
        this.tempResultSet.clear();
        this.basicAppResult = null;
        this.ipResult = null;
        this.themeResultList.clear();
        this.extractResultList.clear();
    }

    /**
     * @param ip
     * @param port
     */
    public T_ip_rule executeIP(final String ip, final String port) {
        this.ipResult = null;
        if (!ip.isEmpty() && !port.isEmpty())
            this.ipResult = this.applicationContainer.getiPMap().get(ip + port);
        return this.ipResult;
    }

    /**
     * @param applicationContainer
     * @param url
     * @param fullDomain
     */
    public boolean execute(final String[] fullDomain) {
        boolean outputBool = false;
        this.clear();

        // String[] fullDomain =
        // AbstractCommonUtils.getFullDomainWithBareUrl(sourceurl.getBytes());
        // domain match
        final String domain = fullDomain[0];
        final String url = fullDomain[1];

        Map<String, List<Object>> domainEqualsMap = this.applicationContainer.getDomainEqualsMap();
        List<Object> domainEqualsSet = domainEqualsMap.get(domain);
        if (null != domainEqualsSet) {
            for (Object object : domainEqualsSet) {
                if (object instanceof T_url_rule) {
                    T_url_rule tur = (T_url_rule) object;
                    if (tur.getRule_type() == 1) {
                        outputBool = true;
                        this.checkBasic(tur);
                    } else {
                        tempResultSet.add(tur);
                    }

                } else if (object instanceof T_theme_url_rule) {
                    T_theme_url_rule ttur = (T_theme_url_rule) object;
                    if (ttur.getRule_type() == 1) {
                        outputBool = true;
                        themeResultList.add(ttur);
                    } else {
                        tempResultSet.add(ttur);
                    }
                } else if (object instanceof T_extract_rule) {
                    tempResultSet.add(object);
                }

            }
        }
        // domain ģ��ƥ��
        Set<Param> paramSet = applicationContainer.getAfDomain().serachResult(domain);
        if (!paramSet.isEmpty()) {
            for (Param param : paramSet) {
                Object object = param.getObj();
                if (object instanceof T_url_rule) {
                    T_url_rule tur = (T_url_rule) object;
                    if (tur.getRule_type() == 2) {
                        outputBool = true;
                        this.checkBasic(tur);
                    } else {
                        tempResultSet.add(tur);
                    }
                } else if (object instanceof T_theme_url_rule) {
                    T_theme_url_rule ttur = (T_theme_url_rule) object;
                    if (ttur.getRule_type() == 2) {
                        outputBool = true;
                        themeResultList.add(ttur);
                    } else {
                        tempResultSet.add(ttur);
                    }
                } else if (object instanceof T_extract_rule) {
                    tempResultSet.add(object);
                }
            }
        }

        // ����ģ��ƥ��
        paramSet = applicationContainer.getAfRule().serachResult(url);
        if (!paramSet.isEmpty()) {
            for (Param param : paramSet) {
                Object object = param.getObj();
                if (object instanceof T_url_rule) {
                    T_url_rule tur = (T_url_rule) object;
                    if (tur.getRule_type() == 3) {
                        outputBool = true;
                        this.checkBasic(tur);
                    } else {
                        if (this.tempResultSet.contains(tur)) {
                            outputBool = true;
                            this.checkBasic(tur);
                            this.tempResultSet.remove(object);
                        }
                    }

                } else if (object instanceof T_theme_url_rule) {
                    T_theme_url_rule ttur = (T_theme_url_rule) object;
                    if (ttur.getRule_type() == 3) {
                        outputBool = true;
                        themeResultList.add(ttur);
                    } else {
                        if (this.tempResultSet.contains(ttur)) {
                            outputBool = true;
                            themeResultList.add(ttur);
                            this.tempResultSet.remove(object);
                        }
                    }
                } else if (object instanceof T_extract_rule) {
                    T_extract_rule ter = (T_extract_rule) object;
                    if (ter.getRule_type() == 2) {
                        outputBool = true;
                        this.extractResultList.add(ter);
                    } else {
                        if (this.tempResultSet.contains(ter)) {
                            outputBool = true;
                            this.extractResultList.add(ter);
                            this.tempResultSet.remove(object);
                        }
                    }

                }
            }
        }
        // extract pattern
        Map<T_extract_rule, Pattern> extractPattern = applicationContainer.getExtractPatternRuleMap();

        for (Map.Entry<T_extract_rule, Pattern> entryMap : extractPattern.entrySet()) {
            T_extract_rule t_extract_rule = entryMap.getKey();
            if (entryMap.getValue().matcher(url).find()) {
                outputBool = true;
                this.extractResultList.add(t_extract_rule);
            }
        }

        Map<T_extract_rule, Pattern> extractDoMainPattern = applicationContainer.getExtractDoMainPatternRuleMap();
        if (!this.tempResultSet.isEmpty() && !extractDoMainPattern.isEmpty()) {
            int patternInt = extractDoMainPattern.size();
            int tempInt = this.tempResultSet.size();
            if (patternInt > tempInt) {
                for (Object setobj : this.tempResultSet) {
                    if (setobj instanceof T_extract_rule) {
                        T_extract_rule domainTer = (T_extract_rule) setobj;
                        Pattern p = extractDoMainPattern.get(domainTer);
                        if (null != p && p.matcher(url).find()) {
                            outputBool = true;
                            this.extractResultList.add(domainTer);
                        }
                    }
                }
            } else {
                for (Map.Entry<T_extract_rule, Pattern> entryMap : extractDoMainPattern.entrySet()) {
                    T_extract_rule t_extract_rule = entryMap.getKey();
                    if (this.tempResultSet.contains(t_extract_rule) && entryMap.getValue().matcher(url).find()) {
                        outputBool = true;
                        this.extractResultList.add(t_extract_rule);
                        this.tempResultSet.remove(t_extract_rule);
                    }
                }
            }

        }

        // for (Map.Entry<T_extract_rule, Pattern> entryMap :
        // extractPattern.entrySet()) {
        // T_extract_rule t_extract_rule = entryMap.getKey();
        // if (t_extract_rule.getRule_type() == 1 &&
        // entryMap.getValue().matcher(url).find()) {
        // outputBool = true;
        // this.extractResultList.add(t_extract_rule);
        // } else {
        // if (this.tempResultSet.contains(t_extract_rule) &&
        // entryMap.getValue().matcher(url).find()) {
        // outputBool = true;
        // this.extractResultList.add(t_extract_rule);
        // this.tempResultSet.remove(t_extract_rule);
        // // System.out.println(t_extract_rule.getRid());
        // }
        // }
        // }

        // end
        return outputBool;
    }

    public void outputClear() {

    }

    public void outputIpClear() {

    }

    public void outputBasic() {

    }

    public void outputApp() {

    }

    public void outputTheme() {

    }

    /**
     * @param url
     * @param t_extract_rule
     */
    public void outputExtract(String url, T_extract_rule t_extract_rule) {
        String result = ExtractUtils.extractResult(url, t_extract_rule);
        System.out.println("===========" + Arrays.toString(t_extract_rule.getParamRegexPatternArray()));
    }

    public void writer() {

    }

    public T_url_rule getBasicAppResult() {
        return basicAppResult;
    }

    public List<T_theme_url_rule> getThemeResultList() {
        return themeResultList;
    }

    public List<T_extract_rule> getExtractResultList() {
        return extractResultList;
    }

    public T_ip_rule getIpResult() {
        return ipResult;
    }

}
