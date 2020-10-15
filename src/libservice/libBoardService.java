package libservice;

import libUtil.ScanUtil;
import libUtil.View;
import libdao.libBoardDao;

public class libBoardService {
	private libBoardService() {
	}

	// 인스턴스 설정
	private static libBoardService instance;

	public static libBoardService getInstance() {
		if (instance == null) {
			instance = new libBoardService();
		}
		return instance;
	}

	// libBoardDao 인스턴스 호출
	private libBoardDao libboardDao = libBoardDao.getInstance();

	// 테이블 보기 메소드 미사용????
	// public int boardList() {
	// List<Map<String, Object>> boardList = libboardDao.selectbookinfo();
	// for (int i = 0; i < boardList.size(); i++) {
	// System.out.println(boardList.get(i).get("bookno"));
	// }
	// return View.BOARD_LIST;
	// }

	// 도서 신청 게시판 보기 >> 출력후 등록 // 만약 삭제구현시 작성자 아이디가 해당 접속자글만 삭제가능함
	public int applyBoardShow() {
		System.out.println("===도서신청게시판");

		// 도서신청게시판 조회 출력
		libboardDao.selectApplyBoard();

		System.out.println("===도서신청 게시판메뉴");
		System.out.println("1.도서신청 글등록   2.도서신청 글삭제   0.이전화면");
		System.out.println("신청게시판에서 수행할 번호 입력");

		int applyinput = ScanUtil.nextInt();
		switch (applyinput) {
		case 1:
			System.out.println("==도서신청글 등록");
			libboardDao.insertApply();

			return View.APPLYBOARDSHOW;

		case 2:
			System.out.println("==도서신청글 삭제");
			System.out.println("삭제할 신청글 번호를 입력해주세요>>");
			int deleteno = ScanUtil.nextInt();

			libboardDao.deleteApply(deleteno);

			return View.APPLYBOARDSHOW;

		case 0:
			System.out.println("이전화면이동");
			// 도서신청 메뉴이동(도서신청게시글 확인)
			return View.APPLYBOARDSHOW;

		default:
			break;
		}
		// 도서검색으로 이동
		return View.USERBOOKSEARCH;
	}
}
