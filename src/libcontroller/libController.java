package libcontroller;

import java.util.Map;

import libUtil.ScanUtil;
import libUtil.View;
import libservice.libBoardService;
import libservice.libUserService;

public class libController {
	public static Map<String, Object> Loginuserno;
	// 메인메소드
	public static void main(String[] args) {

		// 클래스 호출 밋 시작 메소드 실행
		new libController().start();

	}

		private libUserService libuserService = libUserService.getInstance();
	private libBoardService libboardService = libBoardService.getInstance();

	private void start() {
		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				// 메인 메뉴 (첫화면)
				view = home();
				break;
			// 멤버 로그인의 경우
			case View.USERLOGIN:
				view = libuserService.userlogin();
				System.out.println("컨트롤러메소드상태 입력된 회원 아이디값: " + Loginuserno);
				System.out.println("컨트롤러 View.USERLOGIN: 브레이크");
				break;

			// 회월 로그인후 회원의 첫 메인메뉴
			case View.USERMENU:
				view = libuserService.userMenu();
				break;

			// 회원 첫메뉴의 1번 항목 도서검색 호출 userAA() 메소드 호출
			case View.USERBOOKSEARCH:
				view = libuserService.userBookSearch();
				break;
				
//			case View.APPLYBOARDSHOW:
//				view = userBookSearch
				
//			case View.USEREDIT:
//				view = libuserService.userEdit();
			}
		}
	}

	// 첫 메인화면 출력 및 수행 메소드
	private int home() {
		System.out.println("----------------------------");
		System.out.println("1.회원로그인 \t2.관리자로그인 \t0.프로그램종료");
				System.out.println("----------------------------");
		System.out.println("로그인 종류를 선택해주세요>>");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			//회원로그인선택시
			return View.USERLOGIN;
		case 2:
			//관리자로그인선택시
			return View.ADMINLOGIN;
		case 0:
			//프로그램종료 선택시
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
			break;
		default:
			//오류 입력시
			System.out.println("잘못된 입력, 항목을 확인해주세요 #오류입력시 상위 잘 실행");
			break;
		}
		return View.HOME;
	}

}
