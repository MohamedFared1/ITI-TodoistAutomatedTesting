package tests;

import Requests.Environment;
import Requests.SectionRequests;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SectionTests {

    private SectionRequests sectionRequests = new SectionRequests();

    @Test(priority = 1)
    public void createSectionTest() {
        // Retrieve projectId from Environment
        String projectId = Environment.getProjectId();


        System.out.println("Using Project ID for Section creation: " + projectId);

        // Create a new Section
        String response = sectionRequests.createSection(projectId, "Software-Testing");
        JsonPath js = new JsonPath(response);
        String sectionId = js.getString("id");

        // After creating the section and obtaining sectionId
        Environment.setSectionId(sectionId);

        System.out.println("Section Created with ID: " + sectionId);

        // Assertions
        Assert.assertNotNull(sectionId, "Section ID is missing");
        Assert.assertTrue(sectionId instanceof String, "Section ID should be a string");
        Assert.assertEquals(js.getString("name"), "Software-Testing", "Section name does not match expected value");

        // Additional assertions as per your original code
        // ...
    }

    @Test(dependsOnMethods = "createSectionTest")
    public void getSectionTest() {
        String sectionId = Environment.getSectionId();
        long startTime = System.currentTimeMillis();

        String response = sectionRequests.getSection(sectionId);
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);
        System.out.println("Section Retrieved and Verified with ID: " + sectionId);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Section ID is missing");
        Assert.assertEquals(js.getString("name"), "Software-Testing", "Section name does not match expected value");

        // Additional assertions as per your original code
        // ...
    }

    @Test(dependsOnMethods = "getSectionTest")
    public void updateSectionTest() {
        String sectionId = Environment.getSectionId();
        long startTime = System.currentTimeMillis();

        String response = sectionRequests.updateSection(sectionId, "EmbeddedSystems");
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Section ID is missing");
        Assert.assertEquals(js.getString("name"), "EmbeddedSystems", "Section name should be updated");

        System.out.println("Section name updated successfully.");
    }

    @Test(dependsOnMethods = "updateSectionTest")
    public void getUpdatedSectionTest() {
        String sectionId = Environment.getSectionId();
        String response = sectionRequests.getSection(sectionId);
        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertEquals(js.getString("name"), "EmbeddedSystems", "Section name does not match updated value");

        System.out.println("Section details after update are correct and validated.");
    }
  /*  @Test(dependsOnMethods = "getUpdatedSectionTest", alwaysRun = true)
    public void deleteSectionTest() {
        String sectionId = Environment.getSectionId();
        sectionRequests.deleteSection(sectionId);
        System.out.println("Section Deleted with ID: " + sectionId);

        // Optionally verify that the section no longer exists
        // ...
    }

   */
}