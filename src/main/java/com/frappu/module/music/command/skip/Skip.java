package com.frappu.module.music.command.skip;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.player.GuildMusicManager;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.module.music.player.TrackScheduler;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Skip implements ICommand {

  @Override
  public String getName() {
    return "skip";
  }

  @Override
  public String getDescription() {
    return "El que sigue";
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
    trackScheduler.skip();
    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.WARN)
        .setTitle("Skip")
        .setDescription("Song skipped");

    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
