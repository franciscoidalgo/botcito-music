package com.frappu.player;

import com.frappu.utils.TimeUtils;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import java.nio.ByteBuffer;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;

public class AudioForwarder implements AudioSendHandler {

  private final AudioPlayer player;

  private final Guild guild;

  private final ByteBuffer buffer;

  private final MutableAudioFrame frame;

  private long lastActive;

  public AudioForwarder(AudioPlayer player, Guild guild) {
    this.player = player;
    this.guild = guild;
    this.buffer = ByteBuffer.allocate(1024);
    this.frame = new MutableAudioFrame();
    this.frame.setBuffer(this.buffer);
    this.lastActive = TimeUtils.getEpochSeconds();
  }

  @Override
  public boolean canProvide() {
    boolean canProvide = this.player.provide(this.frame);
    long currentSecondsEpoch = TimeUtils.getEpochSeconds();
    if (!canProvide) {
      long inactiveTimeSeconds = currentSecondsEpoch - this.lastActive;
      if (inactiveTimeSeconds >= 10) {
        this.guild
            .getAudioManager()
            .closeAudioConnection();
      }

    } else {
      this.lastActive = currentSecondsEpoch;
    }

    return canProvide;
  }

  @Override
  public ByteBuffer provide20MsAudio() {
    return this.buffer.flip();
  }

  @Override
  public boolean isOpus() {
    return true;
  }

}
