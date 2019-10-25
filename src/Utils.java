import javax.swing.text.Position;
import java.util.ArrayList;

class Utils {
    private int nullPoint = 0;
    private Point[] point = new Point[7]; //point arr
    private Point[] validPoint;
    private Vector[] vector = new Vector[point.length];
    private double[] derivative = new double[vector.length];
    private Point pointArtil = new Point(4.5, 6); //artilery
    private SecurePoints[] sourcePoint;
    private Point[] allPoint;
    private Line[] allLines;
    private boolean check = false;
    private CrossChecker crossChecker;
    private int sizeCrossLine = 0;
    private LocalPosition[] positions;
    private LocalPosition[] fPoint = new LocalPosition[4];
    private LocalPosition[] cPoint = new LocalPosition[fPoint.length];
    private Line[] farLine = new Line[fPoint.length];
    private Line[] nearCross = new Line[cPoint.length-1];
    private Line[] validFarLine;

    private int[] indexFarPoint;
    private int[] indexNearPoint;
    private Segment[] nearLine2Segment;
    private Segment[] farLine2Segment;
    private Segment[] crossSegment;
    private LocalPosition[] farDistance;
    private LocalPosition[] nearDistance;
    private LocalPosition[] midlDistance;
    private TrapezArea[] sizeTrapez;
    private TrapezArea maxAreaTrapez;
    private Point oldPoint;
    private Point newPoint;
    private LocalPosition newNearDistance;



    void run() {
        point[0] = new Point(-0.5,2.5);
        point[1] = new Point(-2, 0.5);
        point[2] = new Point(-1, -1);
        point[3] = new Point(1, -1.5);
        point[4] = new Point(2.5, 0.5);
        point[5] = new Point(0.5, 0.5);
        point[6] = new Point(1, 1.5);
        cVector(point);


    }

    private void checker(double[] derivative) {
        int m =0;
        for (int i = 0; i < derivative.length; i++) {
            if (derivative[i] >= 0) {
                check = true;
            } else if (i==derivative.length-1){
                point[0] = null;
                nullPoint++;
                validPoint = new Point[point.length - nullPoint];
                for (int j = 0; j <point.length ; j++) {
                    if (point[j]!=null) {
                        validPoint[m]=point[j];
                        m++;

                    }
                }
                vector = new Vector[validPoint.length];
                cVector(validPoint);
                break;


            }else {
                point[i+1] = null;
                nullPoint++;
                validPoint = new Point[point.length - nullPoint];
                for (int j = 0; j <point.length ; j++) {
                    if (point[j]!=null) {
                        validPoint[m]=point[j];
                        m++;

                    }
                }
                vector = new Vector[validPoint.length];
                cVector(validPoint);
                break;
            }
        }



        if (check) {
            System.out.println("!!!Фигура Опуклая!!!");
            check = false;
            sourcePoint = new SecurePoints[validPoint.length];
            sourcePoints(validPoint);


        }
    }

    private void cVector(Point[] pnt) {
        for (int i = 0; i < vector.length; i++) {
            if (i < vector.length - 1) {
                vector[i] = new Vector(pnt[i].getX(), pnt[i + 1].getX(), pnt[i].getY(), pnt[i + 1].getY());
            } else vector[i] = new Vector(pnt[i].getX(), pnt[0].getX(), pnt[i].getY(), pnt[0].getY());
        }
        derivative = new double[vector.length];
        cDerivative(vector);
    }

    private void cDerivative(Vector[] vek) {
        for (int i = 0; i < derivative.length; i++) {
            if (i < vek.length - 1) {
                derivative[i] = (vek[i].getA() * vek[i + 1].getB()) - (vek[i].getB() * vek[i + 1].getA());
            } else derivative[i] = vek[i].getA() * vek[0].getB() - vek[i].getB() * vek[0].getA();
        }
        checker(derivative);
    }

    ////////////////////////////////////////////////////////

    private void sourcePoints(Point[] source) {

        for (int i = 0; i < validPoint.length; i++) {
            if (i < source.length - 1) {
                sourcePoint[i] = new SecurePoints((source[i].getX() + source[i + 1].getX()) / 2, (source[i].getY() + source[i + 1].getY()) / 2);
            } else
                sourcePoint[i] = new SecurePoints((source[i].getX() + source[0].getX()) / 2, (source[i].getY() + source[0].getY()) / 2);

        }
        allPoint = new Point[validPoint.length + sourcePoint.length];
        simbios(validPoint, sourcePoint);


    }

    private void simbios(Point[] mass1, Point[] mass2) {

        for (int i = 0; i < allPoint.length; i++) {
            allPoint[i] = i % 2 == 0 ? mass1[i / 2] : mass2[(i - 1) / 2];
            System.out.println("AllPoint"+allPoint[i].getX()+"=="+allPoint[i].getY());
        }

        positions = new LocalPosition[allPoint.length];
       length(pointArtil, allPoint);
    }


    private void length(Point dot, Point[] spray) {
        for (int i = 0; i < positions.length; i++) {
            positions[i] = new LocalPosition(dot, spray[i]);

        }


        crossLine(allPoint);

    }

    private void crossLine(Point[] allDot) {
        allLines = new Line[allPoint.length];
        for (int i = 0; i < allLines.length; i++) {
            if (i < allLines.length - 1) {
                allLines[i] = new Line(allDot[i].getX(), allDot[i + 1].getX(), allDot[i].getY(), allDot[i + 1].getY());
            } else
                allLines[i] = new Line(allDot[i].getX(), allDot[0].getX(), allDot[i].getY(), allDot[0].getY());

        }
        positionSort(positions);

    }


    private void positionSort(LocalPosition[] posit) {
        int size = posit.length;
        int x = size - 1;
        boolean swap = true;

        while (swap) {
            swap = false;

            for (int i = x; i > size - 1 - x; i--) {
                if (posit[i].getDistance() < posit[i - 1].getDistance()) {
                    // swap
                    LocalPosition temp = posit[i];
                    posit[i] = posit[i - 1];
                    posit[i - 1] = temp;
                    swap = true;
                }
            }

            for (int i = size - x; i < x; i++) {  //двигаемся слева направо
                if (posit[i].getDistance() > posit[i + 1].getDistance()) {      // если текущий элемент больше следующего,меняем!
                    // swap
                    LocalPosition temp = posit[i];
                    posit[i] = posit[i + 1];
                    posit[i + 1] = temp;
                    swap = true;
                }
            }

            if (!swap) {
                break;
            }

        }
        for (int i = 0; i < positions.length ; i++) {
            System.out.println(positions[i].getPoint().getX()+"=="+positions[i].getPoint().getY());
            
        }


        farNClother(positions);

    }

    private void farNClother(LocalPosition[] mass) {

        int k = 0;
        for (int i = 0; i < mass.length; i++) {

            if (i <= cPoint.length - 1) {
                cPoint[i] = mass[i];
            } else if (i >= mass.length - fPoint.length) {
                fPoint[k++] = mass[i];

            }

        }
        for (int i = 0; i <cPoint.length ; i++) {
            System.out.println("Cpoint"+cPoint[i].getPoint().getX()+"=="+cPoint[i].getPoint().getY());

        }
        for (int i = 0; i <fPoint.length ; i++) {
            System.out.println("Fpoint"+fPoint[i].getPoint().getX()+"=="+fPoint[i].getPoint().getY());

        }


        nearPointSorting(cPoint);



    }

    private void nearPointSorting(LocalPosition[] mass) {
        int[] decel = new int[mass.length];
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < allPoint.length; j++) {
                if (mass[i].getPoint().getX() == allPoint[j].getX() && mass[i].getPoint().getY() == allPoint[j].getY()) {
                    decel[i] = j;
                }

            }

        }
        System.out.println(decel.length);
        for (int i = 0; i <decel.length ; i++) {
            System.out.println("Index of near Point  "+decel[i]);

        }
        for (int i = 0; i <decel.length ; i++) {
            System.out.println("Index of near Point XXX "+allPoint[decel[i]].getX());

        }

        boolean swap = false;
        int temp;
        while (!swap) {
            swap = true;
            for (int i = 0; i < decel.length - 1; i++) {
                if (decel[i] > decel[i + 1]) {
                    temp = decel[i];
                    decel[i] = decel[i + 1];
                    decel[i + 1] = temp;
                    swap = false;
                }
            }
        }
        for (int i = 0; i < decel.length; i++) {
            mass[i].setPoint(allPoint[decel[i]]);

        }
        int n = mass.length;
        //Переменная, которая будет использоваться при обмене элементов
        LocalPosition boom;

        for (int i = 0; i < n/2; i++) {
            boom = mass[n-i-1];
            mass[n-i-1] = mass[i];
            mass[i] = boom;
        }
        for (int i = 0; i <cPoint.length ; i++) {
            System.out.println("Index of near Point After   "+cPoint[i].getPoint().getX());

        }
        farPointSorting(fPoint);
    }

    private void farPointSorting(LocalPosition[] mass) {
        int[] decel = new int[mass.length];
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < allPoint.length; j++) {
                if (mass[i].getPoint().getX() == allPoint[j].getX() && mass[i].getPoint().getY() == allPoint[j].getY()) {
                    decel[i] = j;
                }

            }

        }
        System.out.println(decel.length);
        for (int i = 0; i <decel.length ; i++) {
            System.out.println("Index of far Point  "+decel[i]);

        }
        for (int i = 0; i <decel.length ; i++) {
            System.out.println("Index of far Point XXX "+allPoint[decel[i]].getX());

        }

        boolean swap = false;
        int temp;
        while (!swap) {
            swap = true;
            for (int i = 0; i < decel.length - 1; i++) {
                if (decel[i] > decel[i + 1]) {
                    temp = decel[i];
                    decel[i] = decel[i + 1];
                    decel[i + 1] = temp;
                    swap = false;
                }
            }
        }
        for (int i = 0; i < decel.length; i++) {
            mass[i].setPoint(allPoint[decel[i]]);

        }
        for (int i = 0; i <mass.length ; i++) {
            System.out.println("Index of far Point After   "+mass[i].getPoint().getX());

        }
        setFarLine(fPoint, pointArtil);
    }

    private void setFarLine(LocalPosition[] massFar, Point artillery) {


        for (int i = 0; i < farLine.length; i++) {
            farLine[i] = new Line(artillery.getX(), massFar[i].getPoint().getX(), artillery.getY(), massFar[i].getPoint().getY());
        }
        for (int i = 0; i <farLine.length ; i++) {
            System.out.println("FarLine koef"+ "\nA: "+farLine[i].getA()+"\nB: "+farLine[i].getB()+"\nC: "+farLine[i].getC());

        }
        neareLineCross(cPoint);


    }

    private void neareLineCross(LocalPosition[] massNear) {
        System.out.println(nearCross.length);
        for (int i = 0; i < nearCross.length ; i++) {
            nearCross[i] = new Line(massNear[i].getPoint().getX(), massNear[i+1].getPoint().getX(), massNear[i].getPoint().getY(), massNear[i+1].getPoint().getY());

        }
        for (int i = 0; i <nearCross.length ; i++) {
            System.out.println("nearCross koef"+ "\nA: "+nearCross[i].getA()+"\nB: "+nearCross[i].getB()+"\nC: "+nearCross[i].getC());

        }
        System.out.println("nearCross koef111111111"+ "\nA: "+nearCross[1].getA()+"\nB: "+nearCross[1].getB()+"\nC: "+nearCross[1].getC());
        System.out.println("nearCross koef111111111"+ "\nA: "+nearCross[2].getA()+"\nB: "+nearCross[2].getB()+"\nC: "+nearCross[2].getC());


        //lineVsLine(nearCross,farLine);
        checkCross();

    }

    private void checkCross() {
        sizeCrossLine = 0;
        for (int i = 0; i < fPoint.length; i++) {
            for (int j = 1; j < cPoint.length; j++) {
                crossChecker = new CrossChecker(pointArtil, fPoint[i].getPoint(), cPoint[j-1].getPoint(), cPoint[j].getPoint());
                if (crossChecker.isFlag()) {
                    sizeCrossLine++;


                }


            }

        }
        indexFarPoint = new int[sizeCrossLine];
        indexNearPoint = new int[sizeCrossLine];
        int m=0;

        for (int i = 0; i < fPoint.length; i++) {
            for (int j = 1; j <= cPoint.length - 1; j++) {
                crossChecker = new CrossChecker(pointArtil, fPoint[i].getPoint(), cPoint[j-1].getPoint(), cPoint[j ].getPoint());
                if (crossChecker.isFlag()) {
                    indexFarPoint[m] = i;
                    indexNearPoint[m] = j;
                    m++;

                }


            }

        }

        validFarLine = new Line[indexFarPoint.length];
        for (int i = 0; i <validFarLine.length ; i++) {
            validFarLine[i]= farLine[indexFarPoint[i]];

        }



        for (int i = 0; i <validFarLine.length ; i++) {
            System.out.println("11111111"+farLine[indexFarPoint[i]].getA());
            System.out.println("22222222"+validFarLine[i].getA());

        }


        for (int i = 0; i <indexNearPoint.length ; i++) {
            System.out.println("Cpof:"+cPoint[indexNearPoint[i]].getPoint().getX()+" "+cPoint[indexNearPoint[i]].getPoint().getY());
        }
        for (int i = 0; i <indexFarPoint.length ; i++) {
            System.out.println("Fpof:"+fPoint[indexFarPoint[i]].getPoint().getX()+" "+fPoint[indexFarPoint[i]].getPoint().getY());
        }



        System.out.println("indexNear"+indexNearPoint.length);
        System.out.println("indexFar"+indexFarPoint.length);
        System.out.println("cPoint"+cPoint.length);
        System.out.println("fPoint"+fPoint.length);

        lineVsLine();

    }
    private void lineVsLine(){
        nearLine2Segment = new Segment[cPoint.length];
        farLine2Segment = new Segment[validFarLine.length];
        System.out.println(sizeCrossLine);
        crossSegment = new Segment[sizeCrossLine];


        System.out.println(farLine.length);
        for (int i = 0; i <validFarLine.length ; i++) {
            System.out.println("A:  "+validFarLine[i].getA()+"\nB:  "+validFarLine[i].getB()+"\nC:  "+validFarLine[i].getC());
            System.out.println("----");

        }
        for (int i = 0; i <farLine2Segment.length ; i++) {
            farLine2Segment[i] = new Segment(validFarLine[i].getA(),validFarLine[i].getB(),validFarLine[i].getC());


        }
        /*for (int i = 0; i <farLine2Segment.length-1 ; i++) {
            farLine2Segment[i] = new Segment(farLine[indexFarPoint[i]].getA(),farLine[indexFarPoint[i]].getB(),farLine[indexFarPoint[i]].getC());


        }*/


        for (int i = 0; i <nearCross.length ; i++) {
            System.out.println("nearCross"+nearCross[indexNearPoint[i]].getA()+"+"+nearCross[indexNearPoint[i]].getB()+""+nearCross[indexNearPoint[i]].getC());

        }
        for (int i = 0; i <nearLine2Segment.length-1; i++) {
            nearLine2Segment[i] = new Segment(nearCross[indexNearPoint[i]].getA(),nearCross[indexNearPoint[i]].getB(),nearCross[indexNearPoint[i]].getC());


        }
        for (int i = 0; i <crossSegment.length ; i++) {
            crossSegment[i] = new Segment(farLine2Segment[i].getStartA(),farLine2Segment[i].getEndB(),nearLine2Segment[i].getStartA(),nearLine2Segment[i].getEndB());


        }

        for (int i = 0; i <crossSegment.length ; i++) {
            System.out.println(nearLine2Segment[i].getEndB()+"=="+nearLine2Segment[i].getStartA());

        }
        System.out.println("----------------------------------------------------------------");
        for (int i = 0; i <crossSegment.length ; i++) {
            System.out.println(farLine2Segment[i].getEndB()+"=="+farLine2Segment[i].getStartA());

        }

        for (int i = 0; i <crossSegment.length ; i++) {

            System.out.println("x:  "+crossSegment[i].getX()+"\ny:  "+crossSegment[i].getY());

        }
        for (int i = 0; i <crossSegment.length ; i++) {
            System.out.println("CrossSegment"+crossSegment[i]);

        }


        creatDistance();
    }
    private void creatDistance(){
        farDistance = new LocalPosition[fPoint.length-1];
        nearDistance = new LocalPosition[cPoint.length-1];
        midlDistance = new LocalPosition[crossSegment.length];


        for (int i = 0; i <farDistance.length -1 ; i++) {
            farDistance[i]= new LocalPosition(fPoint[indexFarPoint[i]].getPoint(),fPoint[indexFarPoint[i+1]].getPoint());

        }
        for (int i = 0; i <nearDistance.length-1 ; i++) {
            nearDistance[i] = new LocalPosition(cPoint[indexNearPoint[i]].getPoint(),cPoint[indexNearPoint[i]].getPoint());

        }

        for (int i = 0; i <midlDistance.length ; i++) {

            midlDistance[i] = new LocalPosition(fPoint[indexFarPoint[i]].getPoint(),crossSegment[i]);

        }
        getAreaTrapezoid();

    }
    private void getAreaTrapezoid(){
        double min;
        double max;
        double maxSizeArea = 0;
        double l;



        Point point1 = new Point(0,0);
        Point point2 = new Point(0,0);
        Point point3 = new Point(0,0);
        Point point4 = new Point(0,0);

        sizeTrapez = new TrapezArea[midlDistance.length];
        LocalPosition[] newFarDistance = new LocalPosition[midlDistance.length];
        for (int i = 0; i < 1 ; i++) {
            for (int j = 0; j <newFarDistance.length ; j++) {
                newFarDistance[j] = new LocalPosition(fPoint[indexFarPoint[i]].getPoint(),fPoint[indexFarPoint[j]].getPoint());

            }

        }
        for (int i = 0; i <midlDistance.length ; i++) {
            for (int j = 0; j <midlDistance.length ; j++) {
                if (midlDistance[i].getDistance()<midlDistance[j].getDistance()){
                    min = midlDistance[i].getDistance();
                    max = midlDistance[j].getDistance();
                    oldPoint = new Point(crossSegment[i].getX(),crossSegment[i].getY());
                    l=Math.round((min/max)*1000)/1000;
                    newPoint = new Point(fPoint[indexFarPoint[j]].getPoint().getX()+(crossSegment[j].getX()-fPoint[indexFarPoint[j]].getPoint().getX())*l,
                                        fPoint[indexFarPoint[j]].getPoint().getY()+(crossSegment[j].getY()-fPoint[indexFarPoint[j]].getPoint().getY())*l);


                    newNearDistance = new LocalPosition(oldPoint,newPoint);
                    sizeTrapez[j] = new TrapezArea(newFarDistance[j].getDistance(),newNearDistance,min);
                    if (maxSizeArea<sizeTrapez[j].getTrapArea()){
                        maxSizeArea = sizeTrapez[j].getTrapArea();
                        point1 = new Point(fPoint[indexFarPoint[i]].getPoint().getX(),fPoint[indexFarPoint[i]].getPoint().getY());
                        point2 = new Point(fPoint[indexFarPoint[j]].getPoint().getX(),fPoint[indexFarPoint[j]].getPoint().getY());
                        point3 = new Point(oldPoint.getX(),oldPoint.getY());
                        point4 = new Point(newPoint.getX(),newPoint.getY());
                    }

                } else {
                    min = midlDistance[j].getDistance();
                    max = midlDistance[i].getDistance();
                    oldPoint = new Point(crossSegment[j].getX(),crossSegment[j].getY());
                    l=(min/max);
                    l=Math.round(l*1000);
                    l/=1000;
                    newPoint = new Point(fPoint[indexFarPoint[i]].getPoint().getX()+(crossSegment[i].getX()-fPoint[indexFarPoint[i]].getPoint().getX())*l,
                            fPoint[indexFarPoint[i]].getPoint().getY()+(crossSegment[i].getY()-fPoint[indexFarPoint[i]].getPoint().getY())*l);
                    newNearDistance = new LocalPosition(newPoint,oldPoint);
                    sizeTrapez[j] = new TrapezArea(newFarDistance[j].getDistance(),newNearDistance,min);
                    if (maxSizeArea<sizeTrapez[j].getTrapArea()){
                        maxSizeArea = sizeTrapez[j].getTrapArea();
                        point1 = new Point(fPoint[indexFarPoint[i]].getPoint().getX(),fPoint[indexFarPoint[i]].getPoint().getY());
                        point2 = new Point(fPoint[indexFarPoint[j]].getPoint().getX(),fPoint[indexFarPoint[j]].getPoint().getY());
                        point3 = new Point(oldPoint.getX(),oldPoint.getY());
                        point4 = new Point(newPoint.getX(),newPoint.getY());
                    }
                }

            }
            maxAreaTrapez = new TrapezArea(point1,point2,point3,point4,maxSizeArea);
        }
        show();



    }


    void show() {
        System.out.println("Points Area:"+"\nPoint1:"+maxAreaTrapez.getPoint1().getX()+"  "+maxAreaTrapez.getPoint1().getY()+"\nPoint2:"+maxAreaTrapez.getPoint2().getX()+"  "+maxAreaTrapez.getPoint2().getY()+
                "\nPoint3:"+maxAreaTrapez.getPoint3().getX()+"  "+maxAreaTrapez.getPoint3().getY()+"\nPoint4:"+maxAreaTrapez.getPoint4().getX()+"  "+maxAreaTrapez.getPoint4().getY());
        System.out.println("New Area:"+maxAreaTrapez.getMax()+"\n");



    }


}
