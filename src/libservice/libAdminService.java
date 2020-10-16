package libservice;

import java.util.Map;

import libUtil.ScanUtil;
import libUtil.View;
import libcontroller.libController;
import libdao.libAdminDao;
import libdao.libBoardDao;
import libdao.libUserDao;

public class libAdminService {
	// 각 외부 인스턴스 호출
	private libUserDao libuserDao = libUserDao.getInstance();
	private libUserService libuserService = libUserService.getInstance();
	private libBoardDao libboardDao = libBoardDao.getInstance();
	private libBoardService libboardService = libBoardService.getInstance();
	private libAdminDao libadminDao = libAdminDao.getInstance();

	private libAdminService() {
	}

	// 인스턴스 생성
	private static libAdminService instance;

	public static libAdminService getInstance() {
		if (instance == null) {
			instance = new libAdminService();
		}
		return instance;
	}

	// 관리자 로그인 메소드 정보 일치 확인
	public int adminlogin() {
		System.out.println("===관리자 로그인===");
		System.out.println("관리자 계정 입력(*대소문자구별)>> ");
		String adminid = ScanUtil.nextLine();
		System.out.println("관리자 비밀번호 입력>>");
		String adminpw = ScanUtil.nextLine();

		Map<String, Object> admin = libadminDao.selectAdmin(adminid, adminpw);

		if (admin == null) {
			System.out.println("없는 관리자계정입니다. 재입력바랍니다.");
		} else {
			System.out.println("관리자 로그인 성공");
			// 관리자 정보 저장.
			libController.Loginadminno = admin;
			return View.ADMINMENU;
		}
		return View.HOME;
	}

	// 관리자 로그인 후 메뉴화면
	public int adminMenu() {

		System.out.println("===관리자 메인 화면");
		System.out.println("1.도서관리\t2.공지글관리\t3.회원관리\t4.도서대여\t0.로그아웃");
		System.out.println("해당 항목 선택 입력>>>");
		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			// 도서관리
			// 도서관리 메소드로 진입
			bookCtrl();
			break;
		case 2:
			// 공지글관리
			// 공지글관리 메소드 진입
			break;
		case 3:
			// 회원관리
			// 회원관리 메소드 진입
			break;
		case 4:
			// 도서대여
			// 도서대여 메소드 진입
			break;
		case 0:
			// 로그아웃
			System.out.println("0번 로그아웃을 선택했습니다.로그인화면으로 돌아갑니다.");
			return View.HOME;

		default:
			System.out.println("잘못된 입력, 관리자 메인메뉴를 확인해주세요");
			break;
		}
		return View.ADMINMENU;
	}

	// 관리자 도서관리 진입
	private int bookCtrl() {
		System.out.println("===도서관리");
		System.out.println("1.등록\t2.수정\t3.삭제\t4대출확인\t5.도서조회\t0.이전화면이동");
		System.out.println("해당 항목 선택 입력>>>");
		int inputctrl = ScanUtil.nextInt();

		switch (inputctrl) {
		case 1:// 도서 등록
			System.out.println("===도서등록을 시작합니다.안내 순서에 따라 값을 입력해주세요");
			System.out.println("===등록을 시작합니다.");
			// 등록 인풋을 통한 입력 및 입력 완료 확인.
			// 도서 추가 메소드 및 insert.
			libboardDao.addbook();

			System.out.println("1.등록\t2.수정\t3.삭제\t4대출확인\t5.도서조회\t0.이전화면이동");
			System.out.println("해당 항목 선택 입력>>>");
			break;
		case 2:// 도서 수정
			System.out.println("===도서수정을 시작합니다.");

			
			//번호입력에 따른 수정 메소드
			libboardDao.modbook();
			
			break;
		case 3:// 도서 삭제
			break;
		case 4:// 대출 확인
			break;
		case 5://
			break;
		case 0:
			break;

		default:
			break;
		}

		return View.ADMINMENU;
	}

}// 관리자 서비스 클래스 말단
