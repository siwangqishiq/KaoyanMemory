package com.xinlan.maze;

import java.util.LinkedList;
import java.util.Stack;

/**
 * A*寻路
 * 
 * @author Panyi
 * 
 */
public class AStarDemo {
	public static int starx = 0;
	public static int stary = 0;
	public static int endx = 2;
	public static int endy = 0;

	public static int[][] map = {//
	/*  */{ 0, 1, 0, 0, 0, 0 },//
			{ 0, 0, 1, 1, 1, 0 },//
			{ 0, 1, 0, 1, 0, 0 },//
			{ 0, 1, 0, 1, 0, 0 },//
			{ 0, 0, 0, 0, 0, 0 },//
			{ 0, 0, 0, 1, 0, 0 } };

	public static void main(String[] args) {
		Stack<Point> stack = mazePath(starx, stary, endx, endy);
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
			int endy) {
		LinkedList<Point> openList = new LinkedList<Point>();// 开放列表
		LinkedList<Point> closeList = new LinkedList<Point>();// 关闭列表
		Point start = new Point(startx, starty);// 开始点
		openList.add(start);
		Point curPt = null;
		do {
			curPt = selectMinFPoint(openList);// 选择出F值最小的节点 设为当前节点
			openList.remove(curPt);// 将当前节点从开放列表中删除
			closeList.add(curPt);// 将当前节点放入关闭列表中
			checkSurroundPoint(curPt, openList, closeList);
		} while (!isInList(endx, endy, openList) || openList.isEmpty());
		Stack<Point> ret=new Stack<Point>();
		Point p=getPointFromList(endx, endy, openList);
		while(p!=null){
			ret.push(p);
			p=p.parent;//寻找父节点
		}
		return ret;
	}

	/**
	 * 检查指定节点周围节点
	 * 
	 * @param pt
	 */
	public static void checkSurroundPoint(Point pt, LinkedList<Point> openList,
			LinkedList<Point> closeList) {
		processSide(pt.x + 1, pt.y, pt, openList, closeList);
		processSide(pt.x, pt.y + 1, pt, openList, closeList);
		processSide(pt.x - 1, pt.y, pt, openList, closeList);
		processSide(pt.x, pt.y - 1, pt, openList, closeList);
	}

	public static void processSide(int newx, int newy, Point pt,
			LinkedList<Point> openList, LinkedList<Point> closeList) {
		if (!isInList(newx, newy, closeList) && canGo(newx, newy, map)) {// 右侧点
			if (isInList(newx, newy, openList)) {// 新增节点在开放列表中
				// 比较新的G值与原有G值
				int newg = pt.f + 10 + getHValue(newx, newy, endx, endy);
				Point p = getPointFromList(newx, newy, openList);
				if (newg < p.g) {// 新的G值小于原来的
					p.parent = pt;
					p.f = pt.f + 10;
					p.h = getHValue(newx, newy, endx, endy);
				}
			} else {// 新增节点不在开放列表中
				openList.add(new Point(newx, newy, pt.f + 10, getHValue(newx,
						newy, endx, endy), pt));
			}// end is inopenList
		}
	}

	/**
	 * 求出两点之间霍夫曼距离
	 * 
	 * @param x
	 * @param y
	 * @param endx
	 * @param endy
	 * @return
	 */
	public static int getHValue(int x, int y, int endx, int endy) {
		return Math.abs(endx - x) + Math.abs(endy - y);
	}

	/**
	 * 从列表中选择出指定点
	 * 
	 * @param x
	 * @param y
	 * @param list
	 * @return
	 */
	public static Point getPointFromList(int x, int y, LinkedList<Point> list) {
		for (int i = 0; i < list.size(); i++) {
			Point p = list.get(i);
			if (p.x == x && p.y == y) {
				return p;
			}
		}// end for i
		return null;
	}

	/**
	 * 检查节点是否在列表内
	 * 
	 * @param pt
	 * @param list
	 * @return
	 */
	public static boolean isInList(int x, int y, LinkedList<Point> list) {
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
	public static Point selectMinFPoint(LinkedList<Point> list) {
		Point minPoint = null;// 返回值
		int curMinFValue = Integer.MAX_VALUE;// 当前最小F值
		for (int i = 0; i < list.size(); i++) {
			Point pt = list.get(i);
			if (curMinFValue > pt.f) {
				minPoint = pt;
				curMinFValue = minPoint.f;
			}
		}// end for i
		return minPoint;
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

	private static class Point {
		int x, y;
		Point parent;
		int f, g, h;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
			this.parent = null;
			f = g = h = 0;
		}

		public Point(int x, int y, int g, int h, Point parent) {
			this.x = x;
			this.y = y;
			this.g = g;
			this.h = h;
			f = g + h;
			this.parent = parent;
		}
	}// end inner class
}// end class
