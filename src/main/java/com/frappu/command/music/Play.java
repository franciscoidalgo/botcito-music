package com.frappu.command.music;

import com.frappu.command.ICommand;
import com.frappu.player.MusicManagers;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Play implements ICommand {

  private static final List<OptionData> OPTIONS = Collections.singletonList(
      new OptionData(OptionType.STRING, "name", "Name of the song", true));

  @Override
  public String getName() {
    return "play";
  }

  @Override
  public String getDescription() {
    return "Play music";
  }

  @Override
  public List<OptionData> getOptions() {
    return OPTIONS;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    String name = event
        .getOption("name")
        .getAsString();
    try {
      new URI(name);
    } catch (URISyntaxException e) {
      name = "ytmsearch:" + name;
    }

    event
        .deferReply()
        .queue();
    MusicManagers musicManagers = MusicManagers.get();
    musicManagers.play(event, name);
  }

  @Override
  public void onSelection(StringSelectInteractionEvent event) {
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
          .getGuild()
          .getAudioManager()
          .openAudioConnection(memberVoiceState.getChannel());
    } else {
      if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
        event
            .reply("You need to be in the same channel as me")
            .queue();
        return;
      }
    }

    String selectedUri = event
        .getSelectedOptions()
        .get(0)
        .getValue();

    event
        .deferReply()
        .queue();
    MusicManagers musicManagers = MusicManagers.get();
    musicManagers.play(event, selectedUri);
  }

}
