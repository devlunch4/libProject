package libcontroller;

import java.util.Map;

import libUtil.ScanUtil;
import libUtil.View;
import libservice.libBoardService;
import libservice.libUserService;

public class libController {

	// 메인메소드
	public static void main(String[] args) {

		// 클래스 호출 밋 시작 메소드 실행
		new libController().start();

	}

	public static Map<String, Object> LoginMemUser;
	private libUserService libuserService = libUserService.getInstance();
	private libBoardService libboardService = libBoardService.getInstance();

	private void start() {
		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				// 메인 메뉴 (첫화면)
				view = home();
				// 멤버 로그인의 경우
			case View.USERLOGIN:
				view = libuserService.userlogin();
				System.out.println("확인2" + LoginMemUser);
				break;
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
			return View.USERLOGIN;
		case 2:
			return View.ADMINLOGIN;
		case 0:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
			break;
		default:
			System.out.println("잘못된 입력, 아래 메뉴 확인후 올바르게 입력해주세요");
		}

		return View.HOME;
	}

}
