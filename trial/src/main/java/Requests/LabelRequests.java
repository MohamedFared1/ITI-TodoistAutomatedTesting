package Requests;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class LabelRequests {

    public LabelRequests() {
        RestAssured.baseURI = Constants.BASE_URI;
    }

    public String createLabel(String name) {
        return given()
                .queryParam("name", name)
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .post("/rest/v2/labels")
                .then()
                .extract()
                .asString();
    }

    public String getLabel(String labelId) {
        return given()
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .when()
                .get("/rest/v2/labels/" + labelId)
                .then()
                .extract()
                .asString();
    }

    public String updateLabel(String labelId, String newName) {
        return given()
                .queryParam("name", newName)
                .header("Authorization", "Bearer " + Constants.AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .when()
                .post("/rest/v2/labels/" + labelId)
                .then()
                .extract()
                .asString();
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
}
