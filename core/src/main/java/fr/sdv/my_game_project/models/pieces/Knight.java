package fr.sdv.my_game_project.models.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
        this.setTexture("Knight");
    }

    @Override
    public List<Vector2> getLegalMoves(Board board, int x, int y) {
        List<Vector2> moves = new ArrayList<>();
        int[][] offsets = {
            {1, 2}, {2, 1}, {-1, 2}, {-2, 1},
            {1, -2}, {2, -1}, {-1, -2}, {-2, -1}
        };

        for (int[] offset : offsets) {
            int dx = offset[0], dy = offset[1];
            int nx = x + dx, ny = y + dy;
            if (isValidTile(nx, ny)) {
                if (isEmpty(board, nx, ny) || isEnemy(board, nx, ny)) {
                    moves.add(new Vector2(nx, ny));
                }
            }
        }

        return moves;
    }

}
