package io.ruin.central.model.social;

import com.google.gson.annotations.Expose;

public class SocialContainer {

    @ Expose protected int friendsCount;
    @ Expose public SocialMember[] friends;
    @ Expose protected int ignoresCount;
    @ Expose public SocialMember[] ignores;

    public boolean addFriend(SocialMember friend) {
        if (this.friends == null) {
            this.friends = new SocialMember[400];
        }
        if (this.friendsCount >= this.friends.length || this.isFriend(friend.name)) {
            return false;
        }
        this.friends[this.friendsCount++] = friend;
        return true;
    }

    public String deleteFriend(String name) {
        for (int index = 0; index < this.friendsCount; ++index) {
            SocialMember friend = this.friends[index];
            if (!name.equalsIgnoreCase(friend.name)) continue;
            --this.friendsCount;
            for (int i = index; i < this.friendsCount; ++i) {
                this.friends[i] = this.friends[i + 1];
            }
            this.friends[this.friendsCount] = null;
            return friend.name;
        }
        return "";
    }

    public SocialMember getFriend(String name) {
        if (this.friends == null) {
            return null;
        }
        for (SocialMember friend : this.friends) {
            if (friend == null || !friend.name.equalsIgnoreCase(name)) continue;
            return friend;
        }
        return null;
    }

    public boolean isFriend(String name) {
        return this.getFriend(name) != null;
    }

    public void addIgnore(SocialMember ignore) {
        if (this.ignores == null) {
            this.ignores = new SocialMember[400];
        }
        if (this.ignoresCount >= this.ignores.length || this.isIgnored(ignore.name)) {
            return;
        }
        this.ignores[this.ignoresCount++] = ignore;
    }

    public void deleteIgnore(String name) {
        for (int index = 0; index < this.ignoresCount; ++index) {
            SocialMember ignore = this.ignores[index];
            if (!name.equalsIgnoreCase(ignore.name)) continue;
            --this.ignoresCount;
            if (this.ignoresCount - index >= 0)
                System.arraycopy(this.ignores, index + 1, this.ignores, index, this.ignoresCount - index);
            this.ignores[this.ignoresCount] = null;
            return;
        }
    }

    public SocialMember getIgnore(String username) {
        if (this.ignores == null) {
            return null;
        }
        for (SocialMember ignore : this.ignores) {
            if (ignore == null || !username.equalsIgnoreCase(ignore.name)) continue;
            return ignore;
        }
        return null;
    }

    public boolean isIgnored(String username) {
        return this.getIgnore(username) != null;
    }
}