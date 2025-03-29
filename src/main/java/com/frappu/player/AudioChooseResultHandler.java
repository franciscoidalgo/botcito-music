package com.frappu.player;

import com.frappu.utils.ColorConstants;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioChooseResultHandler implements AudioLoadResultHandler {

  private static final Logger log = LoggerFactory.getLogger(AudioChooseResultHandler.class);

  private final GuildMusicManager musicManager;

  private final StringSelectInteractionEvent event;

  public AudioChooseResultHandler(GuildMusicManager musicManager, StringSelectInteractionEvent event) {
    this.musicManager = musicManager;
    this.event = event;
  }

  @Override
  public void trackLoaded(AudioTrack audioTrack) {
    this.musicManager
        .getTrackScheduler()
        .addToQueue(audioTrack);
    AudioTrackInfo info = audioTrack.getInfo();
    EmbedBuilder embedBuilder = new EmbedBuilder()
        .setColor(ColorConstants.OK)
        .setTitle("Playing")
        .setDescription(info.title + " by " + info.author);
    this.event
        .getHook()
        .sendMessageEmbeds(embedBuilder.build())
        .queue();

  }

  @Override
  public void playlistLoaded(AudioPlaylist audioPlaylist) {
    AudioTrack audioTrack = audioPlaylist
        .getTracks()
        .get(0);
    this.trackLoaded(audioTrack);
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

}
