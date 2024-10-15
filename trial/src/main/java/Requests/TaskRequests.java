package Requests;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class TaskRequests {

    public TaskRequests() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    public String createTask(String projectId, String sectionId, String content) {
        return given()
                .queryParam("content", content)
                .queryParam("project_id", projectId)
                .queryParam("section_id", sectionId)
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .post("/rest/v2/tasks")
                .then()
                .extract()
                .asString();
    }

    public String getTask(String taskId) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/tasks/" + taskId)
                .then()
                .extract()
                .asString();
    }

    public String updateTask(String taskId, String newContent) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\"content\": \"" + newContent + "\"}")
                .when()
                .post("/rest/v2/tasks/" + taskId)
                .then()
                .extract()
                .asString();
    }

    public void closeTask(String taskId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .post("/rest/v2/tasks/" + taskId + "/close")
                .then()
                .assertThat()
                .statusCode(204);
    }

    public void reopenTask(String taskId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .post("/rest/v2/tasks/" + taskId + "/reopen")
                .then()
                .assertThat()
                .statusCode(204);
    }

    public void deleteTask(String taskId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/tasks/" + taskId)
                .then()
                .assertThat()
                .statusCode(204);
    }
}
