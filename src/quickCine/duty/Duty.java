package quickCine.duty;
import java.util.List;
import java.util.Scanner;
import quickCine.Sql.SqlManager;
import quickCine.utile.Utiles;

public class Duty {
	private String duty = "not defined";
	protected SqlManager manager = SqlManager.get();
	
	/**초기화, 업무를 표기하기 위해 duty(업무이름)를 초기화시에 입력*/
	public Duty(String duty){ this.duty = duty; }
	
	/**업무를 반복하게 만들어준다.*/
	public void update() {
		while(progress());
	}
	
	/**sql 오류시 처음부터 시작하게 하며, 메세지 출력 반복해서 사용하기에 메서드화*/
	protected boolean SqlError() {
		System.out.println("SQL오류로 다시 시작합니다. 죄솝합니다.");
		return true;
	}
	
	/**실행 부분 각 자식에 오버라이드 하여야함 또한 오버라이드한 progress를 종료할때 리턴값으로 사용해야함*/
	protected boolean progress() {
		Utiles.liner(duty + "업무를 종료합니다.");
		return false;
	}
	
	/**List<Integer>의 Integer값 이나 -1 값을 선택하게 해주는 메서드*/
	protected int selector(List<Integer> table) { return selector(table,""); }
	/**List<Integer>의 Integer값 이나 -1 값을 선택하게 해주는 메서드*/
	protected int selector(List<Integer> table,String post) {
		int input = 0;
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Utiles.liner(post+"번호를 입력해주세요. 종료를 원하시면 -1를 입력해주세요.");
			// 정수만 입력 받는다.
			while(!scanner.hasNextInt()) {
				System.out.println("번호만을 입력해 주세요.");
				scanner.next();
				continue;
			}
			input =scanner.nextInt();
			if(input == -1 ) { return -1; }
			// 음수가 아니며 table의 사이즈보다 작을 경우
			if(input < 0 || input > table.size()) { 
				System.out.println("유효한 번호가 아닙니다.");
				continue;
			}
			else break;
		}
		return table.get(input); 
	}
	
	/**0을 제외한 정수 또는 -1 값을 선택하게 해주는 메서드*/
	protected int selector(String post) {
		int input = 0;
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Utiles.liner(post+" 입력해주세요. 종료를 원하시면 -1를 입력해주세요.");
			//정수만 입력 받는다
			if(!scanner.hasNextInt()) {
				System.out.println("번호만을 입력해 주세요.");
				scanner.next();
				continue;
			}
			input =scanner.nextInt();
			if(input == -1) { return -1; }
			// 음수 또는 0 일 경우
			if(input <= 0 ) { 
				System.out.println("0이나 음수는 넣을 수 업습니다.");
				continue;
			}
			else break;
		}
		return input;
	}
	
	/** Y / N / -1 를 선택하는 메서드*/
	protected String isAnser(String post) {
		String input = "";
		Scanner scanner = new Scanner(System.in);
		while(true) {
			Utiles.liner(post+"\n( y / n ) 종료를 원하시면 -1을 입력해주세요.");
			// toUpperCase로 영어를 대문자로 바꾼다.
			input = scanner.nextLine().toUpperCase();
			// Y N -1 밖에 선택하지 못하기에 3개중 하나가 아니면 while문을 재실행한다.
			if(!"Y".equals(input) || "N".equals(input) || "-1".equals(input)) {
				System.out.println("형식에 맞지 않습니다. 다시 입력해주세요.");
				continue;
			}
			break;
		}
		return input;
	}
	
	/**password의 형식이 맞는지 확인 하는 메서드*/
	protected String hasPassword() {
		String result = "";
		Scanner scanner = new Scanner(System.in);
		while (true) {
			Utiles.liner("비밀번호를 4자리 숫자만 입력해주세요. 종료를 원하시면 -1를 입력해주세요.");
			result = scanner.nextLine();
			if("-1".equals(result)) { break; }
			//공백, 숫자, 4자리 이하 인지 확인한다.
			if (Utiles.isSpaces(result) || !result.matches(".*\\d.*") || result.length() != 4) {
				System.out.println("비밀번호 형식을 맞춰주세요.");
				scanner.next();
				continue;
			}
			break;
		}
		return result;
	}
}
