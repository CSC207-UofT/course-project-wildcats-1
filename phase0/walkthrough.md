GameManager creates 64 instances of square and two new instances of user. Board accordingly stores all instances of square,
each either being empty or containing a piece. GameManager prompts user input to move a piece. GameManager updates two instances
of square, first storing a pawnPiece object in an empty corresponding square and removing a pawnPiece from the original square.
Board state is updated with new squares. GameManager then changes active player and prompts the next user to make a move.
User selects forfeit so gameManager calls endGame method. GameManager prompts user to choose if they want to play again.
If no, gameManager reads input and terminates program.
