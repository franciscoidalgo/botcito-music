package com.frappu.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

  private final AudioPlayer audioPlayer;

  private final BlockingQueue<AudioTrack> queue;

  public TrackScheduler(AudioPlayer audioPlayer) {
    this.audioPlayer = audioPlayer;
    this.queue = new LinkedBlockingQueue<>();
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    player.startTrack(this.queue.poll(), false);
  }

  public void addToQueue(AudioTrack audioTrack) {
    if (!this.audioPlayer.startTrack(audioTrack, true)) {
      this.queue.offer(audioTrack);
    }
  }

  public void stopMusic() {
    this.queue.clear();
    this.audioPlayer.stopTrack();
  }

  public boolean togglePause() {
    boolean isPaused = this.audioPlayer.isPaused();
    this.audioPlayer.setPaused(!isPaused);
    return isPaused;
  }

  public void skip() {
    this.audioPlayer.stopTrack();
  }

  public AudioTrack getPlayingTrack() {
    return this.audioPlayer.getPlayingTrack();
  }

  public List<AudioTrack> getQueue() {
    List<AudioTrack> fullQueue = new ArrayList<>(this.queue.size() + 1);
    fullQueue.add(this.audioPlayer.getPlayingTrack());
    fullQueue.addAll(this.queue);

    return fullQueue;
  }

}
