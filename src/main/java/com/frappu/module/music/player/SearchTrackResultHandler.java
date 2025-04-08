package com.frappu.module.music.player;

import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchTrackResultHandler implements AudioLoadResultHandler {

  private static final Logger log = LoggerFactory.getLogger(SearchTrackResultHandler.class);

  private final GuildMusicManager musicManager;

  private final SlashCommandInteractionEvent event;

  public SearchTrackResultHandler(GuildMusicManager musicManager, SlashCommandInteractionEvent event) {
    this.musicManager = musicManager;
    this.event = event;
  }

  @Override
  public void trackLoaded(AudioTrack audioTrack) {
    this.musicManager
        .getTrackScheduler()
        .addToQueue(audioTrack);
    AudioTrackInfo info = audioTrack.getInfo();
    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.OK)
        .setTitle("Playing")
        .setDescription(BotUtils.getSongLabel(info));
    this.event
        .getHook()
        .sendMessageEmbeds(embedBuilder.build())
        .queue();
  }

  @Override
  public void playlistLoaded(AudioPlaylist audioPlaylist) {
    if (audioPlaylist.isSearchResult()) {
      this.showOptionsMenu(audioPlaylist.getTracks());
    } else {
      this.queuePlaylist(audioPlaylist, this.musicManager.getTrackScheduler());
    }
  }

  @Override
  public void noMatches() {
    this.event
        .getHook()
        .sendMessage("No track found")
        .queue();
  }

  @Override
  public void loadFailed(FriendlyException e) {
    this.event
        .getHook()
        .sendMessage("There was an error loading the track")
        .queue();
    log.error("Error loading track", e);
  }

  public void showOptionsMenu(List<AudioTrack> tracks) {
    List<AudioTrack> audioOptions = tracks
        .subList(0, 5);
    StringSelectMenu.Builder selectMenu = StringSelectMenu
        .create(this.event.getName());
    audioOptions.forEach(audioTrack -> {
      AudioTrackInfo trackInfo = audioTrack
          .getInfo();
      String label = BotUtils.getSongLabel(trackInfo);
      if (label.length() >= 100) {
        label = label.substring(0, 99);
      }
      selectMenu.addOption(label, trackInfo.uri);
    });
    this.event
        .getHook()
        .sendMessage("Choose a song")
        .addActionRow(selectMenu.build())
        .queue();
  }

  public void queuePlaylist(AudioPlaylist audioPlaylist, TrackScheduler trackScheduler) {
    audioPlaylist
        .getTracks()
        .forEach(trackScheduler::addToQueue);
    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.INFO)
        .setTitle("Playing")
        .setDescription(BotUtils.getPlaylistLabel(audioPlaylist));
    this.event
        .getHook()
        .sendMessageEmbeds(embedBuilder.build())
        .queue();
  }

}
