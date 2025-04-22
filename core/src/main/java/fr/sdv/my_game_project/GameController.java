package fr.sdv.my_game_project;

import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.Tile;
import fr.sdv.my_game_project.models.pieces.King;
import fr.sdv.my_game_project.models.pieces.Pawn;
import fr.sdv.my_game_project.models.pieces.Piece;
import fr.sdv.my_game_project.models.pieces.Queen;

import java.util.List;

/**
 * Handles the game logic of a chess match, including turn management, piece movement,
 * victory detection, and promotion.
 */
public class GameController {

    private Board board;
    private Tile selectedTile;
    private List<Vector2> legalMoves;
    private boolean isWhiteTurn;
    private boolean gameOver;
    private String winnerText;

    /**
     * Constructs a new GameController and initializes a fresh game.
     */
    public GameController() {
        resetGame();
    }

    /**
     * Resets the game to its initial state: new board, white's turn, no selection or winner.
     */
    public void resetGame() {
        board = new Board();
        selectedTile = null;
        legalMoves = null;
        isWhiteTurn = true;
        gameOver = false;
        winnerText = "";
    }

    /**
     * Handles logic when the player clicks a tile on the board.
     *
     * @param x The x-coordinate of the clicked tile
     * @param y The y-coordinate of the clicked tile
     * @return true if the click resulted in a state change
     */
    public boolean handleTileClick(int x, int y) {
        if (gameOver) return false;

        Tile tile = board.getTile(x, y);
        if (tile == null) return false;

        if (selectedTile == null) {
            return trySelectTile(tile, x, y);
        } else {
            return tryMovePiece(tile, x, y);
        }
    }

    /**
     * Attempts to select a piece on the given tile if it's the player's turn.
     *
     * @param tile The tile clicked
     * @param x The x-coordinate of the tile
     * @param y The y-coordinate of the tile
     * @return true if a piece was successfully selected
     */
    private boolean trySelectTile(Tile tile, int x, int y) {
        if (tile.isOccupied() && tile.getPiece().isWhite() == isWhiteTurn) {
            selectedTile = tile;
            legalMoves = tile.getPiece().getLegalMoves(board, x, y);
            return true;
        }
        return false;
    }

    /**
     * Attempts to move the selected piece to the target tile, applying rules like promotion and endgame.
     *
     * @param targetTile The tile the player is trying to move to
     * @param x The x-coordinate of the target tile
     * @param y The y-coordinate of the target tile
     * @return true if the move was valid and executed
     */
    private boolean tryMovePiece(Tile targetTile, int x, int y) {
        Vector2 clicked = new Vector2(x, y);
        if (legalMoves != null && legalMoves.contains(clicked)) {
            Piece captured = targetTile.getPiece();
            Piece movingPiece = selectedTile.getPiece();

            targetTile.setPiece(movingPiece);

            // Promotion
            if (movingPiece instanceof Pawn && shouldPromote(movingPiece, y)) {
                targetTile.setPiece(new Queen(movingPiece.isWhite()));
            }

            // Fin de partie
            if (captured instanceof King) {
                gameOver = true;
                winnerText = isWhiteTurn ? "Les Blancs gagnent !" : "Les Noirs gagnent !";
            }

            selectedTile.removePiece();
            isWhiteTurn = !isWhiteTurn;
        }

        selectedTile = null;
        legalMoves = null;
        return true;
    }

    /**
     * Checks if a pawn should be promoted based on its position.
     *
     * @param piece The piece to check
     * @param y The y-coordinate of the target tile
     * @return true if the pawn should be promoted
     */
    private boolean shouldPromote(Piece piece, int y) {
        return (piece.isWhite() && y == 7) || (!piece.isWhite() && y == 0);
    }


    /**
     * Gets the current game board.
     *
     * @return The current board instance
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns whether it's currently white's turn.
     *
     * @return true if it's white's turn
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    /**
     * Returns whether the game is over.
     *
     * @return true if the game has ended
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the message describing who won the game.
     *
     * @return The winner text (e.g., "Les Blancs gagnent !")
     */
    public String getWinnerText() {
        return winnerText;
    }

    /**
     * Gets the list of legal moves for the currently selected piece.
     *
     * @return List of legal move positions, or null if no piece is selected
     */
    public List<Vector2> getLegalMoves() {
        return legalMoves;
    }

    /**
     * Gets the currently selected tile, if any.
     *
     * @return The selected tile, or null if none is selected
     */
    public Tile getSelectedTile() {
        return selectedTile;
    }
}
