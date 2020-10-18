package com.astart;

public class Point implements Comparable<Point>{
    private int x;
    private int y;
    private int pointType;  //0-空点 1-障碍 2-历练点 3-长安 4-江城 5-庐城 6-蜀城 7-吴城 9-玩家
    private String pointName;
    private int f;  //F就是G和H的总和
    private int g;  //G表示该点到起始点位所需要的代价
    private int h;  //H表示该点到终点的曼哈顿距离
    private Point point;
    private int isJumpPoint; //是否为跳点

    public Point(int x, int y, int pointType){
        this.x = x;
        this.y = y;
        this.pointType = pointType;
        this.setPointType(pointType);
    }
    public String getKey(){
        return x + "_" + y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPointType() {
        return pointType;
    }

    public void setPointType(int pointType) {
        this.pointType = pointType;
        switch (this.pointType){
            case 0:
                this.pointName = "空点";
                break;
            case 1:
                this.pointName = "障碍";
                break;
            case 2:
                this.pointName = "历练点";
                break;
            case 3:
                this.pointName = "长安";
                break;
            case 4:
                this.pointName = "江城";
                break;
            case 5:
                this.pointName = "庐城";
                break;
            case 6:
                this.pointName = "蜀城";
                break;
            case 7:
                this.pointName = "吴城";
                break;
            case 9:
                this.pointName = "玩家";
                break;
        }
    }

    public String getPointName() {
        return pointName;
    }


    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
        this.setF(this.g + this.h);
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
        this.setF(this.g + this.h);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "MapInfo{" +
                "x=" + x +
                ", y=" + y +
                ", pointType=" + pointType +
                ", pointName='" + pointName + '\'' +
                '}';
    }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Point other = (Point) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

    @Override
    public int compareTo(Point o) { {
            if (o == null) return -1;
            if (this.getF() == o.getF()){
                if (this.getH() > o.getH())
                    return 1;
                else if (this.getH() < o.getH()) return -1;
                return 0;
            }
            else if (this.getF() < o.getF()) return -1;
            else if(this.getF() > o.getF()){
                return 1;
            } else return 0;
        }
    }
}
