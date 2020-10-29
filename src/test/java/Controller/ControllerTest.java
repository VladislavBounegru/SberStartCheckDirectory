package Controller;

import check_directory_api.Controller.Controller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerTest {

    Controller controller;
    String path;
    String fileName;

    @Before
    public void before() {
        path = "src/test/resources/TestController/";
    }


    @Test
    public void getJsonHandler() {
        fileName = "test.json";
        controller = new Controller(Paths.get(path + fileName));
        Assert.assertTrue(controller.getHandler().getClass().getName().contains("JsonFileHandler"));
    }

    @Test
    public void getXmlHandler() {
        fileName = "test.xml";
        controller = new Controller(Paths.get(path + fileName));
        Assert.assertTrue(controller.getHandler().getClass().getName().contains("JsonFileHandler"));
    }

    @Test
    public void getDeleteHandler() {
       fileName = "test.txt";
        controller = new Controller(Paths.get(path + fileName));
        Assert.assertTrue(controller.getHandler().getClass().getName().contains("JsonFileHandler"));
    }
}
