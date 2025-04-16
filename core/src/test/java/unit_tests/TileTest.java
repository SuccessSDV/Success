
package tests.unit_tests;

import fr.sdv.my_game_project.models.Tile;
import fr.sdv.my_game_project.models.pieces.Pawn;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testTileInitialization() {
        Tile tile = new Tile(3, 4);
        assertEquals(3, tile.getX());
        assertEquals(4, tile.getY());
        assertNull(tile.getPiece());
    }

    @Test
    public void testSetAndGetPiece() {
        Tile tile = new Tile(0, 0);
        Pawn pawn = new Pawn(true);
        tile.setPiece(pawn);
        assertEquals(pawn, tile.getPiece());
    }

    @Test
    public void testRemovePiece() {
        Tile tile = new Tile(0, 0);
        tile.setPiece(new Pawn(true));
        tile.removePiece();
        assertNull(tile.getPiece());
    }
}
