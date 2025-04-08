package com.frappu.module.music.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public enum MusicManagers {
  INSTANCE;

  private final Map<Long, GuildMusicManager> musicManagers;

  private final AudioPlayerManager audioPlayerManager;

  MusicManagers() {
    this.musicManagers = new ConcurrentHashMap<>();
    this.audioPlayerManager = new DefaultAudioPlayerManager();
    YoutubeAudioSourceManager youtubeAudioSourceManager = new YoutubeAudioSourceManager();
    this.audioPlayerManager.registerSourceManager(youtubeAudioSourceManager);
    AudioSourceManagers.registerRemoteSources(this.audioPlayerManager,
                                              com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager.class);
  }

  public static MusicManagers get() {
    return INSTANCE;
  }

  public GuildMusicManager getGuildMusicManager(Guild guild) {
    return this.musicManagers.computeIfAbsent(guild.getIdLong(), guildId -> {
      GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager, guild);
      guild
          .getAudioManager()
          .setSendingHandler(guildMusicManager.getAudioForwarder());

      return guildMusicManager;
    });
  }

  public void play(SlashCommandInteractionEvent event, String trackUrl) {
    GuildMusicManager musicManager = this.getGuildMusicManager(event.getGuild());
    this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new SearchTrackResultHandler(musicManager, event));

  }

  public void play(StringSelectInteractionEvent event, String trackUrl) {
    GuildMusicManager musicManager = this.getGuildMusicManager(event.getGuild());
    this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new SelectTrackResultHandler(musicManager, event));
  }

}
