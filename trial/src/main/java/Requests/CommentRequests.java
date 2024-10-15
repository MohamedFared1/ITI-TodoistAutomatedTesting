package Requests;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class CommentRequests {

    public CommentRequests() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    public String createComment(String taskId, String content, String attachmentJson) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"task_id\": \"" + taskId + "\",\n" +
                        "    \"content\": \"" + content + "\",\n" +
                        "    \"attachment\": " + attachmentJson + "\n" +
                        "}")
                .when()
                .post("/rest/v2/comments")
                .then()
                .extract()
                .asString();
    }

    public String getComment(String commentId) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/comments/" + commentId)
                .then()
                .extract()
                .asString();
    }

    public String updateComment(String commentId, String newContent) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"content\": \"" + newContent + "\"\n" +
                        "}")
                .when()
                .post("/rest/v2/comments/" + commentId)
                .then()
                .extract()
                .asString();
    }

    public void deleteComment(String commentId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/comments/" + commentId)
                .then()
                .assertThat()
                .statusCode(204);
    }
}
