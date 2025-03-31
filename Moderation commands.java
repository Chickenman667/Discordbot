// File Name: DiscordBot.java

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {

    public static void main(String[] args) throws Exception {
        // Create a new JDABuilder instance to set up the bot
        JDABuilder builder = JDABuilder.createDefault("YOUR_BOT_TOKEN");
        builder.addEventListeners(new DiscordBot());
        builder.build();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] commandParts = event.getMessage().getContentRaw().split(" ");
        String command = commandParts[0].toLowerCase();

        if (command.equals("!ban") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            if (commandParts.length > 1) {
                Member memberToBan = event.getMessage().getMentionedMembers().get(0);
                event.getGuild().ban(memberToBan, 0).queue();
                event.getChannel().sendMessage(memberToBan.getUser().getAsTag() + " has been banned!").queue();
            }
        } else if (command.equals("!kick") && event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            if (commandParts.length > 1) {
                Member memberToKick = event.getMessage().getMentionedMembers().get(0);
                event.getGuild().kick(memberToKick).queue();
                event.getChannel().sendMessage(memberToKick.getUser().getAsTag() + " has been kicked!").queue();
            }
        } else if (command.equals("!mute") && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            if (commandParts.length > 1) {
                Member memberToMute = event.getMessage().getMentionedMembers().get(0);
                Role mutedRole = event.getGuild().getRolesByName("Muted", true).get(0);
                event.getGuild().addRoleToMember(memberToMute, mutedRole).queue();
                event.getChannel().sendMessage(memberToMute.getUser().getAsTag() + " has been muted!").queue();
            }
        } else if (command.equals("!unmute") && event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            if (commandParts.length > 1) {
                Member memberToUnmute = event.getMessage().getMentionedMembers().get(0);
                Role mutedRole = event.getGuild().getRolesByName("Muted", true).get(0);
                event.getGuild().removeRoleFromMember(memberToUnmute, mutedRole).queue();
                event.getChannel().sendMessage(memberToUnmute.getUser().getAsTag() + " has been unmuted!").queue();
            }
        }
    }
}
