package com.frappu.module.music.player;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import jakarta.inject.Singleton;

public class PlayerModule extends AbstractModule {

  @Provides
  @Singleton
  AudioPlayerManager audioPlayerManager() {
    AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    YoutubeAudioSourceManager youtubeAudioSourceManager = new YoutubeAudioSourceManager();
    audioPlayerManager.registerSourceManager(youtubeAudioSourceManager);
    AudioSourceManagers.registerRemoteSources(audioPlayerManager, YoutubeAudioSourceManager.class);

    return audioPlayerManager;
  }

}
