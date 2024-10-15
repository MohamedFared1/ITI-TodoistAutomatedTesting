package Requests;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class SectionRequests {

    public SectionRequests() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    public String createSection(String projectId, String sectionName) {
        return given()
                .queryParam("name", sectionName)
                .queryParam("project_id", projectId)
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .post("/rest/v2/sections")
                .then()
                .extract()
                .asString();
    }

    public String getSection(String sectionId) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/sections/" + sectionId)
                .then()
                .extract()
                .asString();
    }

    public String updateSection(String sectionId, String newName) {
        return given()
                .queryParam("name", newName)
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .post("/rest/v2/sections/" + sectionId)
                .then()
                .extract()
                .asString();
    }

   /* public void deleteSection(String sectionId) {
        given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .delete("/rest/v2/sections/" + sectionId)
                .then()
                .assertThat()
                .statusCode(204);
    }

    */
}
