package tests;

import Requests.Environment;
import Requests.ProjectRequests;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ProjectTests {

    private ProjectRequests projectRequests;
    private String projectId;

    @BeforeTest
    public void setUp() {
        projectRequests = new ProjectRequests();

    }

    @Test(priority = 1)
    public void createProjectTest() {
        long startTime = System.currentTimeMillis();
        String response = projectRequests.createProject("ITI");
        long responseTime = System.currentTimeMillis() - startTime;
        Assert.assertTrue(responseTime < 5000, "Response time should be less than 5000 ms");
        JsonPath js = new JsonPath(response);
        projectId = js.getString("id");
        Environment.setProjectId(projectId);

        System.out.println("Project Created with ID: " + projectId);

        // Step 2: Validate the presence of required fields and their data types
        Assert.assertNotNull(js.getString("id"), "Field 'id' is missing in the response");
        Assert.assertFalse(js.getString("id").isEmpty(), "Field 'id' should be a non-empty string");

        Assert.assertNotNull(js.getString("name"), "Field 'name' is missing in the response");
        Assert.assertTrue(js.getString("name") instanceof String, "Field 'name' should be a string");

        Assert.assertNotNull(js.getString("url"), "Field 'url' is missing in the response");
        Assert.assertTrue(js.getString("url") instanceof String, "Field 'url' should be a string");

        // Step 3: Validate specific values and their data types
        Assert.assertNull(js.get("parent_id"), "parent_id should be null");

        Assert.assertNotNull(js.getString("color"), "Field 'color' is missing in the response");
        Assert.assertTrue(js.getString("color") instanceof String, "Field 'color' should be a string");
        Assert.assertEquals(js.getString("color"), "charcoal", "color should be 'charcoal'");

        // Continue with other assertions as in your original code
        // ...

        System.out.println("Project details are correct and validated.");
    }

    @Test(dependsOnMethods = "createProjectTest")
    public void getProjectTest() {
        System.out.println("Project Retrieved with ID: " + projectId);

        String response = projectRequests.getProject(projectId);
        JsonPath js = new JsonPath(response);
        Assert.assertEquals(js.getString("id"), projectId, "The 'id' in the response should match the created ProjectID");

        System.out.println("Project details are correct and validated.");
    }

    @Test(dependsOnMethods = "getProjectTest")
    public void updateProjectTest() {
        System.out.println("Project to be updated with ID: " + projectId);

        String updatedProjectPayload = "{\"name\": \"ITI_Scholarship\", \"color\": \"light_blue\"}";

        String response = projectRequests.updateProject(projectId, updatedProjectPayload);
        JsonPath js = new JsonPath(response);

        // Step 2: Validate response time if needed
        // Step 3: Validate the presence of required fields and their data types
        // ...

        Assert.assertEquals(js.getString("name"), "ITI_Scholarship", "Project name should be updated to 'ITI_Scholarship'");
        Assert.assertEquals(js.getString("color"), "light_blue", "Project color should be updated to 'light_blue'");

        // Continue with other assertions as in your original code
        // ...

        System.out.println("Project name updated successfully.");
    }

    @Test(dependsOnMethods = "updateProjectTest")
    public void getAfterProjectUpdateTest() {

        String response = projectRequests.getProject(projectId);
        JsonPath js = new JsonPath(response);

        // Perform assertions to ensure the project was updated
        Assert.assertEquals(js.getString("name"), "ITI_Scholarship", "Project name should be 'ITI_Scholarship'");
        Assert.assertEquals(js.getString("color"), "light_blue", "Project color should be 'light_blue'");

        System.out.println("Project Retrieved after update with ID: " + projectId + " and the details after update are correct and validated.");
    }

  /*@Test(dependsOnMethods = "getAfterProjectUpdateTest")
    public void deleteProjectTest() {
        projectRequests.deleteProject(projectId);
        System.out.println("Project Deleted with ID: " + projectId);

        // Optionally, attempt to get the project to ensure it was deleted
        String response = projectRequests.getProject(projectId);
        Assert.assertTrue(response.isEmpty(), "Project should be deleted and response should be empty.");
    }

   */
}