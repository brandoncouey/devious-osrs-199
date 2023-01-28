package io.ruin.services.dupewatch;


import io.ruin.Server;
import io.ruin.api.utils.TimeUtils;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.containers.bank.Bank;
import io.ruin.services.discord.DiscordConnection;
import net.dv8tion.//jda.api.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DupeWarden {

  //  private static final Logger logger = LoggerFactory.getLogger(DupeWarden.class);

    private static final int RAW_INC_THRESHOLD = 750_000; // 750k of alch value is a LOT.
    private static final int RAW_INC_CRITICAL = 5_000_000; // 5 million!? of alch value is a HUUUGE LOT.

    private long inv = -1;
    private long equip = -1;
    private long bank = -1;
    private long total = -1;
    private int ticksUntil = 5;
    private boolean exclude;

    public void update(Player player) {
        // Don't log admin+++ as they can spawn
        if (player.isAdmin())
            return;
        if (player.isStaff())
            return;

        if (ticksUntil-- > 0) return;

        long newinv = totalPriceOf(player.getInventory());
        long newbank = totalPriceOfBank(player.getBank());
        long newequip = totalPriceOf(player.getEquipment());
        long newtotal = newinv + newbank + newequip;

        if (!exclude) {
            if (total != -1 && criticalIncrease(total, newtotal)) {
                reportCritical(player, newinv, newbank, newequip, newtotal);
            } else if (total != -1 && significantIncrease(total, newtotal)) {
                report(player, newinv, newbank, newequip, newtotal);
            }
        }

        total = newtotal;
        inv = newinv;
        equip = newequip;
        bank = newbank;

        exclude = false;
        ticksUntil = 5;
    }


    public void exclude() {
        exclude = true;
    }

    private void report(Player player, long inv, long bank, long equip, long total) {
     //   logger.warn("Dupe Warden spotted significant increase of wealth for {}. {}/{}/{} ({}) => {}/{}/{} ({}), increase of {}/{}/{} ({}).", player.getName(), this.inv, this.equip, this.bank, this.total, inv, equip, bank, total, signed(inv - this.inv), signed(equip - this.equip), signed(bank - this.bank), signed(total - this.total));
    }

    private void reportCritical(Player player, long inv, long bank, long equip, long total) {
      //  logger.warn("Dupe Warden spotted CRITICAL increase of wealth for {}! {}/{}/{} ({}) => {}/{}/{} ({}), increase of {}/{}/{} ({}).", player.getName(), this.inv, this.equip, this.bank, this.total, inv, equip, bank, total, signed(inv - this.inv), signed(equip - this.equip), signed(bank - this.bank), signed(total - this.total));

        World.players.forEach(p -> {
            if (p != null && p.isStaff()) {
                p.sendMessage(Color.DARK_RED, "[DUPE WARDEN] CRITICAL WARNING!");
                p.sendMessage(Color.DARK_RED, player.getName() + " Age(" + TimeUtils.fromMs(player.playTime * Server.tickMs(), false) + ") Wealth " + NumberFormat.getInstance().format(this.total) + " => " + NumberFormat.getInstance().format(total) + " (" + signed(total - this.total) + ")");
            }
        });

        if (!World.isDev()) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date date = new Date();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("[DUPE WARDEN! CRITICAL WARNING!]");
            eb.addField("Username: ", player.getName(), true);
            eb.addField("Duped: ", " Wealth: " + NumberFormat.getInstance().format(this.total) + " => " + NumberFormat.getInstance().format(total) + " (" + signed(total - this.total) + ")", true);
            eb.addField("Coordinates: ", "X:" + player.getPosition().getX() + " Y:" + player.getPosition().getY() + " Z:" + player.getPosition().getZ(), true);
            eb.addField("Date: ", formatter.format(date), true);
            eb.setColor(new java.awt.Color(0xB00D03));
            //DiscordConnection.//jda.getTextChannelById("991831245841506424").sendMessage(eb.build()).queue();
        }
        long oldinv = this.inv;
        long oldequip = this.equip;
        long oldbank = this.bank;

        Server.siteDb.execute(con -> {
            try (PreparedStatement s = con.prepareStatement("INSERT INTO dupewatch_triggers(account_id, account_name, account_ip, account_uuid, old_inventory, old_equipment, old_bank, new_inventory, new_equipment, new_bank, x, y, z, timestamp) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
                s.setInt(1, player.getUserId());
                s.setString(2, player.getName());
                s.setString(3, player.getIp());
                s.setString(4, player.getUUID());
                s.setLong(5, oldinv);
                s.setLong(6, oldequip);
                s.setLong(7, oldbank);
                s.setLong(8, inv);
                s.setLong(9, equip);
                s.setLong(10, bank);
                s.setInt(11, player.getPosition().getX());
                s.setInt(12, player.getPosition().getY());
                s.setInt(13, player.getPosition().getZ());
                s.setTimestamp(14, Timestamp.valueOf(LocalDateTime.now()));
                s.addBatch();
                s.executeBatch();
            }

        });
    }

    private String signed(long num) {
        if (num < 0) {
            return NumberFormat.getInstance().format(num);
        } else {
            return "+" + NumberFormat.getInstance().format(num);
        }
    }

    private static long totalPriceOf(ItemContainer container) {
        long l = 0;
        for (Item item : container.getItems()) {
            if (item != null) {
                l += ((long) item.getDef().highAlchValue * item.getAmount());
            }
        }
        return l;
    }

    private static long totalPriceOfBank(Bank container) {
        long l = 0;
        for (Item item : container.getItems()) {
            if (item != null) {
                l += ((long) item.getDef().highAlchValue * item.getAmount());
            }
        }
        return l;
    }

    private static boolean significantIncrease(long old, long current) {
        return current - old > (RAW_INC_THRESHOLD);
    }

    private static boolean criticalIncrease(long old, long current) {
        return current - old > (RAW_INC_CRITICAL);
    }
}
