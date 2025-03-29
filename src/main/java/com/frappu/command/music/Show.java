package com.frappu.command.music;

import com.frappu.command.ICommand;
import com.frappu.player.GuildMusicManager;
import com.frappu.player.MusicManagers;
import com.frappu.utils.ColorConstants;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.apache.commons.lang3.time.DurationFormatUtils;

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
  public List<OptionData> getOptions() {
    return null;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    Member member = event.getMember();
    GuildVoiceState memberVoiceState = member.getVoiceState();

    if (!memberVoiceState.inAudioChannel()) {
      event
          .reply("You need to be in a voice channel")
          .queue();
      return;
    }

    Member self = event
        .getGuild()
        .getSelfMember();
    GuildVoiceState selfVoiceState = self.getVoiceState();

    if (!selfVoiceState.inAudioChannel()) {
      event
          .reply("I am not in an audio channel")
          .queue();
      return;
    }

    if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
      event
          .reply("You are not in the same channel as me")
          .queue();
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
    String length = DurationFormatUtils.formatDuration(info.length, "mm:ss", true);
    EmbedBuilder embedBuilder = new EmbedBuilder()
        .setColor(ColorConstants.INFO);
    embedBuilder.setTitle("Currently Playing");
    embedBuilder.setDescription("**Name:** " + info.title);
    embedBuilder.appendDescription("\n**Author:** " + info.author);
    embedBuilder.appendDescription("\n**Length:** " + length);
    embedBuilder.appendDescription("\n**URL:** " + info.uri);
    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
