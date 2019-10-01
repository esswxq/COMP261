import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Astar implements Comparable <Astar> {	
	private int nodeID;
	private double lat;
	private double lon;	
	private Node preNode;
	
	Node node;
	double g;
	double h;
	double f;
	
	
	
	
	

	public Astar(Node currentNode,Node preNode,double g,double f) {
		this.node = currentNode;
		this.preNode = preNode;
		this.g = g;
		this.f = f;
	}
	
	public Node getNode() {
		return node;
	}
	
	public Node getPreNode() {
		return preNode;
	}
	
	public double getCurrentCost() {
		return g;
	}
	
	public double getEstimatedCost() {
		return h;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public void SetPreNode(Node preNode) {
		this.preNode = preNode;
	}
	
	public void setCurrentCost(double g) {
		this.g = g;
	}
	
	public void setEstimatedCost(double f) {
		this.f = f;
	}
	
	public int compareTo(Astar node) {
		if(f < node.f) {
			return -1;
		}
		else if (f > node.f) {
			return 1;
		}
		return 0;
	}

	//1.initialize the open list
	Stack <Node> openList = new Stack<Node>();
	//2.initialize the closed list
	List<Node> closedList = new ArrayList<Node>();{
	
	
	
	
	//put the starting node on the open list
	//openList.push(startNode); //set f at 0
	
	//3. while the open list is nor empty
	while(!openList.isEmpty()) {
		//a.find the node with the least f on the open list, q
		
		
		
		
		//b.pop q off the open list
		//openList.pop(q);
		
		
		//generate q's 8 neighbors and set their parents to q
		
		
		//for each neighbor
		for(int i =0; i< openList.size();i++){
			//if neighbor is the goal, stop search neighbor.
			//neighbor.g=q.g+distance between neighbor and q neighbor
			//neighbor.h=distance from goal to neighbor 
			//neighbor.f = neighbor.g + neighbor.h
			
			
			
			
			//if a node with the same  position as neighbor is in the open list
			//which has a lower f than neighbor, skip this neighbor
			
			
			
			
			
			//if a node with the same position as neighbor is in the closed list
			//which has a lower f than neighbor, skip this neighbor 
			//otherwise, ass the node to the open list
			
			
			
			//end for loop
			}	
		//push q on the closed list 
			
			
		
		//end while loop
		
		}
	
	
	
	}
}
