package quickCine.selector;

import java.util.ArrayList;
import java.util.List;

/**업무 목록을 메서드화*/
public class Selectors{
	/**업무 목록을 enum화*/
	enum Selector {
		영화_예매, 예약_조회, 예약_취소, 종료,
	}
	
	/** selecter를 List<String>의 크기를 반환한다.*/
	public static int size() { return Selector.values().length; }
	
	/** selecter를 List<String>으로 반환한다.*/
	public static List<String> get(){
		List<String> result = new ArrayList<String>();
		for(Selector i : Selector.values()) {
			result.add(String.valueOf(i));
		}
		return result;
	}
	
	/** selector Enum을 _을 공백(스페이스바)로 바꿔서 넘긴다. */
	public static String get(int num) {
		String result = "";
		//num에 맞는  Selector enum을 가져와 _ 을 기준으로 나눈다. 
		String[] split = String.valueOf(Selector.values()[num]).split("_");
		//나눈 글자를 " "을 넣으며 순서대로 입력한다.
		for(String i : split) { result += i+" "; }
		return result;
	}
	
}
