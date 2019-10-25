public class TrapezArea {
    private double trapArea;
    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
    private double max;

    public TrapezArea(double trapArea, Point point1, Point point2, Point point3, Point point4, double max) {
        this.trapArea = trapArea;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        this.max = max;
    }

    public TrapezArea(double bigBase, LocalPosition smallBase, double rib) {
        this.trapArea = ((bigBase+smallBase.getDistance())/2)*(Math.sqrt(rib*rib-Math.pow(bigBase-smallBase.getDistance(),2)/4));

    }
    public TrapezArea(Point point1, Point point2, Point point3, Point point4,double max) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
        this.max = max;

    }


    public double getTrapArea() {
        return trapArea;
    }

    public void setTrapArea(double trapArea) {
        this.trapArea = trapArea;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    public Point getPoint4() {
        return point4;
    }

    public void setPoint4(Point point4) {
        this.point4 = point4;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
