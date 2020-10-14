package libservice;

import java.util.Map;

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

	public int userlogin() {
		System.out.println("=====회원로그인=====");
		System.out.println("회원번호를 입력해주세요(바코드 인식해주세요)>>");
		String userno = ScanUtil.nextLine();
		String password = null;

		// 회원테이블과 비교하기
		Map<String, Object> user = libuserDao.selectUser(userno, password);

		if (user == null) {
			System.out.println("없는 회원번호입니다.재입력 바랍니다.");
		} else {
			System.out.println("로그인성공");

			libController.LoginMemUser = user;

			// 회원로그인 완료확인
			System.out.println("확인1" + libController.LoginMemUser);
			System.out.println("");

			// 로그인후 선택화면 출력 및 선택인자 받아서 메소드로 해당 출력
			System.out.println("1.도서검색\t 2.회원정보수정/대여연장\t 3.공지글조회\t 0.로그아웃");
			System.out.println();
			int input1 = ScanUtil.nextInt();

			switch (input1) {
			case 1: {
				System.out.println("1-1도서검색진입");
				break;
			}
			case 2: {
				System.out.println("1-2도서검색진입");
				break;
			}
			case 3: {
				System.out.println("1-3공지글조회진입");
				break;
			}
			case 0: {
				System.out.println("회원 로그아웃 되었습니다.");
				return View.HOME;
			}
			default:
				System.out.println("잘못된입력 재입력 바랍니다.");
			}

			//return View.BOARD_LIST;
			//로그인이 완료된다면 간략한 회원정보를 창을 알려준다 (회원번호 이름 가입일정도?)
		}

		return View.HOME;
	}

}
