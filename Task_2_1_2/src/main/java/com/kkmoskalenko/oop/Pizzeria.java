package com.kkmoskalenko.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public final class Pizzeria {
    private static final Random RANDOM = new Random();
    private final String workersFilename;
    private final QueueContainer<Order> orders;
    private final QueueContainer<Pizza> storage;
    private Baker[] bakers;
    private Deliveryman[] deliverymen;
    private Boolean isPizzeriaOpened = false;
    private Boolean bakersStillWorking = false;

    public Pizzeria(final String workersFilename, final int storageCapacity) {
        if (storageCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Storage capacity must be positive"
            );
        }

        this.workersFilename = workersFilename;

        this.orders = new QueueContainer<>();
        this.storage = new QueueContainer<>(storageCapacity);
    }

    public void start(final int ordersCount, final int maxDelay) {
        isPizzeriaOpened = true;
        bakersStillWorking = true;

        importWorkers();
        generateOrders(ordersCount, maxDelay);

        isPizzeriaOpened = false;
        releaseBakers();

        bakersStillWorking = false;
        releaseDeliverymen();

        showSuggestions();
    }

    private void importWorkers() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(
                Baker.class, new BakerDeserializer());
        builder.registerTypeAdapter(
                Deliveryman.class, new DeliverymanDeserializer());
        Gson gson = builder.create();

        try {
            FileReader reader = new FileReader(workersFilename);
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            JsonElement bakersNode = obj.get("bakers");
            JsonElement deliverymenNode = obj.get("deliverymen");

            bakers = gson.fromJson(bakersNode, Baker[].class);
            deliverymen = gson.fromJson(deliverymenNode, Deliveryman[].class);
        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private void generateOrders(final int count, final int maxDelay) {
        for (int i = 0; i < count; i++) {
            Pizza.Type pizzaType = Pizza.Type.random();
            Order order = new Order(pizzaType);

            log(order, "received (pizza: " + pizzaType + ")", null);
            orders.add(order);

            try {
                Thread.sleep(RANDOM.nextInt(maxDelay));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void releaseBakers() {
        synchronized (orders) {
            orders.notifyAll();
        }

        for (Baker bakerThread : bakers) {
            try {
                bakerThread.join();
            } catch (InterruptedException e) {
                System.out.println(bakerThread + " interrupted");
            }
        }
    }

    private void releaseDeliverymen() {
        synchronized (storage) {
            storage.notifyAll();
        }

        for (Deliveryman deliverymanThread : deliverymen) {
            try {
                deliverymanThread.join();
            } catch (InterruptedException e) {
                System.out.println(deliverymanThread + " interrupted");
            }
        }
    }

    private void showSuggestions() {
        System.out.println();

        int blockedOrderGets = orders.getBlockedGets();
        if (blockedOrderGets > 0) {
            System.out.println(
                    "The bakers were unable to get a new order for "
                            + blockedOrderGets + " times. Suggestion: "
                            + "increase the number of orders."
            );
        }

        int blockedStorageAdds = storage.getBlockedAdds();
        if (blockedStorageAdds > 0) {
            System.out.println(
                    "The bakers were unable to store an order for "
                            + blockedStorageAdds + " times. Suggestion: "
                            + "increase the storage capacity or hire more "
                            + "deliverymen."
            );
        }

        int blockedStorageGets = storage.getBlockedGets();
        if (blockedStorageGets > 0) {
            System.out.println(
                    "The deliverymen were unable to get an order from the "
                            + "storage for " + blockedOrderGets + " times. "
                            + "Suggestion: increase the number of orders."
            );
        }
    }

    private void log(
            final Order order, final String state, final Thread thread
    ) {
        synchronized (System.out) {
            System.out.printf("ORDER #%-6d", order.getId());
            System.out.print("State: " + state);
            if (thread != null) {
                System.out.print(" (thread: " + thread.getName() + ")");
            }
            System.out.println();
        }
    }

    private final class BakerDeserializer
            implements JsonDeserializer<Baker> {
        private static final String NAME_PROPERTY = "name";
        private static final String EXPERIENCE_PROPERTY = "experience";

        @Override
        public Baker deserialize(
                final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context
        ) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            String name = object.get(NAME_PROPERTY).getAsString();
            int experience = object.get(EXPERIENCE_PROPERTY).getAsInt();
            return new Baker(name, experience);
        }
    }

    private final class DeliverymanDeserializer
            implements JsonDeserializer<Deliveryman> {
        private static final String NAME_PROPERTY = "name";
        private static final String CAPACITY_PROPERTY = "trunkCapacity";

        @Override
        public Deliveryman deserialize(
                final JsonElement json, final Type typeOfT,
                final JsonDeserializationContext context
        ) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();
            String name = object.get(NAME_PROPERTY).getAsString();
            int capacity = object.get(CAPACITY_PROPERTY).getAsInt();
            return new Deliveryman(name, capacity);
        }
    }

    private class Baker extends Thread {
        private static final int MAX_EXPERIENCE = 100;
        private static final int PRO_COOKING_TIME = 200;
        private static final int BEG_COOKING_TIME = 700;

        private final int cookingTime;

        Baker(final String name, final int workExperience) {
            if (workExperience < 0 || workExperience > MAX_EXPERIENCE) {
                throw new IllegalArgumentException(
                        "Experience must be between 0 and " + MAX_EXPERIENCE
                );
            }

            int slope = -(BEG_COOKING_TIME - PRO_COOKING_TIME) / MAX_EXPERIENCE;
            this.cookingTime = slope * workExperience + BEG_COOKING_TIME;

            this.setName(name);
            this.start();
        }

        public void run() {
            while (isPizzeriaOpened || orders.isNotEmpty()) {
                Order order = orders.get(isPizzeriaOpened);
                if (order == null) {
                    continue;
                }

                Pizza pizza = cook(order);
                store(pizza);
            }
        }

        private Pizza cook(final Order order) {
            log(order, "start cooking", this);

            try {
                Thread.sleep(cookingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log(order, "finished cooking", this);
            return new Pizza(order);
        }

        private void store(final Pizza pizza) {
            storage.add(pizza);
            log(pizza.getOrder(), "put into storage", this);
        }
    }

    private class Deliveryman extends Thread {
        private static final int MIN_DELIVERY_TIME = 400;
        private static final int MAX_DELIVERY_TIME = 700;

        private final int trunkCapacity;
        private final ArrayList<Pizza> trunk;

        Deliveryman(final String name, final int trunkCapacity) {
            if (trunkCapacity < 1) {
                throw new IllegalArgumentException(
                        "The trunk must fit at least 1 pizza"
                );
            }

            this.trunkCapacity = trunkCapacity;
            this.trunk = new ArrayList<>(trunkCapacity);

            this.setName(name);
            this.start();
        }

        public void run() {
            while (bakersStillWorking || storage.isNotEmpty()) {
                takePizzas();
                trunk.forEach(this::deliver);
                trunk.clear();
            }
        }

        private void takePizzas() {
            while (trunk.size() < trunkCapacity && storage.isNotEmpty()) {
                Pizza pizza = storage.get(bakersStillWorking);
                if (pizza == null) {
                    break;
                }

                trunk.add(pizza);
                log(pizza.getOrder(), "picked up for delivery", this);
            }
        }

        private void deliver(final Pizza pizza) {
            int deliveryTime = MIN_DELIVERY_TIME + RANDOM.nextInt(
                    MAX_DELIVERY_TIME - MIN_DELIVERY_TIME);
            try {
                Thread.sleep(deliveryTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Order order = pizza.getOrder();
            long time = order.getElapsedTime();

            log(order, "delivered in " + time + " ms", this);
        }
    }
}
