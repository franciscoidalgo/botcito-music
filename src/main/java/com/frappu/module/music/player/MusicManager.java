package com.frappu.module.music.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

@Singleton
public class MusicManager {

  private final Map<Long, GuildMusicManager> musicManagers;

  private final AudioPlayerManager audioPlayerManager;

  @Inject
  public MusicManager(AudioPlayerManager audioPlayerManager) {
    this.musicManagers = new ConcurrentHashMap<>();
    this.audioPlayerManager = audioPlayerManager;
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
