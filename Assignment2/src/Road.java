import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Road represents ... a road ... in our graph, which is some metadata and a
 * collection of Segments. We have lots of information about Roads, but don't
 * use much of it.
 * 
 * @author tony
 */
public class Road {
	public final int roadID;
	public final String name, city;
	public final Collection<Segment> components;
	private boolean oneway;
	private int speed;
	private int roadType;
	private boolean noPed;
	private boolean noBike;
	private boolean noCar;
	private List<Segment> segments = new ArrayList<Segment>();//creates a list of the segments

	public Road(int roadID, int type, String label, String city, int oneway, int speed, int roadclass, int notforcar,
			int notforpede, int notforbicy) {
		this.roadID = roadID;
		this.city = city;
		this.name = label;
		this.components = new HashSet<Segment>();
		this.oneway = (oneway == 1);
		this.speed = speed;
		this.roadType = roadclass;
		this.noPed = (notforpede == 1);
		this.noBike = (notforbicy == 1);
		this.noCar = (notforcar == 1);
	}

	public void addSegment(Segment seg) {
		components.add(seg);
	}
	

	
	public int getRoadID() {// Gets the id
		return roadID;
	}
	
	
	public List<Segment> getSegments () {// Gets the segments
		return segments;
	}
	
	
	public String getName() {// Gets the street name
		return name;
	}
	
	
	public String getCity() {// Gets the city name
		return city;
	}

	
	public boolean isOneway() {//checks if it is one way
		return oneway;
	}
	

	public int getSpeed() {	// Gets the speed limit
		return speed;
	}
	

	public int getRoadType() {	// Gets the road class
		return roadType;
	}
	
	
	public boolean getNoPedestrians() { // checks if pedestrians are allowed
		return noPed;
	} 

	public boolean getNoBikes() { // checks if bikes are allowed
		return noBike;
	}

	public boolean getNoCars() {	// checks if cars are allowed
		return noCar;
	}

}

// code for COMP261 assignments