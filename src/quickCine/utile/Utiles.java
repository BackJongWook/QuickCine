package quickCine.utile;

public class Utiles {
	
	/**---라인을 그어준다.*/
	public static void liner() { liner(""); }
	
	/**---라인을 그어준다. 다음 줄의 텍스트도 입력해준다.*/
	public static void liner(String post) {
		String str = "------------------------";
		if(!"".equals(str)) { str+="\n"; }
		System.out.println(str+post);
	}
	
	/** 문자열에 공백 또는 빈값인지 확인한다.*/
	public static boolean isSpaces(String str) {
		if(str == null || str.contains(" ") || str.isEmpty()) { return true; }
		else return false;
	}
}
