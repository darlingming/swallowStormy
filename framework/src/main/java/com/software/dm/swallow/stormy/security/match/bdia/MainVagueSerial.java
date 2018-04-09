package com.software.dm.swallow.stormy.security.match.bdia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.software.dm.swallow.stormy.security.match.bdia.builder.CreateVagueFactroy;

/**
 * @author DM
 * @version v1.0.0.1
 * @Description
 * @date 2017
 */
public class MainVagueSerial {

    public static final String param_Flag = "-D";
    public static final String CONF_PATH = "conf.path";
    public static final String SERIAL_NAME = "serial.name";
    public static final String VERSION = "BDIA_FS_V4.0.2.3";

    public MainVagueSerial() {
    }

    public static void main(String[] args) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()) + " start......");
        System.out.println("Serial_Version:" + VERSION);
        System.out.println(Arrays.toString(args));
        Map<String, String> paramMap = new HashMap<String, String>();
        if (null != args && args.length > 0 && (args.length & 1) == 0) {
            for (int i = 0; i < args.length; i++) {
                if (param_Flag.equals(args[i])) {
                    String[] vs = args[++i].split("=", -1);
                    if (vs.length == 2) {
                        paramMap.put(vs[0], vs[1]);
                    }
                }
            }
        }
        CreateVagueFactroy cf = new CreateVagueFactroy();
        cf.exec(paramMap.get(CONF_PATH), paramMap.get(SERIAL_NAME));
        System.out.println(df.format(new Date()) + " end......");
    }

}
