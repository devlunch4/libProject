package libUtil;

import java.util.Scanner;

////reused build 20201014
public class ScanUtil {

	private static Scanner s = new Scanner(System.in);

	// 유틸리티 성향의 메서드인 경우 static을 붙인다
	// 입력자 생성 STRING
	public static String nextLine() {
		return s.nextLine();
	}

	// 입력자 int
	public static int nextInt() {
		return Integer.parseInt(s.nextLine());
	}

}
