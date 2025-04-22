
package integration;

import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.Tile;
import fr.sdv.my_game_project.models.pieces.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardIntegrationTest {

    @Test
    public void testPlaceAndRetrievePiece() {
        Board board = new Board();
        Tile tile = board.getTile(0, 0);
        Rook rook = new Rook(true);
        tile.setPiece(rook);
        assertEquals(rook, board.getTile(0, 0).getPiece());
    }

    @Test
    public void testPawnInitialDoubleMove() {
        Board board = new Board();
        Tile tile = board.getTile(4, 1);
        Pawn pawn = new Pawn(true);
        tile.setPiece(pawn);
        List<Vector2> moves = pawn.getLegalMoves(board, 4, 1);
        assertTrue(moves.contains(new Vector2(4, 2)));
        assertTrue(moves.contains(new Vector2(4, 3)));
    }

    @Test
    public void testRookBlockedBySameColor() {
        Board board = new Board();
        Rook rook = new Rook(true);
        board.getTile(0, 0).setPiece(rook);
        board.getTile(0, 1).setPiece(new Pawn(true)); // Friendly piece blocking

        List<Vector2> moves = rook.getLegalMoves(board, 0, 0);
        assertFalse(moves.contains(new Vector2(0, 1))); // Should not include blocked tile
    }

    @Test
    public void testRookCanCaptureEnemy() {
        Board board = new Board();
        Rook rook = new Rook(true);
        board.getTile(0, 0).setPiece(rook);
        board.getTile(0, 2).setPiece(new Pawn(false)); // Enemy piece

        List<Vector2> moves = rook.getLegalMoves(board, 0, 0);
        assertTrue(moves.contains(new Vector2(0, 2))); // Can capture enemy
    }

    @Test
    public void testKnightJumpsOverPieces() {
        Board board = new Board();
        Knight knight = new Knight(true);
        board.getTile(1, 0).setPiece(knight);
        board.getTile(2, 2).setPiece(new Pawn(false)); // Target enemy

        List<Vector2> moves = knight.getLegalMoves(board, 1, 0);
        assertTrue(moves.contains(new Vector2(2, 2))); // Knight can jump to it
    }

    @Test
    public void testQueenCombinesRookAndBishopMoves() {
        Board board = new Board();
        Queen queen = new Queen(true);
        board.getTile(3, 3).setPiece(queen);

        List<Vector2> moves = queen.getLegalMoves(board, 3, 3);
        assertTrue(moves.contains(new Vector2(0, 0))); // Diagonal
        assertTrue(moves.contains(new Vector2(3, 0))); // Vertical
        assertTrue(moves.contains(new Vector2(7, 3))); // Horizontal
    }
}
