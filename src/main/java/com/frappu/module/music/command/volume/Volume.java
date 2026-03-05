package com.frappu.module.music.command.volume;

import com.frappu.app.command.ICommand;
import com.frappu.app.command.IOption;
import com.frappu.module.music.player.GuildMusicManager;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.module.music.player.TrackScheduler;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

@Singleton
public class Volume implements ICommand {

  @Override
  public String getName() {
    return "volume";
  }

  @Override
  public String getDescription() {
    return "Bajale un toque";
  }

  @Override
  public IOption[] getOptions() {
    return VolumeOptions.values();
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    if (!BotUtils.validateIsInSameChannel(event)) {
      return;
    }

    OptionMapping volumeOption = event.getOption(VolumeOptions.VOLUME_VALUE.getName());
    GuildMusicManager guildMusicManager = MusicManagers
        .get()
        .getGuildMusicManager(event.getGuild());
    TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
    if (volumeOption == null) {
      EmbedBuilder embedBuilder = BotUtils
          .buildEmbed(BotColor.INFO)
          .setTitle("Volume")
          .setDescription("Current volume is `%d`".formatted(trackScheduler.getVolume()));
      event
          .replyEmbeds(embedBuilder.build())
          .queue();
      return;
    }

    int volume = volumeOption.getAsInt();
    trackScheduler.setVolume(volume);

    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.WARN)
        .setTitle("Volume")
        .setDescription("Volume set to `%d`".formatted(volume));
    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
