package com.model2.mvc.common.util;

public class CommonUtil {
	
	///Field
	
	///Constructor
	
	///Method
	
	/* public static되어서 외부에서 클래스생성 없이 메서드사용 가능 :: ex) CommonUtil.null2str(org,converted);
	 * 각각 기능이 모듈화되어 이곳에 담아둔 것 같음 */
	
	/* org가 null 이거나 길이가 0이면(null String)이면  */
	public static String null2str(String org, String converted) {
		if (org == null || org.trim().length() == 0)
			return converted;
		else
			return org.trim();
	}
	
	public static String null2str(String org) {
		return CommonUtil.null2str(org, "");
	}
	
}
