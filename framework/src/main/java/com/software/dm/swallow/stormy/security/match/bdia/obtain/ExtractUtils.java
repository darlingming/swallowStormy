package com.software.dm.swallow.stormy.security.match.bdia.obtain;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;

import com.software.dm.swallow.stormy.security.match.bdia.bean.T_extract_rule;
import com.software.dm.swallow.stormy.security.match.bdia.common.RepoConstant;

/**
 * 
 * @Description
 * @author DM
 * @date 2017
 * @version v1.0.0.1
 * 
 */
public class ExtractUtils {
	private static BASE64Decoder base64Decoder = new BASE64Decoder();
	public static Pattern p_email = Pattern.compile("^[0-9a-z\\._-]+@[0-9a-z\\._-]+\\.(com|net|cn|cc|hk|org|tw|edu)$", Pattern.CASE_INSENSITIVE);

	public ExtractUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String changeUrlcode(String result, String urlcodeType) {
		try {
			result = java.net.URLDecoder.decode(result.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), urlcodeType);
		} catch (Exception e) {
			return result;
		}
		return result;
	}

	public static String changeMac(String result) {
		StringBuffer str = new StringBuffer(result);
		str.insert(2, ":");
		str.insert(5, ":");
		str.insert(8, ":");
		str.insert(11, ":");
		str.insert(14, ":");
		return str.toString();
	}

	public static String chageIdfa(String result) {
		StringBuffer str = new StringBuffer(result);
		str.insert(8, "-");
		str.insert(13, "-");
		str.insert(18, "-");
		str.insert(23, "-");
		return str.toString();
	}

	public static String chageMacTypeTwo(String result) {
		return result.replaceAll("-", ":");
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String getRexStr(String rex, String str) {
		String result = "";
		Pattern p = Pattern.compile(rex.toLowerCase());
		Matcher m = p.matcher(str.toLowerCase());
		if (m.find()) {
			result = m.group(1);
		}
		return result;
	}

	public static String changeUnicode(String result) {
		try {
			StringBuffer sb = new StringBuffer();
			while (result.length() > 0) {
				if (result.startsWith("\\u")) {
					int x = Integer.parseInt(result.substring(2, 6), 16);
					sb.append((char) x);
					result = result.substring(6);
				} else {
					sb.append(result.charAt(0));
					result = result.substring(1);
				}
			}
			result = sb.toString();
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	/**
	 * @author DM
	 * @param result
	 * @param action_id
	 * @return
	 */
	public static String removeDirtyData(String result, int action_id) {
		if (result == null || result.isEmpty()) {
			return "";
		}
		final String temp = result.toLowerCase();

//		if ("null".equals(temp) || CommonUtils.checkExistsChar(temp) || "undefined".equals(temp)) {
//			return "";
//		}

		if (9002 == action_id) {// mac
			if ("|02:00:00:00:00:00|00:00:00:00:00:00|ff:ff:ff:ff:ff:ff|".indexOf("|" + temp + "|") != -1) {
				return "";
			}
		} else if (9001 == action_id) {// idfa
			if ("|00000000-0000-0000-0000-000000000000|ffffffff-ffff-ffff-ffff-ffffffffffff|".indexOf("|" + temp + "|") != -1) {
				return "";
			}
		} else if (9029 == action_id) {// carCode
			if (result.length() > 2) {
				String flag = result.substring(0, 2);
				String car_val = carMap.get(flag);
				if (null != car_val) {
					result = car_val + result.substring(2);
				}
			}

		} else if (9014 == action_id) {// mail
			Matcher m = p_email.matcher(result);
			if (!m.matches()) {
				return "";
			}
		}

		return result;
	}

	public static Map<String, String> carMap = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("01", "¾©");
			put("02", "½ò");
			put("03", "¼½");
			put("04", "½ú");
			put("05", "ÃÉ");
			put("06", "ÁÉ");
			put("07", "¼ª");
			put("08", "ºÚ");
			put("09", "»¦");
			put("10", "ËÕ");
			put("11", "Õã");
			put("12", "Íî");
			put("13", "Ãö");
			put("14", "¸Ó");
			put("15", "Â³");
			put("16", "Ô¥");
			put("17", "¶õ");
			put("18", "Ïæ");
			put("19", "ÔÁ");
			put("20", "¹ð");
			put("21", "Çí");
			put("22", "Óå");
			put("23", "´¨");
			put("24", "¹ó");
			put("25", "ÔÆ");
			put("26", "²Ø");
			put("27", "ÉÂ");
			put("28", "¸Ê");
			put("29", "Çà");
			put("30", "Äþ");
			put("31", "ÐÂ");
		}
	};

	public static String changeBase64(String target) {
		String result;
		try {
			result = new String(base64Decoder.decodeBuffer(target));
		} catch (Exception e) {
			result = target;
		}
		return result;
	}

	/**
	 * removeDirtyData(decodedStr, t_extract_rule.getAction_id()).replaceAll("["
	 * + '\001' + "\\t\\n\\r]", "");
	 * 
	 * @param extractedStr
	 * @param t_extract_rule
	 * @return
	 */
	private static String decode(final String extractedStr, Integer[] uncode) {
		if (null == extractedStr || extractedStr.isEmpty())
			return "";
		String decodedStr = extractedStr;
		for (Integer codeInt : uncode) {
			switch (codeInt) {
			case 0:
				break;
			case 1:
				decodedStr = changeUnicode(decodedStr);// unicode
				break;
			case 2:
				decodedStr = changeUrlcode(decodedStr, "utf-8");// urlcode
				break;
			case 3:
				decodedStr = changeMac(decodedStr);// Mac
				break;
			case 4:
				decodedStr = chageIdfa(decodedStr);
				break;
			case 5:
				decodedStr = chageMacTypeTwo(decodedStr);
				break;
			case 6:
				decodedStr = changeUrlcode(decodedStr, "gb2312");// urlcode
				break;
			case 7:

				break;
			case 8:
				decodedStr = changeBase64(decodedStr.toString());// base64
				break;
			case 9:
				decodedStr = decodedStr.toLowerCase();
				break;
			case 10:
				decodedStr = decodedStr.toUpperCase();
				break;
			case 11:
				Pattern p = Pattern.compile("\"scenicId\":.*?,", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(decodedStr);
				while (m.find()) {
					decodedStr = m.group().substring("\"scenicId\":".length(), m.group().length() - 1);
				}
				break;
			case 12:
				p = Pattern.compile("\"productId\":.*?,", Pattern.CASE_INSENSITIVE);
				m = p.matcher(decodedStr);
				while (m.find()) {
					decodedStr = m.group().substring("\"productId\":".length(), m.group().length() - 1);
				}
				break;

			}
		}
		return decodedStr;
	}

	/**
	 * 
	 * @param url
	 * @param bean
	 * @return
	 */
	public static String extractResult(final String url, final T_extract_rule bean) {
		Pattern[] paramPatternArray = bean.getParamRegexPatternArray();
		if (null == paramPatternArray)
			return "";
		String tmpResult = "";
		for (int i = 0; i < paramPatternArray.length; i++) {
			if (i != 0) {
				if (91 == bean.getTheme_id()) {
					tmpResult = tmpResult + RepoConstant.MULTI_RESULT_LONLAT_SEPARATOR;
				} else {
					tmpResult = tmpResult + RepoConstant.MULTI_RESULT_SEPARATOR;
				}
			}
			Pattern paramPattern = paramPatternArray[i];
			if (paramPattern != null) {
				Matcher matcher = paramPattern.matcher(url);
				if (matcher.find() && matcher.groupCount() > 0) {
					String str_tmp = decode(matcher.group(1), bean.getUncodes().get(i));
					if (90 == bean.getTheme_id())
						str_tmp = removeDirtyData(str_tmp, bean.getAction_id());
					tmpResult = tmpResult + str_tmp;
				}
			}
		}
//		return tmpResult.isEmpty() ? tmpResult : AbstractCommonUtils.convertChar(tmpResult, ConfigConstant.FILTER_CHARS);
		// tmpResult.replaceAll("[" + '\001' + "\\t\\n\\r]", "");
		return tmpResult.isEmpty() ? tmpResult : tmpResult.replaceAll("[" + '\001' + "\\t\\n\\r]", "");
	}
}
