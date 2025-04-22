package fr.sdv.my_game_project.models.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.Tile;
import java.util.List;

/**
 * Abstract class representing a chess piece.
 * Provides basic functionalities shared by all pieces.
 */
public abstract class Piece {
    protected boolean isWhite;
    protected Texture texture;

    /**
     * Constructs a piece with its color.
     * @param isWhite true if the piece is white, false if black.
     */
    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    /**
     * Returns the color of the piece.
     * @return true if the piece is white, false otherwise.
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Returns the texture (image) of the piece.
     * @return texture of the piece.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Returns a list of legal moves the piece can make from its current position.
     * @param board the current game board.
     * @param x the x-position of the piece.
     * @param y the y-position of the piece.
     * @return list of legal moves as Vector2 positions.
     */
    public abstract List<Vector2> getLegalMoves(Board board, int x, int y);

    // ===================== UTILS =========================

    /**
     * Set the piece's texture via the name of the piece
     * @param name the name of the piece to be used in the file path
     */
    protected void setTexture(String name) {
        texture = new Texture( "images/lol-pieces/" + (isWhite ? "Demacia/" : "Noxus/") + name + (isWhite ? "_back" : "_front") +".png");
    }

    /**
     * Checks if a tile position is valid on the board (within bounds).
     * @param x x-coordinate of the tile.
     * @param y y-coordinate of the tile.
     * @return true if the tile is within the board, false otherwise.
     */
    protected boolean isValidTile(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    /**
     * Checks if the tile at (x, y) contains an enemy piece.
     * @param board the current board.
     * @param x x-coordinate of the tile.
     * @param y y-coordinate of the tile.
     * @return true if the tile contains an enemy piece, false otherwise.
     */
    protected boolean isEnemy(Board board, int x, int y) {
        Tile tile = board.getTile(x, y);
        return tile != null && tile.isOccupied() && tile.getPiece().isWhite() != this.isWhite;
    }

    /**
     * Checks if the tile at (x, y) is empty.
     * @param board the current board.
     * @param x x-coordinate of the tile.
     * @param y y-coordinate of the tile.
     * @return true if the tile is empty, false otherwise.
     */
    protected boolean isEmpty(Board board, int x, int y) {
        Tile tile = board.getTile(x, y);
        return tile != null && !tile.isOccupied();
    }

    /**
     * Adds the position (x, y) to the list of moves if it is valid and empty.
     * @param board the current board.
     * @param moves the list of possible moves.
     * @param x x-coordinate of the tile.
     * @param y y-coordinate of the tile.
     */
    protected void addMoveIfValid(Board board, List<Vector2> moves, int x, int y) {
        if (isValidTile(x, y) && isEmpty(board, x, y)) {
            moves.add(new Vector2(x, y));
        }
    }

    /**
     * Adds the position (x, y) to the list of moves if it contains an enemy piece.
     * @param board the current board.
     * @param moves the list of possible moves.
     * @param x x-coordinate of the tile.
     * @param y y-coordinate of the tile.
     */
    protected void addCaptureIfEnemy(Board board, List<Vector2> moves, int x, int y) {
        if (isValidTile(x, y) && isEnemy(board, x, y)) {
            moves.add(new Vector2(x, y));
        }
    }

    /**
     * Adds all valid moves in a specific direction (dx, dy) until a piece blocks the way.
     * Useful for rook, bishop, and queen-like movement.
     * @param board the current board.
     * @param moves the list of possible moves.
     * @param startX starting x-coordinate.
     * @param startY starting y-coordinate.
     * @param dx direction along x-axis.
     * @param dy direction along y-axis.
     */
    protected void addDirectionalMoves(Board board, List<Vector2> moves, int startX, int startY, int dx, int dy) {
        int x = startX + dx;
        int y = startY + dy;

        while (isValidTile(x, y)) {
            Tile tile = board.getTile(x, y);
            if (!tile.isOccupied()) {
                moves.add(new Vector2(x, y));
            } else {
                if (tile.getPiece().isWhite() != this.isWhite) {
                    moves.add(new Vector2(x, y)); // Capture
                }
                break; // blocked by any piece
            }
            x += dx;
            y += dy;
        }
    }
}
