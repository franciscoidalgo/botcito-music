package com.frappu.command.music;

import com.frappu.command.ICommand;
import com.frappu.player.GuildMusicManager;
import com.frappu.player.MusicManagers;
import com.frappu.utils.ColorConstants;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Queue implements ICommand {

  @Override
  public String getName() {
    return "queue";
  }

  @Override
  public String getDescription() {
    return "Que sigue";
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
    List<AudioTrack> queue = guildMusicManager
        .getTrackScheduler()
        .getQueue();
    if (queue == null || queue.isEmpty()) {
      event
          .reply("There's nothing in queue")
          .queue();
      return;
    }
    AudioTrack audioTrack = queue.get(0);
    EmbedBuilder embedBuilder = new EmbedBuilder()
        .setColor(ColorConstants.INFO);
    embedBuilder.setTitle("Queue");
    embedBuilder.setDescription("1. " + audioTrack
        .getInfo().title);
    for (int i = 1; i < queue.size(); i++) {
      audioTrack = queue.get(i);
      embedBuilder.appendDescription("\n" + (i + 1) + ". " + audioTrack.getInfo().title);
    }
    event
        .replyEmbeds(embedBuilder.build())
        .queue();
  }

}
