package com.software.dm.swallow.stormy.security.match.bdia;

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.obtain.SerialVagueFactroy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public final class MainVagueDeSerial {

    public MainVagueDeSerial() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            String serialPath = "D:/DearM/repo/yun/serial" + File.separatorChar + df.format(new Date()) + ".serial";
            SerialVagueFactroy sf = new SerialVagueFactroy(serialPath);
            // Set<Param> pset =
            // applicationContainer.getAfDomain().serachResult("mp.weixin.qq.comclient.map.baidu.com");
            // System.out.println(pset);
            String url = "http://gdl.sregame-download.com/mobile/immortal_texas/src/common/layer/SubLayer.lua?game_timestamp=1.0.84";
            System.out.println(AbstractCommonUtils.toLowerCase(url));
            String[] fullDomain = AbstractCommonUtils.getFullDomainWithBareUrl(url.getBytes());
            System.out.println(Arrays.toString(fullDomain));
            sf.execute(fullDomain);
            // System.out.println(sf.basicAppResult.getBasicTypes().size());
            // for (T_theme_url_rule ttur : sf.themeResultList) {
            // System.out.println(ttur.getThemeTypes().size());
            // System.out.println(ttur.getN_id());
            // }
            System.out.println(sf.getThemeResultList());
            System.out.println(sf.getExtractResultList());
            System.out.println(sf.getIpResult());
            System.out.println("Bqz_id="+sf.getBasicAppResult().getBqz_id());
            for (T_extract_rule t_extract_rule : sf.getExtractResultList()) {

                sf.outputExtract(url, t_extract_rule);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
