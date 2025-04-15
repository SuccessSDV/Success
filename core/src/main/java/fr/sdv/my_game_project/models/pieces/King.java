package fr.sdv.my_game_project.models.pieces;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
        this.setTexture("King");
    }

    @Override
    public List<Vector2> getLegalMoves(Board board, int x, int y) {
        List<Vector2> moves = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx;
                int ny = y + dy;
                if (isValidTile(nx, ny)) {
                    if (isEmpty(board, nx, ny) || isEnemy(board, nx, ny)) {
                        moves.add(new Vector2(nx, ny));
                    }
                }
            }
        }
        return moves;
    }

}
