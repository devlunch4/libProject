package libservice;

import java.util.List;
import java.util.Map;

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

	// 신청일
	// 공지글 출력 3개까지
	public int readNoticeboard() {
		// 공지게시글 리스트로 추출 공지글은 무조건 3개 이상 존재하고있음.
		List<Map<String, Object>> readNoticeList = libboardDao.selectNoticeU();

		System.out.println("===최근 공지 게시글 3건만 보여집니다. ");
		System.out.println("공지번호\t공지제목\t\t\t공지내용\t\t\t공지작성자\t공지게시일");
		for (int i = 0; i < 3; i++) {
			System.out.print(readNoticeList.get(i).get("BOARDNO"));
			System.out.print("\t" + readNoticeList.get(i).get("BTITLE"));
			System.out.print("\t\t" + readNoticeList.get(i).get("BCONTENT"));
			System.out.print("\t\t" + readNoticeList.get(i).get("BWRITER"));
			System.out.println("\t" + readNoticeList.get(i).get("BDATE"));

		}

		return View.USERREADBOARD;
	}

	// 조회번호를 입력받아 해당 게시글 출력
	public int readNBCon() {
		// 조회할 공지게시글 번호 입력 받기
		System.out.println("내용 확인 할 게시글 번호 입력>");
		int readNBConno = ScanUtil.nextInt();

		// 입력받은 게시글 출력
		libboardDao.readNBone(readNBConno);

		return View.USERREADBOARD;

	}
}
