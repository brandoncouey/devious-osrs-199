package io.ruin.api.protocol.world;

public enum WorldType {
    ECO("Devious", "https://deviousps.com"),
    BETA("Devious BETA", "https://deviousps.com"),
    PVP("Devious", "https://deviousps.com"),
    DEADMAN("DeviousDMM", "https://deviousps.com"),
    DEV("Development", "https://deviousps.com");

    WorldType(String worldName, String websiteUrl) {
        this.worldName = worldName;
        this.websiteUrl = websiteUrl;
    }

    public boolean isDeadman() {
        return this == DEADMAN;
    }

    private String worldName, websiteUrl;

    public String getWorldName() {
        return worldName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }
}