import java.util.Iterator;
import java.util.List;

public class HalfDeck extends CircularLinkedList<Integer> {

	private final int DECK_SIZE = 26;
	private final int JOKER_COUNT = 2;
	private final int FULL_SIZE = DECK_SIZE + JOKER_COUNT;

	public HalfDeck() {
		this.add(1);
		this.add(4);
		this.add(7);
		this.add(10);
		this.add(13);
		this.add(16);
		this.add(19);
		this.add(22);
		this.add(25);
		this.add(28);
		this.add(3);
		this.add(6);
		this.add(9);
		this.add(12);
		this.add(15);
		this.add(18);
		this.add(21);
		this.add(24);
		this.add(27);
		this.add(2);
		this.add(5);
		this.add(8);
		this.add(11);
		this.add(14);
		this.add(17);
		this.add(20);
		this.add(23);
		this.add(26);
	}

	public HalfDeck(List<Integer> givenHalfDeck) {
		for (int curCard : givenHalfDeck)
			this.add(curCard);
	}

	/** Shifts the card at cardIndex to the right shiftAmount times.
	 *
	 * @param cardIndex The desired card to be shifted's index
	 * @param shiftAmount amount of times to shift
	 */
	public void shiftRight(int cardIndex, int shiftAmount) {
		// Check if we can/should shift at all
		if (cardIndex < 0 || cardIndex >= size)
			throw new IndexOutOfBoundsException("Attempted to get index " + cardIndex + " when size is " + size);

		if (shiftAmount == 0 || size <= 1)
			return;

		// Fix shiftAmount if it's OOB
		if (shiftAmount >= size)
			shiftAmount = (cardIndex + 1) % size;

		// Gets the current node then repeatedly swaps its data with the one to its right shiftAmount of times.
		Node<Integer> curNode = getNode(cardIndex);
		for (int i = 0; i < shiftAmount; i++) {
			int nextData = curNode.next.data;

			curNode.next.data = curNode.data; // Shift current data to the right
			curNode.data = nextData;
			curNode = curNode.next;
		}
	}

	/**
	 * Performs the "triple cut" by shifting all cards on top of the first
	 * joker with all of the cards below the second joker
	 */
	public void jokerTripleCut() {
		Node<Integer> currentCard = head, previousCard = null;
		Node<Integer>[] jokerPositions = new Node[2];
		Node<Integer> beforeFirstJoker = null;

		// Assign joker positions
		for (int i = 0, j = 0; i < size && j < 2; i++, previousCard = currentCard, currentCard = currentCard.next) {
			if (isJoker(currentCard.data)) {
				if (j == 0)
					beforeFirstJoker = previousCard;

				jokerPositions[j++] = currentCard;
			}
		}

		if (jokerPositions[0] == head) { // A joker is the top card, no cards before it to move.
			head = jokerPositions[1].next;
			tail = jokerPositions[1];
		} else if (jokerPositions[1] == tail) { // A joker is the bottom card, no cards after it...
			head = jokerPositions[0];
			tail = beforeFirstJoker;
		} else { // No special care should be taken when shifting
			Node<Integer> newHead = jokerPositions[1].next;
			jokerPositions[1].next = head;
			tail.next = jokerPositions[0];
			head = newHead;
			tail = beforeFirstJoker;
			tail.next = head;
		}
	}

	/**
	 *
	 */
	public void shiftAmountOfCards() {
		int removedCard = this.remove(size - 1);

		// Redirect removedCard amount of cards to the bottom of the desk
		tail = getNode(Math.min(removedCard, 27) - 1); // -1 because we just removed a card.
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