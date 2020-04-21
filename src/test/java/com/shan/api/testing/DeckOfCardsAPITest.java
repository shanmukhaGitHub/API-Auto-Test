package com.shan.api.testing;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class DeckOfCardsAPITest {
	
	String newDeckName ="";
	
	String newPileName ="";
	
	@BeforeClass
	public void setUp() throws Exception {
	
		Response response = get("https://deckofcardsapi.com/api/deck/new/");

		newDeckName= response.jsonPath().getString("deck_id");
		
		given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName)
		  .then()
		  .statusCode(200)
		 ;
		  
	}
	
	
	
	@Test
	public void test_01_get_DeckOfCards_Status_and_response_check() {
		
	  
	  given()
	  .get("https://deckofcardsapi.com/api/deck/"+newDeckName)
	  .then()
	  .statusCode(200)
	  .body("remaining", equalTo(52))
	  .log().body();
	  
	}
	
	@Test
	 public void test_02_DeckOfCards_draw_four_cards() {
		
		
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/draw/?count=4")
		  .then()
		  .statusCode(200)
		  .body("remaining", equalTo(48))
		  .log().body();
	}

	
	@Test
	 public void test_03_DeckOfCards_reshuffle() {
		
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/shuffle")
		  .then()
		  .statusCode(200)
		  .body("remaining", equalTo(52))
		  .log().body();
	}
	
	
	@Test
	 public void test_04_DeckOfCards_partial_deck() {
		
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/shuffle/?cards=AS,2S,KS,AD,2D,KD,AC,2C,KC,AH,2H,KH")
		  .then()
		  .statusCode(200)
		  .body("remaining", equalTo(12))
		  .log().body();
	}
	
	
	@Test
	 public void test_05_DeckOfCards_adding_piles() {
		newPileName ="player1";
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/pile/"+newPileName+"/add/?cards=3H,4S,8D")
		  .then()
		  .statusCode(200)
		  .body("piles.player1.remaining", equalTo(3))
		  .log().body();
	}
	
	@Test
	 public void test_06_DeckOfCards_piles_shuffle() {
		newPileName ="player1";
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/pile/"+newPileName+"/shuffle/")
		  .then()
		  .statusCode(200)
		  .body("remaining", equalTo(12))
		  .log().body();
	}
	
	@Test
	 public void test_07_DeckOfCards_piles_list() {
		newPileName ="player1";
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/pile/"+newPileName+"/list/")
		  .then()
		  .statusCode(200)
		  .body("remaining", equalTo(12))
		  .log().body();
	}
	
	@Test
	 public void test_08_DeckOfCards_piles_draw_AS() {
		newPileName ="player1";
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/pile/"+newPileName+"/draw/?cards=3H")
		  .then()
		  .statusCode(200)
		 .body("cards[0].code", equalTo("3H"));
	}
	
	@Test
	 public void test_09_DeckOfCards_piles_draw_card_2() {
		newPileName ="player1";
		 given()
		  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/pile/"+newPileName+"/draw/?count=1")
		  .then()
		  .statusCode(200)
		  .body("piles.player1.remaining", equalTo(1))
		  .log().body();
	}
	
	
	@Test
	public void test_010_get_DeckOfCards_add_joker() {
		
	  
	  given()
	  .get("https://deckofcardsapi.com/api/deck/"+newDeckName+"/?jokers_enabled=true")
	  .then()
	  .statusCode(200)
	  .body("remaining", equalTo(52))
	  .log().body();
	  
	}
	
}
