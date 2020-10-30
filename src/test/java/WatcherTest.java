import check_directory_api.Watcher.Watcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class WatcherTest {//TODO find a way to make correct test

    Path path;
    Watcher watcher;
    String fileName;
    WatchService watchService;
    private static Logger LOGGER;

    static {
        try (FileInputStream ins = new FileInputStream("Log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(WatcherTest.class.getName());
            FileHandler fh = new FileHandler("TestLog.log");
            LOGGER.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before
    public void beforeTests() {
        path = Paths.get("src/test/resources/TestWatcher");
        watcher = new Watcher(path);
        try {
            watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addFileTest() throws IOException {
        fileName = "test.json";
        System.out.println(path);
        Path pathToFile = Paths.get(path.toString() + "/" + fileName);
        Files.deleteIfExists(pathToFile);
        Files.createFile(pathToFile);
        watcher.watchService(watchService, null, LOGGER);
        Assert.assertTrue(Files.exists(pathToFile));
        Files.deleteIfExists(pathToFile);
    }

    @Test
    public void deleteFileTest() throws IOException, InterruptedException {
        fileName = "test.test";
        System.out.println(path);
        Path pathToFile = Paths.get(path.toString() + "/" + fileName);
        Files.deleteIfExists(pathToFile);
        Files.createFile(pathToFile);
        watcher.watchService(watchService, null, LOGGER);
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(Files.notExists(pathToFile));
        Files.deleteIfExists(pathToFile);
    }


}
