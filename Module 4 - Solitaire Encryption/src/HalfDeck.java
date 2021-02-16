import java.util.Iterator;

public class HalfDeck extends CircularLinkedList<Integer> {

	private final int DECK_SIZE = 26;
	private final int JOKER_COUNT = 2;
	private final int FULL_SIZE = DECK_SIZE + JOKER_COUNT;

	public HalfDeck() {
		for (int i = 0; i < FULL_SIZE; i++) // Create the half-deck of cards + jokers
			this.add(i + 1);
	}

	// Step 1 and Step 2
	public void shiftRight(int index, int shiftAmount) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Attempted to get index " + index + " when size is " + size);

		if (shiftAmount == 0 || size <= 1)
			return;

		if (shiftAmount >= size)
			shiftAmount = (index + 1) % size;

		Node<Integer> curNode = getNode(index);
		for (int i = 0; i < shiftAmount; i++) {
			int nextData = curNode.next.data;

			curNode.next.data = curNode.data; // Shift current data to the right
			curNode.data = nextData;
			curNode = curNode.next;
		}
	}

	public void jokerTripleCut() {
		Node<Integer> currentCard = head, previousCard = null;
		Node<Integer>[] jokerPositions = (Node<Integer>[]) new Node<?>[2];
		Node<Integer> beforeFirstJoker = null;

		// Assign joker positions
		for (int i = 0, j = 0; i < size && j < 2; i++, previousCard = currentCard, currentCard = currentCard.next)
			if (isJoker(currentCard.data)) {
				if (j == 0)
					beforeFirstJoker = previousCard;

				jokerPositions[j++] = currentCard;
			}

		if (jokerPositions[0] == head) { // A joker is the top card, no cards before it to move.
			head = jokerPositions[1].next;
			tail = jokerPositions[1];
		} else { // No special care should be taken when shifting
			Node<Integer> newHead = jokerPositions[1].next;
			jokerPositions[1].next = head;
			tail.next = jokerPositions[0];
			head = newHead;
			tail = beforeFirstJoker;
			tail.next = head;
		}

	}

	/*
	 Remove the bottom card from the deck. Count down from the top card by a
	 quantity of cards equal to the value of that bottom card. (If the bottom
	 card is a joker, let its value be 27, regardless of which joker it is.)
	 Take that group of cards and move them to the bottom of the deck. Return
	 the bottom card to the bottom of the deck.
	 */
	public void shiftAmountOfCards() {
		int removedCard = this.remove(size - 1);

		// Redirect removedCard amount of cards to the bottom of the desk
		tail = getNode(Math.min(removedCard, 27) - 1);
		head = tail.next;

		add(removedCard); // Put the removed card back to the bottom of the deck
	}

	public int nextCardValue() {
		return getData(Math.min(head.data, 27)); // Return the value of the smallest card or 27 if joker
	}

	public int getCardPosition(int cardNumber) {
		Iterator<Integer> deckIterator = this.iterator();

		for (int i = 0, curCard = deckIterator.next(); i < FULL_SIZE; i++, curCard = deckIterator.next())
			if (curCard == cardNumber)
				return i;

		throw new IllegalArgumentException(cardNumber + " is not a valid card. Please enter a card from 0 to " + FULL_SIZE);
	}

	public boolean isJoker(int cardNumber) {
		return cardNumber >= 27;//cardNumber == 27 || cardNumber == 28;
	}
}