package regnart.william;

import java.util.Random;
import java.util.Scanner;

public class Coord {
	private String coord;
	private char charCoord;
	private int numberCoord;
	
	public Coord() {}
	
	public Coord(char charCoord, int numberCoord) {
		this.charCoord=charCoord;
		this.numberCoord=numberCoord;
		this.coord=String.valueOf(charCoord)+String.valueOf(numberCoord); //coord is the charCoord+numberCoord
	}
	
	public Coord(String coord) {
		this.coord=coord;
		charCoord=coord.charAt(0); //charCoord is the first character of coord
		numberCoord=Integer.parseInt(coord.substring(1,coord.length())); //numberCoord is the number part of coord
	}
	
	public char getCharCoord() {
		return charCoord;
	}
	public int getNumberCoord() {
		return numberCoord;
	}

	public String getCoord() {
		return coord;
	}

	public boolean checkCoord(int height,int length) { //check if the coords are correct
		
		if (charCoord<'A') { //if the coord's letter is before 'A', wrong coord
			return false;
		}
		if (charCoord>'A'+length-1) {// after 
			return false;
		}
		if (numberCoord<1) {// if the coord's number is before 1, wrong coord
			return false;
		}
		if (numberCoord>length){ //after last line
			return false;
		}
		return true;
	}
	
	public String inputCoord(Scanner sc,int height,int length) {//ask user to put a correct coord and return it
		boolean enternewcoord=true;
		coord=sc.nextLine();
		if(coord.length()<2) { //if coords have less than 2 characters, wrong coord
			enternewcoord=true;
		}
		else {
			charCoord=coord.charAt(0);
			if(coord.substring(1,coord.length()).matches("[0-9]+")) { //if numberCoord is a number
				numberCoord=Integer.parseInt(coord.substring(1,coord.length())); //set numberCoord
				enternewcoord=!checkCoord(height,length); //check the coord
			}
		}
		while (enternewcoord) { //While wrong coord
			System.out.println("Wrong Coord");
			System.out.println("Try again :");
			coord = sc.nextLine(); //enter a new coord
			if(coord.length()<2) { //if coord has less characters than needed, start again
				enternewcoord=true;
			}
			else {
			charCoord=coord.charAt(0);
				if(coord.substring(1,coord.length()).matches("[0-9]+")) { //if number part of coord can be a number
					numberCoord=Integer.parseInt(coord.substring(1,coord.length())); //set numberCoord
					enternewcoord=!checkCoord(height,length); //check if the coord is on grid
				}
			}
		}
		return coord;
	}

	public void setCoord(char charCoord,int numberCoord) {
		coord=String.valueOf(charCoord)+String.valueOf(numberCoord);
	}
	
	public void setCoord(String coord) {
		this.coord = coord;
	}

	public void setCharCoord(char charCoord) {
		this.charCoord = charCoord;
	}

	public void setNumberCoord(int numberCoord) {
		this.numberCoord = numberCoord;
	}
	
	public void setMissileCoordRand(int length,int height) { //choose randomly a missileCoord
		Random rand = new Random();
		charCoord = (char)(rand.nextInt(length) + 65);//select a char Coord between 'A' and 'J' (if length=10)
		numberCoord=rand.nextInt(height) + 1; //select a number Coord between 1 and 10 (if height=10)
		coord=String.valueOf(charCoord)+String.valueOf(numberCoord); //set coord
	}

	// AI method which choose missileCoord to destroy a ship already hit
	public String setMissileShipEngaged(Player IA,boolean twoHits,String position,String direction,Coord firstHit,Coord lastHit,int height,int length) {
		boolean shipEngaged=true; //to be return, see if the ship attack has to end
		
		if(position.equals("horizontal")&&(twoHits)) { //if supposed position of ship is horizontal and has been shot twice
			numberCoord=firstHit.getNumberCoord(); //missile numberCoord is numberCoord of firstHit of the ship (horizontal)
			
			if(direction.equals("left")) {//if supposed direction for hit ship is left
				
				if(!lastHit.getCoord().equals("A0")) { // if the last Hit hit the ship
					
					charCoord=(char)(lastHit.getCharCoord()-1); //missileCoord is set at left of lastHit
					setCoord(charCoord,numberCoord);
					if(!checkCoord(height,length)) { // if new missileCoord is wrong
						direction="right"; // new direction is right because there's nothing on left
						lastHit.setCharCoord(firstHit.getCharCoord());
						lastHit.setNumberCoord(firstHit.getNumberCoord());
						lastHit.setCoord(firstHit.getCoord()); // set lastHit as firstHit value
					}
				}
				else { // if the last Hit didn't hit the ship
					
					direction="right"; //new direction is right because there's nothing on left
					lastHit.setCharCoord(firstHit.getCharCoord());
					lastHit.setNumberCoord(firstHit.getNumberCoord());
					lastHit.setCoord(firstHit.getCoord()); // set lastHit as firstHit value
				}
			}
			
			if(direction.equals("right")) { //if supposed direction for hit ship is left
				
				if(!lastHit.getCoord().equals("A0")) { // if the last Hit hit the ship
					
					charCoord=(char)(lastHit.getCharCoord()+1); //missileCoord is set at right of lastHit
					setCoord(charCoord,numberCoord);
					if(!checkCoord(height,length)) { // if new missileCoord is wrong
						position="vertical"; // new position is vertical because there's nothing on horizontal
						direction="up"; //direction begin with up
						lastHit.setCharCoord(firstHit.getCharCoord());
						lastHit.setNumberCoord(firstHit.getNumberCoord());
						lastHit.setCoord(firstHit.getCoord()); // set lastHit as firstHit value
						numberCoord=firstHit.getNumberCoord();
						charCoord=firstHit.getCharCoord();
						setCoord(charCoord,numberCoord); // set missileCoord as firstHit value
					}
				}
				else { // if the last Hit didn't hit the ship
					lastHit.setCharCoord(firstHit.getCharCoord());
					lastHit.setNumberCoord(firstHit.getNumberCoord());
					lastHit.setCoord(firstHit.getCoord()); // set lastHit as firstHit value
					numberCoord=firstHit.getNumberCoord();
					charCoord=firstHit.getCharCoord();
					setCoord(charCoord,numberCoord); // set missileCoord as firstHit value
					position="vertical"; // new position is vertical because there's nothing on horizontal
					direction="up";
				}
			}
		}
		if(position.equals("vertical")&&(twoHits)) { //same for vertical position
			charCoord=firstHit.getCharCoord();
			if(direction.equals("up")) {
				if(!lastHit.getCoord().equals("A0")) {
					numberCoord=lastHit.getNumberCoord()-1;
					setCoord(charCoord,numberCoord);
					if(!checkCoord(height,length)) {
						lastHit.setCharCoord(firstHit.getCharCoord());
						lastHit.setNumberCoord(firstHit.getNumberCoord());
						lastHit.setCoord(firstHit.getCoord());
						direction="down";
					}
				}
				else {
					lastHit.setCharCoord(firstHit.getCharCoord());
					lastHit.setNumberCoord(firstHit.getNumberCoord());
					lastHit.setCoord(firstHit.getCoord());
					direction="down";
				}
			}
			if(direction.equals("down")) {
				if(!lastHit.getCoord().equals("A0")) {
					numberCoord=lastHit.getNumberCoord()+1;
					setCoord(charCoord,numberCoord);
				}
				else {
					shipEngaged=false; //all coords have been check,end of hunting, missileCoord are randomly chosen
					setMissileCoordRand(length,height);
				}
			}
		}
		if(!twoHits) { // if the ship have been hit just once, no supposed position
			
			charCoord=(char)(firstHit.getCharCoord()-1);
			numberCoord=firstHit.getNumberCoord();
			position="horizontal";
			direction="left";
			setCoord(charCoord,numberCoord); // begin by horizontal left, missileCoord is at left of first hit
			if(IA.getShots().contains(coord)||IA.getShots().contains('$'+coord)||(!checkCoord(height,length))) { //if coord doesn't exist or is already in shots
				charCoord=(char)(firstHit.getCharCoord()+1);
				setCoord(charCoord,numberCoord);
				position="horizontal";
				direction="right"; // begin by horizontal left, missileCoord is at left of first hit
			}
			if(IA.getShots().contains(coord)||IA.getShots().contains('$'+coord)||(!checkCoord(height,length))) {
				charCoord=firstHit.getCharCoord();
				numberCoord=firstHit.getNumberCoord()-1;
				setCoord(charCoord,numberCoord);
				position="vertical";
				direction="up";  // begin by vertical up, missileCoord is above first hit
			}
			if(IA.getShots().contains(coord)||IA.getShots().contains('$'+coord)||(!checkCoord(height,length))) {
				numberCoord=firstHit.getNumberCoord()+1;
				position="vertical";
				setCoord(charCoord,numberCoord);
				direction="down"; // begin by vertical down, missileCoord is below first hit
			}
		}
		return position+" "+direction+" "+shipEngaged;
	}
}

