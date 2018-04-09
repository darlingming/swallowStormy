package com.software.dm.swallow.stormy.algoac;

import com.software.dm.swallow.stormy.algoac.impl.AhoCorasickCharacterTree;
import com.software.dm.swallow.stormy.algoac.inter.AbstractAhoCorasick;
import com.software.dm.swallow.stormy.algoac.pojo.Param;
import com.software.dm.swallow.stormy.algoac.pojo.ResultSetEntity;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;

import java.util.*;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 * @preserve public
 */

public final class AnalysisFactroy {

    public AnalysisFactroy() {
        // TODO Auto-generated constructor stub
    }

    /**
     *
     */
    private final AbstractAhoCorasick<Character> rootAc = new AhoCorasickCharacterTree();
    private List<Param> paramList = new ArrayList<Param>();

    /**
     * @param param
     * @preserve
     */
    public void addParam(Param param) {
        paramList.add(param);
    }

    /**
     * @preserve
     */
    public void init() {
        for (Param param : paramList) {
            String value = AbstractCommonUtils.trimAsterisk(param.getText());
            String[] v = value.split("\\*");
            for (int i = 0; i < v.length; i++) {
                ResultSetEntity reg = new ResultSetEntity(v.length - i, v[i], v, param.getType(), param);
                rootAc.addKeyWord(reg);
            }

        }
        paramList.clear();
        rootAc.prepare();
    }

    private final Set<Param> resultDataSet = new HashSet<Param>();
    private final HashMap<Object, Integer> tempDataMap = new HashMap<Object, Integer>();
    private final Set<Object> coll = new HashSet<Object>();

    /**
     * @param value
     * @return
     * @preserve
     */
    public Set<Param> serachResult(String value) {
        tempDataMap.clear();
        resultDataSet.clear();
        coll.clear();
        rootAc.search(value, coll);
        for (Object object : coll) {
            ResultSetEntity reg = (ResultSetEntity) object;
            int regLen = reg.getValues().length;
            if (regLen == 1) {
                resultDataSet.add((Param) reg.getSource());
            } else {
                Integer rrde = tempDataMap.get(reg.getSource());
                if (null != rrde) {
                    rrde = rrde + reg.getRegnum();
                    if (rrde == ((regLen * (regLen + 1)) >> 1)) {
                        resultDataSet.add((Param) reg.getSource());
                        tempDataMap.remove(reg.getSource());
                    } else {
                        tempDataMap.put(reg.getSource(), rrde);
                    }
                } else {
                    tempDataMap.put(reg.getSource(), reg.getRegnum());
                }
            }
        }

        return resultDataSet;
    }
}
