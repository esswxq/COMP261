
public class Restriction {
	
	int startNodeID;
	int endNodeID;
	int nodeID;
	int startRoadID;
	int endRoadID;
	
	public Restriction(int fromNodeID,int toNodeID,int nodeID, int fromRoadID, int toRoadID) {
		this.startNodeID = fromNodeID;
		this.endNodeID = toNodeID;
		this.nodeID = nodeID;
		this.startRoadID = fromRoadID;
		this.endRoadID = toRoadID;		
	}
	
	public Restriction(String line) {
		String[] l = line.split("\t");
		this.startNodeID = Integer.parseInt(l[0]);
		this.startRoadID = Integer.parseInt(l[1]);
		this.nodeID = Integer.parseInt(l[2]);
		this.endRoadID = Integer.parseInt(l[3]);
		this.endNodeID = Integer.parseInt(l[4]);
	}

	
	public int getStartNodeID() {
		return this.startNodeID;
	}
	
	public int getEndNodeID() {
		return endNodeID;
	}
	
	public int getNodeID() {
		return nodeID;
	}

	public int getStartRoadID() {
		return startRoadID;
	}

	public int getEndRoadID() {
		return endRoadID;
	}

	public void setStartNodeID(int fromNodeID) {
		this.startNodeID = fromNodeID;
	}

	public void setEndNodeID(int toNodeID) {
		this.endNodeID = toNodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public void setStartRoadID(int fromRoadID) {
		this.startRoadID = fromRoadID;
	}

	public void setEndRoadID(int toRoadID) {
		this.endRoadID = toRoadID;
	}	
}
