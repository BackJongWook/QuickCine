package quickCine.duty;

public class Searcher extends Duty {

	public Searcher(String duty) { super(duty); }
	
	protected boolean progress() {
		//(!) 예약 번호 확인 
		int ticket = selector("예약 번호를 ");
		if(ticket == -1) { return super.progress(); }
		// 매니저를 통해 번호로 예약 정보를 확인한다.
		if(!manager.getTicket(ticket)) {
			System.out.println("예약 번호가 확인 되지 않습니다. "); 
			return true;
		}
		else { return super.progress(); }
		//(!!) 예약 번호 확인
	} 
	

}