package io.ruin.model.clan;

import io.ruin.model.entity.player.Player;
import lombok.Getter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Clan implements Serializable {

    private static final long serialVersionUID = 4062231702422979939L;


    @Getter
    private final String name;

    @Getter
    private double experience;

    @Getter
    private String clanLeaderUsername;

    @Getter
    private int threadId;

    @Getter
    private String clanMessage;

    @Getter
    private final long created;

    @Getter
    private final List<ClanMember> members = new ArrayList<>();

    @Getter
    private final List<String> banned = new ArrayList<>();

    public Clan(String name, Player clanLeaderUsername) {
        this.name = name;
        this.created = System.currentTimeMillis();
        this.clanLeaderUsername = clanLeaderUsername.getName();
        setClanLeaderUsername(addMember(clanLeaderUsername, ClanMember.Ranks.LEADER));
    }

    public void setClanLeaderUsername(ClanMember member) {
        clanLeaderUsername = member.getUsername();
    }

    public ClanMember addMember(Player player, ClanMember.Ranks rank) {
        ClanMember member = new ClanMember(player.getName(), rank);
        members.add(member);
        return member;
    }

    public String getDateCreated() {
        return new SimpleDateFormat("dd MMM").format(new Date(created));
    }

    public int getLevel() {
        return 1;
    }
}
