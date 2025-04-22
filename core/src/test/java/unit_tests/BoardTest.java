
package unit_tests;

import fr.sdv.my_game_project.models.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardInitialization() {
        Board board = new Board();
        assertNotNull(board.getTile(0, 0));
        assertEquals(8, board.getTiles().length);
        assertEquals(8, board.getTiles()[0].length);
    }

    @Test
    public void testGetTileOutOfBounds() {
        Board board = new Board();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            board.getTile(8, 8);
        });
    }
}
