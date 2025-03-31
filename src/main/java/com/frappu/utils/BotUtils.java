package com.frappu.utils;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

public final class BotUtils {

  private static final String TOPIC_SUFFIX = " - Topic";

  private BotUtils() {
  }

  public static String getSongLabel(AudioTrackInfo trackInfo) {
    return trackInfo.title + " - " + removeAuthorSuffix(trackInfo.author);
  }

  public static String removeAuthorSuffix(String author) {
    if (author.endsWith(TOPIC_SUFFIX)) {
      return author.substring(0, author.length() - TOPIC_SUFFIX.length());
    }
    return author;
  }

}
