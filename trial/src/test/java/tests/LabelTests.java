package tests;

import Requests.Environment;
import Requests.LabelRequests;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LabelTests {

    private LabelRequests labelRequests = new LabelRequests();

    @Test(priority = 1)
    public void createLabelTest() {
        long startTime = System.currentTimeMillis();

        String response = labelRequests.createLabel("LastSession");

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);
        String labelId = js.getString("id");

        // Save labelId in Environment
        Environment.setLabelId(labelId);
        System.out.println("Label Created with ID: " + labelId);

        // Assertions
        Assert.assertNotNull(labelId, "Label ID is missing in the response");
        Assert.assertEquals(js.getString("name"), "LastSession", "Label name does not match the expected value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Create Label Response: " + response);
    }

    @Test(dependsOnMethods = "createLabelTest")
    public void getLabelTest() {
        String labelId = Environment.getLabelId();
        long startTime = System.currentTimeMillis();

        String response = labelRequests.getLabel(labelId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Label ID is missing in the response");
        Assert.assertEquals(js.getString("name"), "LastSession", "Label name does not match the expected value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Get Label Response: " + response);
    }

    @Test(dependsOnMethods = "getLabelTest")
    public void updateLabelTest() {
        String labelId = Environment.getLabelId();
        long startTime = System.currentTimeMillis();

        String response = labelRequests.updateLabel(labelId, "FirstSession");

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Label ID is missing in the response");
        Assert.assertEquals(js.getString("name"), "FirstSession", "Label name was not updated correctly");

        // Additional assertions as per your original code
        // ...

        System.out.println("Update Label Response: " + response);
    }

    @Test(dependsOnMethods = "updateLabelTest")
    public void getUpdatedLabelTest() {
        String labelId = Environment.getLabelId();

        String response = labelRequests.getLabel(labelId);
        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertEquals(js.getString("name"), "FirstSession", "Label name does not match the updated value");

        System.out.println("Get Updated Label Response: " + response);
    }
/*
    @Test(dependsOnMethods = "getUpdatedLabelTest", alwaysRun = true)
    public void deleteLabelTest() {
        String labelId = Environment.getLabelId();
        labelRequests.deleteLabel(labelId);
        System.out.println("Label Deleted with ID: " + labelId);

        // Optionally verify that the label no longer exists
        // ...
    }

 */
}
