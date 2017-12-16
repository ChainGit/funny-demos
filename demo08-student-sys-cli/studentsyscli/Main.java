package com.chain.part2.demo00.studentsyscli;

import java.io.PrintStream;

/**
 * ѧ����Ϣ����ϵͳDemo <br>
 * ϵͳ��飺
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class Main {

	 private static final String LOG_PATH = "E:\\Special\\Tests\\stusys.log";
//	private static final String LOG_PATH = "stusys.log";

	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream(LOG_PATH));
			StuSysTool.inital();
			StuSysMenu.showMenu();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				StuSysTool.getInstance().exit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
