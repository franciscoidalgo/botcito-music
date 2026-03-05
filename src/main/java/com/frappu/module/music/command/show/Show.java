package com.frappu.module.music.command.show;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.player.GuildMusicManager;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import jakarta.inject.Singleton;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Singleton
public class Show implements ICommand {

  @Override
  public String getName() {
    return "show";
  }

  @Override
  public String getDescription() {
    return "Que onda";
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    if (!BotUtils.validateIsInSameChannel(event)) {
      return;
    }

    GuildMusicManager guildMusicManager = MusicManagers
        .get()
        .getGuildMusicManager(event.getGuild());
    AudioTrack audioTrack = guildMusicManager
        .getTrackScheduler()
        .getPlayingTrack();
    if (audioTrack == null) {
      event
          .reply("I am not playing anything")
          .queue();
      return;
    }
    AudioTrackInfo info = audioTrack
        .getInfo();
    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.INFO)
        .setTitle("Currently Playing")
        .setDescription("**Name:** " + info.title)
        .appendDescription("\n**Author:** " + BotUtils.removeAuthorSuffix(info.author))
        .appendDescription("\n**Length:** " + BotUtils.getFormattedLength(info.length))
        .appendDescription("\n**URL:** " + info.uri);
    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
