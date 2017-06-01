package com.simplegame.game;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.kotcrab.vis.plugin.spriter.runtime.SpriterSupport;
import com.kotcrab.vis.runtime.RuntimeContext;
import com.kotcrab.vis.runtime.data.SceneData;
import com.kotcrab.vis.runtime.font.FreeTypeFontProvider;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.SceneFeature;
import com.kotcrab.vis.runtime.scene.SceneLoader;
import com.kotcrab.vis.runtime.scene.SceneLoader.SceneParameter;
import com.kotcrab.vis.runtime.scene.SystemProvider;
import com.kotcrab.vis.runtime.util.EntityEngineConfiguration;
import com.simplegame.game.screens.GameEntry;
import com.simplegame.game.screens.LoadingScreen;
import com.simplegame.game.screens.MenuScreen;
import com.simplegame.game.systems.AlwaysInViewPortSystem;
import com.simplegame.game.systems.CameraControllerSystem;
import com.simplegame.game.systems.ControlsSystem;
import com.simplegame.game.systems.GameSaverSystem;
import com.simplegame.game.systems.MovingPlatform;
import com.simplegame.game.systems.ParticleSystem;
import com.simplegame.game.systems.PhysicsBodyContactSystem;
import com.simplegame.game.systems.PlayerSystem;
import com.simplegame.game.systems.ScoringSystem;
import com.simplegame.game.systems.SoundSystem;
import com.simplegame.game.systems.SpriteBoundsCreator;
import com.simplegame.game.systems.SpriteBoundsUpdater;
import com.simplegame.game.systems.SpriterPhysicsSystem;
import com.simplegame.game.systems.VisibilitySystem;

import ownLib.Own;

public class GameController implements Screen {
    public static String CURRENT_LEVEL;
    public SpriteBatch spriteBatch;

    private GameEntry game;
    private boolean BOX2D_DEBUG = true;
    private Scene scene = null;
    private OwnSceneLoader manager;
    private String scenePath;
    private LoadingScreen loadingScreen;

    public GameController(GameEntry game, SpriteBatch spriteBatch) {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        this.spriteBatch = spriteBatch;
        this.game = game;

        manager = new OwnSceneLoader(spriteBatch);
        manager.enableFreeType(new FreeTypeFontProvider());
        manager.registerSupport(new SpriterSupport());
        manager.getLogger().setLevel(Logger.ERROR);
        loadingScreen = new LoadingScreen(game, this, spriteBatch, manager);
    }

    private void loadScene(String scenePath, SceneParameter sceneParameter) {
        if (BOX2D_DEBUG && !scenePath.equals(GameData.SCENE_MENU))
            sceneParameter.config.enable(SceneFeature.BOX2D_DEBUG_RENDER_SYSTEM);

        this.scenePath = scenePath;
        this.scene = null;
        this.loadingScreen.startLoading(scenePath, sceneParameter);
    }

    private void unloadPreviousScene() {
        if (scenePath != null) {
            manager.unload(scenePath);
            scenePath = null;
            scene = null;
        }
    }

    public void loadMenuScene() {
        unloadPreviousScene();
        SceneParameter sceneParameter = new SceneLoader.SceneParameter();

        sceneParameter.config.addSystem(SpriteBoundsCreator.class);
        sceneParameter.config.addSystem(SpriteBoundsUpdater.class);
        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new MenuScreen(GameController.this);
            }
        });

        loadScene(GameData.SCENE_MENU, sceneParameter);
    }

    public void loadLevelTutorial() {
        CURRENT_LEVEL = GameData.ID_LEVEL_TUTORIAL;
        unloadPreviousScene();
        SceneParameter sceneParameter = new SceneParameter();
        sceneParameter.config.addSystem(SpriteBoundsCreator.class);
        sceneParameter.config.addSystem(SpriteBoundsUpdater.class);
        sceneParameter.config.addSystem(SpriterPhysicsSystem.class);
        sceneParameter.config.addSystem(PhysicsBodyContactSystem.class);
        sceneParameter.config.addSystem(ScoringSystem.class);
        sceneParameter.config.addSystem(MovingPlatform.class);
        sceneParameter.config.addSystem(GameSaverSystem.class);

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new CameraControllerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new PlayerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new ControlsSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(SoundSystem.class);
        sceneParameter.config.addSystem(AlwaysInViewPortSystem.class);
        sceneParameter.config.addSystem(VisibilitySystem.class);
        loadScene(GameData.SCENE_TUTORIAL, sceneParameter);
    }

    public void loadLevelOneScene() {
        CURRENT_LEVEL = GameData.ID_LEVEL_ONE;
        unloadPreviousScene();
        SceneParameter sceneParameter = new SceneParameter();
        sceneParameter.config.addSystem(SpriteBoundsCreator.class);
        sceneParameter.config.addSystem(SpriteBoundsUpdater.class);
        sceneParameter.config.addSystem(SpriterPhysicsSystem.class);
        sceneParameter.config.addSystem(PhysicsBodyContactSystem.class);
        sceneParameter.config.addSystem(ScoringSystem.class);
        sceneParameter.config.addSystem(MovingPlatform.class);
        sceneParameter.config.addSystem(GameSaverSystem.class);

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new CameraControllerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new PlayerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new ControlsSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(SoundSystem.class);
        sceneParameter.config.addSystem(AlwaysInViewPortSystem.class);
        sceneParameter.config.addSystem(VisibilitySystem.class);
        loadScene(GameData.SCENE_ONE, sceneParameter);
    }

    public void loadLevelTwoScene() {
        CURRENT_LEVEL = GameData.ID_LEVEL_TWO;
        unloadPreviousScene();
        SceneParameter sceneParameter = new SceneParameter();
        sceneParameter.config.addSystem(SpriteBoundsCreator.class);
        sceneParameter.config.addSystem(SpriteBoundsUpdater.class);
        sceneParameter.config.addSystem(ParticleSystem.class);
        sceneParameter.config.addSystem(SpriterPhysicsSystem.class);
        sceneParameter.config.addSystem(PhysicsBodyContactSystem.class);
        sceneParameter.config.addSystem(ScoringSystem.class);
        sceneParameter.config.addSystem(SoundSystem.class);
        sceneParameter.config.addSystem(GameSaverSystem.class);

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new PlayerSystem(GameController.this);
            }
        });
        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new CameraControllerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new ControlsSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(VisibilitySystem.class);
        sceneParameter.config.addSystem(AlwaysInViewPortSystem.class);
        loadScene(GameData.SCENE_TWO, sceneParameter);

    }

    public void loadLevelThreeScene() {
        CURRENT_LEVEL = GameData.ID_LEVEL_THREE;
        unloadPreviousScene();

        SceneParameter sceneParameter = new SceneParameter();

        sceneParameter.config.addSystem(SpriteBoundsCreator.class);
        sceneParameter.config.addSystem(SpriteBoundsUpdater.class);
        sceneParameter.config.addSystem(PhysicsBodyContactSystem.class);
        sceneParameter.config.addSystem(ScoringSystem.class);
        sceneParameter.config.addSystem(MovingPlatform.class);

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new CameraControllerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new PlayerSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(new SystemProvider() {
            @Override
            public BaseSystem create(EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
                return new ControlsSystem(GameController.this);
            }
        });

        sceneParameter.config.addSystem(GameSaverSystem.class);
        sceneParameter.config.addSystem(SoundSystem.class);
        sceneParameter.config.addSystem(VisibilitySystem.class);
        sceneParameter.config.addSystem(AlwaysInViewPortSystem.class);
        loadScene(GameData.SCENE_THREE, sceneParameter);
    }

    @Override
    public void show() {
        if (this.scene == null) {  // Load scene for the first time as there is no scene is loaded
            loadMenuScene();
//            loadLevelTutorial();
//            loadLevelOneScene();
//            loadLevelTwoScene();
//            loadLevelThreeScene();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        if (scene != null) scene.render();
    }

    @Override
    public void resize(int width, int height) {
        if (scene != null) scene.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        spriteBatch.dispose();
    }

    private void restartLevel() {
        switch (this.CURRENT_LEVEL) {
//            case GameData.MENU_SCREEN:
//                loadMenuScene();
//                break;
            case GameData.ID_LEVEL_ONE:
                loadLevelOneScene();
                break;
            case GameData.ID_LEVEL_TWO:
                loadLevelTwoScene();
                break;
            case GameData.ID_LEVEL_THREE:
                loadLevelThreeScene();
                break;
            default:
                loadMenuScene();
                Own.log("Error: Invalid CURRENT_LEVEL info: " + this.CURRENT_LEVEL);
                break;
        }

    }

    public void notify(int gameState) {
        switch (gameState) {
            case GameData.QUIT:
                loadMenuScene();
                break;
            case GameData.RESTART_LEVEL:
                restartLevel();
                break;
            case GameData.LEVEL_END:
                loadMenuScene();
                break;
        }
    }

    public void exitApp() {
        Own.log("Exit app");
        Gdx.app.exit();
    }

    public void debugMode(boolean mode) {
        this.BOX2D_DEBUG = mode;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
