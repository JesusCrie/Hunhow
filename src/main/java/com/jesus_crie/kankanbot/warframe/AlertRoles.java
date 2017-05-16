package com.jesus_crie.kankanbot.warframe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesus_crie.kankanbot.HunhowBot;
import com.jesus_crie.kankanbot.logging.LogFrom;
import com.jesus_crie.kankanbot.logging.Logger;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AlertRoles {

    private static HashMap<String, Role> roles = new HashMap<>();

    public static void init() {
        Guild guild = HunhowBot.getInstance().getJda().getGuildById("275314962468175872");

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<String> rs = mapper.readValue(new URL("http://www.jesus-crie.com/warframe/roles.json"), new TypeReference<List<String>>() {});
            rs.forEach(role ->
                roles.put(role, guild.getRolesByName(role, true).get(0))
            );
            Logger.info("Roles loaded !", LogFrom.WARFRAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getRolesAsStringList() {
        return new ArrayList<>(roles.keySet());
    }

    public static Role getRoleByName(String name) {
        return roles.getOrDefault(name, null);
    }
}
