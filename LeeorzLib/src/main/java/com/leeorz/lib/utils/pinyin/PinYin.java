package com.leeorz.lib.utils.pinyin;

import com.leeorz.lib.utils.pinyin.HanziToPinyin.Token;

import java.util.ArrayList;


public class PinYin {
	//汉字返回拼音，字母原样返回，都转换为小写
	public static String getPinYin(String input) {
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (Token token : tokens) {
				if (Token.PINYIN == token.type) {
					sb.append(token.target);
				} else {
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}
	
	/**
     * 汉字转换为汉语拼音首字母，英文字符不变
     */
    public static String getFirstChar(String input) {
    	ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (Token token : tokens) {
				if (Token.PINYIN == token.type) {
					sb.append(token.target.charAt(0));
				} else {
					sb.append(token.source);
				}
			}
		}
//		DLog.d("firstChar", "input:" + input + ",FirstChar:" + sb.toString().toLowerCase());
		return sb.toString().toLowerCase();
    }     

}
