package fr.sdv.my_game_project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import fr.sdv.my_game_project.models.Tile;

import com.badlogic.gdx.InputMultiplexer;

import java.util.List;

/**
 * The main game screen where the chess board is displayed and interactions occur.
 * Handles rendering, user input, and game state updates.
 */
public class GameScreen extends InputAdapter implements Screen {

    private Main game;
    private OrthographicCamera camera;
    private GameController controller;

    private static final int TILE_SIZE = 66;

    private Texture lightTile;
    private Texture darkTile;
    private Texture highlight;
    private Label winnerLabel;
    private Stage stage;
    private Skin skin;
    private TextButton replayButton;

    /**
     * Constructs the GameScreen and initializes the board, camera, textures, and input handling.
     *
     * @param game The main game instance
     */
    public GameScreen(Main game) {
        this.game = game;
        this.controller = new GameController();
        this.camera = new OrthographicCamera();
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("flat-earth\\skin\\flat-earth-ui.json"));

        InputMultiplexer multiplexer = new InputMultiplexer();

        camera.setToOrtho(false, 8 * TILE_SIZE, 8 * TILE_SIZE);

        lightTile = new Texture("images/tiles/LightTile.png");
        darkTile = new Texture("images/tiles/DarkTile.png");
        highlight = new Texture("images/tiles/HighlightTile.png");


        replayButton = new TextButton("Rejouer", skin);
        replayButton.setSize(200, 50);
        replayButton.setPosition((8 * TILE_SIZE - replayButton.getWidth()) / 2, (8 * TILE_SIZE - replayButton.getHeight()) / 2);
        replayButton.setVisible(false);

        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.resetGame();
                winnerLabel.setVisible(false);
                replayButton.setVisible(false);
            }
        });

        Label.LabelStyle baseStyle = skin.get("default", Label.LabelStyle.class);
        Label.LabelStyle whiteStyle = new Label.LabelStyle(baseStyle.font, Color.WHITE);
        winnerLabel = new Label("", whiteStyle);
        winnerLabel.setVisible(false);
        winnerLabel.setFontScale(1.5f);

        stage.addActor(replayButton);
        stage.addActor(winnerLabel);

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);
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

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boolean isLight = (x + y) % 2 == 0;
                game.batch.draw(isLight ? lightTile : darkTile, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        List<Vector2> legalMoves = controller.getLegalMoves();
        if (legalMoves != null) {
            for (Vector2 move : legalMoves) {
                game.batch.draw(highlight, move.x * TILE_SIZE, move.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Tile tile = controller.getBoard().getTile(x, y);
                if (tile.isOccupied()) {
                    Texture texture = tile.getPiece().getTexture();
                    game.batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        game.batch.end();
        replayButton.setDisabled(false);

        if (controller.isGameOver()) {
            winnerLabel.setText(controller.getWinnerText());
            winnerLabel.pack();
            winnerLabel.setPosition((8 * TILE_SIZE - winnerLabel.getWidth()) / 2,
                (8 * TILE_SIZE + winnerLabel.getHeight() + replayButton.getHeight()) / 2);

            winnerLabel.setVisible(true);
            replayButton.setVisible(true);
        }

        this.stage.act(delta);
        this.stage.draw();
    }

    /**
     * Handles click input from the user to select and move pieces.
     *
     * @return true if the touch was handled
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (controller.isGameOver()) return false;

        Vector3 touch = new Vector3(screenX, screenY, 0);
        camera.unproject(touch);
        int x = (int) (touch.x / TILE_SIZE);
        int y = (int) (touch.y / TILE_SIZE);

        return controller.handleTileClick(x, y);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
    @Override public void show() {}
}
