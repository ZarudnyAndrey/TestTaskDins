package ru.zarudny.autotest;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class PhoneBookTest {

  @Test
  @Order(2)
  public void deleteUser() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
    RestAssured.basePath = "/users";

    given()
        .delete("/1")
    .then()
        .statusCode(500);
  }

  @Test
  @Order(1)
  public void postUser() {
    JSONObject requestJson = new JSONObject();
    requestJson.put("id", 5);
    requestJson.put("firstName", "Kit");
    requestJson.put("lastName", "Harington");

    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
    RestAssured.basePath = "/users";

    given()
        .contentType(ContentType.JSON)
        .body(requestJson.toString())
    .when()
        .post()
    .then()
        .statusCode(201);
  }

  @Test
  @Order(3)
  public void getContacts() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
    RestAssured.basePath = "/users/1/contacts/";

    RestAssured.defaultParser = Parser.JSON;

    Response response = given()
        .get()
    .then()
        .statusCode(200).extract().response();

    Assertions.assertEquals(2, response.jsonPath().getList("$").size());
  }

  @Test
  @Order(4)
  public void deleteContact() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = 8080;
    RestAssured.basePath = "/users/1/contacts/";

    given()
        .delete("/2")
    .then()
        .statusCode(404);
  }
}
