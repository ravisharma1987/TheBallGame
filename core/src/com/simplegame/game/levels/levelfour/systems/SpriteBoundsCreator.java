package com.simplegame.game.levels.levelfour.systems;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.simplegame.game.levels.levelfour.component.Bounds;

/**
 * @author Kotcrab
 */
public class SpriteBoundsCreator extends BaseEntitySystem {
    private ComponentMapper<Bounds> boundsCm;

    public SpriteBoundsCreator() {
        super(Aspect.all(VisSprite.class));
    }

    @Override
    public void inserted(IntBag entities) {
        int[] data = entities.getData();
        for (int i = 0; i < entities.size(); i++) {
            int entityId = data[i];
            boundsCm.create(entityId);
        }
    }

    @Override
    protected void processSystem() {

    }
}
