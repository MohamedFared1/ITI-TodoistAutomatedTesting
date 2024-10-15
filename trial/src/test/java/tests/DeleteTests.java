package tests;

import Requests.DeleteRequests;
import Requests.Environment;
import Requests.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class DeleteTests {

    private DeleteRequests deleteRequests = new DeleteRequests();

    @Test(priority = 1)
    public void deleteLabelTest() {
        String labelId = Environment.getLabelId();
        long startTime = System.currentTimeMillis();

        deleteRequests.deleteLabel(labelId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        // Verify the label no longer exists by checking the list of labels
        Response response = given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/labels");

        String responseBody = response.asString();
        JsonPath js = new JsonPath(responseBody);
        List<String> labelIds = js.getList("id");

        Assert.assertFalse(labelIds.contains(labelId), "Label was not deleted successfully; it still exists in the list.");

        System.out.println("Label deleted successfully. Response time: " + responseTimeMillis + " ms");
    }

    @Test(priority = 2)
    public void deleteCommentTest() {
        String commentId = Environment.getCommentId();
        long startTime = System.currentTimeMillis();

        deleteRequests.deleteComment(commentId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        // Since comments are associated with tasks or projects, we can check the comments list for the task
        String taskId = Environment.getTaskId();

        Response response = given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .queryParam("task_id", taskId)
                .when()
                .get("/rest/v2/comments");

        String responseBody = response.asString();
        JsonPath js = new JsonPath(responseBody);
        List<String> commentIds = js.getList("id");

        Assert.assertFalse(commentIds.contains(commentId), "Comment was not deleted successfully; it still exists in the list.");

        System.out.println("Comment deleted successfully. Response time: " + responseTimeMillis + " ms");
    }

    @Test(priority = 3)
    public void deleteTaskTest() {
        String taskId = Environment.getTaskId();
        long startTime = System.currentTimeMillis();

        deleteRequests.deleteTask(taskId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        // Verify the task no longer exists by checking the list of tasks
        Response response = given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/tasks");

        String responseBody = response.asString();
        JsonPath js = new JsonPath(responseBody);
        List<String> taskIds = js.getList("id");

        Assert.assertFalse(taskIds.contains(taskId), "Task was not deleted successfully; it still exists in the list.");

        System.out.println("Task deleted successfully. Response time: " + responseTimeMillis + " ms");
    }

    @Test(priority = 4)
    public void deleteSectionTest() throws InterruptedException {
        String sectionId = Environment.getSectionId();
        long startTime = System.currentTimeMillis();

        // Optional delay if needed due to API constraints
        Thread.sleep(3000);

        deleteRequests.deleteSection(sectionId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        // Verify the section no longer exists by checking the list of sections in the project
        String projectId = Environment.getProjectId();

        Response response = given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .queryParam("project_id", projectId)
                .when()
                .get("/rest/v2/sections");

        String responseBody = response.asString();
        JsonPath js = new JsonPath(responseBody);
        List<String> sectionIds = js.getList("id");

        Assert.assertFalse(sectionIds.contains(sectionId), "Section was not deleted successfully; it still exists in the list.");

        System.out.println("Section deleted successfully. Response time: " + responseTimeMillis + " ms");
    }

    @Test(priority = 5)
    public void deleteProjectTest() throws InterruptedException {
        String projectId = Environment.getProjectId();
        long startTime = System.currentTimeMillis();

        // Optional delay if needed due to API constraints
        Thread.sleep(3000);

        deleteRequests.deleteProject(projectId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        // Verify the project no longer exists by checking the list of projects
        Response response = given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/projects");

        String responseBody = response.asString();
        JsonPath js = new JsonPath(responseBody);
        List<String> projectIds = js.getList("id");

        Assert.assertFalse(projectIds.contains(projectId), "Project was not deleted successfully; it still exists in the list.");

        System.out.println("Project deleted successfully. Response time: " + responseTimeMillis + " ms");
    }
}
