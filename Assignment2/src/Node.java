import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Node represents an intersection in the road graph. It stores its ID and its
 * location, as well as all the segments that it connects to. It knows how to
 * draw itself, and has an informative toString method.
 * 
 * @author tony
 */
public class Node {

	public final int nodeID;
	public final Location location;
	public final List<Segment> segments = new ArrayList<Segment>();
	public double g;
	public double h;
	public double f;
	public Node father;
	private Segment outgoingSeg;
	private List<Segment> incomingSegments = new ArrayList<Segment>();
	private List<Segment> outgoingSegments = new ArrayList<Segment>();

	public Node(int nodeID, double lat, double lon) {
		this.nodeID = nodeID;
		this.location = Location.newFromLatLon(lat, lon);
		//this.segments = new HashSet<Segment>();	
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setFatherNode(Node father) {
		this.father =  father;
	}
	public Node getFather() {
		return father;
	}
	
	public boolean hasFather() {
		if(father != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public double getNodeDistance(Node other) {
		List<Segment> segmentsOut = new ArrayList<Segment>();
		for(Segment segment : segmentsOut) {
			if(segment.getEndNode() == other);
				return segment.getLength();
		}
		return 0;
	}

	public Node(int ID, Location location) {
		this.location = location;
		this.nodeID = ID;
	}

	// Gets the ID
	public int getNodeID(){
		return nodeID;
	}

	// Adds the segments
	public void addSegment(Segment segment){
		segments.add(segment);
	}

	// Gets the Segments
	public List<Segment> getSegments(){
		return segments;
	}
	// Add a segment in
	public void addSegmentIn(Segment segment){
		incomingSegments.add(segment);
	}
		
	// Add a segment out
	public void addSegmentOut(Segment segment){
		outgoingSegments.add(segment);
	}
	// Get Segments in
	public List<Segment> getSegmentsIn(){
		return incomingSegments;
	}
		
	// Get Segments out
	public List<Segment> getSegmentsOut(){
		return outgoingSegments;
	}

	
	// get adjacent nodes of selected node
	// return a map with selected node as a key, connected segments as value
	public Map<Node, Segment> getAdjacentNodes(){
		Map<Node,Segment> adjacent = new HashMap<Node, Segment>();
		for (Segment Segment : segments) {
				if (Segment.getEndNode().getNodeID() != this.getNodeID()) { 		//check end side
					adjacent.put(Segment.getEndNode(), Segment);
				} else if (Segment.getStartNode().getNodeID() != this.getNodeID()) { 	//check start side
					adjacent.put(Segment.getStartNode(), Segment);
				}
		}
		return adjacent;
	}
		
	
	
	public void draw(Graphics g, Dimension area, Location origin, double scale) {
		Point p = location.asPoint(origin, scale);

		// for efficiency, don't render nodes that are off-screen.
		if (p.x < 0 || p.x > area.width || p.y < 0 || p.y > area.height)
			return;

		int size = (int) (Mapper.NODE_GRADIENT * Math.log(scale) + Mapper.NODE_INTERCEPT);
		g.fillRect(p.x - size / 2, p.y - size / 2, size, size);
	}

	public String toString() {
		Set<String> edges = new HashSet<String>();
		for (Segment s : segments) {
			if (!edges.contains(s.road.name))
				edges.add(s.road.name);
		}

		String str = "ID: " + nodeID + "  loc: " + location + "\nroads: ";
		for (String e : edges) {
			str += e + ", ";
		}
		return str.substring(0, str.length() - 2);
	}
}

// code for COMP261 assignments