package regnart.william;

import java.util.Scanner;
import java.util.Random;

public class CoupleCoord {
	private Coord startCoord;
	private Coord endCoord;
	
	public CoupleCoord() {}
	
	public CoupleCoord(String startCoord,String endCoord) {
		this.startCoord=new Coord(startCoord);
		this.endCoord=new Coord(endCoord);
	}
	
	public int checkLengthShip() {//return the length of the ship
		int realLength;
		if (startCoord.getCharCoord()==endCoord.getCharCoord()){//vertical position
			realLength=endCoord.getNumberCoord()-startCoord.getNumberCoord()+1;
		}
		else {//horizontal position
			realLength=endCoord.getCharCoord()-startCoord.getCharCoord()+1;
		}
		return realLength;
	}
	
	public boolean checkHorizVert() {//check if the ship is in horizontal or vertical position, not in diagonal
		return !((startCoord.getCharCoord()!=endCoord.getCharCoord())&&(startCoord.getNumberCoord()!=endCoord.getNumberCoord()));
	}

	public void createCoordShip(Scanner sc,String shipName,int realLengthShip,Player player,int height,int length) {//create coords for a ship
		boolean continuer=true;
		Coord startCoord=new Coord();
		Coord endCoord=new Coord();
		while(continuer) {//while coords are wrong
			System.out.println("Put your "+ shipName+ " (length "+realLengthShip+"), StartCoord : ");//ask to input StartCoord of the ship
			this.startCoord=new Coord(startCoord.inputCoord(sc,height,length));//set the value of startCoord with the coord inputs by user
			System.out.println("EndCoord : ");//ask to input EndCoord of the ship
			this.endCoord=new Coord(endCoord.inputCoord(sc,height,length));
			if((checkHorizVert())) {//if the ship is not in diagonal position
				if(checkLengthShip()==realLengthShip) {//if the length of the ship is the real length it should have
					continuer=!player.checkGrid(this);//if the cases on the grid are occupied, coords will be wrong, else it's ok
				}
			}
		}
	}
	
	public void setStartCoord(Coord startCoord) {
		this.startCoord = startCoord;
	}

	public void setEndCoord(Coord endCoord) {
		this.endCoord = endCoord;
	}

	public Coord getStartCoord() {
		return startCoord;
	}

	public Coord getEndCoord() {
		return endCoord;
	}

	public void setStartCoordRand(int height,int length) { //choose randomly a StartCoord
		Random rand = new Random();
		int numberrand = rand.nextInt(length) + 65;
		int numberStartCoord=rand.nextInt(height) + 1;
		startCoord=new Coord((char)numberrand,numberStartCoord);
	}

	public void setEndCoordRand(int length,String position) { //calculate End Coord with StartCoord, length and position of future ship
		if (position=="vertical") { //vertical
			endCoord=new Coord(startCoord.getCharCoord(),startCoord.getNumberCoord()+length-1);
		}
		else { //horizontal
			endCoord=new Coord((char)(startCoord.getCharCoord()+length-1),startCoord.getNumberCoord());
		}
	}

	public void createRandCoords(int lengthShip,Player IA,int length,int height) { //create random coords for AI
		boolean setIAcoord=false;
		String positionrand;
		Random rand = new Random();
		int choice= rand.nextInt(2) + 1; //random choice for position (1 vertical, 2 horizontal)
		if(choice==1) {
			positionrand="vertical";
		}
		else {
			positionrand="horizontal";
		}
		while(!setIAcoord) { //while coords are wrong or can't be available
			setStartCoordRand(height,length);
			setEndCoordRand(lengthShip,positionrand);
			setIAcoord=endCoord.checkCoord(height,length); //if endCoord is out of grid, start again
			if(setIAcoord) { //if all is ok
				setIAcoord=IA.checkGrid(this); // check if coords are available, if not, start again
			}
		}
	}
	
}