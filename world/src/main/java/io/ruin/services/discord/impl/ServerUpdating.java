package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Footer;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;

public class ServerUpdating {

    public static void sendDiscordMessage(String shutdownMessage) {
        if (!World.isLive()) {
            return;
        }
        try {
            Webhook webhook = new Webhook("https://discord.com/api/webhooks/994601036515909762/HaKJLhgHAThocOcygcMViyIruGwlHflizFGXtmomK3dp7W36uBqs6rDZR5iJ7LZBFm8e");
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("Server is Being Updated");
            embedMessage.setDescription(shutdownMessage);
            embedMessage.setColor(8917522);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://static.runelite.net/cache/item/icon/" + 10890 + ".png");
            embedMessage.setThumbnail(thumbnail);

            /*
             * Footer
             */
            Footer footer = new Footer();
            footer.setText(World.type.getWorldName() + " - Waste Your Life One Skill At A Time!");
            embedMessage.setFooter(footer);

            /*
             * Fire the message
             */
            message.setEmbeds(embedMessage);
            webhook.sendMessage(message.toJson());
        } catch (Exception e) {
            ServerWrapper.logError("Failed to send discord embed", e);
        }
    }

}
