package libcontroller;

import libUtil.ScanUtil;
import libUtil.View;
import libservice.libUserService;

public class libController {

	// 메인메소드
	public static void main(String[] args) {

		// 클래스 호출 밋 시작 메소드 실행
		new libController().start();

	}

	public static String LoginMemUser;

	private void start() {
		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				// 메인 메뉴 (첫화면)
				view = home();
				// 멤버 로그인의 경우
			case View.MEMBERLOGIN:
				view = libUserService.memlogin();
			}
		}
	}

	// 첫 메인화면 출력 및 수행 메소드
	private int home() {
		System.out.println("----------------------------");
		System.out.println("1.회원로그인\t 2.관리자로그인\t 0.프로그램종료");
		System.out.println("----------------------------");
		System.out.println("로그인 종류를 선택해주세요>>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.MEMBERLOGIN;
		default:
			break;
		}

		return 0;
	}

}
