package libservice;

import java.util.Map;

import libdao.libBoardDao;
import libdao.libUserDao;
import libUtil.ScanUtil;
import libUtil.View;
import libcontroller.libController;

public class libUserService {

	private libUserService() {
	}

	// 인스턴스 생성
	private static libUserService instance;
	private libUserDao libuserDao = libUserDao.getInstance();

	public static libUserService getInstance() {
		if (instance == null) {
			instance = new libUserService();
		}
		return instance;
	}

	// 각 인스턴스 호출
	private libBoardDao libboardDao = libBoardDao.getInstance();
	private libBoardService libboardService = libBoardService.getInstance();

	// 회원로그인 호출시 입력 및 회원 메뉴 진입을 위한 메소드
	public int userlogin() {
		System.out.println("=====회원로그인=====");
		System.out.println("회원번호 10자리를 입력해주세요(바코드 인식해주세요)>>");
		String userno = ScanUtil.nextLine();
		String password = null;

		// 회원테이블과 비교하여 테이블에 해당 정보 존재시 회원 로그인 완료
		Map<String, Object> user = libuserDao.selectUser(userno, password);

		if (user == null) {
			System.out.println("없는 회원번호입니다.재입력 바랍니다.");
			// 아래 return View.USERLOGIN; 실행되도록함
		} else {
			System.out.println("로그인성공");

			// 유저정보 컨트롤러의 변수에 저장
			libController.Loginuserno = user;

			// 회원로그인 완료가 된다면 아래 수행
			System.out.println("회원로그인 아이디 확인" + libController.Loginuserno);

			// 회원로그인 완료후 항목 선택 메뉴가 나오도록함 userA 메소드 호출
			return View.USERMENU;
		}
		System.out.println("회원로그인 실패로 메소드 재실행 리턴");
		return View.USERLOGIN;
	}

	// 회원로그인후 회원전용메뉴 진입
	public int userMenu() {
		System.out.println("===회원 메인 메뉴 입니다 view.USERMENU");
		System.out.println("1.도서검색 \t2.회원정보수정및대여연장 \t3.공지글조회 \t0.로그아웃");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1: // 1.도서검색 $ 완료
			System.out.println("1번 도서검색을 선택했습니다");
			// 도서테이블 활용하여 도서검색 시작
			userBookSearch();
			return View.USERBOOKSEARCH;
			// break;

		case 2: // 2.회원정보수정
			System.out.println("2번 회원정보수정및대여연장을 선택했습니다");
			userEdit();
			// return View.USEREDIT;
			break;

		case 3: // 3.회원정보수정
			System.out.println("3번 공지글조회을 선택했습니다");
			// return View.USERSELECTA;
			break;

		case 0: // 0.로그아웃
			System.out.println("0번 로그아웃을 선택했습니다.로그인화면으로 돌아갑니다.");
			return View.HOME;
		default:
			System.out.println("잘못된 입력, 회원 메인메뉴를 확인해주세요");
			break;
		}
		return View.USERMENU;
	}

	public int userEdit() {
		System.out.println("===1-2회원정보수정 메뉴");
		System.out.println("1.내정보수정 \t2.도서연장 \t0.이전메뉴");
		System.out.println("번호 선택 입력>");

		int edinput = ScanUtil.nextInt();
		switch (edinput) {
		case 1://정보수정
			//내정보 출력 후
			libboardDao.userInfo();
			
			//수정할 사항 선택 및 수정 이전화면 가기
			libuserDao.userModify();
			
			System.out.println("회원정보가 수정되었습니다.");
			
			//출력할 컬럼값 선택후 변경

			break;
		case 2:

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
		System.out.println("===1-1도서검색 메뉴");
		System.out.println("1.도서조회 \t2.도서신청 \t0.이전화면");
		System.out.println("번호 선택 입력>");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			System.out.println("===도서조회메뉴");
			libboardDao.bookboardsearch();
			return View.USERBOOKSEARCH;
		case 2:
			// 도서신청 게시판에 글입력
			System.out.println("===도서신청메뉴");
			// 신청된 도서리스트 보여주기
			libboardService.applyBoardShow();
			// 도서 신청글 등록 완료.
			break;
		case 0:
			// 이전화면 이동
			System.out.println("이전화면으로 이동합니다");
			return View.USERMENU;
		default:
			System.out.println("잘못된 입력, 도서검색 메뉴 재출력합니다.");
			break;
		}
		// 상위메뉴 이동 1도서검색
		return View.USERBOOKSEARCH;
	}
}
