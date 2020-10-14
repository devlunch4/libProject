package libservice;

import libdao.libBoardDao;

public class libBoardService {
	private libBoardService() {
	}

	private static libBoardService instance;

	public static libBoardService getInstance() {
		if (instance == null) {
			instance = new libBoardService();
		}
		return instance;
}

private libBoardDao libboardDao = libBoardDao.getInstance();


}