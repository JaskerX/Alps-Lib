package com.alpsbte.alpslib.utils;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

/**
 * Interface for objects that have associated wiki documentation.
 * <p>
 * Implementing classes should provide a wiki page URL and can use the default
 * {@link #sendWikiLink(Player)} method to send documentation links to players.
 * </p>
 *
 * @version 1.0.0
 * @author Zoriot
 */
public interface WikiDocumented {

    /**
     * @return the wiki page URL for this object
     */
    String getWikiPage();

    /**
     * Sends more information about this object to a player.
     */
    default void sendWikiLink(@NonNull Player p) {
        p.sendMessage(" ");
        p.sendMessage("§cFor more information take a look here:");
        p.sendMessage("§c" + getWikiPage());
    }
}

