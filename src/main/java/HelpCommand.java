import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class HelpCommand {
    public static void helpCommand(GuildMessageReceivedEvent event) {
        //Open DM
        event.getAuthor().openPrivateChannel().queue((dmChannel) -> {
            EmbedBuilder eb = new EmbedBuilder();

            //Make embed
            eb.setTitle("Help Menu");
            eb.setAuthor("Parrot Bot", null, "https://cdn.discordapp.com/avatars/776521471392350288/43cc5b41517bea49d0d2551e3322cd84.png?size=256");
            eb.addField("p![Message]",
                    "Type `p!` followed by your message that you want the bot to parrot.", true);
            eb.addField("p![Channel] [Message]",
                    "Type `p!` followed by the channel where you want the bot to send the message, then your message that you want the bot to parrot.", true);

            MessageEmbed finalEmbed = eb.build();

            //Send embed
            dmChannel.sendMessage(finalEmbed).queue();
        });

        event.getChannel().sendMessage("Check your DMs for the help menu.").queue();
    }
}
