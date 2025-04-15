package fr.sdv.my_game_project.models;

import fr.sdv.my_game_project.models.pieces.Piece;

/**
 * Represents a single square on the chess board.
 */
public class Tile {
    private final int x, y;
    private Piece piece;

    /**
     * Constructs a Tile with specific x and y coordinates.
     *
     * @param x the horizontal position on the board
     * @param y the vertical position on the board
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    /**
     * Returns the x-coordinate of this tile.
     *
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this tile.
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Checks whether this tile is occupied by a piece.
     *
     * @return true if a piece is on the tile, false otherwise
     */
    public boolean isOccupied() {
        return piece != null;
    }

    /**
     * Returns the piece on this tile.
     *
     * @return the piece on the tile, or null if empty
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Places a piece on this tile.
     *
     * @param piece the piece to place
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Removes any piece from this tile.
     */
    public void removePiece() {
        this.piece = null;
    }
}
