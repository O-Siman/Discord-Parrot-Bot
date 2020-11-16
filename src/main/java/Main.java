import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Main extends ListenerAdapter {
    public static JDA api;
    public static String token;

    public static void main(String[] args) {
        token = ReadToken.readToken();
        try {
            startBot();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static boolean startBot() throws InterruptedException {

        JDABuilder preBuild = JDABuilder.createDefault(token);
        preBuild.setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, "p!help"));
        try {
            api = preBuild.build();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        api.addEventListener(new Main());
        api.awaitReady();
        return true;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        //Vars
        Message message = event.getMessage();
        String messageRaw = message.getContentRaw();
        String messageNoPrefix = messageRaw.substring(2);
        TextChannel channel = event.getChannel();

        //Don't reply to bots
        if (event.getAuthor().isBot())
            return;

        if (messageRaw.startsWith("p!")) {
            /*
            Role parrotControllerRole = event.getGuild().getRoleById("776528941098729473");

            if (!event.getMember().getRoles().contains(parrotControllerRole))
                return;
                */

            if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE))
                return;

            if (messageRaw.contains("p!help")) {
                HelpCommand.helpCommand(event);
                return;
            }

            //If no channel's mentioned
            if (message.getMentionedChannels().isEmpty()) {
                channel.sendMessage(messageNoPrefix).queue();
                //If an image is attached
                message.delete().queue();
            }
            //If a channel is mentioned
            else {
                TextChannel channelToSendMessage = message.getMentionedChannels().get(0);

                String newMessage = messageNoPrefix.replaceFirst("<#[0-9]+>", "");
                channelToSendMessage.sendMessage(newMessage).queue();
                message.delete().queue();
            }
        }
    }
}
