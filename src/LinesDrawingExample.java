/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author spouki
 */
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.util.GeometricShapeFactory;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class LinesDrawingExample {
    // Examine the 10 max euclidean Distance....
    public static  Coordinate max_Cords[]= new Coordinate[11];
    public static Coordinate cluster1[]= new Coordinate[11];
    public static  Coordinate cluster2[]= new Coordinate[11];
    public static double RADIUS;





    public LinesDrawingExample() {

    }

    public static void main(String[] args) {
        GeometryFactory gf = new GeometryFactory();
            WKTReader reader = new WKTReader( gf );
        LineSegment Initial_spline= new LineSegment();
 
            Polygon polygon = null;
        try {
            polygon = (Polygon) reader.read("POLYGON((36.1669769 31.7406044,36.4300308 31.7505264,36.6392555 31.8296928,36.8306084 32.0524635,37.0234947 32.2759132,37.3088379 32.5035515,37.5987282 32.7320328,37.8002281 32.9577255,37.9132576 33.1798706,38.0284538 33.4034386,38.4224854 33.6386986,38.3695602 33.7101974,38.3046074 33.9281311,38.1468925 34.1422958,38.1765404 34.3652611,38.0428886 34.2864647,37.8306313 34.0565376,37.5284805 33.8237953,37.1782112 33.663517,36.9752655 33.4358368,36.8642769 33.2123451,36.7550011 32.9895477,36.7360535 32.7708092,36.5418129 32.5467186,36.2066345 32.3889465,35.9424438 32.3790169,35.6459236 32.2962189,35.4170189 32.3594551,35.15662 32.3498497,34.929081 32.4140091,34.734417 32.551384,34.7462006 32.7693901,34.6060257 32.8371887,34.3460922 32.8277779,34.1193466 32.891861,34.0449791 33.1077538,33.8512955 33.2466278,33.561306 33.1637535,33.3792191 32.9385719,33.3703041 32.7201195,33.4468117 32.5052605,33.5238113 32.2910309,33.6865349 32.0804024,33.9112282 32.0168533,34.074337 31.8070564,34.2983322 31.7430992,34.6177483 31.8986626,34.8757706 31.9080429,35.1347122 31.9175167,35.4269562 31.9996872,35.7213211 32.0827942,35.9493713 32.0191231,36.1455193 31.8830185,36.1669769 31.7406044,36.1669769 31.7406044))");    Polygon mbr = (Polygon) reader.read("POLYGON((-6.43308878 33.3948708,-3.03338695 33.3548965,-3.26816607 39.1416397,-6.93436289 39.1892776,-6.43308878 33.3948708,-6.43308878 33.3948708))");
    System.out.print("Our Filament POLYGON Looks like: \n"+polygon.toString()+"\n");
            for(int i=0;i<mbr.getNumPoints();i++){

                mbr.getCoordinate().distance(polygon.getCoordinate());
            }

            Double polyArea = polygon.getArea();
            LineSegment ls= new  LineSegment(new Coordinate(0, 0), new Coordinate(2, 2));
            Coordinate cl= new Coordinate(); 

       /*   double least_max_dist= getbestcandidate(polygon);
            RADIUS=least_max_dist;
          Polygon circle= createCircle(polygon.getCentroid().getX(), polygon.getCentroid().getY(), least_max_dist);
          dummyclustering(polygon);
*/
            Initial_spline= bestpaircandidates(polygon);
          //  LineSegment Perpendicular= Initial_spline.project(Perpendicular);

      //    System.out.println("Among the point in the circle are as follow:"+circle.toString());


     }catch (ParseException ex) {
            Logger.getLogger(LinesDrawingExample.class.getName()).log(Level.SEVERE, null, ex);
        }


}
/*
public static double getbestcandidate(Polygon polygon){
    int number=polygon.getNumPoints();
    Coordinate[] cords=polygon.getCoordinates();
  // cords= removeduplicates(cords);
    Point centroid= polygon.getCentroid();
    System.out.println("The centroid of The Polygon is   "+centroid.getX()+"  ,"+centroid.getY());
    int max_distance= -99;
    int max_distance1= -99;
    int max_distance2= -99;
    int max_distance3= -99;
    int max_distance4= -99;
    int max_distance5= -99;
    int max_distance6= -99;
    int max_distance7= -99;
    int max_distance8= -99;
    int max_distance9= -99;
    int max_distance10= -99;


    // initialization...
     max_Cords[0]=new Coordinate(cords[0].x, cords[0].y);
     max_Cords[1]=new Coordinate(cords[0].x, cords[0].y);
     max_Cords[2]=new Coordinate(cords[0].x, cords[0].y);
     max_Cords[3]=new Coordinate(cords[0].x, cords[0].y);
        max_Cords[4]=new Coordinate(cords[0].x, cords[0].y);
        max_Cords[5]=new Coordinate(cords[0].x, cords[0].y);
        max_Cords[6]=new Coordinate(cords[0].x, cords[0].y);
        max_Cords[7]=new Coordinate(cords[0].x, cords[0].y);
    max_Cords[8]=new Coordinate(cords[0].x, cords[0].y);
    max_Cords[9]=new Coordinate(cords[0].x, cords[0].y);
    max_Cords[10]=new Coordinate(cords[0].x, cords[0].y);

   for(int i=0;i<cords.length;i++){
   //    System.out.println(i+". the distance between"+cords[i].x+" and "+cords[i].y+"is the following......"+cords[i].distance(centroid.getCoordinate()));
       if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())>=max_distance){
          // System.out.println("HERE YOU GO FIRST "+cords[i].x+" The y "+cords[i].y);
           max_distance=(int)cords[i].distance(centroid.getCoordinate());
               max_Cords[10]=max_Cords[9];
               max_Cords[9]=max_Cords[8];
               max_Cords[8]=max_Cords[7];
                max_Cords[7]=max_Cords[6];
                max_Cords[6]=max_Cords[5];
                max_Cords[5]=max_Cords[4];
                max_Cords[4]=max_Cords[3];
                max_Cords[3]=max_Cords[2];
                max_Cords[2]=max_Cords[1];
                max_Cords[1]=max_Cords[0];
                max_Cords[0]=new Coordinate(cords[i].x, cords[i].y);
                 }

       else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance && cords[i].distance(centroid.getCoordinate())>=max_distance1&& cords[i].x!=max_Cords[0].x && cords[i].y!=max_Cords[0].y){
           max_distance1=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=max_Cords[7];
           max_Cords[7]=max_Cords[6];
           max_Cords[6]=max_Cords[5];
           max_Cords[5]=max_Cords[4];
           max_Cords[4]=max_Cords[3];
           max_Cords[3]=max_Cords[2];
           max_Cords[2]=max_Cords[1];
           max_Cords[1]=new Coordinate(cords[i].x, cords[i].y);
       }
       else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance1 &&cords[i].distance(centroid.getCoordinate())>=max_distance2 && (cords[i].x!=max_Cords[1].x && cords[i].y!=max_Cords[1].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance2=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=max_Cords[7];
           max_Cords[7]=max_Cords[6];
           max_Cords[6]=max_Cords[5];
           max_Cords[5]=max_Cords[4];
           max_Cords[4]=max_Cords[3];
           max_Cords[3]=max_Cords[2];
           max_Cords[2]=new Coordinate(cords[i].x, cords[i].y);
       }
       else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance2 &&cords[i].distance(centroid.getCoordinate())>=max_distance3 && (cords[i].x!=max_Cords[2].x && cords[i].y!=max_Cords[2].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance3=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=max_Cords[7];
           max_Cords[7]=max_Cords[6];
           max_Cords[6]=max_Cords[5];
           max_Cords[5]=max_Cords[4];
           max_Cords[4]=max_Cords[3];
           max_Cords[3]=new Coordinate(cords[i].x, cords[i].y);
       }
       else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance3 &&cords[i].distance(centroid.getCoordinate())>=max_distance4&& (cords[i].x!=max_Cords[3].x && cords[i].y!=max_Cords[3].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance4=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=max_Cords[7];
           max_Cords[7]=max_Cords[6];
           max_Cords[6]=max_Cords[5];
           max_Cords[5]=max_Cords[4];
           max_Cords[4]=new Coordinate(cords[i].x, cords[i].y);
       }
       else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance4 &&cords[i].distance(centroid.getCoordinate())>=max_distance5&& (cords[i].x!=max_Cords[4].x && cords[i].y!=max_Cords[4].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance5=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=max_Cords[7];
           max_Cords[7]=max_Cords[6];
           max_Cords[6]=max_Cords[5];
           max_Cords[5]=new Coordinate(cords[i].x, cords[i].y);
       } else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance5 &&cords[i].distance(centroid.getCoordinate())>=max_distance6 && (cords[i].x!=max_Cords[5].x && cords[i].y!=max_Cords[5].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance6=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=max_Cords[7];
           max_Cords[7]=max_Cords[6];
           max_Cords[6]=new Coordinate(cords[i].x, cords[i].y);
       } else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance6 &&cords[i].distance(centroid.getCoordinate())>=max_distance7&& (cords[i].x!=max_Cords[6].x && cords[i].y!=max_Cords[6].y)){
        //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
        max_distance7=(int)cords[i].distance(centroid.getCoordinate());
        max_Cords[10]=max_Cords[9];
        max_Cords[9]=max_Cords[8];
        max_Cords[8]=max_Cords[7];
        max_Cords[7]=new Coordinate(cords[i].x, cords[i].y);
       } else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance7 &&cords[i].distance(centroid.getCoordinate())>=max_distance8&& (cords[i].x!=max_Cords[7].x && cords[i].y!=max_Cords[7].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance8=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=max_Cords[8];
           max_Cords[8]=new Coordinate(cords[i].x, cords[i].y);
       }else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance8 &&cords[i].distance(centroid.getCoordinate())>=max_distance9&& (cords[i].x!=max_Cords[8].x && cords[i].y!=max_Cords[8].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance9=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=max_Cords[9];
           max_Cords[9]=new Coordinate(cords[i].x, cords[i].y);
       }else if(cords[i].z!=-999 &&cords[i].distance(centroid.getCoordinate())<max_distance9 &&cords[i].distance(centroid.getCoordinate())>=max_distance10&& (cords[i].x!=max_Cords[9].x && cords[i].y!=max_Cords[9].y)){
           //    System.out.println("second: The x"+cords[i].x+" The y "+cords[i].y);
           max_distance10=(int)cords[i].distance(centroid.getCoordinate());
           max_Cords[10]=new Coordinate(cords[i].x, cords[i].y);
       }

            }
Coordinate co = new Coordinate(-4,37);
 //   System.out.println("The max distance is "+max_distance);
  //  System.out.println("The max distance 2 is "+max_Cords[0].toString()+"and"+max_Cords[1].toString());
    System.out.print(max_Cords[0].x+" "+max_Cords[0].y+",");
   // System.out.println("The second max distance is "+max_distance1);
    System.out.print(max_Cords[1].x+" "+max_Cords[1].y+",");
   // System.out.println("The third max distance is "+max_distance2);
    System.out.print(max_Cords[2].x+" "+max_Cords[2].y+",");
   // System.out.println("The fourth max distance is "+max_distance3);
    System.out.print(max_Cords[3].x+" "+max_Cords[3].y+",");
   // System.out.println("The fifth max distance is "+max_distance4);
    System.out.print(max_Cords[4].x+" "+max_Cords[4].y+",");
   // System.out.println("The sixth max distance is "+max_distance5);
    System.out.print(max_Cords[5].x+" "+max_Cords[5].y+",");
   // System.out.println("The seventh max distance is "+max_distance6);
    System.out.print(max_Cords[6].x+" "+max_Cords[6].y+",");
    //System.out.println("The eighth max distance is "+max_distance7);
    System.out.print(max_Cords[7].x+" "+max_Cords[7].y+",");
    System.out.print(max_Cords[8].x+" "+max_Cords[8].y+",");
    System.out.print(max_Cords[9].x+" "+max_Cords[9].y+",");
    System.out.println(max_Cords[10].x+" "+max_Cords[10].y);


    max_distance10=(int)max_Cords[5].distance(centroid.getCoordinate());
  //  System.out.println("DISTANCE 10    "+max_distance);
    return max_distance10;
                }

*/
 public static Polygon createCircle(double x, double y, final double RADIUS) {
 GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
 shapeFactory.setNumPoints(64);
 shapeFactory.setCentre(new Coordinate(x, y));
 shapeFactory.setSize(RADIUS * 2);
 Polygon circle = shapeFactory.createCircle();
 circle.setSRID(4326);

   //  System.out.println("The circle is>> "+circle.toString());
 return circle;
 }

    public static void dummyclustering(Polygon polygon){
        int c1=0,c2=0;
        for(int i=0;i<max_Cords.length;i++){
            if(max_Cords[i].x< polygon.getCentroid().getX()){
            cluster1[c1++]=max_Cords[i];
            }
            else cluster2[c2++]=max_Cords[i];
        }
    }

    static LineSegment bestpaircandidates(Polygon pol) {
        LineSegment initial = new LineSegment();
        Coordinate endpoint1 = null, endpoint2 = null;
        Coordinate[] cords= pol.getCoordinates();
        Coordinate centroid_c= pol.getCentroid().getCoordinate();
        for(int i=0;i<cords.length;i++){

            cords[i].z=cords[i].distance(centroid_c);
         //   System.out.println("The x: "+cords[i].x+"The y : "+cords[i].y+"The distance: "+cords[i].z);
                    }


         Arrays.sort(cords, new distanceComparator());
        // CLEAN DUPLICATE.....
        System.out.println("Lets clean duplicates, they are very annoying guven that we can pass the 3 same max points to he 2-Means alg..\n");
        cords=removeduplicates(cords);
        // Sort AGAIn
        System.out.println("What I am doing heere is sorting the list of points belonging to the polygon based on the max euclidean dist from centroid");
        System.out.println("The two first entries in the tuples represents the x, y coordinate of the point the 3rd entry is the distance");
        System.out.println("\n Sorted LIST>>>");
        Arrays.sort(cords, new distanceComparator());

        for(int i=0;i<20;i++){
            System.out.println("{"+cords[i].x+", "+cords[i].y+" z: "+cords[i].z+"},");
        }
        KMeans clusteringmethod = new KMeans();
        System.out.print("After getting the 10 top rows of the previous list that represents the 10 maxs, It is time to apply the 2-means algorithm>>>>>");
        clusteringmethod.run(cords);

        double max1=-999999;
        // find endpoint, max distance from endpoint to the centroid...
        for(int i=0;i<clusteringmethod.cluster1.length;i++){
            if(clusteringmethod.cluster1[i].x!=-99 && clusteringmethod.cluster1[i].distance(pol.getCentroid().getCoordinate())>max1){
                    max1=clusteringmethod.cluster1[i].distance(pol.getCentroid().getCoordinate());
                    endpoint1=clusteringmethod.cluster1[i];
            }
        //    System.out.println( "The max distance is"+max1 );

        }
        double max2=-999999;
        // find endpoint, max distance from endpoint to the centroid...
        for(int i=0;i<clusteringmethod.cluster2.length;i++){
            if(clusteringmethod.cluster2[i].x!=-99 && clusteringmethod.cluster2[i].distance(pol.getCentroid().getCoordinate())>max2){
                max2=clusteringmethod.cluster2[i].distance(pol.getCentroid().getCoordinate());
                endpoint2=clusteringmethod.cluster2[i];
            }
    //        System.out.println( "The second max distance is"+max2 );

        }

        System.out.println( "To find the endpoints of the spline, we calculate the max distance \n" +
                "between points of each cluster and the centroid of the cluster..\n" );

        System.out.println( "The first endpoint is as follow"+"\n x: "+endpoint1.x+"  y: "+endpoint1.y );
        System.out.println( "The second endpoint is as follow"+"\n x: "+endpoint2.x+"  y: "+endpoint2.y );

        System.out.println("You can find 2 ouput pics of 2 polygons that I tested, they look awesome.. refer o the legend for color coding");
        System.out.println("Thats it for now.... \n The next step is to best model the spline line within the polygon\n  ");
       return initial;
    }

    public static Queue Initialize(Queue queue1, Coordinate[] cluster1){
        for (int i = 0; i < cluster1.length; i ++){
            if (cluster1[i] != null)
                queue1.add(cluster1[i]);

        }
        return queue1;
    }

    public static int getsize(Object[] obj){
        int size=0;
        for (int i = 0; i < obj.length; i ++)
            if (obj[i] != null)
                size ++;
        return size;
    }

    public static Coordinate[] removeduplicates( Coordinate[] cords){
        for(int i=1;i<cords.length;i++){
                if(cords[i].x==cords[i-1].x && cords[i].y==cords[i-1].y) cords[i].z=-99;
        }
     return cords;
    }

}
