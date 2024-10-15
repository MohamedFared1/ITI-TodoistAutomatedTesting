package Requests;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class DeleteRequests {

    public DeleteRequests() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    public void deleteLabel(String labelId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/labels/" + labelId)
                .then()
                .assertThat()
                .statusCode(204);
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

    public void deleteTask(String taskId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/tasks/" + taskId)
                .then()
                .assertThat()
                .statusCode(204);
    }

    public void deleteSection(String sectionId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/sections/" + sectionId)
                .then()
                .assertThat()
                .statusCode(204);
    }

    public void deleteProject(String projectId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/projects/" + projectId)
                .then()
                .assertThat()
                .statusCode(204);
    }
}
