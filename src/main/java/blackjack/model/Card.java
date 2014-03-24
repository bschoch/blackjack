package blackjack.model;

public class Card {

    private Suit suit;
    private Number number;

    public Card(Number number, Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public Number getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString() + " " + suit.toString();
    }

    public enum Suit {
        Clubs,
        Diamonds,
        Spades,
        Hearts
    }

    public enum Number {
        Ace,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King
    }
}
