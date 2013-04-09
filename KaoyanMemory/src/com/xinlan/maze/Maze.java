package com.xinlan.maze;
import java.util.Stack;

public class Maze {
	private static class Point {
		int x, y;
		boolean right, down, left, up;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
			right = false;
			down = false;
			left = false;
			up = false;
		}

		public boolean isSame(Point p) {
			return (p.x == x) && (p.y == y);
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
	 * @param x
	 * @param y
	 * @param base
	 * @return
	 */
	public static boolean haveSamePoint(int x,int y,final Stack<Point> base){
		for(int i=0;i<base.size();i++){
			Point p=base.get(i);
			if(p.x==x && p.y==y){
				//System.out.println("重复点!");
				return true;
			}
		}//end for i
		return false;
	}

	public static void main(String[] args) {
		int[][] map = {//
		/*  */{ 0, 1, 0, 0, 0 ,0},//
				{ 0, 0, 0, 1, 1 ,0},//
				{ 0, 1, 0, 1, 0 ,0},//
				{ 0, 1, 0, 1, 0 ,0},//
				{ 0, 0, 0, 1, 0 ,0},//
				{ 0, 0, 0, 1, 0 ,0}
		};

		Stack<Point> stack = mazePath(new Point(5, 5), new Point(0, 0), map);
		for (int i = 0; i < stack.size(); i++) {
			Point p = stack.get(i);
			map[p.y][p.x] = 5;
		}// end for i
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print(map[i][j] + "  ");
			}// end for 列
			System.out.println();
		}// end for 行
	}

	public static Stack<Point> mazePath(Point startPt, Point endPt, int map[][]) {
		Stack<Point> stack = new Stack<Point>();
		Point curPt = startPt;// current point
		int i=0;
		while(true){
			i++;
			if (curPt.isSame(endPt)) {// 到达终点
				stack.push(curPt);
				return stack;
			}
			// 不在终点
			if(!curPt.right){//右边未被访问
				curPt.right=true;//标示右边已被访问
				if(canGo(curPt.x+1, curPt.y, map) && !haveSamePoint(curPt.x+1, curPt.y,stack)){//右边可达
					stack.push(curPt);//当前点入栈
					curPt=new Point(curPt.x+1, curPt.y);//当前点移动到右边
					curPt.left=true;
					continue;
				}
			}//end if
			if(!curPt.down){//下边未被访问
				curPt.down=true;//标示下边已被访问
				if(canGo(curPt.x, curPt.y+1, map) && !haveSamePoint(curPt.x,curPt.y+1,stack)){//右边可达
					stack.push(curPt);//当前点入栈
					curPt=new Point(curPt.x, curPt.y+1);//当前点移动到下面
					curPt.up=true;
					continue;
				}
			}//end if
			
			if(!curPt.left){//左边未被访问
				curPt.left=true;//标示下边已被访问
				if(canGo(curPt.x-1, curPt.y, map) && !haveSamePoint(curPt.x-1,curPt.y,stack)){//左边可达
					stack.push(curPt);//当前点入栈
					curPt=new Point(curPt.x-1, curPt.y);//当前点移动到下面
					curPt.right=true;
					continue;
				}
			}//end if
			
			if(!curPt.up){//上边未被访问
				curPt.up=true;//标示上边已被访问
				if(canGo(curPt.x, curPt.y-1, map) && !haveSamePoint(curPt.x,curPt.y-1,stack)){//右边可达
					stack.push(curPt);//当前点入栈
					curPt=new Point(curPt.x, curPt.y-1);//当前点移动到下面
					curPt.down=true;
					continue;
				}
			}//end if
			if(!stack.isEmpty()){
				curPt=stack.pop();
			}else{
				System.out.println("无路径!");
				break;
			}
		}//end while
		return null;
	}
}// end class
