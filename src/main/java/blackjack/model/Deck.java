package blackjack.model;

import java.util.Random;

public class Deck {

    public static int SUIT_SIZE = Card.Number.values().length;
    public static int DECK_SIZE = SUIT_SIZE * Card.Suit.values().length;

    private Card[] cards;
    private int cardIndex = 0;

    public Deck() {
        cards = new Card[DECK_SIZE];
        int index = 0;
        for(Card.Suit suit : Card.Suit.values()) {
            for (int i=0; i < SUIT_SIZE; ++i) {
                cards[index + i] = new Card(Card.Number.values()[i], suit);
            }
            index += SUIT_SIZE;
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < DECK_SIZE; ++i) {
            swap(cards, i, i + random.nextInt(DECK_SIZE - i));
        }
        cardIndex = 0;
    }

    public Card deal() {
        if (cardIndex < 52) {
            return cards[cardIndex++];
        }
        return null;
    }

    private void swap(Card[] cards, int i, int j) {
        Card card = cards[i];
        cards[i] = cards[j];
        cards[j] = card;
    }
}
