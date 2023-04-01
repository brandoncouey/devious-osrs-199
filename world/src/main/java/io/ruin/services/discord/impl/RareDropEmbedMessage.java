package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Footer;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;

public class RareDropEmbedMessage {

    public static void sendDiscordMessage(String discordMessage, String npcDescriptiveName, int itemId, int npcKC) {
        if (!World.isLive()) {
            return;
        }
        try {
            Webhook webhook = new Webhook("https://discordapp.com/api/webhooks/1069523503633661982/QxvHXRlc7vYdCx9_bD_E2NsZLWGZHUy1c_8oSCGQVCbF-w7XK-eIS1FPmeSnfrAVlpOd");
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("New Loot! Rare drop received!");
            embedMessage.setDescription(discordMessage + " from " + npcDescriptiveName + " At KC: " + npcKC);
            embedMessage.setColor(8917522);

            /*
             * Thumbnail
             */
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setUrl("https://static.runelite.net/cache/item/icon/" + itemId + ".png");
            embedMessage.setThumbnail(thumbnail);

            /*
             * Footer
             */
            Footer footer = new Footer();
            footer.setText(World.type.getWorldName() + " - Let the Wilderness Consume you!");
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
