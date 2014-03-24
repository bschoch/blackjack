To run
cd to project directory
mvn clean install
mvn org.apache.tomcat.maven:tomcat7-maven-plugin:run

/blackjack/accounts/new/{amount}
-creates a new account
{amount}: amount to deposit in account

Response:
{"errors":[],"account":{"id":2,"balance":200},"game":null}
id: id of new account


/blackjack/accounts/{accountId}
-gets account for accountId
{accountId}: accoundId



/blackjack/{accountId}/deal/{bet}
-starts new game
{accountId}: accountId for account to withdraw bet from
{bet}: amount to bet
Parameters:
debugSplitNumber = {Ace, Two, Three, Four, ... , Jack, Queen, King}, a debug parameter in order to guarantee a hand of two duplicate cards of the given number

Response
{{"errors":[],"account":{"id":2,"balance":180},"game":{"id":16,"dealer":{"id":41,"bet":-1,"cards":[{"suit":"Hearts","number":"Ten"}],"result":null,"finished":false,"score":null},"players":[{"id":42,"bet":20,"cards":[{"suit":"Spades","number":"Five"},{"suit":"Clubs","number":"King"}],"result":null,"finished":false,"score":15}],"gameFinished":false,"nextPlayerId":42}}
The json game object has an id parameter that will be used for all subsequent moves of the game.


/blackjack/{gameId}/hit
-hits the player's hand with an id equal to the value of nextPlayerId from previous response

Response
{"errors":[],"account":{"id":2,"balance":180},"game":{"id":16,"dealer":{"id":41,"bet":-1,"cards":[{"suit":"Hearts","number":"Ten"}],"result":null,"finished":false,"score":null},"players":[{"id":42,"bet":20,"cards":[{"suit":"Spades","number":"Five"},{"suit":"Clubs","number":"King"},{"suit":"Spades","number":"Ace"}],"result":null,"finished":false,"score":16}],"gameFinished":false,"nextPlayerId":42}}


/blackjack/{gameId}/stand
-stands on the player's hand with an id equal to the value of nextPlayerId from previous response


/blackjack/{gameId}/double
-doubles the current bet, hits the current hand one more time, and then stands on the current hand
-doubling after splitting is supported


/blackjack/{gameId}/split
-only allowed as first action of the game
-can only split once per game
-two cards value must equal in value but not do not need to equal suit
-two equal cards can be triggered by using debugSplitNumber in the deal request
-hands are indicated by different player objects
-nextPlayerId of the response indicates which player the next request applies too