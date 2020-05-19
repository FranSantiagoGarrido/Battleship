package com.codeoftheweb.salvo.src;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private ScoreRepository scoreRepository;

	@Autowired
	private GamePlayerRepository gamePlayerRepository;

	@Autowired
	private PlayerRepository playerRepository;

	///GAME

	@RequestMapping(path= "/games", method = RequestMethod.GET)
	///JSON detallado de los juegos
	public Object getGameIds() {
		Map<String, Object> gamesDTO = new LinkedHashMap<>();
		List<Game> games = gameRepository.findAll();
		Player player = this.getAuthenticatedPlayer();

		if (player == null) {
			gamesDTO.put("player","Guest");
			gamesDTO.put("games", games.stream().map(Game::getGameDTO).collect(Collectors.toList()));
		} else {
			gamesDTO.put("player",player.getPlayerDTO());
			gamesDTO.put("games", games.stream().map(Game::getGameDTO).collect(Collectors.toList()));
		}
		return gamesDTO;
	}

	/// GAMES

	/// verifica si el usuario esta autenticado y crea un nuevo juego
	@RequestMapping(path= "/games", method = RequestMethod.POST)
	public Object createGame() {
		Player authenticatedPlayer = this.getAuthenticatedPlayer();
		if (authenticatedPlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_NO_LOGUEADO,
					HttpStatus.UNAUTHORIZED);
		} else {
			Game newGame = new Game();
			gameRepository.save(newGame);
			GamePlayer gamePlayer = new GamePlayer(authenticatedPlayer, newGame);
			gamePlayerRepository.save(gamePlayer);
			return this.createResponseEntity(ResponseEntityMsgs.KEY_GPID,
					gamePlayer.getId(), HttpStatus.CREATED);
		}
	}

	/// SHIPS

	@RequestMapping(path="/games/players/{gamePlayerId}/ships", method = RequestMethod.GET)
	public Object getShips(@PathVariable("gamePlayerId") long gpId) {
		Map<String,Object> playerShips = new LinkedHashMap<>();
		GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);

		Player player = getAuthenticatedPlayer();

		if (gamePlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_JUGADOR_NO_ENCONTRADO,
					HttpStatus.NOT_FOUND);
		}

		if (player == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_NO_LOGUEADO, HttpStatus.UNAUTHORIZED);
		}

		if (player.getId() != gamePlayer.getPlayer().getId()) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_JUGADOR_DISTINTO_AL_LOGUEADO, HttpStatus.UNAUTHORIZED);
		}

		playerShips.put("gpid", gamePlayer.getId());
		playerShips.put("ships", gamePlayer.getGamePlayerShipsDTO());
		gamePlayer.updateGameState();
		return playerShips;
	}

	/// SET SHIPS

	///recibe una lista de ships y los asocia con el gameplayer

	@RequestMapping(path="/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
	public Object setShipsLocations(@PathVariable("gamePlayerId") long gpId, @RequestBody List<Ship> ships) {
		Player authenticatedPlayer = getAuthenticatedPlayer();

		if (authenticatedPlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_NO_LOGUEADO,
					HttpStatus.UNAUTHORIZED);
		}

		GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);
		if (gamePlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_JUGADOR_NO_ENCONTRADO,
					HttpStatus.NOT_FOUND);
		}

		if (authenticatedPlayer.getId() != gamePlayer.getPlayer().getId()) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_JUGADOR_DISTINTO_AL_LOGUEADO, HttpStatus.UNAUTHORIZED);
		}

		///Verifica si el usuario esta logado, el gameplayer id existe y es el correspondiente al usuario logado

		if (gamePlayer.hasNoShips()) {
			gamePlayer.addShips(ships);
			gamePlayer.setGameState(GameState.WAIT);
			gamePlayer.updateGameState();
			gamePlayerRepository.save(gamePlayer);
			return this.createResponseEntity(ResponseEntityMsgs.KEY_SUCCESS, ResponseEntityMsgs.MSG_SHIPS_AGREGADOS,
					HttpStatus.CREATED);
		} else {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_SHIPS_NO_AGREGADOS,
					HttpStatus.FORBIDDEN);
		}
	}

	/// SALVOES

	@RequestMapping(path="/games/players/{gamePlayerId}/salvoes", method = RequestMethod.GET)
	public Object getSalvoes(@PathVariable("gamePlayerId") long gpId) {
		Map<String,Object> playerSalvoes = new LinkedHashMap<>();
		GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);
		Player authenticatedPlayer = getAuthenticatedPlayer();

		if (authenticatedPlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_NO_LOGUEADO,
					HttpStatus.FORBIDDEN);
		}

		if (gamePlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_JUGADOR_NO_ENCONTRADO,
					HttpStatus.UNAUTHORIZED);
		}

		if (authenticatedPlayer.getId() != gamePlayer.getPlayer().getId()) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_JUGADOR_DISTINTO_AL_LOGUEADO, HttpStatus.FORBIDDEN);
		}

		playerSalvoes.put("gpid", gamePlayer.getId());
		playerSalvoes.put("salvoes", gamePlayer.getSalvoesDTO());
		gamePlayer.updateGameState();
		return playerSalvoes;
	}

	///SET SALVOES
    ///recibe una lista de salvos y los asocia con el player

	@RequestMapping(path="/games/players/{gamePlayerId}/salvoes", method = RequestMethod.POST)
	public Object setSalvoes(@PathVariable("gamePlayerId") long gpId, @RequestBody Salvo salvo) {
		Player authenticatedPlayer = getAuthenticatedPlayer();

		if (authenticatedPlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_NO_LOGUEADO,
					HttpStatus.UNAUTHORIZED);
		}

		GamePlayer gamePlayer = gamePlayerRepository.getOne(gpId);
		if (gamePlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_JUGADOR_NO_ENCONTRADO,
					HttpStatus.UNAUTHORIZED);
		}

		if (authenticatedPlayer.getId() != gamePlayer.getPlayer().getId()) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_JUGADOR_DISTINTO_AL_LOGUEADO, HttpStatus.FORBIDDEN);
		}

		///Verifica que el usuario esta logado, el gameplayer id existe y es el correspondiente al usuario logado

		Salvo newSalvo = new Salvo(gamePlayer,gamePlayer.getSalvoes().size()+1, salvo.getSalvoLocations());
		if (this.canPlaceSalvoes(gamePlayer, newSalvo)) {
			gamePlayer.addSalvo(newSalvo);
			gamePlayer.setGameState(GameState.WAIT);
			gamePlayer.updateGameState();
			gamePlayerRepository.save(gamePlayer);
			return this.createResponseEntity(ResponseEntityMsgs.KEY_SUCCESS, ResponseEntityMsgs.MSG_SALVOS_AGREGADOS,
					HttpStatus.CREATED);
		} else {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_SALVOS_YA_AGREGADOS,
					HttpStatus.FORBIDDEN);
		}
	}

	///JOIN

	@RequestMapping(path="/game/{nn}/players", method = RequestMethod.POST)
	public Object joinGame(@PathVariable("nn") Long gameId) {
		Player player = getAuthenticatedPlayer();

		if (player == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_NO_LOGUEADO,
					HttpStatus.UNAUTHORIZED);
		}

		Game game = gameRepository.getOne(gameId);

		if (game == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_JUEGO_NO_ENCONTRADO,
					HttpStatus.FORBIDDEN);
		}

		if (game.countGamePlayers() == 2) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_JUEGO_COMPLETO,
					HttpStatus.FORBIDDEN);
		}
		GamePlayer gamePlayer = new GamePlayer(player, game);
		gamePlayerRepository.save(gamePlayer);
		return this.createResponseEntity(ResponseEntityMsgs.KEY_GPID, gamePlayer.getId(),HttpStatus.CREATED);
	}

	///LEADERBOARD

	@RequestMapping("/leaderBoard")
	public List<Object> getLeaderBoard() {
		List<Player> players = playerRepository.findAll();
		return players.stream().map(Player::getScoreHistoryDTO).collect(Collectors.toList());
	}

	///GAME BY ID

	@RequestMapping("/game_view/{nn}")
	public Object getGameById(@PathVariable("nn") Long gamePlayerId) {
		Player authenticatedPlayer = this.getAuthenticatedPlayer();
		if (authenticatedPlayer == null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR, ResponseEntityMsgs.MSG_NO_LOGUEADO,
					HttpStatus.UNAUTHORIZED);
		}

		long authenticatedPlayerId = authenticatedPlayer.getId();
		GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);

		///partida donde el usuario debe estar autenticado en la aplicacion
		if (gamePlayer.getPlayer().getId() ==  authenticatedPlayerId) {
			gamePlayer.updateGameState();
			//verifico si la partida termino y no se colocaron los scores correspondientes
			if (gamePlayer.gameFinished() && (gamePlayer.getGame().getScores().size() != 2)) {
				this.updateScores(gamePlayer.getGameState(), gamePlayer);
			}
			gamePlayerRepository.save(gamePlayer);
			return this.generateGameView(gamePlayer);
		} else {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_JUGADOR_DISTINTO_AL_LOGUEADO, HttpStatus.UNAUTHORIZED);
		}
	}

	private Map<String,Object> generateGameView(GamePlayer gamePlayer) {
		Map<String,Object> gameViewDTO = new LinkedHashMap<>();
		gameViewDTO.put("id", gamePlayer.getGame().getId());
		gameViewDTO.put("created", gamePlayer.getGame().getCreationDate());
		gameViewDTO.put("gameState", gamePlayer.getGameState());
		gameViewDTO.put("gamePlayers", gamePlayer.getGame().getGamePlayersDTO());
		gameViewDTO.put("ships", gamePlayer.getGamePlayerShipsDTO());
		gameViewDTO.put("salvoes", gamePlayer.getGame().getGameSalvoesDTO());
		if (gamePlayer.getGame().countGamePlayers() != 2) {
			gameViewDTO.put("hits", this.getPlaceHolderDTOForHits());
		} else {
			gameViewDTO.put("hits", this.generateGameHitsDTO(gamePlayer));
		}
		return gameViewDTO;
	}

	private Map<String,Object> getPlaceHolderDTOForHits() {
		Map<String,Object> hitsDTO = new LinkedHashMap<>();
		hitsDTO.put("self", new ArrayList<>());
		hitsDTO.put("opponent",new ArrayList<>());
		return hitsDTO;
	}

	private Map<String,Object> generateGameHitsDTO(GamePlayer gamePlayerOfRequest) {
		Map<String,Object> hitsDTO = new LinkedHashMap<>();
		hitsDTO.put("self",this.getHitsResume(gamePlayerOfRequest.getGpOpponent(),gamePlayerOfRequest));
		hitsDTO.put("opponent",this.getHitsResume(gamePlayerOfRequest,gamePlayerOfRequest.getGpOpponent()));
		return hitsDTO;
	}

	private List<Map<String,Object>> getHitsResume(GamePlayer attacker, GamePlayer receiver) {
		List<Map<String,Object>> processedHits = new LinkedList<>();
		final boolean hideLastSalvo;

		if (attacker.getSalvoes().size() > receiver.getSalvoes().size()) {
			//si un jugador disparo y el otro no, solo devuelvo el resultado cuando el otro acabe
			hideLastSalvo = true;
		} else {
			hideLastSalvo = false;
		}

		//verifica que el  historial de hits en los barcos este actualizado
		attacker.updateHitsTakenIfNeeded();
		receiver.updateHitsTakenIfNeeded();

		attacker.getSalvoes().stream().sorted(Comparator.comparingInt(Salvo::getTurn)).forEach(salvo -> {
			Map<String, Object> processedTurnDTO = new LinkedHashMap<>();
			processedTurnDTO.put("turn", salvo.getTurn());
			if (hideLastSalvo && (salvo.getTurn() == attacker.getSalvoes().size())) {
				processedTurnDTO.put("hitLocations", new ArrayList<>());
				processedTurnDTO.put("damages", new LinkedHashMap<>());
				processedTurnDTO.put("missed", -1);
				processedHits.add(processedTurnDTO);
			} else {
				List<String> hits = new LinkedList<>();
				Map<String,Integer> shipsStatusMap = this.createShipsStatusMap();
				salvo.processSalvoLocations(receiver.getShips(), shipsStatusMap, hits);
				processedTurnDTO.put("hitLocations",hits);
				shipsStatusMap.putAll(receiver.getHitsTakenForTurn(salvo.getTurn()).getHitsOnMyFleet());
				processedTurnDTO.put("damages", shipsStatusMap);
				processedTurnDTO.put("missed", salvo.getSalvoLocations().size() - hits.size());
				processedHits.add(processedTurnDTO);
			}
		});
		return processedHits;
	}

	private Map<String,Integer> createShipsStatusMap() {
		Map<String,Integer> shipsStatusMap = new LinkedHashMap<>();
		shipsStatusMap.put("carrierHits",0);
		shipsStatusMap.put("battleshipHits",0);
		shipsStatusMap.put("submarineHits",0);
		shipsStatusMap.put("destroyerHits",0);
		shipsStatusMap.put("patrolboatHits",0);
		return shipsStatusMap;
	}

	///CREATE PLAYER

	@RequestMapping(path= "/players", method = RequestMethod.POST)
	public ResponseEntity<Object> createPlayer(@RequestParam("email") String username,
											   @RequestParam("password") String password) {
		///Nombre de usuario vacio
		if (username.isEmpty() ) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_NOMBRE_DE_USUARIO_INEXISTENTE, HttpStatus.FORBIDDEN);
		}
		Player player = (Player) playerRepository.findByUserName(username);

		///Verifica que no exista un usuario con ese nombre
		if (player != null) {
			return this.createResponseEntity(ResponseEntityMsgs.KEY_ERROR,
					ResponseEntityMsgs.MSG_NOMBRE_DE_USUARIO_REPETIDO, HttpStatus.CONFLICT);
		}

		playerRepository.save(new Player(username,password));
		return this.createResponseEntity(ResponseEntityMsgs.KEY_SUCCESS, ResponseEntityMsgs.MSG_USUARIO_CREADO,
				HttpStatus.CREATED);
	}

	///PRIVATES
	private Player getAuthenticatedPlayer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return null;
		} else {
			return(Player)playerRepository.findByUserName(authentication.getName());
		}
	}

	private void updateScores(GameState gameState, GamePlayer gamePlayerOfRequest) {
		switch (gameState) {
			case WON:
				scoreRepository.save(new Score(1.0, gamePlayerOfRequest.getGame(), gamePlayerOfRequest.getPlayer()));
				scoreRepository.save(new Score(0.0, gamePlayerOfRequest.getGame(), gamePlayerOfRequest.getOpponent()));
				break;
			case LOST:
				scoreRepository.save(new Score(0.0, gamePlayerOfRequest.getGame(), gamePlayerOfRequest.getPlayer()));
				scoreRepository.save(new Score(1.0, gamePlayerOfRequest.getGame(), gamePlayerOfRequest.getOpponent()));
				break;
			case TIE:
				scoreRepository.save(new Score(0.5, gamePlayerOfRequest.getGame(), gamePlayerOfRequest.getPlayer()));
				scoreRepository.save(new Score(0.5, gamePlayerOfRequest.getGame(), gamePlayerOfRequest.getOpponent()));
				break;
			default:
				throw new IllegalStateException("Error al computar el score");
		}
	}

	private boolean canPlaceSalvoes(GamePlayer gamePlayer, Salvo salvo) {
		if (gamePlayer.getSalvoes().isEmpty()) {
			return true;
		}
		return (salvo.getTurn() == gamePlayer.getSalvoes().size() + 1) &&
				(!gamePlayer.repeatedSalvo(salvo.getSalvoLocations()));
	}

	private ResponseEntity<Object> createResponseEntity(String tipoDeRespuesta, Object valor, HttpStatus httpStatus ) {
		Map<String,Object> responseMap = new LinkedHashMap<>();
		responseMap.put(tipoDeRespuesta, valor);
		return new ResponseEntity<>(responseMap, httpStatus);
	}
}

