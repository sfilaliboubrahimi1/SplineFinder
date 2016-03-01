
import com.vividsolutions.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

public class KMeans
{
    private static final int NUM_CLUSTERS = 2;    // Total clusters.
    private static final int TOTAL_DATA = 10;      // Total data points.
    public static  Coordinate[] cluster1= new Coordinate[11],
     cluster2= new Coordinate[11];

    private static ArrayList<Data> dataSet = new ArrayList<Data>();
    public static ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    Coordinate distances[]= new Coordinate[45];// array that stores the distance between all the points

    private static void initialize(Coordinate[] cords)
    {
        Coordinate distances[]= new Coordinate[45];// array that stores the distance between all the points

//        distances[0].x=0;
      // Find distance between all the max point
      // Find distance between all the max point
        for(int k=0;k<45;k++){
          for(int position=0;position<10;position++){
              int past= position;
            for(int element=position+1;element<10;element++){
                distances[k++]=new Coordinate(position, element ,cords[position].distance(cords[element]));
                }
         //     element=position+1;
            }
        }
        // sort the list to find the two most distant maxes and choose them as the center of the initial 2-mean clusters
    //    System.out.println("Sorted LIST>>>");
        Arrays.sort(distances, new distanceComparator());
        // Visualize the array....
  /*      for(int i=0;i<45;i++){
            System.out.println(distances[i].x+"         "+distances[i].y+"       "+distances[i].z);
        }
      */
        double centroid1_x= cords[(int)distances[0].x].x;
        double centroid1_y=cords[(int)distances[0].x].y;
        double centroid2_x=cords[(int)distances[0].y].x;
        double centroid2_y=cords[(int)distances[0].y].y;

            System.out.println("\n \n To find the best initial Centroids of the tw clusters," +
                    "\n I chose the 2 points from the 10 maxs that have the largest distance from each other; which are :");
            centroids.add(new Centroid(centroid1_x, centroid1_y)); // lowest set.
            centroids.add(new Centroid(centroid2_x , centroid1_y)); // highest set.
            System.out.println("     (" + centroids.get(0).X() + ", " + centroids.get(0).Y() + ")");
            System.out.println("     (" + centroids.get(1).X() + ", " + centroids.get(1).Y() + ")");
            System.out.print("\n");
            return;
       // }
    }

    private static void kMeanCluster(Coordinate[] cords)
    {
        final double bigNumber = Math.pow(10, 10);    // some big number that's sure to be larger than our data range.
        double minimum = bigNumber;                   // The minimum value to beat.
        double distance = 0.0;                        // The current minimum value.
        int sampleNumber = 0;
        int cluster = 0;
        boolean isStillMoving = true;
        Data newData = null;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while(dataSet.size() < TOTAL_DATA)
        {
            newData = new Data(cords[sampleNumber].x, cords[sampleNumber].y);
            dataSet.add(newData);
            minimum = bigNumber;
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                distance = dist(newData, centroids.get(i));
                if(distance < minimum){
                    minimum = distance;
                    cluster = i;
                }
            }
            newData.cluster(cluster);

            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            sampleNumber++;
        }

        // Now, keep shifting centroids until equilibrium occurs.
        while(isStillMoving)
        {
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                int totalX = 0;
                int totalY = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++)
                {
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }

            // Assign all data to the new centroids
            isStillMoving = false;

            for(int i = 0; i < dataSet.size(); i++)
            {
                Data tempData = dataSet.get(i);
                minimum = bigNumber;
                for(int j = 0; j < NUM_CLUSTERS; j++)
                {
                    distance = dist(tempData, centroids.get(j));
                    if(distance < minimum){
                        minimum = distance;
                        cluster = j;
                    }
                }
                tempData.cluster(cluster);
                if(tempData.cluster() != cluster){
                    tempData.cluster(cluster);
                    isStillMoving = true;
                }
            }
        }
        return;
    }

    /**
     * // Calculate Euclidean distance.
     * @param d - Data object.
     * @param c - Centroid object.
     * @return - double value.
     */
    private static double dist(Data d, Centroid c)
    {
        return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2));
    }

    private static class Data
    {
        private double mX = 0;
        private double mY = 0;
        private int mCluster = 0;

        public Data()
        {
            return;
        }

        public Data(double x, double y)
        {
            this.X(x);
            this.Y(y);
            return;
        }

        public void X(double x)
        {
            this.mX = x;
            return;
        }

        public double X()
        {
            return this.mX;
        }

        public void Y(double y)
        {
            this.mY = y;
            return;
        }

        public double Y()
        {
            return this.mY;
        }

        public void cluster(int clusterNumber)
        {
            this.mCluster = clusterNumber;
            return;
        }

        public int cluster()
        {
            return this.mCluster;
        }
    }

    private static class Centroid
    {
        private double mX = 0.0;
        private double mY = 0.0;

        public Centroid()
        {
            return;
        }

        public Centroid(double newX, double newY)
        {
            this.mX = newX;
            this.mY = newY;
            return;
        }

        public void X(double newX)
        {
            this.mX = newX;
            return;
        }

        public double X()
        {
            return this.mX;
        }

        public void Y(double newY)
        {
            this.mY = newY;
            return;
        }

        public double Y()
        {
            return this.mY;
        }
    }

    public static void run(Coordinate[] cords)
    {
/*
        Coordinate cords[]= new Coordinate[11];
        cords[0]=new Coordinate(-4.48838806, 34.1505241);
        cords[1]=new Coordinate(-4.27822495, 34.2272224);
        cords[2]=new Coordinate(-4.49262524, 34.2295685);
        cords[3]=new Coordinate(-4.21851587, 34.4633865);
        cords[4]=new Coordinate(-4.57735395, 34.4673233);
        cords[5]=new Coordinate(-4.08722496, 34.6996994);
        cords[6]=new Coordinate(-4.59056091, 34.7051544);
        cords[7]=new Coordinate(-4.02687216, 34.9376717);
        cords[8]=new Coordinate(-5.97019005, 37.7350159);
        cords[9]=new Coordinate(-5.61426592, 37.9808311);
*/
        initialize(cords);
        kMeanCluster(cords);


        Coordinate[] temp_cluster= new Coordinate[11];

      // INITIALIZING CLUSTERSSS
         for(int i = 0; i < cluster1.length; i++)
        {
            cluster1[i]= new Coordinate(-99,-99);
            cluster2[i]= new Coordinate(-99,-99);
        }

        int c1=0,c2=0;
        // Print out clustering results.
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("Cluster " + i + " includes:");
            for(int j = 0; j < TOTAL_DATA; j++)
            {
                if(dataSet.get(j).cluster() == i && i==0){
                    cluster1[c1++]= new Coordinate(dataSet.get(j).X() ,dataSet.get(j).Y());
                    System.out.println("  c1   (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
                }
                else if(dataSet.get(j).cluster() == i && i==1) {
                    cluster2[c2++] = new Coordinate(dataSet.get(j).X(), dataSet.get(j).Y());
                    System.out.println("c2     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
                }
                } // j
            System.out.println();
        } // i
/*System.out.println("Output From K Means Algorithm>>>> These are the points belongins to cluster 1 and cluster 2 respectively");
        for(int i = 0; i < cluster1.length; i++)
        {
            System.out.println("  CL1   ( x:" +  cluster1[i].x + ", y:" + cluster1[i].y);
        }
        for(int i = 0; i < cluster2.length; i++)
        {
            System.out.println("  CL2   ( x:" +  cluster2[i].x + ", y:" + cluster2[i].y);
        }
*/

        // Print out centroid results.
        System.out.println("Centroids finalized at:");
        for(int i = 0; i < NUM_CLUSTERS; i++)
        {
            System.out.println("     (" + centroids.get(i).X() + ", " + centroids.get(i).Y());
        }
        System.out.print("\n");
        return;
    }
}