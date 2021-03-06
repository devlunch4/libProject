package libservice;

import java.util.Map;

import libdao.libBoardDao;
import libdao.libUserDao;
import libUtil.ScanUtil;
import libUtil.View;
import libcontroller.libController;

public class libUserService {
	// 각 외부 인스턴스 호출
	private libUserDao libuserDao = libUserDao.getInstance();
	private libBoardDao libboardDao = libBoardDao.getInstance();
	private libBoardService libboardService = libBoardService.getInstance();

	private libUserService() {
	}

	// 인스턴스 생성
	private static libUserService instance;

	public static libUserService getInstance() {
		if (instance == null) {
			instance = new libUserService();
		}
		return instance;
	}

	// 회원 로그인 호출시 실행 메소드
	public int userlogin() {
		// 회원 로그인 첫 화면
		System.out.println("");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("■                회원로그인                                 ■");
		System.out.println("■----------------------------------------■");
		System.out.println("■          회원번호 10자리를 입력해주세요               ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");

		// 회원 번호 입력
		String userno = ScanUtil.nextLine();
		// 회원은 비밀번호 없음
		String password = null;

		// 회원 테이블의 값 유무 확인을 위한 변수 설정 및 값 지정
		Map<String, Object> user = libuserDao.selectUser(userno, password);

		// 회원 번호 값이 없는 경우 안내창 및 값이 있는경우 로그인 성공 및 유저정보 저장
		if (user == null) {
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.err.println("      \t   🙅‍♂️  존재하지 않는 회원번호입니다   🙅‍♂️");
			System.out.println("\t 회원로그인 실패로 첫화면으로 이동합니다.");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("");
			System.out.println("");
			// 아래 return View.USERMENU; 실행되도록함
		} else {
			System.out.println("로그인성공😀");

			// 유저정보 컨트롤러의 변수에 저장
			libController.Loginuserno = user;

			// 회원로그인 완료가 된다면 아래 수행
			// System.out.println("회원로그인 아이디 확인" + libController.Loginuserno);

			// 회원로그인 완료후 항목 회원 메인 메뉴 호출
			return View.USERMENU;
		}

		return View.HOME;
	}

	// 회원로그인후 회원전용 메인 메뉴 진입
	public int userMenu() {
		System.out.println("");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[회원전용]         메인메뉴                                     Ⅰ      ");
		System.out.println("------------------------------------------");
		System.out.println("■ 1.도서검색                2.회원정보수정 및 도서대여연장 ■");
		System.out.println("■ 3.공지글 조회            0.로그아웃                              ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");

		int inputx;
		try {
			String input = ScanUtil.nextLine();
			inputx = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.err.println("      \t    🙅‍♂️  올바르지 않는 입력값입니다   🙅‍♂️");
			return View.HOME;
		} catch (Exception e) {
			System.err.println("      \t    🙅‍♂️  올바르지 않는 입력값입니다   🙅‍♂️");
			return View.HOME;
		}

		switch (inputx) {
		case 1: // 1.도서검색
			// 도서테이블 활용하여 도서검색 시작
			userBookSearch();
			return View.USERMENU;

		// break;

		case 2: // 2.회원정보수정
			System.out.println("2번 회원정보수정및대여연장을 선택했습니다");
			userEdit();
			// return View.USEREDIT;
			break;

		case 3: // 3.공지글조회
			System.out.println("3번 공지글조회을 선택했습니다");
			userReadBoard();
			// return View.USERSELECTA;
			break;

		case 0: // 0.로그아웃
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("■ 로그인화면으로 돌아갑니다                                         ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			return View.HOME;
		default:
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.err.println("          🙅‍♂️  잘못된 입력, 회원 메인메뉴를 확인해주세요   🙅‍♂️          ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			break;
		}
		return View.USERMENU;
	}

	// 회원 로그인후 3번 입력 후 이동된 공지글 조회
	public int userReadBoard() {
		// 공지글 출력
		// 공지번호,제목,작성자,작성일 조회
		// 제목의 경우 글자수10 제한, 내용의경우 글자수10제한 표현

		// 보드서비스로 넘기기 = 게시공지글 출력 최근 3건만 출력
		libboardService.readNoticeboard();

		// 조회된 게시글 중 선택된 게시글 번호의 내용 보기
		libboardService.readNBCon();

		return View.USERREADBOARD;
	}

	// 회원 로그인후 2번 입력 후 이동된 회원정보수정및도서연장 메인 메소드1

	public int userEdit() {
		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[회원전용]           회원정보                           Ⅰ - Ⅱ      ");
		System.out.println("------------------------------------------");
		System.out.println("■ 1.내 정보수정       2.대여일 연장            0.이전메뉴   ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");

		int edinput = ScanUtil.nextInt();
		switch (edinput) {
		case 1:// 정보수정
				// 내정보 출력 후
			libboardDao.userInfo();

			// 수정할 사항 선택 및 수정 이전화면 가기
			libuserDao.userModify();

			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("[회원전용]           수정완료                          Ⅰ - Ⅴ      ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");

			// 출력할 컬럼값 선택후 변경

			return userEdit();

		case 2: // 도서연장
			// 내가 대출중인 도서 출력
			libboardDao.userrent();

			// 위 메소드 내부에 아래 메소드 추가
			// 도서 반납기간 연장 메소드는 libboardDao.userrent() >>> libuserDao 클래스에 있음
			// libuserDao.userbookext();

			break;
		case 0:
			System.out.println("이전메뉴로 이동합니다");

			break;

		default:
			System.out.println("잘못된 입력입니다.");
			break;
		}

		return View.USEREDIT;

	}

	// 회원 로그인후 메인메뉴 1번 도서검색 메뉴
	public int userBookSearch() {
		// 도서검색 화면 안내 및 선택창
		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[회원전용]         도서검색                               Ⅰ - Ⅱ      ");
		System.out.println("------------------------------------------");
		System.out.println("■ 1.도서조회      2.도서신청                       0.이전화면 ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");
		int input2;
		try {
			String input = ScanUtil.nextLine();
			input2 = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.err.println("      \t    🙅‍♂️  올바르지 않는 입력값입니다   🙅‍♂️");
			return View.USERBOOKSEARCH;
		} catch (Exception e) {
			System.err.println("      \t    🙅‍♂️  올바르지 않는 입력값입니다   🙅‍♂️");
			return View.USERBOOKSEARCH;
		}

		switch (input2) {
		case 1:
			// 도서 조회 메소드 호출
			libboardDao.bookboardsearch();
			return View.USERBOOKSEARCH;
		case 2:
			// 도서신청 게시판에 글입력
			// System.out.println("===도서신청메뉴");
			// 신청된 도서리스트 보여주기
			libboardService.applyBoardShow();
			// 도서 신청글 등록 완료.
			break;
		case 0:
			// 이전화면 이동
			return View.USERMENU;
		default:
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.err.println("            🙅‍♂️  잘못된 입력, 도서검색 메뉴 재출력합니다   🙅‍♂️          ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			break;
		}
		// 상위메뉴 이동 1도서검색
		return View.USERBOOKSEARCH;
	}

}