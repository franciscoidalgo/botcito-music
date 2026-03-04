package com.frappu.module.music.command.pause;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.player.GuildMusicManager;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.module.music.player.TrackScheduler;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Pause implements ICommand {

  @Override
  public String getName() {
    return "toggle-pause";
  }

  @Override
  public String getDescription() {
    return "Banca un toque";
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
    boolean isPaused = trackScheduler.togglePause();
    String message = isPaused ? "Music paused" : "Music resumed";

    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.WARN)
        .setTitle("Pause")
        .setDescription(message);

    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
