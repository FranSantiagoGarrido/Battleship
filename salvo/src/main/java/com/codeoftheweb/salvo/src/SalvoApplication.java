package com.codeoftheweb.salvo.src;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			// PLAYERS

			Player player1 =new Player("j.bauer@ctu.gov","24");
			playerRepository.save(player1);
			Player player2= new Player ( "c.obrian@ctu.gov","42");
			playerRepository.save(player2);
			Player player3= new Player("kim_bauer@gmail.com", "kb");
			playerRepository.save(player3);
			Player player4= new Player("t.almeida@ctu.gob", "mole");
			playerRepository.save(player4);

			//GAMES

			Game game1 = new Game();
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game1);

			Game game2 = new Game(cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game2);

			Game game3 = new Game (cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game3);


			Game game4 = new Game (cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game4);

			Game game5 = new Game (cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game5);

			Game game6 = new Game (cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game6);

			Game game7 = new Game (cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game7);

			Game game8 = new Game (cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 1);
			gameRepository.save(game8);


			// GAMEPLAYERS

			GamePlayer gamePlayer1 = new GamePlayer(player1, game1);
			gamePlayerRepository.save(gamePlayer1);

			GamePlayer gamePlayer2 = new GamePlayer(player2, game1);
			gamePlayerRepository.save(gamePlayer2);

			GamePlayer gamePlayer3 = new GamePlayer(player1, game2);
			gamePlayerRepository.save(gamePlayer3);

			GamePlayer gamePlayer4 = new GamePlayer(player2, game2);
			gamePlayerRepository.save(gamePlayer4);

			GamePlayer gamePlayer5 = new GamePlayer(player2, game3);
			gamePlayerRepository.save(gamePlayer5);

			GamePlayer gamePlayer6 = new GamePlayer(player4, game3);
			gamePlayerRepository.save(gamePlayer6);

			GamePlayer gamePlayer7 = new GamePlayer(player2, game4);
			gamePlayerRepository.save(gamePlayer7);

			GamePlayer gamePlayer8 = new GamePlayer(player1, game4);
			gamePlayerRepository.save(gamePlayer8);

			GamePlayer gamePlayer9 = new GamePlayer(player4, game5);
			gamePlayerRepository.save(gamePlayer9);

			GamePlayer gamePlayer10 = new GamePlayer(player1, game5);
			gamePlayerRepository.save(gamePlayer10);

			GamePlayer gamePlayer11 = new GamePlayer(player3, game6);
			gamePlayerRepository.save(gamePlayer11);

			GamePlayer gamePlayer12 = new GamePlayer(player4, game7);
			gamePlayerRepository.save(gamePlayer12);

			GamePlayer gamePlayer13 = new GamePlayer(player3, game8);
			gamePlayerRepository.save(gamePlayer13);

			GamePlayer gamePlayer14 = new GamePlayer(player4, game8);
			gamePlayerRepository.save(gamePlayer14);

			// SHIPS

			String[] locations = new String[]{"H2","H3","H4"};
			List<String> arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship1 = new Ship(ShipType.Destroyer, gamePlayer1, arrayLocations);
			shipRepository.save(ship1);

			locations = new String[]{"E1", "F1", "G1"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship2 = new Ship(ShipType.Submarine, gamePlayer1, arrayLocations);
			shipRepository.save(ship2);

			locations = new String[]{"B4", "B5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship3 = new Ship(ShipType.Patrolboat, gamePlayer1, arrayLocations);
			shipRepository.save(ship3);

			locations = new String[]{"B5", "C5", "D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship4 = new Ship(ShipType.Destroyer, gamePlayer2, arrayLocations);
			shipRepository.save(ship4);

			locations = new String[]{"F1", "F2"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship5 = new Ship(ShipType.Patrolboat, gamePlayer2, arrayLocations);
			shipRepository.save(ship5);

			locations = new String[]{"B5", "C5", "D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship6 = new Ship(ShipType.Destroyer, gamePlayer3, arrayLocations);
			shipRepository.save(ship6);

			locations = new String[]{"C6","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship7 = new Ship(ShipType.Patrolboat, gamePlayer3, arrayLocations);
			shipRepository.save(ship7);

			locations = new String[]{"A2", "A3", "A4"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship8 = new Ship(ShipType.Submarine, gamePlayer4, arrayLocations);
			shipRepository.save(ship8);

			locations = new String[]{"G6","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship9 = new Ship(ShipType.Patrolboat, gamePlayer4, arrayLocations);
			shipRepository.save(ship9);

			locations = new String[]{"B5","C5","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship10 = new Ship(ShipType.Destroyer, gamePlayer5, arrayLocations);
			shipRepository.save(ship10);

			locations = new String[]{"C6", "C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship11 = new Ship(ShipType.Patrolboat, gamePlayer5, arrayLocations);
			shipRepository.save(ship11);

			locations = new String[]{"A2","A3","A4"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship12 = new Ship(ShipType.Submarine, gamePlayer6, arrayLocations);
			shipRepository.save(ship12);

			locations = new String[]{"G6","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship13 = new Ship(ShipType.Patrolboat, gamePlayer6, arrayLocations);
			shipRepository.save(ship13);

			locations = new String[]{"B5","C5","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship14 = new Ship(ShipType.Destroyer, gamePlayer7, arrayLocations);
			shipRepository.save(ship14);

			locations = new String[]{"C6","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship15 = new Ship(ShipType.Patrolboat, gamePlayer7, arrayLocations);
			shipRepository.save(ship15);

			locations = new String[]{"A2","A3","A4"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship16 = new Ship(ShipType.Submarine, gamePlayer8, arrayLocations);
			shipRepository.save(ship16);

			locations = new String[]{"G6","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship17 = new Ship(ShipType.Patrolboat, gamePlayer8, arrayLocations);
			shipRepository.save(ship17);

			locations = new String[]{"B5","C5","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship18 = new Ship(ShipType.Destroyer, gamePlayer9, arrayLocations);
			shipRepository.save(ship18);

			locations = new String[]{"C6","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship19 = new Ship(ShipType.Patrolboat, gamePlayer9, arrayLocations);
			shipRepository.save(ship19);

			locations = new String[]{"A2","A3","A4"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship20 = new Ship(ShipType.Submarine, gamePlayer10, arrayLocations);
			shipRepository.save(ship20);

			locations = new String[]{"G6","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship21 = new Ship(ShipType.Patrolboat, gamePlayer10, arrayLocations);
			shipRepository.save(ship21);

			locations = new String[]{"B5","C5","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship22 = new Ship(ShipType.Destroyer, gamePlayer11, arrayLocations);
			shipRepository.save(ship22);

			locations = new String[]{"C6","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship23 = new Ship(ShipType.Patrolboat, gamePlayer11, arrayLocations);
			shipRepository.save(ship23);

			locations = new String[]{"B5","C5","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship24 = new Ship(ShipType.Destroyer, gamePlayer13, arrayLocations);
			shipRepository.save(ship24);

			locations = new String[]{"C6","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship25 = new Ship(ShipType.Patrolboat, gamePlayer13, arrayLocations);
			shipRepository.save(ship25);

			locations = new String[]{"A2","A3","A4"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship26 = new Ship(ShipType.Submarine, gamePlayer14, arrayLocations);
			shipRepository.save(ship26);

			locations = new String[]{"G6","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Ship ship27 = new Ship(ShipType.Patrolboat, gamePlayer14, arrayLocations);
			shipRepository.save(ship27);

			//SALVO
			locations = new String[]{"B5","C5","F1"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG1T1P1 = new Salvo(gamePlayer1, 1, arrayLocations);
			salvoRepository.save(salvoG1T1P1);

			locations = new String[]{"B4","B5","B6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG1T1P2 = new Salvo(gamePlayer2, 1, arrayLocations);
			salvoRepository.save(salvoG1T1P2);

			locations = new String[]{"F2","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG1T2P1 = new Salvo(gamePlayer1, 2, arrayLocations);
			salvoRepository.save(salvoG1T2P1);

			locations = new String[]{"E1","H3","A2"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG1T2P2 = new Salvo(gamePlayer2, 2, arrayLocations);
			salvoRepository.save(salvoG1T2P2);

			locations = new String[]{"A2","A4","A6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG2T1P1 = new Salvo(gamePlayer3, 1, arrayLocations);
			salvoRepository.save(salvoG2T1P1);

			locations = new String[]{"B5","D5","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG2T1P2 = new Salvo(gamePlayer4, 1, arrayLocations);
			salvoRepository.save(salvoG2T1P2);

			locations = new String[]{"A3","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG2T2P1 = new Salvo(gamePlayer3, 2, arrayLocations);
			salvoRepository.save(salvoG2T2P1);

			locations = new String[]{"C5","C6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG2T2P2 = new Salvo(gamePlayer4, 2, arrayLocations);
			salvoRepository.save(salvoG2T2P2);

			locations = new String[]{"G6","H6","A4"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG3T1P1 = new Salvo(gamePlayer5, 1, arrayLocations);
			salvoRepository.save(salvoG3T1P1);

			locations = new String[]{"H1","H2","H3"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG3T1P2 = new Salvo(gamePlayer6, 1, arrayLocations);
			salvoRepository.save(salvoG3T1P2);

			locations = new String[]{"A2","A3","D8"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG3T2P1 = new Salvo(gamePlayer5, 2, arrayLocations);
			salvoRepository.save(salvoG3T2P1);

			locations = new String[]{"E1","F2","G3"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG3T2P2 = new Salvo(gamePlayer6, 2, arrayLocations);
			salvoRepository.save(salvoG3T2P2);

			locations = new String[]{"A3","A4","F7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG4T1P1 = new Salvo(gamePlayer7, 1, arrayLocations);
			salvoRepository.save(salvoG4T1P1);

			locations = new String[]{"B5","C6","H1"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG4T1P2 = new Salvo(gamePlayer8, 1, arrayLocations);
			salvoRepository.save(salvoG4T1P2);

			locations = new String[]{"A2","G6","H6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG4T2P1 = new Salvo(gamePlayer7, 2, arrayLocations);
			salvoRepository.save(salvoG4T2P1);

			locations = new String[]{"C5","C7","D5"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG4T2P2 = new Salvo(gamePlayer8, 2, arrayLocations);
			salvoRepository.save(salvoG4T2P2);

			locations = new String[]{"A1","A2","A3"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG5T1P1 = new Salvo(gamePlayer9, 1, arrayLocations);
			salvoRepository.save(salvoG5T1P1);

			locations = new String[]{"B5","B6","C7"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG5T1P2 = new Salvo(gamePlayer10, 1, arrayLocations);
			salvoRepository.save(salvoG5T1P2);

			locations = new String[]{"G6","G7","G8"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG5T2P1 = new Salvo(gamePlayer9, 2, arrayLocations);
			salvoRepository.save(salvoG5T2P1);

			locations = new String[]{"C6","D6","E6"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG5T2P2 = new Salvo(gamePlayer10, 2, arrayLocations);
			salvoRepository.save(salvoG5T2P2);

			locations = new String[]{"H1","H8"};
			arrayLocations = new ArrayList<>(Arrays.asList(locations));
			Salvo salvoG5T3P2 = new Salvo(gamePlayer10, 3, arrayLocations);
			salvoRepository.save(salvoG5T3P2);

			//SCORE

			Score scoreGame1Bauer = new Score(1.0,game1, player1);
			scoreRepository.save(scoreGame1Bauer);

			Score scoreGame1Obrian = new Score(0.0,game1, player2);
			scoreRepository.save(scoreGame1Obrian);

			Score scoreGame2Bauer = new Score(0.5,game2, player1);
			scoreRepository.save(scoreGame2Bauer);

			Score scoreGame2Obrian = new Score(0.5,game2, player2);
			scoreRepository.save(scoreGame2Obrian);

			Score scoreGame3Obrian = new Score(1.0,game3,player2);
			scoreRepository.save(scoreGame3Obrian);

			Score scoreGame3Almeida = new Score(0.0,game3, player4);
			scoreRepository.save(scoreGame3Almeida);

			Score scoreGame4Obrian = new Score(0.5,game4,player2);
			scoreRepository.save(scoreGame4Obrian);

			Score scoreGame4Bauer = new Score(0.5,game4, player1);
			scoreRepository.save(scoreGame4Bauer);

			Score scoreGame5Almeida = new Score(null,game5,player4);
			scoreRepository.save(scoreGame5Almeida);

			Score scoreGame5Bauer = new Score(null,game5, player1);
			scoreRepository.save(scoreGame5Bauer);

			Score scoreGame6Kbauer = new Score(null, game6, player3);
			scoreRepository.save(scoreGame6Kbauer);

			Score scoreGame7Almeida = new Score(null, game7, player4);
			scoreRepository.save(scoreGame7Almeida);

			Score scoreGame8Kbauer = new Score(null, game8, player3);
			scoreRepository.save(scoreGame8Kbauer);

			Score scoreGame8Almeida = new Score(null, game8, player4);
			scoreRepository.save(scoreGame8Almeida);


		};
	};
}
