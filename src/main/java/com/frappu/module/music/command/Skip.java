package com.frappu.module.music.command;

import com.frappu.app.command.ICommand;
import com.frappu.module.music.player.GuildMusicManager;
import com.frappu.module.music.player.MusicManagers;
import com.frappu.module.music.player.TrackScheduler;
import com.frappu.utils.BotColor;
import com.frappu.utils.BotUtils;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Skip implements ICommand {

  @Override
  public String getName() {
    return "skip";
  }

  @Override
  public String getDescription() {
    return "El que sigue";
  }

  @Override
  public List<OptionData> getOptions() {
    return null;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    Member member = event.getMember();
    GuildVoiceState memberVoiceState = member.getVoiceState();

    if (!memberVoiceState.inAudioChannel()) {
      event
          .reply("You need to be in a voice channel")
          .queue();
      return;
    }

    Member self = event
        .getGuild()
        .getSelfMember();
    GuildVoiceState selfVoiceState = self.getVoiceState();

    if (!selfVoiceState.inAudioChannel()) {
      event
          .reply("I am not in an audio channel")
          .queue();
      return;
    }

    if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
      event
          .reply("You are not in the same channel as me")
          .queue();
      return;
    }

    GuildMusicManager guildMusicManager = MusicManagers
        .get()
        .getGuildMusicManager(event.getGuild());
    TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();
    trackScheduler.skip();
    EmbedBuilder embedBuilder = BotUtils
        .buildEmbed(BotColor.WARN)
        .setTitle("Skip")
        .setDescription("Song skipped");

    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
