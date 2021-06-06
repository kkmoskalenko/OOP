package com.kkmoskalenko.oop;

import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import lombok.SneakyThrows;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;

final class Main {
    private static final String SCRIPT_FILENAME = "config.groovy";
    private static final String TABLE_FORMAT = "    %-15s | %-5s | %-15s%n";

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

        for (Group group : course.getGroups()) {
            System.out.println("Results for group " + group.getName() + ":");
            for (Student student : group.getStudents()) {
                System.out.println("  Student " + student.getName());
                System.out.format(TABLE_FORMAT, "Task", "Build", "Tests");

                File cloneDir = student.cloneRepo();
                for (Task task : course.getTasks()) {
                    File taskDirectory = new File(cloneDir, task.getPath());
                    boolean buildResult = GradleUtils.build(taskDirectory);

                    String testResult;
                    if (!task.isShouldSkipTests()) {
                        testResult = GradleUtils.test(taskDirectory);
                    } else {
                        testResult = "TESTING SKIPPED";
                    }

                    System.out.format(TABLE_FORMAT, task.getName(),
                            buildResult, testResult);
                }
            }
        }
    }
}
