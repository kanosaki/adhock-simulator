package adsim.core;

import org.jruby.embed.ScriptingContainer;

import lombok.*;

public class RubyScenario implements IScenario {
    @Getter
    private String name;

    @Override
    public void init(ISession session) {
        val jruby = new ScriptingContainer();

    }

}
