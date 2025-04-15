package fr.sdv.my_game_project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.sdv.my_game_project.Main;
import fr.sdv.my_game_project.models.Board;
import fr.sdv.my_game_project.models.Tile;
import fr.sdv.my_game_project.models.pieces.King;
import fr.sdv.my_game_project.models.pieces.Pawn;
import fr.sdv.my_game_project.models.pieces.Piece;
import fr.sdv.my_game_project.models.pieces.Queen;

import java.util.List;

/**
 * The main game screen where the chess board is displayed and interactions occur.
 * Handles rendering, user input, and game state updates.
 */
public class GameScreen extends InputAdapter implements Screen {

    private Main game;
    private Board board;
    private OrthographicCamera camera;
    private static final int TILE_SIZE = 66;

    private Tile selectedTile = null;
    private List<Vector2> legalMoves = null;

    private Texture lightTile;
    private Texture darkTile;
    private Texture highlight;

    private boolean isWhiteTurn = true;
    private boolean gameOver = false;
    private String winnerText = "";

    /**
     * Constructs the GameScreen and initializes the board, camera, textures, and input handling.
     *
     * @param game The main game instance
     */
    public GameScreen(Main game) {
        this.game = game;
        this.board = new Board();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 8 * TILE_SIZE, 8 * TILE_SIZE);

        lightTile = new Texture("images/tiles/LightTile.png");
        darkTile = new Texture("images/tiles/DarkTile.png");
        highlight = new Texture("images/tiles/HighlightTile.png");

        Gdx.input.setInputProcessor(this);
    }

    /**
     * Renders the board, pieces, highlights, and end-game message if needed.
     *
     * @param delta Time since last frame
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // Draw tiles
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boolean isLight = (x + y) % 2 == 0;
                game.batch.draw(isLight ? lightTile : darkTile, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Draw legal move highlights
        if (legalMoves != null) {
            for (Vector2 move : legalMoves) {
                game.batch.draw(highlight, move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Draw pieces
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Tile tile = board.getTile(x, y);
                if (tile.isOccupied()) {
                    Texture texture = tile.getPiece().getTexture();
                    game.batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        game.batch.end();

        // Display winner text if game is over
        if (gameOver) {
            GlyphLayout layout = new GlyphLayout();
            layout.setText(game.font, winnerText);

            float textWidth = layout.width;
            float screenWidth = 8 * TILE_SIZE;

            float x = (screenWidth - textWidth) / 2f;
            float y = screenWidth / 2f;

            game.batch.begin();
            game.font.draw(game.batch, winnerText, x, y);
            game.batch.end();
        }
    }

    /**
     * Handles click input from the user to select and move pieces.
     *
     * @return true if the touch was handled
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameOver) return false;

        Vector3 touch = new Vector3(screenX, screenY, 0);
        camera.unproject(touch);
        int x = (int) (touch.x / TILE_SIZE);
        int y = (int) (touch.y / TILE_SIZE);

        Tile tile = board.getTile(x, y);
        if (tile == null) return false;

        if (selectedTile == null) {
            if (tile.isOccupied() && tile.getPiece().isWhite() == isWhiteTurn) {
                selectedTile = tile;
                legalMoves = tile.getPiece().getLegalMoves(board, x, y);
            }
        } else {
            Vector2 clicked = new Vector2(x, y);
            if (legalMoves != null && legalMoves.contains(clicked)) {
                Tile targetTile = board.getTile(x, y);
                Piece captured = targetTile.getPiece();
                Piece movingPiece = selectedTile.getPiece();

                targetTile.setPiece(movingPiece);

                // Handle promotion
                if (movingPiece instanceof Pawn) {
                    boolean shouldPromote = (movingPiece.isWhite() && y == 7) || (!movingPiece.isWhite() && y == 0);
                    if (shouldPromote) {
                        targetTile.setPiece(new Queen(movingPiece.isWhite()));
                    }
                }

                // Check for endgame
                if (captured instanceof King) {
                    gameOver = true;
                    winnerText = isWhiteTurn ? "Les Blancs gagnent !" : "Les Noirs gagnent !";
                }

                selectedTile.removePiece();
                isWhiteTurn = !isWhiteTurn;
            }

            selectedTile = null;
            legalMoves = null;
        }

        return true;
    }

    // The following methods are required by the Screen interface but not used
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
    @Override public void show() {}
}
