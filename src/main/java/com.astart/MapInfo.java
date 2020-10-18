package com.astart;

import java.util.*;

public class MapInfo {
    Point point;    //城市坐标
    List<Point> obstacles = new ArrayList<Point>(); //周围最近的历练点列表
    Set<Point> checkCity = new HashSet<Point>();//城市周围半径

    public MapInfo(final Point point, Set<Point> treasuresPoints,AStar aStar) {
        this.point = point;
        int i = point.getX()> 5 ?point.getX() - 5:point.getX();
        int j = point.getY()> 5 ?point.getY() - 5:point.getY();
        for (; i < point.getX()+ 5 && i < 399; i++) {
            for (; i < point.getY()+ 5 && j < 399; j++) {
                checkCity.add(new Point(i,j,0));
            }
        }
        List<Point> list = new ArrayList<Point>();
        for (Point p :
                treasuresPoints) {
            list.add(p);
        }
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(calcH(point,o1) > calcH(point,o2)){
                    return 1;
                }else if (calcH(point,o1) < calcH(point,o2)){
                    return -1;
                }
                return 0;
            }
        });
        int n = list.size() >= 10 ? 10: list.size();
        for (int x = 0; x < n; x++) {
           Point p = aStar.start(point,list.get(x));
           obstacles.add(p);
        }
        Collections.sort(obstacles, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.getF() > o2.getF()){
                    return 1;
                }else if (o1.getF() < o2.getF()){
                    return -1;
                }
                return 0;
            }
        });
    }

    public boolean checkCitys(Point point){
        if (checkCity.contains(point)){
            return true;
        }else {
            return false;
        }
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<Point> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Point> obstacles) {
        this.obstacles = obstacles;
    }
    private int calcH(Point point1,Point point2)
    {
        return Math.abs(point1.getX() - point2.getX())
                + Math.abs(point1.getY() - point2.getY());
    }
}
