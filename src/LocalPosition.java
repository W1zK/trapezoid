import javax.swing.text.Position;

public class LocalPosition {
    private Point point;
    private double distance;
    private Segment segmentPoint2;

    public LocalPosition(Point point, double distance, Segment segmentPoint2) {
        this.point = point;
        this.distance = distance;
        this.segmentPoint2 = segmentPoint2;
    }

    public LocalPosition(Point observePoint, Point point) {
        this.point = point;
        distance = Math.sqrt(Math.pow(observePoint.getX() - point.getX(), 2) + Math.pow(observePoint.getY() - point.getY(), 2));
        distance = Math.round(distance*1000);
        distance /=1000;

    }
    public LocalPosition(Point point, Segment segment) {
        this.point = point;
        distance = Math.sqrt(Math.pow(point.getX() - segment.getX(), 2) + Math.pow(point.getY() - segment.getY(), 2));
        distance = Math.round(distance*1000);
        distance /=1000;


    }



    public void setPoint(Point point) {
        this.point = point;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Point getPoint() {
        return point;
    }

    public double getDistance() {
        return distance;
    }

}
