
package tests.load;

import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.Tile;
import fr.sdv.my_game_project.models.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoadTest {

    @Test
    public void testMassivePawnMovements() {
        Board board = new Board();
        Pawn pawn = new Pawn(true);
        board.getTile(4, 1).setPiece(pawn);

        for (int i = 2; i < 100; i++) {
            board.getTile(4, i - 1).removePiece();
            board.getTile(4, i).setPiece(pawn);
            assertNull(board.getTile(4, i - 1).getPiece());
            assertEquals(pawn, board.getTile(4, i).getPiece());
        }
    }

    @Test
    public void testMultipleBoardInitializations() {
        for (int i = 0; i < 100; i++) {
            Board board = new Board();
            assertNotNull(board.getTile(0, 0));
            assertNotNull(board.getTile(7, 7));
        }
    }

    @Test
    public void testRepeatedGameLoopSimulation() {
        for (int turn = 0; turn < 200; turn++) {
            Board board = new Board();
            Pawn whitePawn = new Pawn(true);
            Pawn blackPawn = new Pawn(false);
            board.getTile(4, 1).setPiece(whitePawn);
            board.getTile(4, 6).setPiece(blackPawn);

            // Simulate a turn: each player moves forward
            board.getTile(4, 1).removePiece();
            board.getTile(4, 2).setPiece(whitePawn);
            board.getTile(4, 6).removePiece();
            board.getTile(4, 5).setPiece(blackPawn);

            assertEquals(whitePawn, board.getTile(4, 2).getPiece());
            assertEquals(blackPawn, board.getTile(4, 5).getPiece());
        }
    }
}
