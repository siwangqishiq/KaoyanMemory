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
		if (y < 0 || y >= map.length) {// ��Խ�����
			return false;
		} else if (x < 0 || x >= map[y].length) {// ��Խ�����
			return false;
		}
		if (map[y][x] != 0) {// �ϰ���
			return false;
		}
		return true;
	}
	
	/**
	 * �ж��Ƿ����ظ���
	 * @param x
	 * @param y
	 * @param base
	 * @return
	 */
	public static boolean haveSamePoint(int x,int y,final Stack<Point> base){
		for(int i=0;i<base.size();i++){
			Point p=base.get(i);
			if(p.x==x && p.y==y){
				//System.out.println("�ظ���!");
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
			}// end for ��
			System.out.println();
		}// end for ��
	}

	public static Stack<Point> mazePath(Point startPt, Point endPt, int map[][]) {
		Stack<Point> stack = new Stack<Point>();
		Point curPt = startPt;// current point
		int i=0;
		while(true){
			i++;
			if (curPt.isSame(endPt)) {// �����յ�
				stack.push(curPt);
				return stack;
			}
			// �����յ�
			if(!curPt.right){//�ұ�δ������
				curPt.right=true;//��ʾ�ұ��ѱ�����
				if(canGo(curPt.x+1, curPt.y, map) && !haveSamePoint(curPt.x+1, curPt.y,stack)){//�ұ߿ɴ�
					stack.push(curPt);//��ǰ����ջ
					curPt=new Point(curPt.x+1, curPt.y);//��ǰ���ƶ����ұ�
					curPt.left=true;
					continue;
				}
			}//end if
			if(!curPt.down){//�±�δ������
				curPt.down=true;//��ʾ�±��ѱ�����
				if(canGo(curPt.x, curPt.y+1, map) && !haveSamePoint(curPt.x,curPt.y+1,stack)){//�ұ߿ɴ�
					stack.push(curPt);//��ǰ����ջ
					curPt=new Point(curPt.x, curPt.y+1);//��ǰ���ƶ�������
					curPt.up=true;
					continue;
				}
			}//end if
			
			if(!curPt.left){//���δ������
				curPt.left=true;//��ʾ�±��ѱ�����
				if(canGo(curPt.x-1, curPt.y, map) && !haveSamePoint(curPt.x-1,curPt.y,stack)){//��߿ɴ�
					stack.push(curPt);//��ǰ����ջ
					curPt=new Point(curPt.x-1, curPt.y);//��ǰ���ƶ�������
					curPt.right=true;
					continue;
				}
			}//end if
			
			if(!curPt.up){//�ϱ�δ������
				curPt.up=true;//��ʾ�ϱ��ѱ�����
				if(canGo(curPt.x, curPt.y-1, map) && !haveSamePoint(curPt.x,curPt.y-1,stack)){//�ұ߿ɴ�
					stack.push(curPt);//��ǰ����ջ
					curPt=new Point(curPt.x, curPt.y-1);//��ǰ���ƶ�������
					curPt.down=true;
					continue;
				}
			}//end if
			if(!stack.isEmpty()){
				curPt=stack.pop();
			}else{
				System.out.println("��·��!");
				break;
			}
		}//end while
		return null;
	}
}// end class
