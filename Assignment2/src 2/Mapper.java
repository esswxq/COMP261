import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * This is the main class for the mapping program. It extends the GUI abstract
 * class and implements all the methods necessary, as well as having a main
 * function.
 * 
 * @author tony
 */
public class Mapper extends GUI {
	public static final Color NODE_COLOUR = new Color(77, 113, 255);
	public static final Color SEGMENT_COLOUR = new Color(130, 130, 130);
	public static final Color HIGHLIGHT_COLOUR = new Color(255, 219, 77);

	// these two constants define the size of the node squares at different zoom
	// levels; the equation used is node size = NODE_INTERCEPT + NODE_GRADIENT *
	// log(scale)
	public static final int NODE_INTERCEPT = 1;
	public static final double NODE_GRADIENT = 0.8;

	// defines how much you move per button press, and is dependent on scale.
	public static final double MOVE_AMOUNT = 100;
	// defines how much you zoom in/out per button press, and the maximum and
	// minimum zoom levels.
	public static final double ZOOM_FACTOR = 1.3;
	public static final double MIN_ZOOM = 1, MAX_ZOOM = 200;

	// how far away from a node you can click before it isn't counted.
	public static final double MAX_CLICKED_DISTANCE = 0.15;

	// these two define the 'view' of the program, ie. where you're looking and
	// how zoomed in you are.
	private Location origin;
	private double scale;

	// our data structures.
	private Graph graph;
	private Trie trie;
	
	//1.initialize the open list
	Stack <Node> openList = new Stack<Node>();
	//2.initialize the closed list
	List<Node> closedList = new ArrayList<Node>();
	//h value
	//oneLocation.distance(Location other);
	// two selected nodes
	List<Node> selectedNode = new ArrayList<Node>();
	
	//for A* search
	Node currentNode;
	Node startingPoint;
	Node finishPoint;
	int currentNodeSize = 10;
	ArrayList<Node> currentNodes;
	List<Segment> PathSegments;
	private boolean pathFind = false;
	
	private List<Node> findPath(Node startNode, Node endNode) {
		for(Node n:graph.nodes.values()) {
			n.father = null;
		}
		double g ;
		double f;
		//  initialize the closed list
		Set<Node> closed = new HashSet<Node>();
		
		//initialize the fringe
		Queue<Astar> fringe = new PriorityQueue<Astar>((Astar a,Astar b)->a.compareTo(b));
		
		Astar start = new Astar(startNode, null, 0, startNode.getLocation().distance(endNode.getLocation()));
		//put the starting node in the fringe
		fringe.add(start);		
		
		//3. while the fringe list if it is not empty
		while (fringe.size() > 0) { 
			//a.find the node with the least f on the fringe, search node
			Astar searchNode = fringe.poll(); // b.dequeue the highest node, and process it if is not been visited		
			//set the node's father node as previous node  
			searchNode.node.father = searchNode.getPreNode();
			
			//if the node is not visited 
			if(!closed.contains(searchNode.getNode())) {
				//set the node visited
				closed.add(searchNode.getNode());
				
				// if the node equals goal return the node
				if (searchNode.getNode().nodeID == endNode.nodeID) {
					List<Node> pathNode = new ArrayList<Node>();
					pathNode.add(endNode);
					while(endNode.nodeID != startNode.nodeID) {
						pathNode.add(endNode.getFather());
						endNode = endNode.getFather();			
					}
					pathFind = true;
					return pathNode;    //return the nodes
				}			
				//generate search node's neighbors and set their parents to search node
				
				
				Map<Node, Segment> neighbours = searchNode.getNode().getAdjacentNodes(); 				
				for(Node node: neighbours.keySet()) {
					openList.add(node);
					if(!closed.contains(node)) {						
						g = searchNode.g + neighbours.get(node).length;
						f = g + node.getLocation().distance(endNode.getLocation());
						fringe.add(new Astar(node,searchNode.node,g,f));
					}
				}
			}
		}
		pathFind = false;
		return null;
	}
	
	@Override
	protected void redraw(Graphics g) {
		if (graph != null)
			graph.draw(g, getDrawingAreaDimension(), origin, scale);
	}

	List<Segment> segments = new ArrayList<Segment>();
	List<Node> nodes = new ArrayList<Node>();
	@Override
	protected void onClick(MouseEvent e) {
		Location clicked = Location.newFromPoint(e.getPoint(), origin, scale);
		// find the closest node.
		double bestDist = Double.MAX_VALUE;
		Node closest = null;

		for (Node node : graph.nodes.values()) {
			double distance = clicked.distance(node.location);
			if (distance < bestDist) {
				bestDist = distance;
				closest = node;
			}
		}

		// if it's close enough, highlight it and show some information.
			if (clicked.distance(closest.location) < MAX_CLICKED_DISTANCE) {
				if(selectedNode.size()<2) {
					selectedNode.add(closest);
					doClick();
					//getTextOutputArea().setText(closest.toString());
					
				}
				else {								
					selectedNode.clear();
					segments.clear();
					nodes.clear();
				}
			}
	}
	
	public void doClick() {
		if(selectedNode.size() == 2) {
			nodes = findPath(selectedNode.get(0),selectedNode.get(1));
			if(nodes != null){
				graph.setHighlightNode(nodes);					
				for(Node n : nodes) {
					for(Segment segment : n.getSegments()) {
						if((segment.getStartNode() == n) && (segment.getEndNode() == n.father)||
						   ((segment.getEndNode() == n) && (segment.getStartNode() == n.father))){
							segments.add(segment);
						}
					}
				}
				graph.setHighlightSeg(segments);
				List<String> roads = new ArrayList<String>();
				for(Segment segment : segments) {
					DecimalFormat df = new DecimalFormat("0.000");
					roads.add(segment.getRoad().getName() + " - segment length: " + df.format(segment.getLength()));				
				}
				getTextOutputArea().setText(roads.toString());
				pathFind  = false;
			}
		}
		else{
			
			graph.setHighlightNode(selectedNode);	
		}
	}

	@Override
	protected void onSearch() {
		if (trie == null)
			return;

		// get the search query and run it through the trie.
		String query = getSearchBox().getText();
		Collection<Road> selected = trie.get(query);

		// figure out if any of our selected roads exactly matches the search
		// query. if so, as per the specification, we should only highlight
		// exact matches. there may be (and are) many exact matches, however, so
		// we have to do this carefully.
		boolean exactMatch = false;
		for (Road road : selected)
			if (road.name.equals(query))
				exactMatch = true;

		// make a set of all the roads that match exactly, and make this our new
		// selected set.
		if (exactMatch) {
			Collection<Road> exactMatches = new HashSet<>();
			for (Road road : selected)
				if (road.name.equals(query))
					exactMatches.add(road);
			selected = exactMatches;
		}

		// set the highlighted roads.
		graph.setHighlight(selected);

		// now build the string for display. we filter out duplicates by putting
		// it through a set first, and then combine it.
		Collection<String> names = new HashSet<>();
		for (Road road : selected)
			names.add(road.name);
		String str = "";
		for (String name : names)
			str += name + "; ";

		if (str.length() != 0)
			str = str.substring(0, str.length() - 2);
		getTextOutputArea().setText(str);
	}

	@Override
	protected void onMove(Move m) {
		if (m == GUI.Move.NORTH) {
			origin = origin.moveBy(0, MOVE_AMOUNT / scale);
		} else if (m == GUI.Move.SOUTH) {
			origin = origin.moveBy(0, -MOVE_AMOUNT / scale);
		} else if (m == GUI.Move.EAST) {
			origin = origin.moveBy(MOVE_AMOUNT / scale, 0);
		} else if (m == GUI.Move.WEST) {
			origin = origin.moveBy(-MOVE_AMOUNT / scale, 0);
		} else if (m == GUI.Move.ZOOM_IN) {
			if (scale < MAX_ZOOM) {
				// yes, this does allow you to go slightly over/under the
				// max/min scale, but it means that we always zoom exactly to
				// the centre.
				scaleOrigin(true);
				scale *= ZOOM_FACTOR;
			}
		} else if (m == GUI.Move.ZOOM_OUT) {
			if (scale > MIN_ZOOM) {
				scaleOrigin(false);
				scale /= ZOOM_FACTOR;
			}
		}
	}

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons, File restrictions) {
		graph = new Graph(nodes, roads, segments, polygons, restrictions);
		trie = new Trie(graph.roads.values());
		origin = new Location(-250, 250); // close enough
		scale = 1;
	}

	/**
	 * This method does the nasty logic of making sure we always zoom into/out of
	 * the centre of the screen. It assumes that scale has just been updated to be
	 * either scale * ZOOM_FACTOR (zooming in) or scale / ZOOM_FACTOR (zooming out).
	 * The passed boolean should correspond to this, ie. be true if the scale was
	 * just increased.
	 */
	private void scaleOrigin(boolean zoomIn) {
		Dimension area = getDrawingAreaDimension();
		double zoom = zoomIn ? 1 / ZOOM_FACTOR : ZOOM_FACTOR;

		int dx = (int) ((area.width - (area.width * zoom)) / 2);
		int dy = (int) ((area.height - (area.height * zoom)) / 2);

		origin = Location.newFromPoint(new Point(dx, dy), origin, scale);
	}
	

	public static void main(String[] args) {
		new Mapper();
	}
}
