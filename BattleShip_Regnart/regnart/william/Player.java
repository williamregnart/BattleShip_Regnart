package regnart.william;

import java.util.ArrayList;
import java.util.Collections;


public class Player {
	private int nbship; //number of ship player still have
	private Ship carrier;
	private Ship battleship;
	private Ship cruiser;
	private Ship submarine;
	private Ship destroyer;
	private ArrayList<String> shots= new ArrayList<String>(); //list of shots realized by the player
	private ArrayList<String> gridships= new ArrayList<String>();  //list of coords of his ship

	public Player(){
		this.nbship=5; //at the beginning, player has 5 ships
	}
	
	public Player(Ship carrier, Ship battleship, Ship cruiser, Ship submarine, Ship destroyer) {
		this.carrier = carrier;
		this.battleship = battleship;
		this.cruiser = cruiser;
		this.submarine = submarine;
		this.destroyer = destroyer;
		this.nbship=5;
	}

	public int getNbship(){
		return nbship;
	}
	
	public void setNbship(int nbship){
		this.nbship=nbship;
	}
	
	public Ship getCarrier(){
		return carrier;
	}
	
	public void setCarrier(Ship carrier){
		this.carrier=carrier;
	}
	
	public Ship getBattleship(){
		return battleship;
	}
	
	public void setBattleship(Ship battleship){
		this.battleship=battleship;
	}
	
	public Ship getCruiser(){
		return cruiser;
	}
	
	public void setCruiser(Ship cruiser){
		this.cruiser=cruiser;
	}
	
	public Ship getSubmarine(){
		return submarine;
	}
	
	public void setSubmarine(Ship submarine){
		this.submarine=submarine;
	}

	public Ship getDestroyer(){
		return destroyer;
	}
	
	public void setDestroyer(Ship destroyer){
		this.destroyer=destroyer;
	}

	public ArrayList<String> getShots() {
		return shots;
	}

	public ArrayList<String> getGridships() {
		return gridships;
	}
	
	public Ship hitShip(String missileCoord) {//return the ship which is hit by missileCoord, return null if no ship is hit
		if(carrier.isHit(missileCoord)) {
			return carrier;
		}
		if(cruiser.isHit(missileCoord)) {
			return cruiser;
		}
		if(battleship.isHit(missileCoord)) {
			return battleship;
		}
		if(submarine.isHit(missileCoord)) {
			return submarine;
		}
		if(destroyer.isHit(missileCoord)) {
			return destroyer;
		}
		else {
			return null;
		}
	}

	public void printGridShips(int height,int length,Player adv) { //print grid of player
		System.out.println(height+","+length);
		Collections.sort(gridships); //organize gridships list
		String s;
		int caract;
		int number;
		char[][] matrice=new char[height][length];
		for(int i=0;i<gridships.size();i++) { //for all coords of gridships
			s=gridships.get(i); //s contains actual coord
			caract=s.charAt(0)-65; //caract contains char of actual coord
			number=Integer.parseInt(s.substring(1,s.length()))-1; //number contains number of actual coord
			matrice[number][caract]='o'; //put a 'o' where the coord ship is located
		}
		for(int i=0;i<adv.getShots().size();i++) { //to print all shots of the enemy
			s=adv.getShots().get(i);
			if(s.charAt(0)=='$') { //$ means that the shot was successful
				caract=s.charAt(1)-65;
				number=Integer.parseInt(s.substring(2,s.length()))-1;
				matrice[number][caract]='x'; //put a 'x' on the coord
			}
			else { //no $, shot was miss
				caract=s.charAt(0)-65;
				number=Integer.parseInt(s.substring(1,s.length()))-1;
				matrice[number][caract]='~'; //put a '~' on the coord
			}
		}
		System.out.print("   ");
		for(int i=0;i<length;i++) {
			System.out.print((char)('A'+i)+" ");
		}
		System.out.println();
		for(int i=0;i<height;i++){
			
			//to print correctly the grid
			if(i+1<10) {
				System.out.print(" "+(i+1)+" ");
			}
			else {
				System.out.print((i+1)+" ");
			}
			//print all cells of grid
			for(int k=0;k<length;k++){
				System.out.print(matrice[i][k]+" ");
			}
			System.out.println("");
		}
	}
	
	public void addShips(Ship ship) { //add coords of a ship in gridships
		for(int i=0;i<ship.getAllcoords().size();i++){
			gridships.add(ship.getAllcoords().get(i));
		}
	}
	
	public boolean checkGrid(CoupleCoord coord) { //check if coords for a ship are not used for another ship int the grid 
	
		if(coord.getStartCoord().getCharCoord()==coord.getEndCoord().getCharCoord()) { //vertical position
			for(int i=coord.getStartCoord().getNumberCoord()-1;i<=coord.getEndCoord().getNumberCoord()+1;i++) { //for coords of future ship
				if(gridships.contains(coord.getStartCoord().getCharCoord()+String.valueOf(i))) { //if coord is in ships coords of player
					return false;
				}
			}
		}
		else { //horizontal position
			for(char i=(char) (coord.getStartCoord().getCharCoord()-1);i<=(char)(coord.getEndCoord().getCharCoord()+1);i++) {
				if(gridships.contains(String.valueOf(i)+(coord.getStartCoord().getNumberCoord()))) {
					return false;
				}
			}
		}
		return true;
	}

	public void printShots(int length,int height) { //print shots grid of player
		Collections.sort(shots);
		String s;
		int caract;
		int number;
		char[][] matrice=new char[height][length];
		for(int i=0;i<shots.size();i++) {
			s=shots.get(i);
			if(s.charAt(0)=='$') {
				caract=s.charAt(1)-65;
				number=Integer.parseInt(s.substring(2,s.length()))-1;
				matrice[number][caract]='x';
			}
			else {
				caract=s.charAt(0)-65;
				number=Integer.parseInt(s.substring(1,s.length()))-1;
				matrice[number][caract]='~';
			}
		}
		System.out.print("   ");
		for(int i=0;i<length;i++) {
			System.out.print((char)('A'+i)+" ");
		}
		System.out.println();
		for(int i=0;i<height;i++){
			if(i+1<10) {
				System.out.print(" "+(i+1)+" ");
			}
			else {
				System.out.print((i+1)+" ");
			}
			for(int k=0;k<length;k++){
				System.out.print(matrice[i][k]+" ");
			}
			System.out.println("");
		}
	}

	public void addShot(String shot) { //add missileCoord in shots list
		shots.add(shot);
	}
}
	