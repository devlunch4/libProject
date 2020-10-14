package libservice;


import libUtil.ScanUtil;
import libUtil.View;
import libcontroller.libController;
import libdao.libUserDao;


public class libUserService {

	
	private libUserService(){
	}
	
	//인스턴스 생성
	private static libUserService instance;
	
	public static libUserService getInstance(){
		if (instance == null) {
			instance = new libUserService();
		}
		return instance;
	}
	
	
	public static int memlogin(){
		System.out.println("=====회원로그인=====");
		System.out.println("회원번호를 입력해주세요(바코드 인식해주세요)>>");
		String memId = ScanUtil.nextLine();

		String user = libUserDao.selectMemUser(memId);
		
		if(user == null){
			System.out.println("없는 회원번호입니다");
		} else{
			System.out.println("로그인성공");
			
			libController.LoginMemUser = user;
			
			return View.BOARD_LIST;
					}
		
			
	return View.HOME;
	}
	
}
