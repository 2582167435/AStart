package com.astart;
import java.util.*;

/**
 * 
 * ClassName: AStar 
 * @Description: A星算法
 */
public class AStar
{
	public final static int PATH = 8; // 路径
	public static final int TREASURES_COST = -40;   //宝藏成本
	public static final int POINT_COST = 10;   //空点成本
	public static final int PLAYER_COST = 300;   //玩家成本
	private static final int HAVE_SHOE = 1;
	Queue<Point> openList = new PriorityQueue<Point>(); // 优先队列(升序)
	List<Point> closeList = new ArrayList<Point>();
	private Set<Point> obstaclePoints = new HashSet<Point>();   //障碍物
	private Set<Point> treasuresPoints = new HashSet<Point>();  //历练点
	private Set<Point> playerPoints =  new HashSet<Point>();   //玩家

	public Set<Point> getPlayerPoints() {
		return playerPoints;
	}

	public void setPlayerPoints(Set<Point> playerPoints) {
		this.playerPoints = playerPoints;
	}

	public Set<Point> getObstaclePoints() {
		return obstaclePoints;
	}

	public void setObstaclePoints(Set<Point> obstaclePoints) {
		this.obstaclePoints = obstaclePoints;
	}

	public Set<Point> getTreasuresPoints() {
		return treasuresPoints;
	}

	public void setTreasuresPoints(Set<Point> treasuresPoints) {
		this.treasuresPoints = treasuresPoints;
	}

	/**
	 * 开始算法
	 */


	public Point start(Point startPoint, Point endPoint)
	{
		// clean
		openList.clear();
		closeList.clear();
		// 开始搜索
		openList.add(startPoint);
		return moveNodes(endPoint);
	}

	/**
	 * 移动当前结点
	 */
	private Point moveNodes(Point endPoint)
	{
		while (!openList.isEmpty())
		{
			Point currentPoint = openList.poll();
			closeList.add(currentPoint);
			addNeighborNodeInOpen(endPoint,currentPoint);
			if (isPointInClose(endPoint))
			{
				//drawPath(mapInfo.maps, mapInfo.end);
				return endPoint;
			}
		}
		return endPoint;
	}
	
	/**
	 * 在二维数组中绘制路径
	 */
	private int[][] drawPath(int[][] maps, Point end)
	{
		System.out.println(end.getF());
		while (end != null)
		{
			maps[end.getY()][end.getX()] = PATH;
			end = end.getPoint();
		}
		return maps;
	}

	/**
	 * 添加所有邻结点到open表
	 */
	private void addNeighborNodeInOpen(Point endPoint,Point currentPoint)
	{
		int x = currentPoint.getX();
		int y = currentPoint.getY();
		// 左
		addNeighborNodeInOpen(endPoint,currentPoint, x - HAVE_SHOE, y, POINT_COST);
		// 上
		addNeighborNodeInOpen(endPoint,currentPoint, x, y - HAVE_SHOE, POINT_COST);
		// 右
		addNeighborNodeInOpen(endPoint,currentPoint, x + HAVE_SHOE, y, POINT_COST);
		// 下
		addNeighborNodeInOpen(endPoint,currentPoint, x, y + HAVE_SHOE, POINT_COST);
		// 左上
		addNeighborNodeInOpen(endPoint,currentPoint, x - HAVE_SHOE, y - HAVE_SHOE, POINT_COST);
		// 右上
		addNeighborNodeInOpen(endPoint,currentPoint, x + HAVE_SHOE, y - HAVE_SHOE, POINT_COST);
		// 右下
		addNeighborNodeInOpen(endPoint,currentPoint, x + HAVE_SHOE, y + HAVE_SHOE, POINT_COST);
		// 左下
		addNeighborNodeInOpen(endPoint,currentPoint, x - HAVE_SHOE, y + HAVE_SHOE, POINT_COST);
	}
	/**
	 * 添加一个邻结点到open表
	 */
	private void addNeighborNodeInOpen(Point endPoint,Point currentPoint, int x, int y, int value)
	{
		if (canAddNodeToOpen(x, y)) {
			Point point = new Point(x,y,0);
			if(isPointInTreasures(point)){
				point.setPointType(2);
				value = TREASURES_COST;
			}
			if(isPointHavePlayer(point)){
				point.setPointType(9);
				value = PLAYER_COST;
			}

			int G = currentPoint.getG() + value; // 计算邻结点的G值
			Point childPoint = findNodeInOpen(point);
			if (childPoint == null) {
				int H = calcH(endPoint,point); // 计算H值
				if(isEndNode(endPoint,point)) {
					childPoint = endPoint;
					childPoint.setPoint(currentPoint);
					childPoint.setG(G);
					childPoint.setH(H);
				}
				else {
					childPoint = new Point(point.getX(), point.getY(), point.getPointType());
					childPoint.setH(H);
					childPoint.setG(G);
					childPoint.setPoint(currentPoint);
				}
				openList.add(childPoint);
			} else if (obstacle(currentPoint)) {
				childPoint.setF(childPoint.getF() + currentPoint.getF());
				childPoint.setPoint(currentPoint);
				//openList.add(childPoint);
			}else {

			}
		}
	}
	private boolean obstacle(Point currentPoint){
		if(currentPoint.getPointType() == 2){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * 是否需要优先进入历练点
	 */

	/**
	 * 从Open列表中查找结点
	 */
	private Point findNodeInOpen(Point point)
	{
		if (point == null || openList.isEmpty()) return null;
		for (Point node : openList)
		{
			if (node.equals(point))
			{
				return node;
			}
		}
		return null;
	}


	/**
	 * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
	 */
	private int calcH(Point point1,Point point2)
	{
		return Math.abs(point1.getX() - point2.getX())
				+ Math.abs(point1.getY() - point2.getY());
	}
	
	/**
	 * 判断结点是否是最终结点
	 */
	private boolean isEndNode(Point endPoint,Point point)
	{
		return point != null && endPoint.equals(point);
	}

	/**
	 * 判断结点能否放入Open列表
	 */
	private boolean canAddNodeToOpen(int x, int y)
	{
		// 是否在地图中
		if (x < 0 || x >= 7 || y < 0 || y >= 4) return false;
		// 判断是否是不可通过的结点
		if (isPointInObstacle(new Point(x,y,0))) return false;
		// 判断结点是否存在close表
		if (isPointInClose(x, y)) return false;

		return true;
	}

	/**
	 * 判断坐标是否在close表中
	 */
	private boolean isPointInClose(Point point)
	{
		return point!=null&&isPointInClose(point.getX(), point.getY());
	}

	/**
	 * 判断坐标是否在close表中
	 */
	private boolean isPointInClose(int x, int y)
	{
		if (closeList.isEmpty()) return false;
		for (Point point : closeList)
		{
			if (point.getX() == x && point.getY() == y)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断坐标是否在障碍物表中
	 */
	private boolean isPointInObstacle(Point point) {
		if (obstaclePoints.isEmpty()) return false;
		if(obstaclePoints.contains(point)){
			return true;
		}
			return false;
	}

	/**
	 * 判断坐标是否在历练点表中
	 */
	private boolean isPointInTreasures(Point point) {
		if (treasuresPoints.isEmpty()) return false;
		if(treasuresPoints.contains(point)){
			return true;
		}
		return false;
	}

	/**
	 * 判断坐标是否有玩家
	 */
	private boolean isPointHavePlayer(Point point) {
		if (playerPoints.isEmpty()) return false;
		if(playerPoints.contains(point)){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		Set<Point> obstacle = new HashSet<Point>();
//		Point point1 = new Point(1,0,1);
//		Point point2 = new Point(1,1,1);
//		obstacle.add(point1);
//		obstacle.add(point2);

		Set<Point> treasures = new HashSet<Point>();
//		Point point3 = new Point(1,3,2);
//		treasures.add(point3);
		Set<Point> player = new HashSet<Point>();
		AStar aStar = new AStar();
		int[][] maps = {
				{1,0,0,0,0,0,2},
				{1,1,1,1,9,0,2},
				{0,0,0,0,0,0,0},
				{0,0,0,0,0,2,0}
		};
		for (int i = 0; i < maps.length; i++) {
			for (int j = 0; j < maps[i].length; j++) {
				if (maps[i][j] == 1){
					obstacle.add(new Point(j,i,1));
				}else if(maps[i][j] == 2){
					treasures.add(new Point(j,i,2));
				}else if(maps[i][j] == 9){
					player.add(new Point(j,i,9));
				}
			}
		}
		aStar.setObstaclePoints(obstacle);
		aStar.setTreasuresPoints(treasures);
		aStar.setPlayerPoints(player);
		Point startP = new Point(3,3,0);
		Point endP = new Point(1,0,4);
		Point p  = aStar.start(startP,endP);

		maps = aStar.drawPath(maps,p);
		for (int i = 0; i < maps.length; i++) {
			for (int j = 0; j < maps[i].length; j++) {
				System.out.print(maps[i][j]);
			}
			System.out.println();
		}
	}
}
