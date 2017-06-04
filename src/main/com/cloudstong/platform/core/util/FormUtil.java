package com.cloudstong.platform.core.util;

public class FormUtil {
	public static String getInputTypeByModel(String model){
		String inputType="";
		if(model.equalsIgnoreCase("sys_textbox")){
			inputType="0";
		}else if(model.equalsIgnoreCase("sys_combox")){
			inputType="1";
		}else if(model.equalsIgnoreCase("sys_textarea")){
			inputType="2";
		}else if(model.equalsIgnoreCase("sys_radiomgt")){
			inputType="3";
		}else if(model.equalsIgnoreCase("sys_checkboxmgt")){
			inputType="4";
		}else if(model.equalsIgnoreCase("sys_uploadfilebox")){
			inputType="5";
		}else if(model.equalsIgnoreCase("sys_riqizujian")){
			inputType="6";
		}else if(model.equalsIgnoreCase("sys_tree")){
			inputType="7";
		}else if(model.equalsIgnoreCase("sys_searchcombox")){
			inputType="8";
		}else if(model.equalsIgnoreCase("sys_texteditor")){
			inputType="10";
		}else if(model.equalsIgnoreCase("sys_passwordbox")){
			inputType="11";
		}else if(model.equalsIgnoreCase("sys_codecasecade")){
			inputType="13";
		}else if(model.equalsIgnoreCase("sys_uploadify")){
			inputType="14";
		}
		return inputType;
	}
}
