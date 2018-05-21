package regnart.william;

import java.util.Scanner;


public class Battleship {
	
	public static void resultPrint(Ship shipHit) { //print result of a shot
		if(shipHit==null) { //if no ship is hit
			System.out.println("Miss");
		}
		else {
			System.out.println("Hit");
		}
	}

	//all the turn of a realplayer as first parameter
	public static void levelPerson(Player player,Player Enemy,boolean activePlayer,Coord missileCoord,Scanner sc,Ship shipHit,int height,int length) {
		System.out.println("Player "+activePlayer+", your turn, this is your situation grid : ");
		player.printGridShips(height,length,Enemy); //print grid player to see what the oponent shot and what is is situation
		System.out.println("This is your firing grid : ");
		player.printShots(height,length);// print the enemy grid, what he shot
		System.out.println("Choose your missile coordonates : ");//player put missile coords
		missileCoord.inputCoord(sc,height,length); //player input missileCoord
		shipHit=Enemy.hitShip(missileCoord.getCoord());//return ship hit by the missile (but no print to player), null is no ship hit 
		resultPrint(shipHit);//print result(miss, hit)
		if(shipHit!=null) {//if a ship is hit
			player.addShot("$"+missileCoord.getCoord()); //add the shot as a successful shot ('$'+missileCoord) to player
			if(shipHit.isDestroyed()) {//if ship is destroyed
				System.out.println("Ship destroyed");//print a message
				Enemy.setNbship(Enemy.getNbship()-1);//the J2 number of ship -1
				System.out.println("Staying Ships : "+Enemy.getNbship());
			}
		}
		else {//if nothing hit
			player.addShot(missileCoord.getCoord()); //add the shot (just missileCoord) to player
		}
	}

	//to place all IA ship
	public static void setShipIA(Player IA,Player Enemy,int height,int length) {
		
		CoupleCoord coords=new CoupleCoord();
		int lengthship=5; //length of carrier
		coords.createRandCoords(lengthship,IA,height,length); //create random coords for IA ship
		Ship IACarrier=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord()); //create the ship
		IA.setCarrier(IACarrier);//set the ship to the player
		IA.addShips(IACarrier);//set coords of the ship to the player
		
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
		IA.printGridShips(height,length,Enemy);//print grid of player, to see where it ships are
	}

	//set all ships of a real player
	public static void setShipPerson(Player player,Player Enemy,Scanner sc,int height,int length) {
		CoupleCoord coords=new CoupleCoord();
		String shipName="Carrier";
		coords.createCoordShip(sc,shipName,5,player,height,length); //create coords for ship player with input in function
		Ship playerCarrier=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		player.setCarrier(playerCarrier);
		player.addShips(playerCarrier);
		player.printGridShips(height,length,Enemy);
		
		shipName="BattleShip";
		coords.createCoordShip(sc,shipName,4,player,height,length);
		Ship playerBattleship=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		player.setBattleship(playerBattleship);
		player.addShips(playerBattleship);
		player.printGridShips(height,length,Enemy);
		
		shipName="Cruiser";
		coords.createCoordShip(sc,shipName,3,player,height,length);
		Ship playerCruiser=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		player.setCruiser(playerCruiser);
		player.addShips(playerCruiser);
		player.printGridShips(height,length,Enemy);
		
		shipName="Submarine";
		coords.createCoordShip(sc,shipName,3,player,height,length);
		Ship playerSubmarine=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		player.setSubmarine(playerSubmarine);
		player.addShips(playerSubmarine);
		player.printGridShips(height,length,Enemy);
		
		shipName="Destroyer";
		coords.createCoordShip(sc,shipName,2,player,height,length);
		Ship playerDestroyer=new Ship(coords.getStartCoord().getCoord(),coords.getEndCoord().getCoord());
		player.setDestroyer(playerDestroyer);
		player.addShips(playerDestroyer);
		player.printGridShips(height,length,Enemy);
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		Player player1=new Player(); //creation of player 1
		
		System.out.println("Choose player 2 :\n1 - Real player\n2 - IA level 0\n3 - IA level 1\n4 - IA level 2"); //choice of type of player 2
		String choicePlayer2=sc.nextLine();
		while(!choicePlayer2.equals("1")&&!choicePlayer2.equals("2")&&!choicePlayer2.equals("3")&&!choicePlayer2.equals("4")) { //while choice is not in the list, ask again
			System.out.println("Choose player 2 :\n1 - Real player\n2 - IA level 0\n3 - IA level 1\n4 - IA level 2");
			choicePlayer2=sc.nextLine();
		}
		Player player2=new Player();
		
		int points1=0; //points of player1
		int points2=0; //... player2
		int height=10; //height of the grid
		int length=10; //length of the grid
		
		String askreplay="1"; //to see if the game has to be restart, 1-yes, 2-no
		boolean activePlayer=true;//to see the active player
		while(askreplay.equals("1")) {//while player want to replay
			player1=new Player();
			player2=new Player();
			//SETTING UP SHIPS
			setShipPerson(player1,player2,sc,height,length);
			
			if(choicePlayer2.equals("1")) { //if player 2 is a real person
				setShipPerson(player2,player1,sc,height,length);
			}
			else { //if player 2 is an AI
				setShipIA(player2,player1,height,length);
			}
			
			//INITIALIZATION OF VARIABLES
			boolean stay=true;//to stay in the loop while the game is not finished
			Ship shipHit = null;//to see the current ship hit
			Coord missileCoord=new Coord();
			
			//IA variables
			boolean shipEngaged2=false; //to see if player 2 (if IA) has shot a ship and hunt it
			Coord firstHit2=new Coord(); //to see the coord where it shot at first the current hunt ship
			Coord lastHit2=new Coord(); //to see where it shot last time
			boolean twoHits2=false; //to see if it shot hunt ship more than once
			String positionEngaged2="null"; //to see the supposed position of hunt ship
			String sensEngaged2="null"; //to see the supposed direction of hunt ship
			
			//GAME ON
			while(stay) {
				if(activePlayer) { //if player 1 turn
					levelPerson(player1, player2, activePlayer, missileCoord, sc, shipHit,height,length); //function play
					stay=(player2.getNbship()!=0);//if player2 has no ship, finish
				}
				else { //player 2 turn
					if(choicePlayer2.equals("1")) { //if real player
						levelPerson(player2, player1, activePlayer, missileCoord, sc, shipHit,height,length);
					}
					else { //if AI
						if(shipEngaged2==false) { //if no ship is hunt
							missileCoord.setMissileCoordRand(length,height); //IA choose randomly missileCoord
							if(!choicePlayer2.equals("2")) { //if IA level 1 or up
								// while missileCoord is on shot already done, choose anothe missileCoord
								while(player2.getShots().contains(missileCoord.getCoord())||player2.getShots().contains('$'+missileCoord.getCoord())) {
									missileCoord.setMissileCoordRand(length,height);
								}
							}
						}
						else { //if a ship is hunt
							//positionsens take the supposed position, direction (and boolean for end hunt) of hunt ship missileCoord is determined
							String positionsens=missileCoord.setMissileShipEngaged(player2,twoHits2, positionEngaged2, sensEngaged2, firstHit2, lastHit2,height,length);
							positionEngaged2=positionsens.split(" ")[0]; //position is recupered
							sensEngaged2=positionsens.split(" ")[1]; //direction is recupered
							shipEngaged2=positionsens.split(" ")[2].equals("true"); //false if the hunt is finished
						}
						System.out.println("AI missile : "+missileCoord.getCoord());
						shipHit=player1.hitShip(missileCoord.getCoord());//see which is hit (null if not)
						resultPrint(shipHit);
						if(shipHit!=null) {//if a ship is hit
							if(choicePlayer2.equals("4")) { //if AI level 2 (because only this AI can have ShipEngaged
								if(shipEngaged2) { //if ship was already engaged, it's the second shot on it
									twoHits2=true;
								}
								else { //if first shot on ship
									shipEngaged2=true; //hunt begin, ship engaged
									firstHit2=new Coord(missileCoord.getCoord()); //set first hit with missileCoord
									twoHits2=false; // first shot
								}
								lastHit2=new Coord(missileCoord.getCoord()); //last hit is actual missilecoord
							}
							player2.addShot("$"+missileCoord.getCoord()); //add the successful shot to list of shot of AI
							if(shipHit.isDestroyed()) { //if ship is destroyed
								System.out.println("Ship destroyed");
								player1.setNbship(player1.getNbship()-1); //reduce player 1 number of ship
								if(choicePlayer2.equals("4")) { //if AI level 2
									positionEngaged2="null"; //hunt is finish, set position,direction,shipEngaged,lastHit,firsthit to default values
									sensEngaged2="null";
									shipEngaged2=false;
									lastHit2.setCoord("A0");
									firstHit2.setCoord("A0");
								}
							}
						}
						else { //if no ship hit
							player2.addShot(missileCoord.getCoord()); //add the shot in list of shots
							if(choicePlayer2.equals("4")) { //if AI level 2
								lastHit2.setCharCoord('A');
								lastHit2.setNumberCoord(0);
								lastHit2.setCoord("A0"); //set last Hit to 'A0' (lastshot miss)
							}
						}	
					}
					stay=(player1.getNbship()!=0); //if player 1 has no ship, finish
				}
				activePlayer=!activePlayer; //switch player turn
			}
			//if J1 has no ship, player 2 wins
			if(player1.getNbship()==0) {
				System.out.println("Player "+2+" wins");
				points2++;
			}
			//else, player 1 wins
			else {
				System.out.println("Player "+1+" wins");
				points1++;
			}
			System.out.println("PLayer 1 : "+points1+"\nPlayer 2 : "+points2);
			System.out.println("Replay ?\n1 - Yes\n2 - No");
			askreplay=sc.nextLine();
			while(!askreplay.equals("1")&&!askreplay.equals("2")) { //if choice is not in the list, ask again
				System.out.println("Replay ?\n1 - Yes\n2 - No");
				askreplay=sc.nextLine();
			}
			activePlayer=!activePlayer; //switch players
		}
		sc.close();
	}
}

