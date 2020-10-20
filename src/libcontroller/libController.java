package libcontroller;

import java.util.Map;
import java.util.Scanner;

import libUtil.ScanUtil;
import libUtil.View;
import libdao.libAdminDao;
import libdao.libBoardDao;
import libdao.libUserDao;
import libservice.libAdminService;
import libservice.libBoardService;
import libservice.libUserService;

public class libController {
	public static Map<String, Object> Loginuserno;
	public static Map<String, Object> Loginadminno;

	// 메인메소드
	public static void main(String[] args) {

		// 클래스 호출 밋 시작 메소드 실행
		new libController().start();

	}

	private libUserService libuserService = libUserService.getInstance();
	private libAdminService libadminService = libAdminService.getInstance();
	private libBoardService libboardService = libBoardService.getInstance();
	private libUserDao libuserDao = libUserDao.getInstance();
	private libAdminDao libadminDao = libAdminDao.getInstance();
	private libBoardDao libboardDao = libBoardDao.getInstance();

	private void start() {
		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME:
				// 메인 메뉴 (첫화면)
				view = home();
				break;

			case View.USERLOGIN:
				// 멤버 로그인의 경우
				view = libuserService.userlogin();

				// 확인테스트출력
				// System.out.println("컨트롤러메소드상태 입력된 회원 아이디값: " + Loginuserno);
				break;

			// 회월 로그인후 회원의 첫 메인메뉴
			case View.USERMENU:
				view = libuserService.userMenu();
				break;

			// 회원 첫메뉴의 1번 항목 도서검색 호출 userAA() 메소드 호출
			case View.USERBOOKSEARCH:
				view = libuserService.userBookSearch();
				break;

			// 회원이 공지글을 읽기위한 메소드
			case View.USERREADBOARD:
				view = libuserService.userReadBoard();
				break;

			// case View.APPLYBOARDSHOW:
			// view = userBookSearch
			// break;

			// case View.USEREDIT:
			// view = libuserService.userEdit();
			// break;

			case View.USERBOOKEXT:
				view = libboardDao.userrent();
				break;

			// ////////////////////////////////
			// 관리자 계정 관련 시작
			// ////////////////////////////////

			// 관리자 로그인
			case View.ADMINLOGIN:
				// 관리자 로그인 확인 메소드
				view = libadminService.adminlogin();
				break;

			case View.ADMINMENU:
				// 관리자 메뉴 진입
				view = libadminService.adminMenu();
				break;

			}
		}
	}

	// 첫 메인화면 출력 및 수행 메소드
	private int home() {
		// 첫 메인 로그인 타입 선택 화면
		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("■  1.회원로그인 \t2.관리자로그인   0.프로그램종료   ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■"); 
		System.out.println("■          로그인 종류를 선택해주세요                      ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");

		// 잘못된 입력시 while문의 에러메시지 출력
		Scanner sc = new Scanner(System.in);
		while (!sc.hasNextInt()) {
			// 입력값 초기화
			sc.next(); // 잘못된 입력 초기화
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.err.println("         🙅‍♂️  올바르지 않는 입력값입니다    🙅‍♂️");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■"); 
			System.out.println("■  1.회원로그인 \t2.관리자로그인 \t0.프로그램종료   ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");

			System.out.println("");
		}
		// 타입 결정 입력 숫자
		int input = sc.nextInt();

		switch (input) {
		case 1:
			// 회원로그인선택
			return View.USERLOGIN;
		case 2:
			// 관리자로그인선택
			return View.ADMINLOGIN;
		case 0:
			// 프로그램종료 선택
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
			break;
		default:
			// 오류 입력시
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.err.println("      \t    🙅‍♂️  올바르지 않는 입력값입니다   🙅‍♂️");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■"); 
			System.out.println("");
			System.out.println("");
			return View.HOME;
		}
		return View.HOME;
	}

}