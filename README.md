# TCP1201-OOPDS-Assignment-2023
This is the game of GO BOOM, where the players can only play as the same suit or same rank as the lead card. 
Player will win the trick if they play the highest rank and same suit as the lead card.
The game will end when one of the players run out of hand card, the other players' score will be calculated.
The player with lowest score will be the winner of the game.

## How to get started
1. Compile Game.java file by javac Game.java, then run using java game
2. #1 trick's lead card will be dealt, to determine the first player and the sequence of game.
3. After the #1 trick, the lead card will be determined by the previous trick player and the sequence of play. (e.g. player 3 wins trick #1, so player 3 will play the lead card in the next trick)
4. Player can only play card with same suit as lead card. (e.g. lead card is Joker 5, so player can only play card with Joker suit or rank 5)
5. If the player doesn't have either of the card, they will need to draw from deck until they have a playable card. If the player draw until the deck runs out, their turn is skipped.
6. After all players have played card, the winner of the trick will be decided by the one with highest rank same suit card.
7. The winner of trick will lead the next trick.
8. The game continues until one of the players run out of hand cards. For other players' the score will be calculated based on rank of remaning cards with the scoring:
      - Ace = 1
      - Numbers = according to their numbers
      - K/Q/J = 10
9. The player with lowest score wins the game.

### Contributors
1. 1221303664 Ng Yi Min
2. 1211101153 Mischelle Thanusha Julius
3. 1221305225 Chin Jhun Loc 
4. 1221303663 Chan Yeng Hui 
