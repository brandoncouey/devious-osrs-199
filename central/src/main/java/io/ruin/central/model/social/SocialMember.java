package io.ruin.central.model.social;

import com.google.gson.annotations.Expose;
import io.ruin.central.model.Player;

import java.util.Objects;

public class SocialMember {

    @ Expose public String name;
    @ Expose public String lastName;
    protected boolean newName;
    @ Expose public SocialRank rank;
    public int worldId = -1;

    public SocialMember(String username, SocialRank rank) {
        this.name = username;
        this.lastName = "";
        this.rank = rank;
    }

    public SocialMember(int userId, String name, int worldId) {
        this.name = name;
        this.lastName = "";
        this.worldId = worldId;
    }

    public void resend() {
        this.worldId = -1;
    }

    protected void checkName(Player player) {
        if (!Objects.equals(this.name, player.name)) {
            System.err.print(this.name);
            this.lastName = this.name;
            this.name = player.name;
            this.newName = true;
        }
    }

    public boolean sendNewName() {
        if (this.newName) {
            this.newName = false;
            this.resend();
            return true;
        }
        return false;
    }
}