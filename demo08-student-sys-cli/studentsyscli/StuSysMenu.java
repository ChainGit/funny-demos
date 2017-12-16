package com.chain.part2.demo00.studentsyscli;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

/**
 * 学生信息管理系统 <b>菜单类</b> <br>
 * StuSysMenu不能被实例化
 * 
 * @version 20170109
 * @author Chain
 *
 */
public class StuSysMenu {

	private static boolean isOver = false;
	private static StuSysTool tool;

	private StuSysMenu() {

	}

	public static void showMenu() {
		if (tool == null)
			tool = StuSysTool.getInstance();
		while (!isOver) {
			resetMenu();
			printMenu();
			selectMenu(StuSysTool.getScanner());
		}
	}

	private static void printMenu() {
		System.out.println("                 欢迎使用学生信息管理系统Demo");
		System.out.println();
		System.out.println("菜单:");
		System.out.println("        0. 退出");
		System.out.println("        1. 显示 \t 2. 查找 \t 3. 保存 ");
		System.out.println("        4. 增加 \t 5. 删除 \t 6. 修改 ");
		System.out.println();
		tool.getDataNum();
		System.out.println();
		System.out.println();
	}

	private static void resetMenu() {
		 boolean chs = false;
//		boolean chs = true;
		if (chs) {
			// in cmd
			try {
				new ProcessBuilder("cmd", "/C", "cls").inheritIO().start();
				Thread.sleep(200);
			} catch (Exception e) {
				throw new RuntimeException("err exec cls");
			}
		} else {
			// in eclipse
			try {
				Robot r = new Robot();
				r.mousePress(InputEvent.BUTTON3_MASK);
				r.mouseRelease(InputEvent.BUTTON3_MASK);
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_R);
				r.keyRelease(KeyEvent.VK_R);
				r.keyRelease(KeyEvent.VK_CONTROL);
				r.delay(100);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	private static void selectMenu(Scanner in) {
		System.out.print("请输入要进行的操作> ");
		procMenu(in.nextInt());
	}

	static enum Select {
		SELECT_EXIT, SELECT_SHOW, SELECT_SAVE, SELECT_SEARCH, SELECT_ADD, SELECT_DELETE, SELECT_MODIFY,
	}

	private static void procMenu(int sel) {
		switch (sel) {
		case 0:
			askForExit();
			break;
		case 1:
			tool.showData();
			break;
		case 2:
			tool.searchData();
			break;
		case 3:
			tool.save();
			break;
		case 4:
			tool.addData();
			break;
		case 5:
			tool.deleteData();
			break;
		case 6:
			tool.modifyData();
			break;
		default:
			System.out.println("输入的序号错误.");
		}

		if (sel != 0)
			pressAnyKeyReturn();
	}

	private static void pressAnyKeyReturn() {
		System.out.print("按回车键继续.");
		try {
			// 回车
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void askForExit() {
		System.out.print("确定要保存修改并退出系统吗?(y/n)");
		if (StuSysTool.getScanner().next().toLowerCase().contains("y")) {
			// tool.exit();// 若是正常的退出,会保存两次,抛出异常
			isOver = true;
		} else
			System.out.println("操作取消.");
	}

}
