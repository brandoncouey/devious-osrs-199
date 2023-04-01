package io.ruin.model.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;

public class TicketSystem {

	public static final List<String> tickets = new LinkedList<String>();


	public static void filterTickets() {
		tickets.removeIf(player -> World.getPlayer(player) == null);
	}
	

	public static void answerTicket(Player player) {
		if (!player.isStaff()) return;
		filterTickets();
		if (tickets.isEmpty()) {
			player.sendMessage("There are currently no tickets open at this time.");
			return;
		}
		while(tickets.size() > 0) {
			String ticket = tickets.get(0);// next in line
			Player target = World.getPlayer(ticket);
			if (target == null) 
				continue; // shouldn't happen but k
			player.startEvent(e -> {
				player.clearHits();
				player.getPacketSender().fadeOut();
				e.delay(1);
				player.getMovement().teleport(target.getPosition());
				player.lastTeleport = target.getPosition();
				player.getPacketSender().fadeIn();
				player.unlock();
			});
			tickets.remove(ticket);
			break;
		}
	}
	
	public static void requestTicket(Player player) {
		filterTickets();
		if(tickets.contains(player.getName())) {
			player.sendMessage("You cannot send a ticket yet!");
			return;
		}
		tickets.add(player.getName());
		for(Player mod : World.getPlayerStream().collect(Collectors.toList())) {
			if(mod == null || !mod.isStaff())
				continue;
			mod.sendMessage("A ticket has been submitted by " + player.getName() + "! ::ticket to solve it!");
			mod.sendMessage("There is currently "+tickets.size()+" tickets active.");
		}
	}
}
