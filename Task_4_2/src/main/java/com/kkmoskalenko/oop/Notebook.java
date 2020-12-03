package com.kkmoskalenko.oop;

import com.google.gson.Gson;
import org.apache.commons.cli.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Notebook {
    private final static String filename = "notes.json";
    private final static Gson gson = new Gson();

    private static List<Note> load() {
        try (Reader reader = new FileReader(filename)) {
            Note[] notes = gson.fromJson(reader, Note[].class);
            return new ArrayList<>(Arrays.asList(notes));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private static void save(List<Note> notes) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(notes, writer);
        } catch (IOException exception) {
            System.err.println("Failed to save the notes file: "
                    + exception.getLocalizedMessage());
        }
    }

    private static void add(Note note) {
        List<Note> notes = load();
        notes.add(note);
        save(notes);
    }

    private static void remove(String title) {
        List<Note> notes = load();

        boolean removed = notes.removeIf(note ->
                note.getTitle().equals(title));

        if (removed) {
            save(notes);
        } else {
            System.err.println("Failed to find the note to remove");
        }
    }

    private static void show() {
        load()
                .stream()
                .sorted(Comparator.comparing(Note::getTimestamp))
                .forEach(System.out::println);
    }

    private static void show(Date start, Date end, String[] keywords) {
        load()
                .stream()
                .filter(note -> Arrays.stream(keywords)
                        .anyMatch(note.getTitle()::contains) &&
                        note.getTimestamp().after(start) &&
                        note.getTimestamp().before(end)
                )
                .sorted(Comparator.comparing(Note::getTimestamp))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        DefaultParser parser = new DefaultParser();

        // A group of mutually exclusive options
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(new Option("add", false, "Adds a new note"));
        optionGroup.addOption(new Option("rm", true, "Removes all notes with the specified name"));
        optionGroup.addOption(new Option("show", false, "Prints a list of notes"));
        optionGroup.setRequired(true);

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        try {
            CommandLine commandLine = parser.parse(options, args);
            String[] positionalArgs = commandLine.getArgs();

            if (commandLine.hasOption("add")) {
                if (positionalArgs.length != 2) {
                    throw new RuntimeException("The 'add' option must be used with 2 arguments");
                }

                add(new Note(positionalArgs[0], positionalArgs[1], new Date()));
            }

            if (commandLine.hasOption("rm")) {
                remove(commandLine.getOptionValue("rm"));
            }

            if (commandLine.hasOption("show")) {
                if (positionalArgs.length == 0) {
                    show();
                } else if (positionalArgs.length >= 2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String[] keywords = Arrays.copyOfRange(positionalArgs, 2, positionalArgs.length);

                    Date start = format.parse(positionalArgs[0]);
                    Date end = format.parse(positionalArgs[1]);

                    show(start, end, keywords);
                } else {
                    throw new RuntimeException("The 'show' option must be used with 0 or 2+ arguments");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
