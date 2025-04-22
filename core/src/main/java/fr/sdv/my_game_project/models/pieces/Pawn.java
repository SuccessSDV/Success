package fr.sdv.my_game_project.models.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
        if (isWhite) this.setTexture("garen");
        else this.setTexture("darius");
    }

    @Override
    public List<Vector2> getLegalMoves(Board board, int x, int y) {
        List<Vector2> moves = new ArrayList<>();
        int dir = isWhite ? 1 : -1;

        if (isValidTile(x, y + dir) && isEmpty(board, x, y + dir)) {
            moves.add(new Vector2(x, y + dir));

            boolean isStartRow = isWhite ? y == 1 : y == 6;
            if (isStartRow && isEmpty(board, x, y + 2 * dir)) {
                moves.add(new Vector2(x, y + 2 * dir));
            }
        }

        addCaptureIfEnemy(board, moves, x - 1, y + dir);
        addCaptureIfEnemy(board, moves, x + 1, y + dir);

        return moves;
    }
}
