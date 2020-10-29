package check_directory_api;

import check_directory_api.Watcher.Watcher;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args)  {

        File dir = new File("src/main/resources/directoryToCheck");
        Watcher watcher = new Watcher(dir.toPath());
        watcher.checkDirectory();
    }
}
