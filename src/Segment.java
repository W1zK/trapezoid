class Segment {

    private double x;
    private double y;
    /*private double a;
    private double b;
    private double c;*/
    private double startA;
    private double endB;

    public Segment() {

    }
    public Segment(double a, double b, double c) {
        this.startA = -c/a;
        this.endB = -c/b;
    }

    public Segment(double x1, double x2, double y1,double y2) {
        x=(x2*y1*y2-x1*y1*y2)/(x2*y1-x1*y2);
        x=Math.round(x*1000);
        x/=1000;
        y=(-(x1*y1-x1*x)/y1);
        y=Math.round(y*1000);
        y/=1000;

    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getStartA() {
        return startA;
    }

    public void setStartA(double startA) {
        this.startA = startA;
    }

    public double getEndB() {
        return endB;
    }

    public void setEndB(double endB) {
        this.endB = endB;
    }
}
