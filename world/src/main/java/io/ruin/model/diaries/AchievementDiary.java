package io.ruin.model.diaries;

import io.ruin.model.diaries.ardougne.ArdougneAchievementDiary;
import io.ruin.model.diaries.ardougne.ArdougneDiaryEntry;
import io.ruin.model.diaries.desert.DesertAchievementDiary;
import io.ruin.model.diaries.desert.DesertDiaryEntry;
import io.ruin.model.diaries.falador.FaladorAchievementDiary;
import io.ruin.model.diaries.falador.FaladorDiaryEntry;
import io.ruin.model.diaries.fremennik.FremennikAchievementDiary;
import io.ruin.model.diaries.fremennik.FremennikDiaryEntry;
import io.ruin.model.diaries.kandarin.KandarinAchievementDiary;
import io.ruin.model.diaries.kandarin.KandarinDiaryEntry;
import io.ruin.model.diaries.karamja.KaramjaAchievementDiary;
import io.ruin.model.diaries.karamja.KaramjaDiaryEntry;
import io.ruin.model.diaries.kourend.KourendAchievementDiary;
import io.ruin.model.diaries.kourend.KourendDiaryEntry;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorAchievementDiary;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import io.ruin.model.diaries.morytania.MorytaniaAchievementDiary;
import io.ruin.model.diaries.morytania.MorytaniaDiaryEntry;
import io.ruin.model.diaries.varrock.VarrockAchievementDiary;
import io.ruin.model.diaries.varrock.VarrockDiaryEntry;
import io.ruin.model.diaries.western.WesternAchievementDiary;
import io.ruin.model.diaries.western.WesternDiaryEntry;
import io.ruin.model.diaries.wilderness.WildernessAchievementDiary;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.utils.Config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;


/**
 * Represents an Achievement diary implementation
 *
 * @param <T> Enumeration representing the various achievments a {@link Player}
 *            can get
 * @author Kaleem
 * @version 1.1
 */
public abstract class AchievementDiary<T extends Enum<T>> {

    static {
        LoginListener.register(p -> p.DiaryRecorder.forEach((s, integer) -> {
            for (ArdougneDiaryEntry value : ArdougneDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getArdougneDiary().setAchievementStage(value, integer, false);
                }
            }
            for (DesertDiaryEntry value : DesertDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getDesertDiary().setAchievementStage(value, integer, false);
                }
            }
            for (FaladorDiaryEntry value : FaladorDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getFaladorDiary().setAchievementStage(value, integer, false);
                }
            }
            for (FremennikDiaryEntry value : FremennikDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getFremennikDiary().setAchievementStage(value, integer, false);
                }
            }
            for (KandarinDiaryEntry value : KandarinDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getKandarinDiary().setAchievementStage(value, integer, false);
                }
            }
            for (KaramjaDiaryEntry value : KaramjaDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getKaramjaDiary().setAchievementStage(value, integer, false);
                }
            }
            for (LumbridgeDraynorDiaryEntry value : LumbridgeDraynorDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getLumbridgeDraynorDiary().setAchievementStage(value, integer, false);
                }
            }
            for (MorytaniaDiaryEntry value : MorytaniaDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getMorytaniaDiary().setAchievementStage(value, integer, false);
                }
            }
            for (VarrockDiaryEntry value : VarrockDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getVarrockDiary().setAchievementStage(value, integer, false);
                }
            }
            for (WesternDiaryEntry value : WesternDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getWesternDiary().setAchievementStage(value, integer, false);
                }
            }
            for (WildernessDiaryEntry value : WildernessDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getWildernessDiary().setAchievementStage(value, integer, false);
                }
            }
            for (KourendDiaryEntry value : KourendDiaryEntry.values()) {
                if (s.equalsIgnoreCase(value.name())) {
                    p.getDiaryManager().getKourendDiary().setAchievementStage(value, integer, false);
                }
            }
        }));
    }

    /**
     * The name of this {@link AchievementDiary}
     */
    private final String name;

    /**
     * The {@link Player} object the {@link AchievementDiary} is responsible for
     */
    public Player player;

    /**
     * A {@link HashSet} representing the various {@link T} achievements the
     * {@link #player} has
     */
    public Set<T> achievements = new HashSet<>();

    /**
     * Creates a new {@link AchievementDiary} implementation (should be effectively immutable)
     *
     * @param name   The name of this {@link AchievementDiary}
     * @param player
     */
    public AchievementDiary(String name, Player player) {
        this.name = name;
        this.player = player;
    }

    public boolean complete(T achievement) {
        boolean success = achievements.add(achievement);
        if (success)
            uponCompletion(achievement);
        return success;
    }

    public final void nonNotifyComplete(T achievement) {
        achievements.add(achievement);
    }

    public final boolean remove(T achievement) {
        return achievements.remove(achievement);
    }

    public void uponCompletion(T achievement) {
        player.sendMessage("<col=8B0000>Well done! You have completed a task in the " + getName() + ". Your Achievement");
        player.sendMessage("<col=8B0000>Diary has been updated.");

        for (ArdougneDiaryEntry easy : ArdougneAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.easyArdy += 1;
                Config.ARDOUGNE_EASY.set(player, player.easyArdy);
                if (player.easyArdy == 4) {
                    Config.ARDOUGNE_EASY_COMPLETED.set(player, 1);
                }
            }
        }

        for (ArdougneDiaryEntry medium : ArdougneAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.medArdy += 1;
                Config.ARDOUGNE_MEDIUM.set(player, player.medArdy);
                if (player.medArdy == 4) {
                    Config.ARDOUGNE_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }

        for (ArdougneDiaryEntry hard : ArdougneAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.hardArdy += 1;
                Config.ARDOUGNE_HARD.set(player, player.hardArdy);
                if (player.hardArdy == 4) {
                    Config.ARDOUGNE_HARD_COMPLETED.set(player, 1);
                }
            }
        }

        for (ArdougneDiaryEntry elite : ArdougneAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.eliteArdy += 1;
                Config.ARDOUGNE_ELITE.set(player, player.eliteArdy);
                if (player.eliteArdy == 3) {
                    Config.ARDOUGNE_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (DesertDiaryEntry easy : DesertAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.desertEasy += 1;
                Config.DESERT_EASY.set(player, player.desertEasy);
                if (player.desertEasy == 5) {
                    Config.DESERT_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (DesertDiaryEntry medium : DesertAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.desertMedium += 1;
                Config.DESERT_MEDIUM.set(player, player.desertMedium);
                if (player.desertMedium == 7) {
                    Config.DESERT_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (DesertDiaryEntry hard : DesertAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.desertHard += 1;
                Config.DESERT_HARD.set(player, player.desertHard);
                if (player.desertHard == 2) {
                    Config.DESERT_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (DesertDiaryEntry elite : DesertAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.desertElite += 1;
                Config.DESERT_ELITE.set(player, player.desertElite);
                if (player.desertElite == 1) {
                    Config.DESERT_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (FaladorDiaryEntry easy : FaladorAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.faladorEasy += 1;
                Config.FALADOR_EASY.set(player, player.faladorEasy);
                if (player.faladorEasy == 7) {
                    Config.FALADOR_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (FaladorDiaryEntry medium : FaladorAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.faladorMedium += 1;
                Config.FALADOR_MEDIUM.set(player, player.faladorMedium);
                if (player.faladorMedium == 6) {
                    Config.FALADOR_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (FaladorDiaryEntry hard : FaladorAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.faladorHard += 1;
                Config.FALADOR_HARD.set(player, player.faladorHard);
                if (player.faladorHard == 4) {
                    Config.FALADOR_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (FaladorDiaryEntry elite : FaladorAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.faladorElite += 1;
                Config.FALADOR_ELITE.set(player, player.faladorElite);
                if (player.faladorElite == 3) {
                    Config.FALADOR_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (FremennikDiaryEntry easy : FremennikAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.fremennikEasy += 1;
                Config.FREMMY_EASY.set(player, player.fremennikEasy);
                if (player.fremennikEasy == 5) {
                    Config.FREMMY_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (FremennikDiaryEntry medium : FremennikAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.fremennikMedium += 1;
                Config.FREMMY_MEDIUM.set(player, player.fremennikMedium);
                if (player.fremennikMedium == 4) {
                    Config.FREMMY_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (FremennikDiaryEntry hard : FremennikAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.fremennikHard += 1;
                Config.FREMMY_HARD.set(player, player.fremennikHard);
                if (player.fremennikHard == 5) {
                    Config.FREMMY_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (FremennikDiaryEntry elite : FremennikAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.fremennikElite += 1;
                Config.FREMMY_ELITE.set(player, player.fremennikElite);
                if (player.fremennikElite == 4) {
                    Config.FREMMY_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (KandarinDiaryEntry easy : KandarinAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.kandarinEasy += 1;
                Config.KANDARIN_EASY.set(player, player.kandarinEasy);
                if (player.kandarinEasy == 4) {
                    Config.KANDARIN_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (KandarinDiaryEntry medium : KandarinAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.kandarinMedium += 1;
                Config.KANDARIN_MEDIUM.set(player, player.kandarinMedium);
                if (player.kandarinMedium == 5) {
                    Config.KANDARIN_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (KandarinDiaryEntry hard : KandarinAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.kandarinHard += 1;
                Config.KANDARIN_HARD.set(player, player.kandarinHard);
                if (player.kandarinHard == 4) {
                    Config.KANDARIN_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (KandarinDiaryEntry elite : KandarinAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.kandarinElite += 1;
                Config.KANDARIN_ELITE.set(player, player.kandarinElite);
                if (player.kandarinElite == 2) {
                    Config.KANDARIN_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (KaramjaDiaryEntry easy : KaramjaAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.karamjaEasy += 1;
                Config.KARAMJA_EASY.set(player, player.karamjaEasy);
                if (player.karamjaEasy == 5) {
                    Config.KARAMJA_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (KaramjaDiaryEntry medium : KaramjaAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.karamjaMedium += 1;
                Config.KARAMJA_MEDIUM.set(player, player.karamjaMedium);
                if (player.karamjaMedium == 3) {
                    Config.KARAMJA_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (KaramjaDiaryEntry hard : KaramjaAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.karamjaHard += 1;
                Config.KARAMJA_HARD.set(player, player.karamjaHard);
                if (player.karamjaHard == 3) {
                    Config.KARAMJA_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (KaramjaDiaryEntry elite : KaramjaAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.karamjaElite += 1;
                Config.KARAMJA_ELITE.set(player, player.karamjaElite);
                if (player.karamjaElite == 3) {
                    Config.KARAMJA_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (LumbridgeDraynorDiaryEntry easy : LumbridgeDraynorAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.lumbEasy += 1;
                Config.LUMBRIDGE_EASY.set(player, player.lumbEasy);
                if (player.lumbEasy == 7) {
                    Config.LUMBRIDGE_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (LumbridgeDraynorDiaryEntry medium : LumbridgeDraynorAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.lumbMedium += 1;
                Config.LUMBRIDGE_MEDIUM.set(player, player.lumbMedium);
                if (player.lumbMedium == 5) {
                    Config.LUMBRIDGE_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (LumbridgeDraynorDiaryEntry hard : LumbridgeDraynorAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.lumbHard += 1;
                Config.LUMBRIDGE_HARD.set(player, player.lumbHard);
                if (player.lumbHard == 3) {
                    Config.LUMBRIDGE_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (LumbridgeDraynorDiaryEntry elite : LumbridgeDraynorAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.lumbElite += 1;
                Config.LUMBRIDGE_ELITE.set(player, player.lumbElite);
                if (player.lumbElite == 3) {
                    Config.LUMBRIDGE_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (MorytaniaDiaryEntry easy : MorytaniaAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.morytaniaEasy += 1;
                Config.MORYTANIA_EASY.set(player, player.morytaniaEasy);
                if (player.morytaniaEasy == 3) {
                    Config.MORYTANIA_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (MorytaniaDiaryEntry medium : MorytaniaAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.morytaniaMedium += 1;
                Config.MORYTANIA_MEDIUM.set(player, player.morytaniaMedium);
                if (player.morytaniaMedium == 4) {
                    Config.MORYTANIA_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (MorytaniaDiaryEntry hard : MorytaniaAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.morytaniaHard += 1;
                Config.MORYTANIA_HARD.set(player, player.morytaniaHard);
                if (player.morytaniaHard == 4) {
                    Config.MORYTANIA_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (MorytaniaDiaryEntry elite : MorytaniaAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.morytaniaElite += 1;
                Config.MORYTANIA_ELITE.set(player, player.morytaniaElite);
                if (player.morytaniaElite == 3) {
                    Config.MORYTANIA_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (VarrockDiaryEntry easy : VarrockAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.varrockEasy += 1;
                Config.VARROCK_EASY.set(player, player.varrockEasy);
                if (player.varrockEasy == 7) {
                    Config.VARROCK_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (VarrockDiaryEntry medium : VarrockAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.varrockMedium += 1;
                Config.VARROCK_MEDIUM.set(player, player.varrockMedium);
                if (player.varrockMedium == 5) {
                    Config.VARROCK_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (VarrockDiaryEntry hard : VarrockAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.varrockHard += 1;
                Config.VARROCK_HARD.set(player, player.varrockHard);
                if (player.varrockHard == 2) {
                    Config.VARROCK_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (VarrockDiaryEntry elite : VarrockAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.varrockElite += 1;
                Config.VARROCK_ELITE.set(player, player.varrockElite);
                if (player.varrockElite == 3) {
                    Config.VARROCK_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (WesternDiaryEntry easy : WesternAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.westernEasy += 1;
                Config.WESTERN_PROV_EASY.set(player, player.westernEasy);
                if (player.westernEasy == 6) {
                    Config.WESTERN_PROV_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (WesternDiaryEntry medium : WesternAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.westernMedium += 1;
                Config.WESTERN_PROV_MEDIUM.set(player, player.westernMedium);
                if (player.westernMedium == 4) {
                    Config.WESTERN_PROV_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (WesternDiaryEntry hard : WesternAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.westernHard += 1;
                Config.WESTERN_PROV_HARD.set(player, player.westernHard);
                if (player.westernHard == 3) {
                    Config.WESTERN_PROV_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (WesternDiaryEntry elite : WesternAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.westernElite += 1;
                Config.WESTERN_PROV_ELITE.set(player, player.westernElite);
                if (player.westernElite == 2) {
                    Config.WESTERN_PROV_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
        for (WildernessDiaryEntry easy : WildernessAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.wildyEasy += 1;
                Config.WILDERNESS_EASY.set(player, player.wildyEasy);
                if (player.wildyEasy == 7) {
                    Config.WILDERNESS_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (WildernessDiaryEntry medium : WildernessAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.wildyMedium += 1;
                Config.WILDERNESS_MEDIUM.set(player, player.wildyMedium);
                if (player.wildyMedium == 7) {
                    Config.WILDERNESS_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (WildernessDiaryEntry hard : WildernessAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.wildyHard += 1;
                Config.WILDERNESS_HARD.set(player, player.wildyHard);
                if (player.wildyHard == 7) {
                    Config.WILDERNESS_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (WildernessDiaryEntry elite : WildernessAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.wildyElite += 1;
                Config.WILDERNESS_ELITE.set(player, player.wildyElite);
                if (player.wildyElite == 6) {
                    Config.WILDERNESS_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }

        for (KourendDiaryEntry easy : KourendAchievementDiary.EASY_TASKS) {
            if (easy.name().equalsIgnoreCase(achievement.name())) {
                player.kourendEasy += 1;
                Config.KOUREND_EASY.set(player, player.kourendEasy);
                if (player.kourendEasy == 8) {
                    Config.KOUREND_EASY_COMPLETED.set(player, 1);
                }
            }
        }
        for (KourendDiaryEntry medium : KourendAchievementDiary.MEDIUM_TASKS) {
            if (medium.name().equalsIgnoreCase(achievement.name())) {
                player.kourendMedium += 1;
                Config.KOUREND_MEDIUM.set(player, player.kourendMedium);
                if (player.kourendMedium == 8) {
                    Config.KOUREND_MEDIUM_COMPLETED.set(player, 1);
                }
            }
        }
        for (KourendDiaryEntry hard : KourendAchievementDiary.HARD_TASKS) {
            if (hard.name().equalsIgnoreCase(achievement.name())) {
                player.kourendHard += 1;
                Config.KOUREND_HARD.set(player, player.kourendHard);
                if (player.kourendHard == 8) {
                    Config.KOUREND_HARD_COMPLETED.set(player, 1);
                }
            }
        }
        for (KourendDiaryEntry elite : KourendAchievementDiary.ELITE_TASKS) {
            if (elite.name().equalsIgnoreCase(achievement.name())) {
                player.kourendElite += 1;
                Config.KOUREND_ELITE.set(player, player.kourendElite);
                if (player.kourendElite == 8) {
                    Config.KOUREND_ELITE_COMPLETED.set(player, 1);
                    player.getInventory().addOrDrop(30307, 1);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean hasDone(T entry) {
        return get(entry).isPresent();
    }

    public boolean hasDone(Set<T> entries) {
        boolean containsAll = true;
        for (T entry : entries) {
            if (!achievements.contains(entry)) {
                containsAll = false;
            }
        }
        return containsAll;
    }

    public void forEach(Consumer<T> action) {
        achievements.forEach(entry -> action.accept(entry));
    }

    public Optional<T> get(T entry) {
        return achievements.stream().filter(some -> some.equals(entry))
                .findAny();
    }

    public Set<T> getAchievements() {
        return achievements;
    }

    public Player getPlayer() {
        return player;
    }
}
