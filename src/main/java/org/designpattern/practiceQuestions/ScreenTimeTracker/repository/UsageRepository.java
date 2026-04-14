package org.designpattern.practiceQuestions.ScreenTimeTracker.repository;

import org.designpattern.practiceQuestions.ScreenTimeTracker.model.AppUsage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsageRepository {
    Map<String, List<AppUsage>> store = new HashMap<>();  // appId -> usage records
    String filePath;

    public UsageRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    public void save(AppUsage usage) {
        addToStore(usage);
        persistToFile();
    }

    public List<AppUsage> getByApp(String appId) {
        if (!store.containsKey(appId)) return new ArrayList<>();
        return store.get(appId);
    }

    public List<AppUsage> getAll() {
        List<AppUsage> all = new ArrayList<>();
        for (List<AppUsage> list : store.values()) {
            all.addAll(list);
        }
        return all;
    }

    public void pruneOlderThan(LocalDate cutoff) {
        for (String appId : store.keySet()) {
            List<AppUsage> fresh = new ArrayList<>();
            for (AppUsage u : store.get(appId)) {
                if (!u.date.isBefore(cutoff)) fresh.add(u);
            }
            store.put(appId, fresh);
        }
        persistToFile();
    }

    private void addToStore(AppUsage usage) {
        if (!store.containsKey(usage.appId)) {
            store.put(usage.appId, new ArrayList<>());
        }
        store.get(usage.appId).add(usage);
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                addToStore(AppUsage.fromCSV(line));
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void persistToFile() {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (AppUsage u : getAll()) {
                writer.write(u.toCSV() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
