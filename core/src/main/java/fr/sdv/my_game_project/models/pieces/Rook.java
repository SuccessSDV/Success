package fr.sdv.my_game_project.models.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import fr.sdv.my_game_project.models.Board;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
        this.setTexture("Rook");
    }

    @Override
    public List<Vector2> getLegalMoves(Board board, int x, int y) {
        List<Vector2> moves = new ArrayList<>();
        addDirectionalMoves(board, moves, x, y, 1, 0);  // droite
        addDirectionalMoves(board, moves, x, y, -1, 0); // gauche
        addDirectionalMoves(board, moves, x, y, 0, 1);  // haut
        addDirectionalMoves(board, moves, x, y, 0, -1); // bas
        return moves;
    }

}
