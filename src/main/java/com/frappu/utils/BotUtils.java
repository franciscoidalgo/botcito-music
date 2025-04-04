package com.frappu.utils;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.apache.commons.lang3.time.DurationFormatUtils;

public final class BotUtils {

  private static final String TOPIC_SUFFIX = " - Topic";

  private BotUtils() {
  }

  public static String getSongLabel(AudioTrackInfo trackInfo) {
    return trackInfo.title + " - " + removeAuthorSuffix(trackInfo.author);
  }

  public static String getPlaylistLabel(AudioPlaylist playlist) {
    long totalDuration = playlist
        .getTracks()
        .stream()
        .map(AudioTrack::getDuration)
        .reduce(0L, Long::sum);
    return playlist.getName() + " (" + getFormattedLength(totalDuration) + ")";
  }

  public static String getFormattedLength(long lengthMillis) {
    return DurationFormatUtils.formatDuration(lengthMillis, "mm:ss", true);
  }

  public static String removeAuthorSuffix(String author) {
    if (author.endsWith(TOPIC_SUFFIX)) {
      return author.substring(0, author.length() - TOPIC_SUFFIX.length());
    }
    return author;
  }

  public static boolean validateAndConnectToVoice(IReplyCallback event) {
    Member member = event.getMember();
    GuildVoiceState memberVoiceState = member.getVoiceState();

    if (!memberVoiceState.inAudioChannel()) {
      event
          .reply("You need to be in a voice channel")
          .queue();
      return false;
    }

    Member self = event
        .getGuild()
        .getSelfMember();
    GuildVoiceState selfVoiceState = self.getVoiceState();

    if (!selfVoiceState.inAudioChannel()) {
      event
          .getGuild()
          .getAudioManager()
          .openAudioConnection(memberVoiceState.getChannel());
    } else {
      if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
        event
            .reply("You need to be in the same channel as me")
            .queue();
        return false;
      }
    }

    return true;
  }

  public static EmbedBuilder buildEmbed(BotColor color) {
    return new EmbedBuilder()
        .setColor(color.get());
  }

}
