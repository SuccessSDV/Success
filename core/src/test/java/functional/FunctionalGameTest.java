
package functional;

import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.pieces.Pawn;
import fr.sdv.my_game_project.models.pieces.Rook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionalGameTest {

    @Test
    public void testPawnMoveForward() {
        Board board = new Board();
        Pawn pawn = new Pawn(true);
        board.getTile(4, 1).setPiece(pawn);

        // Move pawn forward one step
        board.getTile(4, 1).removePiece();
        board.getTile(4, 2).setPiece(pawn);

        assertNull(board.getTile(4, 1).getPiece());
        assertEquals(pawn, board.getTile(4, 2).getPiece());
    }

    @Test
    public void testPawnCapturesEnemy() {
        Board board = new Board();
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);

        board.getTile(3, 3).setPiece(whitePawn);
        board.getTile(4, 4).setPiece(blackPawn);

        // Simulate white capturing black
        board.getTile(3, 3).removePiece();
        board.getTile(4, 4).setPiece(whitePawn);

        assertNull(board.getTile(3, 3).getPiece());
        assertEquals(whitePawn, board.getTile(4, 4).getPiece());
    }

    @Test
    public void testTwoTurnsSimulated() {
        Board board = new Board();

        Pawn whitePawn = new Pawn(true);
        board.getTile(2, 1).setPiece(whitePawn);

        // White plays
        board.getTile(2, 1).removePiece();
        board.getTile(2, 3).setPiece(whitePawn);

        assertEquals(whitePawn, board.getTile(2, 3).getPiece());

        // Black responds
        Pawn blackPawn = new Pawn(false);
        board.getTile(5, 6).setPiece(blackPawn);
        board.getTile(5, 6).removePiece();
        board.getTile(5, 4).setPiece(blackPawn);

        assertEquals(blackPawn, board.getTile(5, 4).getPiece());
    }

    @Test
    public void testInvalidMoveAttempt() {
        Board board = new Board();
        Rook rook = new Rook(true);
        board.getTile(0, 0).setPiece(rook);

        // Try to move the rook like a knight (invalid)
        Vector2 attemptedMove = new Vector2(1, 2);
        boolean valid = rook.getLegalMoves(board, 0, 0).contains(attemptedMove);

        assertFalse(valid, "Rook should not be able to move like a knight.");
    }
}
