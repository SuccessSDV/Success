
package unit_tests;

import fr.sdv.my_game_project.GameController;
import fr.sdv.my_game_project.models.Tile;
import fr.sdv.my_game_project.models.pieces.King;
import fr.sdv.my_game_project.models.pieces.Pawn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    @Test
    public void testWhiteStartsFirst() {
        GameController controller = new GameController();
        assertTrue(controller.isWhiteTurn());
    }

    @Test
    public void testSelectsOwnPiece() {
        GameController controller = new GameController();
        assertTrue(controller.handleTileClick(0, 1));
        assertNotNull(controller.getSelectedTile());
    }

    @Test
    public void testMovePiece() {
        GameController controller = new GameController();
        controller.handleTileClick(0, 1);
        assertTrue(controller.handleTileClick(0, 2));
        assertTrue(controller.getBoard().getTile(0, 2).isOccupied());
    }

    @Test
    public void testGameEndsOnKingCapture() {
        GameController controller = new GameController();
        controller.getBoard().getTile(3, 3).setPiece(new Pawn(true));
        controller.getBoard().getTile(3, 4).setPiece(new King(false));

        controller.handleTileClick(3, 3);
        controller.handleTileClick(3, 4);

        assertTrue(controller.isGameOver());
        assertEquals("Les Blancs gagnent !", controller.getWinnerText());
    }
}
