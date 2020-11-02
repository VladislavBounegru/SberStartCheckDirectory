package check_directory_api.Watcher;

import check_directory_api.Controller.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SelfWatcher {
    Path path;
    Controller controller;
    ExecutorService executorService;
    private static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("Log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Watcher.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SelfWatcher(Path path) {
        this.path = path;
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void checkDirectory() {
        try {
            List<Path> existingFiles = getFiles();

            while (true) {
                existingFiles = removeUnnecessary(existingFiles);

                List<Path> newFiles = getFiles();
                newFiles.removeAll(existingFiles);
                System.out.println(newFiles);
                handleFiles(newFiles);
                existingFiles = getFiles();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

    public List<Path> getFiles() {
        try {
            return Files.walk(path)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Path> removeUnnecessary(List<Path> list) {
        for (int i = 0; i < list.size(); i++) {

            // remove files which are not suitable from list
            if (!list.get(i).toString().endsWith(".json") &&
                    !list.get(i).toString().endsWith(".xml") &&
                    !list.get(i).toString().endsWith(".DS_Store")) {
                list.remove((list.get(i)));
            }
        }
        return list;
    }

    //handle files in directory
    public void handleFiles(List<Path> newFiles) {
        for (Path newFile : newFiles) {
            System.out.println(newFile);
            log(newFile.getFileName().toString());
            controller = new Controller(newFile);
            executorService.submit(controller);
        }
    }

    public void log(String fileName) {
        long start = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date startDate = new Date(start);
        LOGGER.log(Level.INFO, "Added " + fileName + " file ad - " + sdf.format(startDate));
    }

}
