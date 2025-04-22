package fr.sdv.my_game_project.models.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
        if (isWhite) this.setTexture("kayle");
        else this.setTexture("ambessa");
    }

    @Override
    public List<Vector2> getLegalMoves(Board board, int x, int y) {
        List<Vector2> moves = new ArrayList<>();
        // Combinaison tour + fou
        addDirectionalMoves(board, moves, x, y, 1, 0);
        addDirectionalMoves(board, moves, x, y, -1, 0);
        addDirectionalMoves(board, moves, x, y, 0, 1);
        addDirectionalMoves(board, moves, x, y, 0, -1);
        addDirectionalMoves(board, moves, x, y, 1, 1);
        addDirectionalMoves(board, moves, x, y, -1, 1);
        addDirectionalMoves(board, moves, x, y, 1, -1);
        addDirectionalMoves(board, moves, x, y, -1, -1);
        return moves;
    }

}
