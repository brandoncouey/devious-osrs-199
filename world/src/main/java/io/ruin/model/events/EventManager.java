package io.ruin.model.events;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;
import io.ruin.model.World;
import io.ruin.model.activities.ActivityTimer;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Utils;
import kotlin.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {

    private static final int MAIN_INTERFACE_ID = 1034;

    private static EventManager instance;

    private static final int[] EVENT_SLOT_INDEX = new int[] { 31, 34, 37, 40, 43, 46, 49, 52, 55, 58, 61, 64, 67, 70, 73, 76, 79, 82, 85, 88 };

    private static final int EVENT_SLOT_START = 30;

    private static final int MAX_EVENT_SLOTS = 20;

    @Expose public List<Event> PVP_EVENTS = new CopyOnWriteArrayList<>();

    @Expose public List<Event> PVM_EVENTS = new CopyOnWriteArrayList<>();

    @Expose public List<Event> SKILLING_EVENTS = new CopyOnWriteArrayList<>();

    @Expose public List<Event> MINIGAME_EVENTS = new CopyOnWriteArrayList<>();

    private static final File events_folder = new File("data/events/");

    public static void main(String[] args) {
        getInstance().start(Event.Task.FASTEST_JAD_KILL, System.currentTimeMillis() * 1000, System.currentTimeMillis() * 1000);
        getInstance().start(Event.Task.MOST_MONEY_PKED, System.currentTimeMillis() * 1000, System.currentTimeMillis() * 1000);
        getInstance().start(Event.Task.MOST_MAGIC_LOGS_BURNED, System.currentTimeMillis() * 1000, System.currentTimeMillis() * 1000);
        save();
    }

    private interface DefaultAction extends InterfaceAction {

        void handle(Player player, int childId, int option, int slot, int itemId);

        default void handleClick(Player player, int childId, int option, int slot, int itemId) {
            handle(player, childId, option, slot, itemId);
        }
    }


    static {


        InterfaceHandler.register(MAIN_INTERFACE_ID, i -> {
            i.actions[17] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                //Pvp Events
                EventManager.getInstance().show(p, Event.Type.PVP);
            };

            i.actions[20] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                //PvM Events
                EventManager.getInstance().show(p, Event.Type.PVM);
            };

            i.actions[23] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                //Skilling Events
                EventManager.getInstance().show(p, Event.Type.SKILLING);
            };

            i.actions[26] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                //Minigame Events
                EventManager.getInstance().show(p, Event.Type.MINIGAME);
            };

            i.actions[112] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                //Add Rewards
                getInstance().addRewards(p);
            };

            i.actions[115] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                //Leaderboards
                getInstance().viewLeaderboards(p);
            };

            for (int index = 0; index < EVENT_SLOT_INDEX.length; index++) {
                int finalIndex = index;
                i.actions[EVENT_SLOT_INDEX[index]] = (DefaultAction) (p, childId, option, slot, itemId) -> {
                    getInstance().display(p, finalIndex);
                };
            }

        });
    }

    public void checkEvents() {

        //Check broadcast
        for (Event event : PVM_EVENTS) {
            if (event == null) continue;
            if (!event.isBroadcastedStarted() && isActive(event.getTask())) {
                event.setBroadcastedStarted(true);
                Broadcast.WORLD.sendNews(Utils.formatString(event.getTask().getType().name()) + " Event: " + event.getTask().getName() + " has just started and will end at " + event.getEndTimeFormatted() + ". View the Event Board to see the rewards!");
            }
        }
        for (Event event : PVP_EVENTS) {
            if (event == null) continue;
            if (!event.isBroadcastedStarted() && isActive(event.getTask())) {
                event.setBroadcastedStarted(true);
                Broadcast.WORLD.sendNews(Utils.formatString(event.getTask().getType().name()) + " Event: " + event.getTask().getName() + " has just started and will end at " + event.getEndTimeFormatted() + ". View the Event Board to see the rewards!");
            }
        }
        for (Event event : SKILLING_EVENTS) {
            if (event == null) continue;
            if (!event.isBroadcastedStarted() && isActive(event.getTask())) {
                event.setBroadcastedStarted(true);
                Broadcast.WORLD.sendNews(Utils.formatString(event.getTask().getType().name()) + " Event: " + event.getTask().getName() + " has just started and will end at " + event.getEndTimeFormatted() + ". View the Event Board to see the rewards!");
            }
        }
        for (Event event : MINIGAME_EVENTS) {
            if (event == null) continue;
            if (!event.isBroadcastedStarted() && isActive(event.getTask())) {
                event.setBroadcastedStarted(true);
                Broadcast.WORLD.sendNews(Utils.formatString(event.getTask().getType().name()) + " Event: " + event.getTask().getName() + " has just started and will end at " + event.getEndTimeFormatted() + ". View the Event Board to see the rewards!");
            }
        }

        //Checked finished
        for (Event event : PVM_EVENTS) {
            if (event == null) continue;
            if (System.currentTimeMillis() >= event.getEndMilis() && !event.isRewarded()) {
                finishEvent(event.getTask());
                PVM_EVENTS.remove(event);
            }
        }
        for (Event event : PVP_EVENTS) {
            if (event == null) continue;
            if (System.currentTimeMillis() >= event.getEndMilis() && !event.isRewarded()) {
                finishEvent(event.getTask());
                PVP_EVENTS.remove(event);
            }
        }
        for (Event event : SKILLING_EVENTS) {
            if (event == null) continue;
            if (System.currentTimeMillis() >= event.getEndMilis() && !event.isRewarded()) {
                finishEvent(event.getTask());
                SKILLING_EVENTS.remove(event);
            }
        }
        for (Event event : MINIGAME_EVENTS) {
            if (event == null) continue;
            if (System.currentTimeMillis() >= event.getEndMilis() && !event.isRewarded()) {
                finishEvent(event.getTask());
                MINIGAME_EVENTS.remove(event);
            }
        }

    }

    public void start(Event.Task task, long start, long end) {
        if (isPending(task) || isActive(task)) {
            System.out.println("It's pending, nope");
            return;
        }
        final Event event = new Event(task, start, end);
        switch (task.getType()) {
            case PVP:
                PVP_EVENTS.add(event);
                break;
            case PVM:
                PVM_EVENTS.add(event);
                break;
            case SKILLING:
                SKILLING_EVENTS.add(event);
                break;
            case MINIGAME:
                MINIGAME_EVENTS.add(event);
                break;
        }
        Broadcast.WORLD.sendNews(task.getType() + " EVENT: " + task.getName() + " has just been added to the board and will start at " + event.getStartMilis() + ". View the Event Board for more details!");
    }

    public boolean isPending(Event.Task task) {
        switch (task.getType()) {
            case PVP: {
                for (Event event : PVP_EVENTS) {
                    if (event.getTask() == task) {
                        return true;
                    }
                }
                break;
            }
            case PVM: {
                for (Event event : PVM_EVENTS) {
                    if (event.getTask() == task) {
                        return true;
                    }
                }
                break;
            }
            case SKILLING: {
                for (Event event : SKILLING_EVENTS) {
                    if (event.getTask() == task) {
                        return true;
                    }
                }
                break;
            }
            case MINIGAME: {
                for (Event event : MINIGAME_EVENTS) {
                    if (event.getTask() == task) {
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    public static boolean isActive(Event.Task task) {
        switch (task.getType()) {
            case PVP: {
                for (Event event : getInstance().PVP_EVENTS) {
                    if (event.getTask() == task) {
                        long startTime = event.getStartMilis();
                        long endTime = event.getEndMilis();
                        return System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime;
                    }
                }
                break;
            }
            case PVM: {
                for (Event event : getInstance().PVM_EVENTS) {
                    if (event.getTask() == task) {
                        long startTime = event.getStartMilis();
                        long endTime = event.getEndMilis();
                        return System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime;
                    }
                }
                break;
            }
            case SKILLING: {
                for (Event event : getInstance().SKILLING_EVENTS) {
                    if (event.getTask() == task) {
                        long startTime = event.getStartMilis();
                        long endTime = event.getEndMilis();
                        return System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime;
                    }
                }
                break;
            }
            case MINIGAME: {
                for (Event event : getInstance().MINIGAME_EVENTS) {
                    if (event.getTask() == task) {
                        long startTime = event.getStartMilis();
                        long endTime = event.getEndMilis();
                        return System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime;
                    }
                }
                break;
            }
        }
        return false;
    }

    public void display(Player player, int index) {
        Event event = null;
        switch (player.currentlyViewingEventType) {
            case PVP:
                if (PVP_EVENTS.size() >= index && PVP_EVENTS.size() > 0)
                    event = PVP_EVENTS.get(index);
                break;
            case PVM:
                if (PVM_EVENTS.size() >= index && PVM_EVENTS.size() > 0)
                    event = PVM_EVENTS.get(index);
                break;
            case SKILLING:
                if (SKILLING_EVENTS.size() >= index && SKILLING_EVENTS.size() > 0)
                    event = SKILLING_EVENTS.get(index);
                break;
            case MINIGAME:
                if (MINIGAME_EVENTS.size() >= index && MINIGAME_EVENTS.size() > 0)
                    event = MINIGAME_EVENTS.get(index);
                break;

        }
        if (event == null) {
            player.getPacketSender().sendString(MAIN_INTERFACE_ID, 97, "Unavailable");
            player.getPacketSender().sendString(MAIN_INTERFACE_ID, 99, "None description.");

            player.getPacketSender().sendString(MAIN_INTERFACE_ID, 105, "N/A");
            player.getPacketSender().sendString(MAIN_INTERFACE_ID, 111, "N/A");
            return;
        }
        player.getPacketSender().sendString(MAIN_INTERFACE_ID, 97, event.getTask().getName());
        player.getPacketSender().sendString(MAIN_INTERFACE_ID, 99, event.getTask().getDescription());

        player.getPacketSender().sendString(MAIN_INTERFACE_ID, 105, event.getStartTimeFormatted());
        player.getPacketSender().sendString(MAIN_INTERFACE_ID, 111, event.getEndTimeFormatted());

    }

    public void viewLeaderboards(Player player) {
        player.getPacketSender().setHidden(MAIN_INTERFACE_ID, 131, false);
    }

    public void addRewards(Player player) {
        //TODO check admin perms
        player.getPacketSender().setHidden(MAIN_INTERFACE_ID, 196, false);
    }

    public void show(Player player, Event.Type type) {
        player.openInterface(InterfaceType.MAIN, MAIN_INTERFACE_ID);
        player.currentlyViewingEventType = type;
        display(player, 0);
        switch (type) {
            case PVP: {
                for (int index = 0; index < PVP_EVENTS.size(); index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), false);
                    player.getPacketSender().sendString(MAIN_INTERFACE_ID, 32 + (index * 3), PVP_EVENTS.get(index).getTask().getName());
                }
                for (int index = PVP_EVENTS.size(); index <= MAX_EVENT_SLOTS; index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), true);
                }
                break;
            }
            case PVM: {
                for (int index = 0; index < PVM_EVENTS.size(); index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), false);
                    player.getPacketSender().sendString(MAIN_INTERFACE_ID, 32 + (index * 3), PVM_EVENTS.get(index).getTask().getName());
                }
                for (int index = PVM_EVENTS.size(); index <= MAX_EVENT_SLOTS; index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), true);
                }
                break;
            }
            case SKILLING: {
                for (int index = 0; index < SKILLING_EVENTS.size(); index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), false);
                    player.getPacketSender().sendString(MAIN_INTERFACE_ID, 32 + (index * 3), SKILLING_EVENTS.get(index).getTask().getName());
                }
                for (int index = SKILLING_EVENTS.size(); index <= MAX_EVENT_SLOTS; index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), true);
                }
                break;
            }
            case MINIGAME: {
                for (int index = 0; index < MINIGAME_EVENTS.size(); index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), false);
                    player.getPacketSender().sendString(MAIN_INTERFACE_ID, 32 + (index * 3), MINIGAME_EVENTS.get(index).getTask().getName());
                }
                for (int index = MINIGAME_EVENTS.size(); index <= MAX_EVENT_SLOTS; index++) {
                    player.getPacketSender().setHidden(MAIN_INTERFACE_ID, EVENT_SLOT_START + (index * 3), true);
                }
                break;
            }
        }
    }

    public Event getEvent(Event.Task task) {
        switch (task.getType()) {
            case PVP:
                for (Event event : PVP_EVENTS) {
                    if (event.getTask() == task)
                        return event;
                }
            case PVM:
                for (Event event : PVM_EVENTS) {
                    if (event.getTask() == task)
                        return event;
                }
            case SKILLING:
                for (Event event : SKILLING_EVENTS) {
                    if (event.getTask() == task)
                        return event;
                }
            case MINIGAME:
                for (Event event : MINIGAME_EVENTS) {
                    if (event.getTask() == task)
                        return event;
                }

        }
        return null;
    }

    public void progressEventTimer(Event.Task task, Player player, long time) {
        Event event = getEvent(task);
        if (isActive(task) && event != null) {
            long previousTime = -1;
            String currentFastestPlayer = getFastestPlayer(task);
            if (event.getResults().containsKey(player.getName())) {
                previousTime = event.getResults().get(player.getName());
            }
            event.getResults().put(player.getName(), time);
            if (previousTime == -1 || time < previousTime) {
                event.getResults().put(player.getName(), time);
            }
            String newFastestPlayer = getFastestPlayer(task);
            assert newFastestPlayer != null;
            if (!newFastestPlayer.equalsIgnoreCase(currentFastestPlayer)) {
                Broadcast.WORLD.sendNews(newFastestPlayer + " has just taken the lead for the " + Utils.formatString(event.getTask().getType().name()) + " event: " + event.getTask().getName() + " with a new record of " + ActivityTimer.format(event.getResults().get(player.getName())) + ".");
            }
        }
    }

    private String getFastestPlayer(Event.Task task) {
        Event event = getEvent(task);
        if (event == null) return "";
        Map.Entry<String, Long> bestTime = null;
        for (Map.Entry<String, Long> entry : event.getResults().entrySet()) {
            if (bestTime == null || entry.getValue() < bestTime.getValue()) {
                bestTime = entry;
            }
        }
        return bestTime == null ? "" : bestTime.getKey();
    }

    public void progressEvent(Event.Task task, Player player) {
        progressEvent(task, player, 1);
    }

    public void progressEvent(Event.Task task, Player player, int amount) {
        Event event = getEvent(task);
        if (isActive(task) && event != null) {
            progress(player, event, amount);
        }
    }

    public void resetProgress(Event.Task task, Player player) {
        Event event = getEvent(task);
        if (isActive(task) && event != null) {
            event.getProgress().remove(player.getName());
        }
    }

    public long getResult(Event.Task task, Player player) {
        Event event = getEvent(task);
        if (isActive(task) && event != null) {
            return event.getResults().getOrDefault(player.getName(), 0L);
        }
        return -1;//Not active event
    }

    public void setResult(Event.Task task, Player player, long amount) {
        Event event = getEvent(task);
        if (isActive(task) && event != null) {
            final Pair<String, Long> currentTopPlayer = getTopPlayer(event);
            if (event.getResults().containsKey(player.getName())) {
                event.getResults().put(player.getName(), (long) amount);
            } else {
                event.getResults().put(player.getName(), (long) amount);
            }
            final Pair<String, Long> newTopPlayer = getTopPlayer(event);
            if (!currentTopPlayer.getFirst().equalsIgnoreCase(newTopPlayer.getFirst())) {
                String bestFormatted;
                if (event.getTask().isTimedEvent()) {
                    bestFormatted = newTopPlayer.getSecond() + " seconds.";
                } else {
                    bestFormatted = Math.round(newTopPlayer.getSecond()) + ".";
                }
                Broadcast.WORLD.sendNews(newTopPlayer.getFirst() + " has just taken the lead for the " + Utils.formatString(event.getTask().getType().name()) + " event: " + event.getTask().getName() + " with a new record of " + bestFormatted);
            }
        }
    }

    public long getProgress(Event.Task task, Player player) {
        Event event = getEvent(task);
        if (isActive(task) && event != null) {
            return event.getProgress().getOrDefault(player.getName(), 0L);
        }
        return -1;//Not active event
    }

    public void checkKill(Player player, NPC npc) {
        for (Event event : PVM_EVENTS) {
            if (event == null || !isActive(event.getTask())) continue;
            if (npc.getDef().name.toLowerCase().contains(event.getTask().getIdentifier().toLowerCase())) {
                //Wilderness Checks
                switch (event.getTask()) {
                    case MOST_WILDY_DRAGONS_KILLED:
                    case MOST_WILDY_ELDER_CHAOS_DRUIDS_KILLED: {
                        if (player.wildernessLevel == 0)
                            return;
                    }
                }
                progress(player, event, 1);
                return;
            }
        }
    }

    private void progress(Player player, Event event, int amount) {
        final Pair<String, Long> currentTopPlayer = getTopPlayer(event);
        if (event.getResults().containsKey(player.getName())) {
            event.getResults().put(player.getName(), event.getResults().get(player.getName()) + amount);
        } else {
            event.getResults().put(player.getName(), (long) amount);
        }
        final Pair<String, Long> newTopPlayer = getTopPlayer(event);
        if (!currentTopPlayer.getFirst().equalsIgnoreCase(newTopPlayer.getFirst())) {
            String bestFormatted;
            if (event.getTask().isTimedEvent()) {
                bestFormatted = newTopPlayer.getSecond() + " seconds.";
            } else {
                bestFormatted = Math.round(newTopPlayer.getSecond()) + ".";
            }
            Broadcast.WORLD.sendNews(newTopPlayer.getFirst() + " has just taken the lead for the " + event.getTask().getType().name().toLowerCase() + " event: " + event.getTask().getName() + " with a new record of " + bestFormatted);
        }
    }

    public Pair<String, Long> getTopPlayer(Event event) {
        String current = "";
        long currentBest = -1;
        for (Map.Entry<String, Long> progress : event.getResults().entrySet()) {
            if (currentBest == -1) {
                current = progress.getKey();
                currentBest = progress.getValue();
                continue;
            }
            if (event.getTask().isTimedEvent()) {
                if (progress.getValue() < currentBest) {
                    current = progress.getKey();
                    currentBest = progress.getValue();
                }
            } else {
                if (progress.getValue() > currentBest) {
                    current = progress.getKey();
                    currentBest = progress.getValue();
                }
            }
        }
        return new Pair<>(current, currentBest);
    }

    public void finishEvent(Event.Task task) {
        Event event = getEvent(task);
        if (event != null) {
            Pair<String, Long> topResult = getTopPlayer(event);
            if (topResult.getFirst().equals("")) return;
            final Player topPlayer = World.getPlayer(topResult.getFirst());
            if (topPlayer != null) {
                Config.OSRS_GP_AVAILABLE.set(topPlayer, Config.OSRS_GP_AVAILABLE.get(topPlayer) + event.getTask().getRsgpReward());
                topPlayer.sendMessage("<col=1da406>Congratulations! You've placed first place on " + Utils.formatString(event.getTask().name()) + ", You've been rewarded " + event.getTask().getRsgpReward() + "M Oldschool Runescape GP. Please talk with a moderator about collecting your GP.");
            } else {
                //TODO load the character and set the osrsgp reward
            }
            Broadcast.WORLD.sendNews(Utils.formatString(task.getType().name()) + " Event: " + task.getName() + " has just ended! " + topResult.getFirst() + " placed 1st place in the event with a winning result of " + (event.getTask().isTimedEvent() ? ActivityTimer.format(topResult.getSecond()) : topResult.getSecond()) + ".");
        }
    }

    public static void save() {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(instance);
            if(!events_folder.exists() && !events_folder.mkdirs())
                throw new IOException("events directory could not be created!");
            Files.write(new File(events_folder, "07events.json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            Server.logError(e.getMessage());
        }

    }

    public static void load() {
        File file = new File(events_folder, "07events.json");
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String json = new String(bytes);
                instance = JsonUtils.GSON_EXPOSE_PRETTY.fromJson(json, EventManager.class);
                Server.println("Successfully loaded 07 events!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static EventManager getInstance() {
        if (instance == null)
            instance = new EventManager();
        return instance;
    }


}
