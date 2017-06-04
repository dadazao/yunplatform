package com.cloudstong.platform.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Allan
 * Created on 2012-11-20
 * 
 * Revision History:
 * Date   		Reviser		Description
 * ----   		-------   	----------------------------------------------------
 * 
 * Description:生成js，cs文件统计的property文件
 */
public class BuildJsCssPropertyUtil {
	
	public static StringBuffer comb = new StringBuffer();
	
	/**
	 * Description:合并dwz的min文件为一个文件dwz.min.js
	 * 
	 * 
	 * @param type
	 * @param fileName
	 * @param searchPath
	 * @throws IOException
	 */
	private static void mergeDWZFile(String type, String fileName, String searchPath) throws IOException {
		FileWriter fw = new FileWriter(fileName); 
		BufferedWriter bw=new BufferedWriter(fw);   
		File  file= new File(searchPath);
		File[] subfs = file.listFiles();
		
		String result = "";
		BufferedReader br = new BufferedReader(new FileReader(searchPath+"/dwz.core.min.js"));
		String s = "";
		while((s=br.readLine())!=null)  {
			result += s + "\r\n";
		}
		br.close();
		
		for (File f : subfs) {
			if(f.isFile() && f.getName().contains(type) && !f.getName().equals("dwz.core.min.js")) {
				BufferedReader br2 = new BufferedReader(new FileReader(f));
				String s2 = "";
				while((s2=br2.readLine())!=null)  {
					result += s2 + "\r\n";
				}
				br.close();
			}
		}
		fw.write(result);   
        fw.write("\r\n"); 
        fw.flush();
        fw.close();
	}

	/**
	 * Description:创建property文件
	 * 
	 * 
	 * @param type
	 * @param fileName
	 * @param searchPath
	 * @throws IOException
	 */
	public static void createFile(String type, String fileName, String searchPath) throws IOException {
		FileWriter fw = new FileWriter(fileName); 
		BufferedWriter bw=new BufferedWriter(fw);   
		buildFilePathStr(type, searchPath);
		fw.write(type + ".files=" + comb.toString().replaceAll("\\\\","/"));   
        fw.write("\r\n"); 
        fw.flush();
        fw.close();
	}

	/**
	 * Description:将所有js或css文件的名称合并，返回字符串
	 * 
	 * @param type
	 * @param searchPath
	 */
	private static void buildFilePathStr(String type, String searchPath) {
		File  file= new File(searchPath);
		if(!"help".equals(file.getName())) {
			File[] subfs = file.listFiles();
			for (File f : subfs) {
				Pattern pattern = Pattern.compile("\\." + type + "$");
	            Matcher matcher = pattern.matcher(f.getAbsolutePath());     
				File of = new File(matcher.replaceAll("\\.min\\." + type));
				if((of.isFile() &&  of.canWrite()) || !of.exists()) {
					if(f.isFile() && !f.getName().contains(".min.") && (type.equals(f.getName().substring(f.getName().length()-type.length())))) {
						comb.append(f.getAbsolutePath() + ",");
					}
				} 
				if(f.isDirectory()) {
					buildFilePathStr(type, f.getAbsolutePath());
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		createFile(args[0],args[1],args[2]);
		mergeDWZFile(args[3],args[4],args[5]);
		//mergeDWZFile("min.js","E:/Workstation/MyEclipse/platform/beaver/WebRoot/js/dwz/dwz.min.js","E:/Workstation/MyEclipse/platform/beaver/WebRoot/js/dwz");
	}

}
