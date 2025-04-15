package fr.sdv.my_game_project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * The main class for launching the LibGDX game.
 * Sets up the rendering system and initializes the main screen.
 */
public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    /**
     * Initializes the game by creating a SpriteBatch, a BitmapFont,
     * and setting the initial screen to GameScreen.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new GameScreen(this));
    }

    /**
     * Delegates rendering to the current active screen.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Releases resources used by the batch, font, and active screen.
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        getScreen().dispose();
    }
}
