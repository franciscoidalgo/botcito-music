package com.frappu.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

public class GuildMusicManager {

  private final TrackScheduler trackScheduler;

  private final AudioForwarder audioForwarder;

  public GuildMusicManager(AudioPlayerManager audioPlayerManager, Guild guild) {
    AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
    this.trackScheduler = new TrackScheduler(audioPlayer);
    audioPlayer.addListener(this.trackScheduler);
    this.audioForwarder = new AudioForwarder(audioPlayer, guild);
  }

  public TrackScheduler getTrackScheduler() {
    return this.trackScheduler;
  }

  public AudioForwarder getAudioForwarder() {
    return this.audioForwarder;
  }

}
