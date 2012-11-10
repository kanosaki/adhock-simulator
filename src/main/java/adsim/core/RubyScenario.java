package adsim.core;

import org.jruby.RubyInstanceConfig.CompileMode;
import org.jruby.embed.ScriptingContainer;
import org.jruby.runtime.scope.ManyVarsDynamicScope;

import lombok.*;

public class RubyScenario /* implements IScenario */ {
    @Getter
    private String name;

    public void init(Session session) {
        val jruby = this.createJRubyEngine();
        val code = jruby.parse(this.loadInitScript());
        this.prepareScope(code.getScope());
        val ret = code.run();
        
    }
    
    protected void prepareScope(ManyVarsDynamicScope scpoe) {
        
    }
    
    protected String loadInitScript() {
        return "puts 'Init script is not set for this scenario'";
    }
    
    private ScriptingContainer createJRubyEngine() {
        val jruby = new ScriptingContainer();
        jruby.setCompileMode(CompileMode.JIT);
        return jruby;
    }
}
