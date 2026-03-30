package FrierenMod.effects;

import FrierenMod.utils.Config;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class PlaySFXEffect extends AbstractGameEffect{
    private boolean playedSfx = false;
    private final String sfxKey;

    public PlaySFXEffect(String sfxKey) {
        this.sfxKey = sfxKey;
    }

    public void update() {
        if(!Config.ALLOW_SPECIAL_SFX){
            this.isDone = true;
            return;
        }
        if (!this.playedSfx) {
            this.playedSfx = true;
            CardCrawlGame.sound.play(sfxKey);
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }
    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
