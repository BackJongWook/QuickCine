package quickCine.duty;

public class Reserve extends Duty {

	public Reserve(String duty) { super(duty); }

	protected boolean progress() {
		int movie = 0;
		int schedule = 0;
		int seat = 0;
		int ticket = 0;
		
		//(!) 예약 데이터 선택
		while(true) {
			//영화 선택
			movie = selector(manager.getMovie(),"영화의 ");
			if(movie == -1) { return super.progress(); }
			
			//상영관, 상영 시간 선택
			schedule = selector(manager.getTimes(movie),"시간표의 ");
			if(schedule == -1) { return super.progress(); }
			
			//좌석 선택
			seat = manager.isSeat(schedule,selector("관람하실 인원을 ")); 
			if(seat == -1) { return super.progress(); }
			if(seat != 0) { break; }
			else { 
				System.out.println("좌석이 없어 다시 영화 목록부터 실행합니다."); 
				continue;
			}
		}
		
		//(!) 예약 진행 및 예약 정보 보여주기
		//hasPassword로 password의 형식이 맞는지 확인
		ticket = manager.setTicket(schedule, seat, hasPassword()); 
		//예약 실패
		if(ticket == 0) { return SqlError(); }
		//예약 성공 티켓의 정보를 보내준다.
		manager.getTicket(ticket);
		//(!!) 예약 진행 및 예약 정보 보여주기
		return super.progress();
	}	
}
