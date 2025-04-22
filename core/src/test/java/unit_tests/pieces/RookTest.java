
package unit_tests.pieces;

import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.pieces.Rook;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RookTest {

    @Test
    public void testInitialization() {
        Rook piece = new Rook(true);
        assertTrue(piece.isWhite());
        assertNotNull(piece.getTexture());
    }

    @Test
    public void testGetLegalMoves() {
        Rook piece = new Rook(true);
        Board board = new Board();
        List<Vector2> moves = piece.getLegalMoves(board, 4, 4);
        assertNotNull(moves);
    }
}
