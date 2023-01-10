package io.ruin.central.model.social.clan;

import io.ruin.central.model.social.SocialMember;

public class ClanContainer {
    public int ccMembersCount;
    public SocialMember[] ccMembers;

    public boolean addClanMember(SocialMember member) {
        if (this.ccMembers == null) {
            this.ccMembers = new SocialMember[9000];
        }
        if (this.ccMembersCount >= this.ccMembers.length || this.inClan(member.name)) {
            return false;
        }
        this.ccMembers[this.ccMembersCount++] = member;
        return true;
    }

    public void removeClanMember(String name) {
        for (int index = 0; index < this.ccMembersCount; ++index) {
            SocialMember member = this.ccMembers[index];
            if (!name.equalsIgnoreCase(member.name)) continue;
            --this.ccMembersCount;
            for (int i = index; i < this.ccMembersCount; ++i) {
                this.ccMembers[i] = this.ccMembers[i + 1];
            }
            this.ccMembers[this.ccMembersCount] = null;
            return;
        }
    }

    public SocialMember getClanMember(String username) {
        if (this.ccMembers == null) {
            return null;
        }
        for (SocialMember member : this.ccMembers) {
            if (member == null || !member.name.equalsIgnoreCase(username)) continue;
            return member;
        }
        return null;
    }

    public boolean inClan(String name) {
        return this.getClanMember(name) != null;
    }
}