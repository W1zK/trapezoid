class Line {
    private double a;
    private double b;
    private double c;
    private double x;
    private double y;

    public Line(double a, double b, double c, double x, double y) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.x = x;
        this.y = y;
    }

    public Line(double x, double x1, double y, double y1){


        a = 1/(y1 - y);
        a= Math.round(a*1000);
        a/=1000;
        b = 1/(x1 - x);
        b=Math.round(b*1000);
        b/=1000;
        c = (y/(y1-y)-x/(x1-x));
        c=Math.round(c*1000);
        c/=1000;


    }
    public Line(double a1, double b1,double c1,double a2, double b2,double c2){
        this.x=(a1*c2-a2*c1)/(a2*b1-a1*b2);
        this.y=(b1*x+c1)/a1;
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

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }
}
