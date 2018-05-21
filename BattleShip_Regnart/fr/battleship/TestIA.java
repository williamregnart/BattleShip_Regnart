//all code is not commented because it was commented in Battleship.java

package fr.battleship;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import regnart.william.Coord;
import regnart.william.CoupleCoord;
import regnart.william.Player;
import regnart.william.Ship;

public class TestIA {

	public static void setShipIA(Player IA,int height,int length) {
		CoupleCoord coords=new CoupleCoord();
		int lengthship=5;
		coords.createRandCoords(lengthship,IA,height,length);
		Ship IACarrier=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		IA.setCarrier(IACarrier);
		IA.addShips(IACarrier);
		
		lengthship=4;
		coords.createRandCoords(lengthship,IA,height,length);
		Ship IABattleShip=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		IA.setBattleship(IABattleShip);
		IA.addShips(IABattleShip);
		
		lengthship=3;
		coords.createRandCoords(lengthship,IA,height,length);
		Ship IACruiser=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		IA.setCruiser(IACruiser);
		IA.addShips(IACruiser);
		
		lengthship=3;
		coords.createRandCoords(lengthship,IA,height,length);
		Ship IASubmarine=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		IA.setSubmarine(IASubmarine);
		IA.addShips(IASubmarine);
		
		lengthship=2;
		coords.createRandCoords(lengthship,IA,height,length);
		Ship IADestroyer=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		IA.setDestroyer(IADestroyer);
		IA.addShips(IADestroyer);
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		PrintWriter fileiaproof=new PrintWriter (new File("ai_proof.csv")); //to open file "ai_proof.csv" and write on it
		StringBuilder sb=new StringBuilder(); //what will be write on file
		sb.append("AI Name;Score 1;AI Name 2;Score 2\n");//complete what will be write
		
		//CHOICE OF BATTLESHIP PLAYERS
		int choicePlayer1=1; // IA level 0
		int choicePlayer2=2; // IA level 1
		
		Player player1=new Player();
		Player player2=new Player();
		int points1;
		int points2;
		int height=10;
		int length=10;
		boolean activePlayer=true;//to see the active player
		
		while(choicePlayer1!=3) {//to do all tests
			points1=0;
			points2=0;
			System.out.println("100 games between IA level "+(choicePlayer1-1)+" and IA level "+(choicePlayer2-1));
			for(int i=0;i<100;i++) { //100 games
				player1=new Player();
				player2=new Player();
				//SETTING UP SHIPS
				
				setShipIA(player1,height,length);
				
				setShipIA(player2,height,length);
				
				//INITIALIZATION OF VARIABLES
				boolean stay=true;//to stay in the loop while the game is not finished
				Ship shipHit = null;//to see the current ship hit
				Coord missileCoord=new Coord();
				boolean shipEngaged1=false;
				Coord firstHit1=new Coord();
				Coord lastHit1=new Coord();
				boolean twoHits1=false;
				String positionEngaged1="null";
				String sensEngaged1="null";
				boolean shipEngaged2=false;
				Coord firstHit2=new Coord();
				Coord lastHit2=new Coord();
				boolean twoHits2=false;
				String positionEngaged2="null";
				String sensEngaged2="null";
				
				//GAME ON
				while(stay) {
					if(activePlayer) {
						if(shipEngaged1==false) {
							missileCoord.setMissileCoordRand(length,height);
							if(choicePlayer1!=1) {
								while(player1.getShots().contains(missileCoord.getCoord())||player1.getShots().contains('$'+missileCoord.getCoord())) {
									missileCoord.setMissileCoordRand(length,height);
								}
							}
						}
						else {
							String positionsens=missileCoord.setMissileShipEngaged(player1,twoHits1, positionEngaged1, sensEngaged1, firstHit1, lastHit1,height,length);
							positionEngaged1=positionsens.split(" ")[0];
							sensEngaged1=positionsens.split(" ")[1];
							shipEngaged1=positionsens.split(" ")[2].equals("true");
						}
						shipHit=player2.hitShip(missileCoord.getCoord());
						if(shipHit!=null) {
							if(choicePlayer1==3) {
								if(shipEngaged1) {
									twoHits1=true;
								}
								else {
									shipEngaged1=true;
									firstHit1=new Coord(missileCoord.getCoord());
									twoHits1=false;
								}
								lastHit1=new Coord(missileCoord.getCoord());
							}
							player1.addShot("$"+missileCoord.getCoord());
							if(shipHit.isDestroyed()) {
								player2.setNbship(player2.getNbship()-1);
								if(choicePlayer1==3) {
									positionEngaged1="null";
									sensEngaged1="null";
									shipEngaged1=false;
									lastHit1.setCoord("A0");
									firstHit1.setCoord("A0");
								}
							}
						}
						else {
							player1.addShot(missileCoord.getCoord());
							if(choicePlayer1==3) {
								lastHit1.setCharCoord('A');
								lastHit1.setNumberCoord(0);
								lastHit1.setCoord("A0");
							}
						}	
						stay=(player2.getNbship()!=0);
					}
					
					
					else {
						if(shipEngaged2==false) {
							missileCoord.setMissileCoordRand(length,height);
							if(choicePlayer2!=1) {
								while(player2.getShots().contains(missileCoord.getCoord())||player2.getShots().contains('$'+missileCoord.getCoord())) {
									missileCoord.setMissileCoordRand(length,height);
								}
							}
						}
						else {
							String positionsens=missileCoord.setMissileShipEngaged(player2,twoHits2, positionEngaged2, sensEngaged2, firstHit2, lastHit2,height,length);
							positionEngaged2=positionsens.split(" ")[0];
							sensEngaged2=positionsens.split(" ")[1];
							shipEngaged2=positionsens.split(" ")[2].equals("true");
						}
						shipHit=player1.hitShip(missileCoord.getCoord());
						if(shipHit!=null) { // if a ship is hit
							if(choicePlayer2==3) { // if IA level 2
								if(shipEngaged2) {
									twoHits2=true;
								}
								else {
									shipEngaged2=true;
									firstHit2=new Coord(missileCoord.getCoord());
									twoHits2=false;
								}
								lastHit2=new Coord(missileCoord.getCoord());
							}
							player2.addShot("$"+missileCoord.getCoord());
							if(shipHit.isDestroyed()) {
								player1.setNbship(player1.getNbship()-1);
								if(choicePlayer2==3) {
									positionEngaged2="null";
									sensEngaged2="null";
									shipEngaged2=false;
									lastHit2.setCoord("A0");
									firstHit2.setCoord("A0");
								}
							}
						}
						else {
							player2.addShot(missileCoord.getCoord());
							if(choicePlayer2==3) {
								lastHit2.setCharCoord('A');
								lastHit2.setNumberCoord(0);
								lastHit2.setCoord("A0");
							}
						}
						stay=(player1.getNbship()!=0); // if player1 has no ship, end of the game
					}
					activePlayer=!activePlayer; // player adverse turn
				}
				//if player 1 has no ship, player 2 wins
				if(player1.getNbship()==0) {
					points2++;
				}
				//else, player 1 wins
				else {
					points1++;
				}
				activePlayer=!activePlayer; // switch starting AI
			}

			sb.append("AI Level "+(choicePlayer1-1)+';'+points1+';'+"Level "+(choicePlayer2-1)+';'+points2+'\n'); // save game score in sb
			System.out.println(sb);
			if(choicePlayer2!=3) { // while second IA is not the harder
				choicePlayer2+=1; // this IA is level up (make test IA level 0 vs level 1 and level 0 vs level 2)
			}
			else { // if second IA is the harder
				choicePlayer1+=1; // first IA is level up (make test IA level 1 vs level 2)
			}
		}
		fileiaproof.write(sb.toString()); // writing data in file iaproof
		fileiaproof.close(); // closing file iaproof
	}
}

