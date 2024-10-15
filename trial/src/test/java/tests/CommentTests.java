package tests;

import Requests.CommentRequests;
import Requests.Environment;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CommentTests {

    private CommentRequests commentRequests = new CommentRequests();

    @Test(priority = 1)
    public void createCommentTest() {
        String taskId = Environment.getTaskId();
        long startTime = System.currentTimeMillis();

        // Attachment JSON as a string
        String attachmentJson = "{\n" +
                "        \"resource_type\": \"file\",\n" +
                "        \"file_url\": \"https://s3.amazonaws.com/domorebetter/Todoist+Setup+Guide.pdf\",\n" +
                "        \"file_type\": \"application/pdf\",\n" +
                "        \"file_name\": \"File.pdf\"\n" +
                "    }";

        String response = commentRequests.createComment(taskId, "I Want To Attend The Scholarship", attachmentJson);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 5000, "Response time exceeded 5000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);
        String commentId = js.getString("id");

        // Save commentId in Environment
        Environment.setCommentId(commentId);
        System.out.println("Comment Created with ID: " + commentId);

        // Assertions
        Assert.assertNotNull(commentId, "Comment ID is missing in the response");
        Assert.assertEquals(js.getString("content"), "I Want To Attend The Scholarship", "Comment content does not match the expected value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Create Comment Response: " + response);
    }

    @Test(dependsOnMethods = "createCommentTest")
    public void getCommentTest() {
        String commentId = Environment.getCommentId();
        long startTime = System.currentTimeMillis();

        String response = commentRequests.getComment(commentId);

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 3000, "Response time exceeded 3000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Comment ID is missing in the response");
        Assert.assertEquals(js.getString("content"), "I Want To Attend The Scholarship", "Comment content does not match the expected value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Get Comment Response: " + response);
    }

    @Test(dependsOnMethods = "getCommentTest")
    public void updateCommentTest() {
        String commentId = Environment.getCommentId();
        long startTime = System.currentTimeMillis();

        String response = commentRequests.updateComment(commentId, "I Want To Cancel IT");

        long endTime = System.currentTimeMillis();
        long responseTimeMillis = endTime - startTime;

        // Validate response time
        Assert.assertTrue(responseTimeMillis <= 2000, "Response time exceeded 2000ms: " + responseTimeMillis + " ms");

        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertNotNull(js.getString("id"), "Comment ID is missing in the response");
        Assert.assertEquals(js.getString("content"), "I Want To Cancel IT", "Comment content does not match the updated value");

        // Additional assertions as per your original code
        // ...

        System.out.println("Update Comment Response: " + response);
    }

    @Test(dependsOnMethods = "updateCommentTest")
    public void getUpdatedCommentTest() {
        String commentId = Environment.getCommentId();

        String response = commentRequests.getComment(commentId);
        JsonPath js = new JsonPath(response);

        // Assertions
        Assert.assertEquals(js.getString("content"), "I Want To Cancel IT", "Comment content does not match the updated value");

        System.out.println("Get Updated Comment Response: " + response);
    }
/*
    @Test(dependsOnMethods = "getUpdatedCommentTest", alwaysRun = true)
    public void deleteCommentTest() {
        String commentId = Environment.getCommentId();
        commentRequests.deleteComment(commentId);
        System.out.println("Comment Deleted with ID: " + commentId);

        // Optionally verify that the comment no longer exists
        // ...
    }

 */
}
