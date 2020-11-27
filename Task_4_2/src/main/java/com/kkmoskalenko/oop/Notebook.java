package com.kkmoskalenko.oop;

import com.google.gson.Gson;

import java.io.*;
import java.text.ParseException;
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
            System.out.println("Failed to save the notes file: "
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
            System.out.println("Failed to remove the note");
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
        if (args.length < 1) {
            System.out.println("No arguments specified");
            System.exit(1);
        }

        switch (args[0]) {
            case "add":
                if (args.length == 3) {
                    add(new Note(args[1], args[2], new Date()));
                    break;
                } else {
                    System.out.println("Invalid arguments");
                    System.exit(1);
                }

            case "rm":
                if (args.length == 2) {
                    remove(args[1]);
                    break;
                } else {
                    System.out.println("Invalid arguments");
                    System.exit(1);
                }

            case "show":
                if (args.length == 1) {
                    show();
                } else if (args.length >= 4) {
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String[] keywords = Arrays.copyOfRange(args, 3, args.length);

                    try {
                        Date start = format.parse(args[1]);
                        Date end = format.parse(args[2]);

                        show(start, end, keywords);
                    } catch (ParseException e) {
                        System.out.println(e.getLocalizedMessage());
                        System.exit(1);
                    }
                } else {
                    System.out.println("Invalid arguments");
                    System.exit(1);
                }
                break;

            default:
                throw new RuntimeException("Invalid command");
        }

    }
}
