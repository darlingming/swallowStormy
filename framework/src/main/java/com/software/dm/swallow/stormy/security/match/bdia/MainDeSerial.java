package com.software.dm.swallow.stormy.security.match.bdia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;
import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.obtain.SerialFactroy;

public final class MainDeSerial {

	public MainDeSerial() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String serialPath = "D:/DearM/repo/v4/serial" + File.separatorChar + df.format(new Date()) + "_serial";
			SerialFactroy sf = new SerialFactroy(serialPath);
			// Set<Param> pset =
			// applicationContainer.getAfDomain().serachResult("mp.weixin.qq.comclient.map.baidu.com");
			// System.out.println(pset);
			String url = "http://APP.nuomi.com/naserver/ITEM/ITEMDETAILNEW/aaa&AS=TYRWINFFJKHRTUJHGFFjsjdfdsfhJJHG";
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
			for (T_extract_rule t_extract_rule : sf.getExtractResultList()) {

				sf.outputExtract(url, t_extract_rule);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
