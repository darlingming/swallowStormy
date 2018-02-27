package com.software.dm.swallow.stormy.hadoop.service.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;

import com.software.dm.swallow.stormy.hadoop.common.HadoopConstants;
import com.software.dm.swallow.stormy.hadoop.service.common.AbstractConstants;
import com.software.dm.swallow.stormy.hadoop.tools.AbstractCommonUtils;
import com.software.dm.swallow.stormy.hadoop.tools.DataConvert;

public class SceneConstant extends AbstractConstants {
	private Configuration conf;
	public static final SimpleDateFormat df = new SimpleDateFormat();
	public static final String DATE_REGEX = "yyyyMMddHHmmss";
	public static final DateFormat DF_OUT = new SimpleDateFormat(DATE_REGEX);
	public static final DateFormat DF_HH_MM_SS = new SimpleDateFormat("HHmmss");
	public static final DateFormat DF_HH_MM = new SimpleDateFormat("HHmm");
	public static final DateFormat DF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat DF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	private static final int wei_poi = 6;
	public static final String COUNT_NUM = "1";
	private int label_num = 20;
	public static final String DATE_FORMAT = "dateFormat";
	public final static String LABE_NUM_NAME = "labelsNum";
	public final static String OPER_DATE = "OperDate";
	private String oper_date = "";
	public final static String PROVICE = "province";
	private String provice = "";
	public final static String VER_BOSE_LEVEL = "verboseLevel";
	private static int ver_bose_level = 1;
	public static final String KV_FILE = "/kv_terminal_db.txt";
	public final char[] mall = { '1', '9' }, pv = { '2', '7' }, ua = { '3', '5' }, coordinates = { '4', '6' }, lacci = { '8', '0' };
	public final static String DATA_MD5 = "data.md5.id";
	public static boolean data_md5 = true;
	public final static String LINE_NUM = "compressRatio";
	public static int line_num = 1;
	public static boolean line_cipher = false;
	public final static String LINE_CIPHER = "line.cipher";

	public final static String OUT_PATH_STANDARD = "standard";
	public final static String OUT_PATH_INCRE = "incre";

	public SceneConstant(Configuration conf) {
		this.conf = conf;
	}

	public int getPv(int pv) {
		// 0~9 ��1,10~99,2,100~999��3,1000~9999��4
		int w_poi = String.valueOf(pv).length();
		int re_result = 1;
		switch (w_poi) {
		case 1:
			re_result = 1;
			break;
		case 2:
			re_result = 2;
			break;
		case 3:
			re_result = 3;
			break;
		case 4:
			re_result = 4;
			break;
		case 5:
			re_result = 5;
			break;
		case 6:
			re_result = 6;
			break;
		}
		return re_result;
	}

	public int getBusinessRandom() {
		return AbstractCommonUtils.getRandomInt(1) & 1;
	}

	public String getpv_label(String pvs) {
		String result = "";
		if (pvs.length() > wei_poi) {
			while (pvs.length() > wei_poi) {
				result += pvs.substring(0, wei_poi) + AbstractConstants.PUB_COMMA;
				pvs = pvs.substring(wei_poi);
			}
			if (!pvs.isEmpty()) {
				result += pvs;
			} else {
				result = result.substring(0, result.length() - 1);
			}
		} else {
			result = pvs;
		}

		return result;
	}

	public void init() {
		INPUT_FIELD = getFieldSplit(conf.get(HadoopConstants.INPUT_FIELD_SPLIT));
		OUTPUT_FIELD = getFieldSplit(conf.get(HadoopConstants.OUTPUT_FIELD_SPLIT));
		label_num = Integer.parseInt(conf.get(LABE_NUM_NAME, "20"));
		oper_date = conf.get(OPER_DATE, "");
		provice = conf.get(PROVICE, "");
		df.applyPattern(conf.get(DATE_FORMAT, "yyyyMMddHHmmss"));
		ver_bose_level = Integer.parseInt(conf.get(VER_BOSE_LEVEL, "1"));
		line_num = Integer.parseInt(conf.get(LINE_NUM, "1"));
		line_cipher = conf.getBoolean(LINE_CIPHER, line_cipher);

	}

	public int getLabel_num() {
		return label_num - label_num / wei_poi;
	}

	public int getLabel_num_true() {
		return label_num;
	}

	public void setLabel_num(int label_num) {
		this.label_num = label_num;
	}

	/**
	 * 
	 * @param word_bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String[] encodeData(byte[] word_bytes) throws UnsupportedEncodingException {
		return DataConvert.fromData(word_bytes);
	}

	/**
	 * 
	 * @param word_bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String encodeData(String word_bytes) throws UnsupportedEncodingException {
		return DataConvert.fromData(word_bytes, 20);
	}

	/**
	 * DM
	 * 
	 * @see ��ʽ����׼ʱ��
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static String getFormatDate(final String datestr) throws ParseException {
		return DF_OUT.format(df.parse(getTimeStr(datestr)));
	}

	/**
	 * 
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public int getHour(String startTime) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(getTimeStr(startTime)));
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public String getHHmmss(String startTime) throws ParseException {
		return DF_HH_MM_SS.format(df.parse(getTimeStr(startTime)));
	}

	/**
	 * 
	 * @param startTime
	 * @return
	 * @throws ParseException
	 */
	public String getHourStr(String startTime) throws ParseException {
		int h = getHour(startTime);
		String result = String.valueOf(getHour(startTime));
		if (h < 10)
			result = "0" + result;
		return result;
	}

	/**
	 * DM
	 * 
	 * @param startTime
	 * @return
	 */
	private static String getTimeStr(final String startTime) {// 2015 1111
																// 005406
		return startTime.substring(0, df.toPattern().length());
	}

	public static void main(String[] args) {
		SceneConstant a = new SceneConstant(null);
		for (int i = 0; i < 100; i++) {
			int z = AbstractCommonUtils.getRandomLessInt(9) + 1;
			System.out.println(z);
		}

	}

	public String getValuePv(int num, int addnum) {
		return String.valueOf(AbstractCommonUtils.getRandomLessInt(num) + addnum);
	}

	public String getProvice() {
		return provice;
	}

	public String getOper_date() {
		return oper_date;
	}

	/**
	 * @throws ParseException
	 * 
	 * @Title: dateSubtractionFive
	 * @Description: ���ڵ���7 ��ȥ5
	 * @param @param hour
	 * @param @return �趨�ļ�
	 * @return String ��������
	 * @throws
	 */
	public String dateSubtractionFive(String startTime) throws ParseException {
		int hour = getHour(startTime);
		if (hour >= 7) {
			return String.valueOf(hour - 5);
		} else if (hour >= 0 && hour < 5) {
			return "x0";
		} else if (hour == 5) {
			return "x1";
		} else if (hour == 6) {
			return "x2";
		}
		return "";
	}

	public int getVer_bose_level() {
		return ver_bose_level;
	}

	// ����double
	public double roundDouble(double maxValue, double totalValue) {
		return Math.round(maxValue * 100D / totalValue) / 100D;
	}

	/**
	 * 
	 * @param ua
	 * @return
	 */
	public String evaluate(String ua) {
		String ret = "-1";
		String[] regexs = { "\\(((?:iphone|ipad)[^\\);]*)(?:;)", "(?:\\(linux;)(?:[^\\)]*?)([^\\);]*)(?:miui|build)(?:[^\\)]*)(?:\\))", "(?:\\(compatible;)(?:[^\\)]*?)([^\\);]*)(?:\\))" };
		for (String regex : regexs) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(ua.toLowerCase());
			if (m.find()) {
				// �ж�ƥ���ϵ������Ƿ���Ҫ����Ϊid����Ʒid����λ��
				ret = m.group(1).trim();
				break;
			}
		}
		return ret;
	}

	private static StringBuffer hexValue = new StringBuffer();

	public static String string2MD5(String inStr) {
		inStr = inStr == null ? "" : inStr;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		hexValue.delete(0, hexValue.length());
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();

	}

	public static String yyyyMMddToyyyy_MM_dd(String yyyyMMdd) {
		try {
			return DF_YYYY_MM_DD.format(DF_YYYYMMDD.parse(yyyyMMdd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return yyyyMMdd;
	}

}
