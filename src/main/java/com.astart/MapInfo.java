package com.astart;

public class MapInfo {
    private int x;
    private int y;
    private int pointType;  //0-空点 1-障碍 2-历练点 3-长安 4-江城 5-庐城 6-蜀城 7-吴成 9-玩家
    private String pointName;
    private int f;  //F就是G和H的总和
    private int g;  //G表示该点到起始点位所需要的代价
    private int h;  //H表示该点到终点的曼哈顿距离
    private int isJumpPoint; //是否为跳点

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
            case 1:
                this.pointName = "障碍";
            case 2:
                this.pointName = "历练点";
            case 3:
                this.pointName = "长安";
            case 4:
                this.pointName = "江城";
            case 5:
                this.pointName = "庐城";
            case 6:
                this.pointName = "蜀城";
            case 7:
                this.pointName = "吴成";
            case 9:
                this.pointName = "玩家";
        }
    }

    public String getPointName() {
        return pointName;
    }


    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
        this.f = this.g + this.h;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
        this.f = this.g + this.h;
    }
}
