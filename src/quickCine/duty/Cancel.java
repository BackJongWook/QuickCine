package quickCine.duty;

public class Cancel extends Duty {

	public Cancel(String duty) { super(duty); }
	
	protected boolean progress() {
		int ticket = 0;
		String password = "";
		String anser = "";
		
		//(!)예약번호 입력
		ticket = selector("예약 번호를 ");
		if(ticket == -1) { return super.progress(); }
		if(!manager.getTicket(ticket)) {
			System.out.println("예약 번호가 확인 되지 않습니다. "); 
			return true;
		}
		//(!!)예약번호 입력
		
		//(!)예약 번호의 비밀번호를 확인
		password = hasPassword();
		if("-1".equals(password)) { return super.progress(); } 
		if(!manager.getTicket(ticket,password)) {
			System.out.println("비밀번호가 틀렸습니다. 다시 예약번호부터 입력해주요."); 
			return true;
		}
		//(!!)예약 번호의 비밀번호를 확인
		
		//(!)예약 취소 확인
		anser = isAnser("예약을 취소 하시겠습니까? ");
		System.out.println(anser);
		if(!"Y".equals(anser)) { return super.progress(); }
		//(!)예약 취소 확인
		
		//(!)예약 취소 실행
		if(!manager.deleteTicket(ticket, password)) { return SqlError(); }
		System.out.println("예약 취소에 성공하였습니다."); 
		return super.progress();
		//(!!)예약 취소 실행
	} 
}
