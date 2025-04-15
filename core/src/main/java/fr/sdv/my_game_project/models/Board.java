package fr.sdv.my_game_project.models;

import fr.sdv.my_game_project.models.pieces.*;
import java.util.*;

/**
 * Represents the chess board and manages tile and piece initialization.
 */
public class Board {
    private final Tile[][] tiles;

    /**
     * Constructs a new 8x8 chess board and initializes tiles and pieces.
     */
    public Board() {
        tiles = new Tile[8][8];
        initializeTiles();
        initializePieces();
    }

    /**
     * Initializes each tile of the board with coordinates.
     */
    private void initializeTiles() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    /**
     * Places all chess pieces on the board in their standard initial positions.
     */
    private void initializePieces() {
        // Pawns
        for (int i = 0; i < 8; i++) {
            tiles[i][1].setPiece(new Pawn(true));
            tiles[i][6].setPiece(new Pawn(false));
        }

        // Rooks
        tiles[0][0].setPiece(new Rook(true));
        tiles[7][0].setPiece(new Rook(true));
        tiles[0][7].setPiece(new Rook(false));
        tiles[7][7].setPiece(new Rook(false));

        // Knights
        tiles[1][0].setPiece(new Knight(true));
        tiles[6][0].setPiece(new Knight(true));
        tiles[1][7].setPiece(new Knight(false));
        tiles[6][7].setPiece(new Knight(false));

        // Bishops
        tiles[2][0].setPiece(new Bishop(true));
        tiles[5][0].setPiece(new Bishop(true));
        tiles[2][7].setPiece(new Bishop(false));
        tiles[5][7].setPiece(new Bishop(false));

        // Queens
        tiles[3][0].setPiece(new Queen(true));
        tiles[3][7].setPiece(new Queen(false));

        // Kings
        tiles[4][0].setPiece(new King(true));
        tiles[4][7].setPiece(new King(false));
    }

    /**
     * Returns the tile at the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the Tile object, or null if out of bounds
     */
    public Tile getTile(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return tiles[x][y];
        }
        return null;
    }

    /**
     * Returns the 2D array of all tiles on the board.
     *
     * @return the tile grid
     */
    public Tile[][] getTiles() {
        return tiles;
    }
}
