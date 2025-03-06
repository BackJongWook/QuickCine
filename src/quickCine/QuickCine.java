package quickCine;
import quickCine.duty.*;
import quickCine.selector.*;
import quickCine.utile.Utiles;

public class QuickCine {
	public static void main(String[] args) {
		// 업무 선택 매서드 (input()) 을 사용하기위해 Selector 생성
		Selector selector = new Selector();
		// 업무를 실행 하기위한 클래스
		Duty duty = null;
		
		//(!) 업무 선택 및 실행 
		while(true) {
			// 업무 선택
			int input = selector.input();
			// 업무의 이름을 가져온다.
			String dutyName = Selectors.get(input);
			Utiles.liner(dutyName+"업무를 선택 하셧습니다.");
			// 선택된 업무의 클래스 선택
			switch(input) {
				// 예약
				case 0:{ duty = new Reserve(dutyName); } break;
				// 예약 조회
				case 1:{ duty = new Searcher(dutyName); } break;
				// 예약 취소
				case 2:{ duty = new Cancel(dutyName); } break; 
				// 프로그램 종료
				default:{ 
					Utiles.liner("종료를 선택하셨습니다. 업무를 종료합니다.");
					duty = null; 
				} break;
			}
			// 시스템 종료 선택시 null 이되어 업무를 실행하지 않고 멈춤
			if(duty == null) { break; }
			// 업무를 선택시 실행
			else duty.update();
		}
		//(!) 업무 선택 및 실행 
	}
}
