package com.xinlan.maze;

import java.util.LinkedList;
import java.util.Stack;

/**
 * A*寻路
 * 
 * @author Panyi
 * 
 */
public class AStar {
	private static class Point {
		int x, y;
		Point parent;
		int f, g, h;

		public Point(int x, int y, Point parent) {
			this.x = x;
			this.y = y;
			this.parent = parent;
			f = g = 0;
		}

		public int getF() {
			f = g + h;
			return f;
		}
	}

	public static boolean canGo(int x, int y, int[][] map) {
		if (y < 0 || y >= map.length) {// 行越界情况
			return false;
		} else if (x < 0 || x >= map[y].length) {// 列越界情况
			return false;
		}
		if (map[y][x] != 0) {// 障碍物
			return false;
		}
		return true;
	}

	/**
	 * 判断是否有重复点
	 * 
	 * @param x
	 * @param y
	 * @param base
	 * @return
	 */
	public static boolean haveSamePoint(int x, int y, final Stack<Point> base) {
		for (int i = 0; i < base.size(); i++) {
			Point p = base.get(i);
			if (p.x == x && p.y == y) {
				// System.out.println("重复点!");
				return true;
			}
		}// end for i
		return false;
	}

	public static void main(String[] args) {
		int[][] map = {//
		/*  */{ 0, 1, 0, 0, 0, 0 },//
				{ 0, 0, 1, 1, 1, 0 },//
				{ 0, 1, 0, 1, 0, 0 },//
				{ 0, 1, 0, 1, 0, 0 },//
				{ 0, 0, 0, 0, 0, 0 },//
				{ 0, 0, 0, 1, 0, 0 } };
		Stack<Point> stack = mazePath(0, 0, 2, 0, map);
		if (stack != null) {
			for (int i = 0; i < stack.size(); i++) {
				Point p = stack.get(i);
				map[p.y][p.x] = 5;
			}// end for i
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + "  ");
			}// end for 列
			System.out.println();
		}// end for 行
	}

	public static Stack<Point> mazePath(int startx, int starty, int endx,
			int endy, int map[][]) {
		LinkedList<Point> openList = new LinkedList<Point>();// 开放列表
		LinkedList<Point> closeList = new LinkedList<Point>();// 关闭列表

		Point start = new Point(startx, starty, null);// 开始点
		openList.add(start);
		Point curPt = null;

		curPt = selectMinFPoint(openList);// 选择出F值最小的节点 设为当前节点
		openList.remove(curPt);// 将当前节点从开放列表中删除
		closeList.add(curPt);// 将当前节点放入关闭列表中
		
		return null;
	}

	/**
	 * 检查指定节点周围节点 
	 * 
	 * @param pt
	 */
	private static void checkSurroundPoint(Point pt,
			LinkedList<Point> openList, LinkedList<Point> closeList, int[][] map) {
		// 检查上部节点
		if (!isInList(pt.x+1,pt.y, closeList) && canGo(pt.x + 1, pt.y, map)) {// 改点可达 且 不在关闭列表中
			
		}
	}

	/**
	 * 检查节点是否在列表内
	 * 
	 * @param pt
	 * @param list
	 * @return
	 */
	private static boolean isInList(int x,int y, LinkedList<Point> list) {
		for (int i = 0; i < list.size(); i++) {
			Point pp = list.get(i);
			if (x == pp.x && y == pp.y) {
				return true;
			}
		}// end for i
		return false;
	}

	/**
	 * 选择出F值最小的节点
	 * 
	 * @param list
	 * @return
	 */
	private static Point selectMinFPoint(LinkedList<Point> list) {
		Point minPoint = null;// 返回值
		int curMinFValue = Integer.MAX_VALUE;// 当前最小F值
		for (int i = 0; i < list.size(); i++) {
			Point pt = list.get(i);
			if (curMinFValue > pt.getF()) {
				minPoint = pt;
				curMinFValue = minPoint.getF();
			}
		}// end for i
		return minPoint;
	}
}// end class
