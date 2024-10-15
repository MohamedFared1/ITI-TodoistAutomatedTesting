package Requests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class ProjectRequests {

    public ProjectRequests() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    public String createProject(String name) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body("{\"name\":\"" + name + "\"}")
                .when()
                .post("/rest/v2/projects")
                .then()
                .extract()
                .asString();
    }

    public String getProject(String projectId) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/projects/" + projectId)
                .then().assertThat().statusCode(200)
                .extract()
                .asString();
    }

    public String updateProject(String projectId, String payload) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/rest/v2/projects/" + projectId)
                .then()
                .extract()
                .asString();
    }

   /* public void deleteProject(String projectId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/projects/" + projectId)
                .then()
                .assertThat()
                .statusCode(204);
    }

    */
}