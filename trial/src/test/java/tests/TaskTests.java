package tests;

import Requests.Environment;
import Requests.TaskRequests;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TaskTests {

    private TaskRequests taskRequests = new TaskRequests();

    @Test(priority = 1)
    public void createTaskTest() {
        String projectId = Environment.getProjectId();
        String sectionId = Environment.getSectionId();

        long startTime = System.currentTimeMillis();

        String response = taskRequests.createTask(projectId, sectionId, "SupportTech");
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);
        String taskId = js.getString("id");

        // Save taskId in Environment
        Environment.setTaskId(taskId);
        System.out.println("Task Created with ID: " + taskId);

        // Assertions
        Assert.assertNotNull(taskId, "Task ID is missing in the response");
        Assert.assertTrue(taskId instanceof String, "Task ID should be a string");
        Assert.assertEquals(js.getString("content"), "SupportTech", "Task content does not match the expected value");

        // Additional assertions as per your original code
        // ...
    }

    @Test(dependsOnMethods = "createTaskTest")
    public void getTaskTest() {
        String taskId = Environment.getTaskId();
        long startTime = System.currentTimeMillis();

        String response = taskRequests.getTask(taskId);
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 1500, "Response time exceeded 1500ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Task ID is missing in the response");
        Assert.assertEquals(js.getString("content"), "SupportTech", "Task content does not match the expected value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Task details retrieved successfully.");
    }

    @Test(dependsOnMethods = "getTaskTest")
    public void updateTaskTest() {
        String taskId = Environment.getTaskId();
        long startTime = System.currentTimeMillis();

        String response = taskRequests.updateTask(taskId, "Call_Center");
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Task ID is missing in the response");
        Assert.assertEquals(js.getString("content"), "Call_Center", "Task content does not match the expected value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Task content updated successfully.");
    }

    @Test(dependsOnMethods = "updateTaskTest")
    public void getUpdatedTaskTest() {
        String taskId = Environment.getTaskId();

        String response = taskRequests.getTask(taskId);
        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertEquals(js.getString("content"), "Call_Center", "Task content does not match the updated value");

        System.out.println("Updated task details retrieved successfully.");
    }

    @Test(dependsOnMethods = "getUpdatedTaskTest")
    public void closeTaskTest() {
        String taskId = Environment.getTaskId();
        long startTime = System.currentTimeMillis();

        taskRequests.closeTask(taskId);
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        System.out.println("Task closed successfully.");

        // Optionally, verify the task is marked as completed
        // ...
    }

    @Test(dependsOnMethods = "closeTaskTest")
    public void reopenTaskTest() {
        String taskId = Environment.getTaskId();
        long startTime = System.currentTimeMillis();

        taskRequests.reopenTask(taskId);
        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 3000, "Response time exceeded 3000ms: " + responseTimeMillis + " ms");

        System.out.println("Task reopened successfully.");

        // Optionally, verify the task is marked as not completed
        // ...
    }
/*
    @Test(dependsOnMethods = "reopenTaskTest", alwaysRun = true)
    public void deleteTaskTest() {
        String taskId = Environment.getTaskId();
        taskRequests.deleteTask(taskId);
        System.out.println("Task Deleted with ID: " + taskId);

        // Optionally verify that the task no longer exists
        // ...
    }

 */
}
