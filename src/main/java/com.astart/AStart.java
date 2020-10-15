package com.astart;

import java.util.*;

public class AStart{
    public static final int TREASURES_COST = 10;   //宝藏成本
    public static final int POINT_COST = 25;   //空点成本
    public static final int DISTANCE = 10;     //距离
    Map<String, Point> openMap = new HashMap<String, Point>();  //打开列表
    Map<String, Point> closeMap = new HashMap<String, Point>(); //关闭列表
    Map<String, Point> nearOutMap;
    private Set<Point> obstaclePoints = new HashSet<Point>();   //障碍物
    private ArrayList<Point> cityPoints = new ArrayList<Point>();       //城市
    private Set<Point> treasuresPoints = new HashSet<Point>();  //历练点
    private LinkedList<Point> playerPoints = new LinkedList<Point>();   //玩家

    Point startPoint;   // 起点
    Point endPoint;     // 终点
    Point currentPoint; // 当前使用节点
    Point lastPoint;    //  目标节点
    int num = 0;

    public AStart(Set<Point> obstaclePoints, ArrayList<Point> cityPoints, Set<Point> treasuresPoints, LinkedList<Point> playerPoints, Point startPoint, Point endPoint) {
        this.obstaclePoints = obstaclePoints;
        this.cityPoints = cityPoints;
        this.treasuresPoints = treasuresPoints;
        this.playerPoints = playerPoints;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point move(int x1, int y1, int x2, int y2) {
        num = 0;
        this.lastPoint=new Point(x2,y2,0);
        this.startPoint = new Point(x1, y1,0);
        Point endPoint=new Point(x2,y2,0);
        this.endPoint = this.getNearPoint(endPoint,endPoint);
        this.closeMap.put(startPoint.getKey(), startPoint);
        this.currentPoint = this.startPoint;
        this.toOpen(x1, y1);
        return endPoint;

    }

    private void toOpen(int x, int y) {
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(x - 1, y,0));
        points.add(new Point(x + 1, y,0));
        points.add(new Point(x, y - 1,0));
        points.add(new Point(x, y + 1,0));
        points.add(new Point(x - 1, y - 1,0));
        points.add(new Point(x - 1, y + 1,0));
        points.add(new Point(x + 1, y - 1,0));
        points.add(new Point(x + 1, y + 1,0));
        for (Point p:
                points) {
            if(this.treasuresPoints.contains(p)){
                p.setPointType(2);
            }
            this.addOpenPoint(p,DISTANCE);
            num++;
            if (num <= 4000) {
                this.toClose(x, y);
            }
        }
    }

    private void toClose(int x, int y) {
        List<Point> list = new ArrayList<Point>(openMap.values());
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.getF() > o2.getF()) {
                    return 1;
                } else if (o1.getF() < o2.getF()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        if (list.size() > 0) {
            this.currentPoint = list.get(0);
            closeMap.put(this.currentPoint.getKey(), this.currentPoint);
            openMap.remove(this.currentPoint.getKey());
            if (!currentPoint.equals(endPoint)) {
                this.toOpen(this.currentPoint.getX(), this.currentPoint.getY());
            } else {
                endPoint = this.currentPoint;
            }
        }
    }

    private void addOpenPoint(Point point, int gCost) {
        if (point.getX() < 0 || point.getY() < 0) {
            return;
        }
        String key = point.getKey();
        if (!this.obstaclePoints.contains(point) && !point.equals(this.currentPoint)) {
            int hEstimate = this.getGuessLength(point.getX(), point.getY(),
                    this.endPoint.getX(), this.endPoint.getY());
            int totalGCost = this.currentPoint.getG() + gCost;
            int fTotal = totalGCost + hEstimate;
            if (!closeMap.containsKey(key)) {
                point.setH(hEstimate);
                point.setG(totalGCost);
                point.setF(fTotal);
                Point oldPoint = openMap.get(key);
                if (oldPoint != null) {
                    if (oldPoint.getG() > totalGCost) {
                        oldPoint.setF(fTotal);
                        oldPoint.setPoint(this.currentPoint);
                        openMap.put(key, oldPoint);
                    }
                } else {
                    point.setPoint(this.currentPoint);
                    openMap.put(key, point);
                }
            } else {
                Point oldPoint = closeMap.get(key);
                if (oldPoint != null) {
                    if ((oldPoint.getG() + gCost) < this.currentPoint.getG()) {
                        if (this.currentPoint.getPoint() != oldPoint) {
                            this.currentPoint.setF(oldPoint.getF() + gCost);
                            this.currentPoint.setG(oldPoint.getG() + gCost);
                            this.currentPoint.setPoint(oldPoint);
                        }
                    }
                }
            }
        }
    }


    public Point getNearPoint(Point point,Point point2) {
        if(this.obstaclePoints.contains(point)){
            nearOutMap = new HashMap<String, Point>();
            this.endPoint = point;
            this.toNearPoint(point,point2);
            List<Point> nearList = new ArrayList<Point>(nearOutMap.values());
            Collections.sort(nearList, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    if (o1.getF() > o2.getF()) {
                        return 1;
                    } else if (o1.getF() < o2.getF()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            this.openMap = new HashMap<String,Point>();
            this.closeMap = new HashMap<String,Point>();
            if (nearList.size() > 0) {
                return nearList.get(0);
            }else{
                return point;
            }
        }else{
            return point;
        }
    }
    public void toNearPoint(Point point1,Point point2) {
        int x = point1.getX();
        int y = point1.getY();
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(x - 1, y,0));
        points.add(new Point(x + 1, y,0));
        points.add(new Point(x, y - 1,0));
        points.add(new Point(x, y + 1,0));
        points.add(new Point(x - 1, y - 1,0));
        points.add(new Point(x - 1, y + 1,0));
        points.add(new Point(x + 1, y - 1,0));
        points.add(new Point(x + 1, y + 1,0));
        for (Point p:
             points) {
            if(this.treasuresPoints.contains(p)){
                p.setPointType(2);
            }
            this.addNearOpenPoint(p,point2);
        }
        if(this.nearOutMap.size()==0){
            List<Point> list = new ArrayList<Point>(openMap.values());
            Collections.sort(list, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    int f1 = o1.getF();
                    int f2 = o2.getF();
                    if (f1 > f2) {
                        return 1;
                    } else if (f1 < f2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            if (list.size() > 0) {
                Point p = list.get(0);
                this.closeMap.put(p.getKey(), p);
                this.openMap.remove(p.getKey());
                this.toNearPoint(list.get(0),point2);
            }
        }
    }

    private void addNearOpenPoint(Point point1,Point point2) {
        String key = point1.getKey();
        int h = this.getGuessLength(point1.getX(), point1.getY(), point2.getX(),
                point2.getY());
        point1.setH(h);
        if (this.treasuresPoints.contains(point1)){
            point1.setG(TREASURES_COST);
        }else {
            point1.setG(POINT_COST);
        }
        if (this.obstaclePoints.contains(point1)) {
            if (!this.openMap.containsKey(key)
                    && !this.closeMap.containsKey(key)) {
                this.openMap.put(key, point1);
            }
        } else {
            this.nearOutMap.put(key, point1);
        }

    }
    private int getGuessLength(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2)) * DISTANCE;
    }

}
