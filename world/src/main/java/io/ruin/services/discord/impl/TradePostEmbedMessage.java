package io.ruin.services.discord.impl;

import io.ruin.api.utils.ServerWrapper;
import io.ruin.model.World;
import io.ruin.services.discord.Webhook;
import io.ruin.services.discord.util.Embed;
import io.ruin.services.discord.util.Footer;
import io.ruin.services.discord.util.Message;
import io.ruin.services.discord.util.Thumbnail;

public class TradePostEmbedMessage {

    public static void sendDiscordMessage(String discordMessage, int itemId) {
        if (!World.isLive()) {
            return;
        }
        try {
            Webhook webhook = new Webhook("https://discord.com/api/webhooks/997514792837324872/8JyA-fPaSwA4OP4WCMnrMxX8vCLjHyHFkHZb2kRODgGTK52Yt2kDzUoxDImUx6KyNsbc");
            Message message = new Message();

            Embed embedMessage = new Embed();
            embedMessage.setTitle("New item listed in the Trade Post!");
            embedMessage.setDescription(discordMessage + " in the trading post!");
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
