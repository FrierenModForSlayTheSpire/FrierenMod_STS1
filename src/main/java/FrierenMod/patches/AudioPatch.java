package FrierenMod.patches;

import FrierenMod.enums.CharacterEnums;
import FrierenMod.utils.Config;
import FrierenMod.utils.Log;
import FrierenMod.utils.ModInformation;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.HashMap;

import static com.megacrit.cardcrawl.audio.MainMusic.newMusic;

public class AudioPatch {
    @SpirePatch(clz = SoundMaster.class, method = "<ctor>")
    public static class SoundPatch {
        public static void Postfix(SoundMaster instance, HashMap<String, Sfx> ___map) {
            AudioPatch.load(instance, ___map, "I_am_frieren.mp3");
            AudioPatch.load(instance, ___map, "kiss.mp3");
            AudioPatch.load(instance, ___map, "normal_attack.mp3");
            AudioPatch.load(instance, ___map, "win.mp3");
            AudioPatch.load(instance, ___map, "Himmel.mp3");
            AudioPatch.load(instance, ___map, "achievement.mp3");
            AudioPatch.load(instance, ___map, "I_am_fern.mp3");
            AudioPatch.load(instance, ___map, "BabySleeping.mp3");
            AudioPatch.load(instance, ___map, "FumeshroomForm.mp3");
            AudioPatch.load(instance, ___map, "SocialDancing.mp3");
            AudioPatch.load(instance, ___map, "SoSmall.mp3");
        }
    }

    @SpirePatch(clz = TempMusic.class, method = "getSong")
    public static class PatchGetSongInTempMusic {
        @SpireInsertPatch(rloc = 0, localvars = {"key"})
        public static SpireReturn<Music> Insert(TempMusic __instance, String key) {
            if (key != null) {
                if (key.equals("Frieren_The_Slayer")) {
                    return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/Frieren_The_Slayer.mp3")));
                }
                if (key.equals("Frieren_DeathStinger")) {
                    return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/Frieren_DeathStinger.mp3")));
                }
                if (canReplaceMusic()) {
                    switch (key) {
                        case "SHOP":
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Merchant_NewMix_v1.ogg")));
                        case "SHRINE":
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Shrine_NewMix_v1.ogg")));
                        case "MINDBLOOM":
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level1_NewMix_v1.mp3")));
                        case "BOSS_BOTTOM":
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Boss1_NewMix_v1.mp3")));
                        case "BOSS_CITY":
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Boss2_NewMix_v1.mp3")));
                        case "BOSS_BEYOND":
                            //未替换
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Boss3_NewMix_v1.ogg")));
                        case "BOSS_ENDING":
                            //未替换
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Boss4_v6.mp3")));
                        case "ELITE":
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_EliteBoss_NewMix_v1.ogg")));
                        case "CREDITS":
                            // 未替换
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Credits_v5.ogg")));
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = MainMusic.class, method = "getSong")
    public static class PatchGetSongInMainMusic {
        @SpireInsertPatch(rloc = 0, localvars = {"key"})
        public static SpireReturn<Music> Insert(MainMusic __instance, String key) {
            if (canReplaceMusic()) {
                switch (key) {
                    case "Exordium":
                        if (AbstractDungeon.miscRng.random(1) == 0) {
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level1_NewMix_v1.mp3")));
                        } else
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level1-2_v2.mp3")));
                    case "TheCity":
                        if (AbstractDungeon.miscRng.random(1) == 0) {
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level2_NewMix_v1.mp3")));
                        } else
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level2-2_v2.mp3")));
                    case "TheBeyond":
                        if (AbstractDungeon.miscRng.random(1) == 0) {
                            return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level3_v2.mp3")));
                        }
                        return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level3-2_v2.mp3")));
                    case "TheEnding":
                        return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Act4_BGM_v2.mp3")));
                    case "MENU":
                        return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_MenuTheme_NewMix_v1.ogg")));
                }
                Log.logger.info("NO SUCH MAIN BGM (playing level_1 instead): " + key);
                return SpireReturn.Return(newMusic(ModInformation.makeAudioPath("sound/STS_Level1_NewMix_v1.ogg")));
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = DeathScreen.class, method = "<ctor>", paramtypez = {MonsterGroup.class})
    public static class PatchDeathScreen {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (isMethodCalled(m)) {
                        m.replace("{" +
                                "if(" + PatchDeathScreen.class.getName() + ".check()){" +
                                PatchDeathScreen.class.getName() + ".calc();" +
                                "}else{" +
                                "$_=$proceed($$);" +
                                "}" +
                                "}");
                    }
                }

                private boolean isMethodCalled(MethodCall m) {
                    String methodName = m.getMethodName();
                    return methodName.equals("playTempBgmInstantly");
                }
            };
        }

        public static boolean check() {
            return canReplaceMusic();
        }

        public static void calc() {
            CardCrawlGame.music.playTempBgmInstantly("Frieren_DeathStinger");
        }
    }

    private static void load(SoundMaster instance, HashMap<String, Sfx> ___map, String key) {
        ___map.put(key, new Sfx(ModInformation.makeAudioPath("sound/" + key), false));
    }

    private static boolean canReplaceMusic() {
        return Config.ALLOW_SPECIAL_BGM && AbstractDungeon.player != null && (AbstractDungeon.player.chosenClass == CharacterEnums.FRIEREN || AbstractDungeon.player.chosenClass == CharacterEnums.FERN);
    }
}