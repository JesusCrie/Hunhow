package com.jesus_crie.kankanbot.permission;

import com.jesus_crie.kankanbot.command.AccessLevel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class Permissions {

    private static Role godTier;
    private static Role bot;
    private static Role admin;
    private static Role modo;

    public static void init(Guild guild) {
        godTier = guild.getRoleById("261095191895605249"); //Skynet
        admin = guild.getRoleById("219802715142750208"); //Sorcier impérial
        modo = guild.getRoleById("219802855513653249"); //Grand Dragon
        bot = guild.getRoleById("219806885467914260"); //T-1000
    }

    public static AccessLevel getUserAccess(Member member) {
        if (member.getUser().getId().equalsIgnoreCase("182547138729869314"))
            return AccessLevel.GODTIER;

        AccessLevel higher = AccessLevel.EVERYONE;
        if (member.getRoles().contains(modo))
            higher = AccessLevel.MODO;
        if (member.getRoles().contains(bot))
            higher = AccessLevel.BOT;
        if (member.getRoles().contains(admin))
            higher = AccessLevel.ADMIN;
        if (member.getRoles().contains(godTier))
            higher = AccessLevel.GODTIER;

        return higher;
    }
}
