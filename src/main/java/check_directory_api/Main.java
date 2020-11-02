package check_directory_api;

import check_directory_api.Watcher.SelfWatcher;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File dir = new File("src/main/resources/directoryToCheck");
        SelfWatcher listener = new SelfWatcher(dir.toPath());
        listener.checkDirectory();
//        Watcher watcher = new Watcher(dir.toPath());
//        watcher.checkDirectory();
    }
}
