package libservice;

import java.util.HashMap;
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
		System.out.println("1.도서관리 \t 2.공지글관리 \t 3.회원관리 \t 4.도서대여 \t 0.로그아웃");
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
			noticectrl();
			break;
		case 3:
			// 회원관리
			userctrl();
			// 회원관리 메소드 진입
			break;
		case 4:
			// 도서대여
			// 도서대여 메소드 진입
			// 아이디체크
			libboardDao.rentbookchkid();
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

	// 관리자의 회원관리메뉴 진입
	private int userctrl() {
		System.out.println("===회원관리");
		System.out.println("1.등록\t2.수정\t3.삭제\t4.회원검색\t0.이전메뉴");
		int uctrlinput = ScanUtil.nextInt();
		switch (uctrlinput) {
		case 1: // 회원 등록
			adduserinfo();
			break;
		case 2: // 회원 수정
			moduserno();
			break;
		case 3: // 회원 삭제
			deluserno();
			break;
		case 4: // 회원 검색
			serchuser();
			break;
		case 0: // 이전 메뉴
			break;

		default:
			System.out.println("잘못된 입력입니다.");
			break;

		}
		return View.ADMINMENU;
	}

	// 회원정보 조회 $$
	private int serchuser() {
		System.out.println("===회원검색");
		System.out.println("1.이름 검색 \t 2.전화번호 검색 \t 3.전체조회 0.이전화면");
		int lookinput = ScanUtil.nextInt();
		switch (lookinput) {
		case 1: // 이름으로 조회
			// 이름이 있는지 없는지 유무확인
			System.out.println("검색 이름을 입력해주세요>>>");
			String usernminput = ScanUtil.nextLine();
			String uname = usernminput;

			Map<String, Object> usernm = libuserDao.selectUsernm(uname);

			if (usernm == null) {
				System.out.println("!!!등록된 해당 이름는 없습니다.");
			} else {
				System.out.println("사용자가 있으니 출력");
				libboardService.viewNserch(usernminput);
			}
			break;

		case 2: // 전화번호 조회
			// 번호가 있는지 없는지 유무확인
			System.out.println("검색 전화번호를 입력해주세요>>>");
			String userphinput = ScanUtil.nextLine();
			String uname1 = userphinput;

			Map<String, Object> userph = libuserDao.selectUserPn(userphinput);

			if (userph == null) {
				System.out.println("!!!등록된 해당 전화번호 없습니다.");
			} else {
				System.out.println("사용자가 있으니 출력");
				libboardService.viewPserch(uname1);
			}
			break;
		case 3: // 전체조회
			libboardService.viewAserch();
			break;

		case 0: // 이전화면 -회원관리
			break;

		default:
			System.out.println("잘못된 입력입니다.");
			break;
		}
		return lookinput;

	}

	// 회원정보 삭제 //구현필요???
	private void deluserno() {
		System.out.println("===회원정보 삭제");
		System.out.println("삭제할 회원번호 입력해주세요>>>");
		// $$$$
		String usernoselect = ScanUtil.nextLine();
		// 삭제할 회원번호 정보 출력 -회원정보 입력받아 넘김
		// 로그인 회원정보 호출 리셋 재설정
		String password = null;
		Map<String, Object> user = libuserDao
				.selectUser(usernoselect, password);
		libController.Loginuserno = user;
		if (user == null) {
			System.out.println("!!!없는 회원번호 입니다.");
		} else {
			// 입력된 회원정보 출력
			System.out.println("---------------------------------");
			libboardDao.userInfo();
			System.out.println("---------------------------------");
			// 회원 삭제물어보기 그리고 삭제 메소드 실행
			// Y/N
			libboardDao.deleteUser();
		}
	}

	// 회원정보 수정
	private void moduserno() {
		System.out.println("===회원정보 수정");
		System.out.println("수정할 회원번호 입력해주세요>>>");
		// $$$$
		String usernoselect = ScanUtil.nextLine();
		// 수정할 회원번호 정보 출력 -회원정보 입력받아 넘김
		// 로그인 회원정보 호출 리셋 재설정
		String password = null;
		Map<String, Object> user = libuserDao
				.selectUser(usernoselect, password);
		libController.Loginuserno = user;
		if (user == null) {
			System.out.println("!!!없는 회원번호 입니다.");
		} else {
			// 입력된 회원정보 출력
			System.out.println("---------------------------------");
			libboardDao.userInfo();
			System.out.println("---------------------------------");
			// 수정할 회원 항목 확인 및 수정 메소드 호출
			System.out.println("===수정을 시작합니다");
			libuserDao.userModifyAdmin();
			System.out.println(usernoselect + ": 수정이 완료되었습니다.");
			// 변경됭 회원정보 출력
			libboardDao.userInfo();
		}

	}

	// 관리자의 회원생성
	private void adduserinfo() {
		System.out.println("===회원정보 등록");
		System.out.println("!!! 신규회원등록이 시작됩니다. 안내에 맞게 입력해주세요");
		// 1회원번호2회원이름3생년월일4주소5전화번호6생성일7관리자계정
		// 1
		// 회원 번호 등록 입력은 숫자10자리를 하지만 타입은 varchar2
		System.out.println("1.등록할 '회원번호' 입력해주세요(숫자10자리)");
		String adduserno = ScanUtil.nextLine();
		// 2
		System.out.println("2.등록할 '회원이름' 입력해주세요");
		String adduname = ScanUtil.nextLine();
		// 3
		System.out.println("3.등록할 '회원생년월일' (YYYYMMDD) 입력해주세요");
		String addubirth = ScanUtil.nextLine();
		// 4
		System.out.println("4.등록할 '회원주소' 입력해주세요");
		String adduadrs = ScanUtil.nextLine();
		// 5
		System.out.println("5.등록할 '회원전화번호(-구분)' 입력해주세요");
		String addutel = ScanUtil.nextLine();
		// 6 생성일 자동 입력 SYSDATE
		// 7 관리자계정 자동입력 libController.Loginadminno; 사용
		String loginadminno = (String) libController.Loginadminno
				.get("ADMINID");

		String addusersql = "INSERT INTO libuser VALUES ('" + adduserno
				+ "', '" + adduname + "', TO_DATE('" + addubirth
				+ "', 'YYYYMMDD'), '" + adduadrs + "', '" + addutel
				+ "', SYSDATE, '" + loginadminno + "')";

		// 입력 메소트 호출 및
		libboardService.userinfoaddin(addusersql);
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
			return bookCtrl();
		case 2:// 도서 수정
				// 수정할 책의 isbn을 받아 정보 조회후 해당값을 수정한다.
			System.out.println("===도서수정을 시작합니다.");
			System.out.println("수정할 도서의 ISBN번호를 입력해주세요>>");
			String modbookid = ScanUtil.nextLine();

			// 입력한 isbn 번호로 테이블내 정보 조회 및 출력
			libboardDao.findbAdmin(modbookid);
			// 도서정보 출력완료
			//
			// 도서정보 수정 메소드 시작 update
			libboardDao.udtfindbAdmin(modbookid);
			return bookCtrl();
		case 3:// 도서 삭제
			System.err.println("!!!도서 삭제는 과거 대여내역 정보가 없는 도서만 삭제가 가능합니다.");
			libboardDao.deletebook();

			return bookCtrl();
		case 4:// 대출 확인
			libboardService.rentbookchk();
			return bookCtrl();
		case 5:// 도서 조회
				// 도서 총 갯수 출력
			libboardService.selectbookinfo();
			// 도서 해당월 검색 조회 출력
			libboardService.selectmonthinput();
			return bookCtrl();
		case 0:
			break;

		default:
			break;
		}

		return View.ADMINMENU;
	}

	// 2- 공지글관리 진입
	public void noticectrl() {
		System.out.println("===공지글관리");
		// 공지글 출력 메소드 호출
		libboardService.selectnotice();
		// 공지글 등록수정삭제 공지글 관리 안내
		System.out.println("1등록\t2.수정\t3.삭제\t0.이전메뉴 돌아가기");
		int editin = ScanUtil.nextInt();
		switch (editin) {
		case 1:// 공지글 등록
			libboardDao.insertB();
			break;

		case 2:// 공지글 수정
			System.out.println("===공지글수정");
			System.out.println("수정할 공지번호 선택 입력>>>");
			int modno = ScanUtil.nextInt();

			// 수정할 공지 번호 입력 받아 수정할 항목들 선택후 내용 입력후 수정 되는 메소드 호출
			libboardDao.modB(modno);
			break;

		case 3:// 공지글 삭제
			System.out.println("===공지글삭제");
			System.out.println("삭제할 공지번호 선택 입력>>>");
			int dinput = ScanUtil.nextInt();

			// jdbc에 delete관련 메소드가 있다면 활용하여 한번에 삭제 가능하나
			// 구현 연습을 위해 하드코딩으로 libboarddao클래스에 메소드 생성 호출//
			String deletebdsql = "DELETE libboard WHERE boardno = " + dinput;
			libboardDao.deletebd(deletebdsql);

			break;

		case 0:// 이전메뉴로
			break;

		default:
			break;
		}

	}

}// 관리자 서비스 클래스 말단