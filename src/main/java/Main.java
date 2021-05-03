import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Main extends ListenerAdapter {
    public static JDA api;
    public static String token;

    public static void main(String[] args) {
        token = System.getenv("PARROT_TOKEN");
        try {
            startBot();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static boolean startBot() throws InterruptedException {

        JDABuilder preBuild = JDABuilder.createDefault(token);
        preBuild.setActivity(Activity.of(Activity.ActivityType.WATCHING, "for p!"));
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
        TextChannel channel = event.getChannel();

        //Don't reply to bots
        if (event.getAuthor().isBot())
            return;

        if (!messageRaw.startsWith("p!"))
            return;

        String messageNoPrefix = messageRaw.substring(2);

            /*
            Role parrotControllerRole = event.getGuild().getRoleById("776528941098729473");

            if (!event.getMember().getRoles().contains(parrotControllerRole))
                return;
                */

//        if (!event.getMember().hasPermission(Permission.MESSAGE_MANAGE))
//            return;

        //If message with no whitespace is "p!"
        if (messageRaw.trim().equals("p!") || messageRaw.contains("p!help")) {
            HelpCommand.helpCommand(event.getAuthor());
            event.getChannel().sendMessage("Check your DMs for the help menu.").queue();
            return;
        }

        //If no channel's mentioned
        if (message.getMentionedChannels().isEmpty()) {
            //If attachment is attached
            List<Message.Attachment> attachments = message.getAttachments();
            if (!attachments.isEmpty()) {
                //TODO: Attachments to file, sendMessage with .addFile
            }

            channel.sendMessage(messageNoPrefix).queue();
        }

        //If a channel is mentioned
        else {
            TextChannel channelToSendMessage = message.getMentionedChannels().get(0);

            String newMessage = messageNoPrefix.replaceFirst("<#[0-9]+>", "");
            channelToSendMessage.sendMessage(newMessage).queue();
        }

        //Delete original message
        message.delete().queue();
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        //Vars
        Message message = event.getMessage();
        String messageRaw = message.getContentRaw();
        PrivateChannel channel = event.getChannel();

        //Don't reply to bots
        if (event.getAuthor().isBot())
            return;

        if (messageRaw.contains("p!help") || messageRaw.contains("help") || messageRaw.contains("p!")) {
            HelpCommand.helpCommand(event.getAuthor());
        }
    }
}
