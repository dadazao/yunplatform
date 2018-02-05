package com.cloudstong.platform.core.util;

import java.util.ArrayList;
import java.util.List;

public class DaoUtil {
	private final static int MAX_IN_VALUE_COUNT=200;
	private DaoUtil(){
		
	}
	
	public static List<List> splitInValues(List inValues){
		List<List> inValuesResult=new ArrayList<List>();
		if (inValues==null||inValues.size()<=MAX_IN_VALUE_COUNT){
			inValuesResult.add(inValues);
			return inValuesResult;
		}
		List currentList=new ArrayList();
		for (Object inValue:inValues){
			currentList.add(inValue);
			if (currentList.size()==MAX_IN_VALUE_COUNT){
				inValuesResult.add(currentList);
				currentList=new ArrayList();
			}
		}
		if (currentList.size()>0)
			inValuesResult.add(currentList);
		return inValuesResult;
	}
	private final static String AND_OP=" and ";
	private final static String OR_OP=" or ";
	/**
	 * @param conditions
	 * @return
	 */
	public static String andCondition(String... conditions){
		return joinCondition(AND_OP,true,conditions);
	}
	
	private static String joinCondition(String joinOp,boolean needBracket,String... conditions){
		if (conditions.length==0)
			return "";
		StringBuffer buf=new StringBuffer();
		if (needBracket)
			buf.append('(');
		buf.append(conditions[0]);
		if (needBracket)
			buf.append(')');
		for(int i=1;i<conditions.length;i++){
			buf.append(joinOp);
			if (needBracket)
				buf.append('(');
			buf.append(conditions[i]);
			if (needBracket)
				buf.append(')');
		}
		return buf.toString();
	}
	
	/**
	 * @param attributeName
	 * @return
	 */
	public static String isNull(String attributeName){
		return "("+attributeName+" is null)";
	}
	
	/**
	 * @param attributeName
	 * @return
	 */
	public static String isNotNull(String attributeName){
		return "("+attributeName+" is not null)";
	}
	
	public static String getCountQL(String ql){
		int start=getFirstIndex(ql,"from");
		int end=getLastIndex(ql,"order by");
		if (end<0)
			return "select count(*) "+ql.substring(start);
		else
			return "select count(*) "+ql.substring(start,end);
	}
	
	private static int getFirstIndex(String ql, String keyword){
		int index=ql.indexOf(keyword);
		int index2=ql.indexOf(keyword.toUpperCase());
		if (index<0||(index2>=0&&index>index2))
			index=index2;
		return index;
	}
	
	private static int getLastIndex(String ql,String keyword){
		int index=ql.lastIndexOf(keyword);
		int index2=ql.lastIndexOf(keyword.toUpperCase());
		if (index<0||(index2>=0&&index<index2)){
			index=index2;
		}
		return index;
	}

	

	private final static String LACALE_MARKER="<locale>";
	/**
	 */
	public static String localizeHql(String hql){
		String currentLocale=getLocale();
		return hql.replaceAll(LACALE_MARKER, currentLocale);
	}
	
	/**
	 */
	public static String globalizeAttributeName(String attrName){
		return attrName+LACALE_MARKER;
	}
	
	private static String getLocale(){
		return "Zh";
	}
	
}
