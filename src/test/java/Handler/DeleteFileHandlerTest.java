package Handler;

import check_directory_api.Hander.DeleteFileHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileHandlerTest {


    @Test
    public void deleteHandlerTest() {
        Path path = Paths.get("src/test/resources/TestHandler/test.txt");

        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            Files.write(path, "Some text".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DeleteFileHandler handler = new DeleteFileHandler(path);
        handler.action();
        Assert.assertTrue(Files.notExists(path));

    }
}
