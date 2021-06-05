package com.kkmoskalenko.oop;

import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import lombok.SneakyThrows;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;

final class Main {
    private static final String SCRIPT_FILENAME = "config.groovy";

    private Main() {

    }

    @SneakyThrows
    public static void main(String[] args) {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());

        GroovyShell shell = new GroovyShell(cc);
        DelegatingScript script = (DelegatingScript)
                shell.parse(new File(SCRIPT_FILENAME));

        Course course = new Course();
        script.setDelegate(course);
        script.run();

        System.out.println(course);
    }
}
