import com.vividsolutions.jts.geom.Coordinate;
import java.util.Comparator;


/**
 * Created by spouki on 28/02/2016.
 */
public class distanceComparator implements Comparator<Coordinate> {


    public int compare(Coordinate p1, Coordinate p2) {
        if (p1.z < p2.z) return 1;
        if (p1.z > p2.z) return -1;
        return 0;
    }

}