package quickCine.selector;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import quickCine.utile.Utiles;


/**QuikCine의 업무를 선택한다.*/
public class Selector {
	List<String> menu = Selectors.get();
	
	/**selecters(업무의 값)의 크기만큼 선택, 반환한다.*/
	public int input() {
		int result = 0;
		String prt = "";
		Scanner scanner = new Scanner(System.in);
		
		//(!) 업무 목록 보여주기 
		Utiles.liner("원하시는 업무의 번호를 입력해주세요.");
		for(int i=0; i < Selectors.size(); i++) {
			prt = String.format("%d. %s", i, Selectors.get(i));
			System.out.println(prt);
		}
		//(!!) 업무 목록 보여주기
		
		//(!) scanner 를 통해 Selectors( 업무 번호 ) 인식
		while(true){
			// 정수 인지 확인
			while(!scanner.hasNextInt()) {
				System.out.println("형식에 맞지 않습니다. 다시 입력해주세요.");
				scanner.next();
			}
			result = scanner.nextInt();
			// 입력된 값이 Selectors( 업무 번호 )에 맞는가
			// Selectors( 업무 번호 )에 맞지 않은 경우
			if(!(result >= 0 && result < menu.size())){
				System.out.println("형식에 맞지 않습니다. 다시 입력해주세요.");
				continue;
			// Selectors( 업무 번호 )에 맞는 경우
			} else { break; }
		}
		//(!!) scanner 를 통해 업무 번호 인식
		
		return result;
	}
}