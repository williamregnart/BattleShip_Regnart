package regnart.william;

import java.util.ArrayList;

public class Ship {
	
	private CoupleCoord coord=new CoupleCoord(); //Couple coords (start and end) of the ship
	private String position; //vertical or horizontal
	private int length;
	private ArrayList<String> allcoords=new ArrayList<String>(); //all coords of the ship
	
	public Ship(String startCoord, String endCoord) {
		
		coord=new CoupleCoord(startCoord,endCoord); //set couple coords of the ship
		
		if(coord.getStartCoord().getCharCoord()==coord.getEndCoord().getCharCoord()) { // if char of start and end coord are equals, vertical position
			position="vertical";
		}
		else { //else, horizontal position
			position="horizontal";
		}
		
		if (position=="horizontal") {
			this.length=(coord.getEndCoord().getCharCoord()-coord.getStartCoord().getCharCoord()+1); //set length of the ship
		}
		else {
			this.length=(coord.getEndCoord().getNumberCoord()-coord.getStartCoord().getNumberCoord()+1);
		}
		setAllCoords(); //set all coords of the ship
	}

	public ArrayList<String> getAllcoords() {
		return allcoords;
	}
	
	public void setAllCoords() {
		if(position=="vertical") {
			for(int i=0;i<length;i++) { //add in the list all coords of the ship
				allcoords.add(coord.getStartCoord().getCharCoord()+String.valueOf(coord.getStartCoord().getNumberCoord()+i));
				//coord add is composed of charStartCoord and numberStartCoord + int between 0 and length of ship
			}
		}
		else {
			for(char i=coord.getStartCoord().getCharCoord();i<=coord.getEndCoord().getCharCoord();i++){
				allcoords.add(i+String.valueOf(coord.getStartCoord().getNumberCoord()));
				//coord add is composed of charCoord which is between charStartCoord and charEndCoord and numberStartCoord
			}
		}
	}
	
	
	public int getLength() {
		return length;
	}
	
	public CoupleCoord getCoupleCoord() {
		return coord;
	}
	
	public boolean isHit(String missileCoord) {
		boolean answer=allcoords.contains(missileCoord); //ship is hit if coord of missile is in coords of it.
		if(answer){
			allcoords.remove(missileCoord); //if ship is hit, remove coord concerned
		}
		return answer;
	}
	
	public boolean isDestroyed() {
		return allcoords.isEmpty(); // ship is destroyed if it list of coords is empty (all hit)
	}


	
}
