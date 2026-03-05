package com.frappu.module.music.command.stop;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.player.GuildMusicManager;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.module.music.player.TrackScheduler;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Singleton
public class Stop implements ICommand {

  @Override
  public String getName() {
    return "stop";
  }

  @Override
  public String getDescription() {
    return "Pero para un cachito";
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    if (!BotUtils.validateIsInSameChannel(event)) {
      return;
    }

    GuildMusicManager guildMusicManager = MusicManagers
        .get()
        .getGuildMusicManager(event.getGuild());
    TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
    trackScheduler.stopMusic();

    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.ERROR)
        .setTitle("Stop")
        .setDescription("Stopped playing music");

    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
