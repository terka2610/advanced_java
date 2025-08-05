package logic;

import java.util.Arrays;

public class Card {
	private String value;
	private String suit;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	public int getNumericValue() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int index = Arrays.asList(ranks).indexOf(value.toLowerCase());
        return (index >= 0) ? index + 2 : 0; 
    }

    public String getImagePath() {
        return "GUI/src/images/" + value + suit + ".png";
    }
}
