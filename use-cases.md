# Use cases

<figure><img src=".gitbook/assets/Progetto Klotski-Use Case Diagram.jpg" alt=""><figcaption><p>Use cases diagram</p></figcaption></figure>

| Use Case 1: Start game |                                                                                                                                                                                |
| ---------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Scope                  | Klotski Puzzle                                                                                                                                                                 |
| Precondition           | None                                                                                                                                                                           |
| Primary Actors         | Player                                                                                                                                                                         |
| Secondary Actors       | Catalog                                                                                                                                                                        |
| Success end condition  | Configuration is saved, the game start                                                                                                                                         |
| Failed end condition   | If the operation fails a message is sent to the player                                                                                                                         |
| Trigger                | Player interaction                                                                                                                                                             |
| Main Success Scenario  | <p>1) The player chooses one of the predefined piece configurations<br>2) The chosen configuration is saved<br>3) The game starts with the chosen configuration</p>            |
| Alternative Flow       | <p>Before step 1, if the player wants to select a previously saved game:<br>1- The player logs in, include use case 2<br>2- The saved game is selected<br>Resume at step 2</p> |



| Use Case 2: Log In    |                                                                                                                                                                                          |
| --------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Scope                 | Klotski Puzzle                                                                                                                                                                           |
| Precondition          | User not authenticated                                                                                                                                                                   |
| Primary Actors        | Player                                                                                                                                                                                   |
| Secondary actors      | Catalog                                                                                                                                                                                  |
| Success end condition | The player is correctly authenticated in into the Catalog                                                                                                                                |
| Failed end condition  | If the operation fails a message is sent to the player                                                                                                                                   |
| Trigger               | Player interaction                                                                                                                                                                       |
| Main Success Scenario | <p>1) The player enters the credentials<br>2) The Catalog checks if the credentials are correct<br>3) The player is authenticated<br>4) A confirmation message is sent to the player</p> |
| Exception             | <p>If at step 2 the credential are wrong<br>1- Error message is sent to the player<br>Use case end</p>                                                                                   |

| Use Case 3: Registration |                                                                                                                                                                                                |
| ------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Scope                    | Klotski Puzzle                                                                                                                                                                                 |
| Precondition             | User not registered                                                                                                                                                                            |
| Primary Actors           | Player                                                                                                                                                                                         |
| Secondary actors         | Catalog                                                                                                                                                                                        |
| Success end condition    | The player is correctly signed up into the Catalog                                                                                                                                             |
| Failed end condition     | If the operation fails a message is sent to the player                                                                                                                                         |
| Trigger                  | Player interaction                                                                                                                                                                             |
| Main Success Scenario    | <p>1) The player enters the credentials<br>2) The Catalog checks if the credentials are usable<br>3) The player is correctly registered<br>4) A confirmation message is sent to the player</p> |
| Exception                | <p>If at step 2 the username is unavailable<br>1- Error message is sent to the player<br>Use case end</p>                                                                                      |

| Use Case 4: Move piece |                                                                                                                                                                                                              |
| ---------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Scope                  | Klotski Puzzle                                                                                                                                                                                               |
| Precondition           | The game is in progress                                                                                                                                                                                      |
| Primary Actors         | Player                                                                                                                                                                                                       |
| Success end condition  | The choosen piece is correctly moved                                                                                                                                                                         |
| Failed end condition   | The choosen piece does not move                                                                                                                                                                              |
| Trigger                | Player interaction                                                                                                                                                                                           |
| Main Success Scenario  | <p>1) The player selects the piece he wants to move<br>2) The player chooses the final position for the piece<br>3) The piece is moved in the final position<br>4) The moves counter is increased by one</p> |
| Exception              | <p>If at step 2 the player choose an illegal final position:<br>1- The use case end</p>                                                                                                                      |
| Extension              | <p>After step 3 if the 2x2 blocks is in the escape point<br>1- A win message is sent to the player</p>                                                                                                       |

| Use Case 5: Undo last move |                                                                                                                                  |
| -------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| Scope                      | Klotski Puzzle                                                                                                                   |
| Precondition               | The game is in progress                                                                                                          |
| Primary Actors             | Player                                                                                                                           |
| Success end condition      | The last move is undone                                                                                                          |
| Failed end condition       | Nothing happen                                                                                                                   |
| Trigger                    | Player interaction                                                                                                               |
| Main Success Scenario      | <p>1) The player selects the undo button<br>2) The last move is undone and erased<br>3) The move counter is decreased by one</p> |
| Exception                  | <p>If at step 1 there are no moves:<br>1- The use case end</p>                                                                   |

| Use Case 6: Save game |                                                                                                                                       |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| Scope                 | Klotski Puzzle                                                                                                                        |
| Precondition          | The game is in progress                                                                                                               |
| Primary Actors        | Player                                                                                                                                |
| Secondary Actors      | Catalog                                                                                                                               |
| Success end condition | The current game is saved in catalog                                                                                                  |
| Failed end condition  | If the operation fails a message is sent to the player                                                                                |
| Trigger               | Player interaction                                                                                                                    |
| Main Success Scenario | <p>1) The player enters a name for the game<br>2) The catalog checks if the name is valid<br>3) The game is saved in the database</p> |
| Extensions            | <p>After the step 2 if the player isn't authenticated:<br>Log in, include use case 2<br>resume execution at step 3</p>                |
| Exception             | <p>At step 3 If the name is unusable:<br>1- an error box is shown<br>The use case is ended</p>                                        |

| Use Case 7: Next Best Move |                                                                                                                                                                      |
| -------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Scope                      | Klotski Puzzle                                                                                                                                                       |
| Precondition               | The game is in progress                                                                                                                                              |
| Primary Actors             | Player                                                                                                                                                               |
| Secondary Actors           | Solver                                                                                                                                                               |
| Success end condition      | The best move in order to win the game is made                                                                                                                       |
| Failed end condition       | If the operation fails, a message is sent to the player                                                                                                              |
| Trigger                    | Player interaction                                                                                                                                                   |
| Main Success Scenario      | <p>1) The Solver calculates the sequence of moves to solve the current configuration<br>2) The first of those moves is made<br>3) The moves counter is increased</p> |
| Extensions                 | <p>after step 3 if the 2x2 blocks is in the escape point<br>1- A win message is sent to the player</p>                                                               |

| Use Case 8: Restart game |                                                                               |
| ------------------------ | ----------------------------------------------------------------------------- |
| Scope                    | Klotski Puzzle                                                                |
| Precondition             | The game is in progress                                                       |
| Primary Actors           | Player                                                                        |
| Secondary Actors         |                                                                               |
| Success end condition    | The current game is started from the begin                                    |
| Failed end condition     | If there are no moves, nothing happen                                         |
| Trigger                  | Player interaction                                                            |
| Main Success Scenario    | <p>1) The game is started from the begin<br>2) The moves counter is reset</p> |

| Use Case 9: Log out   |                                                        |
| --------------------- | ------------------------------------------------------ |
| Scope                 | Klotski Puzzle                                         |
| Precondition          | User authenticated                                     |
| Primary Actors        | Player                                                 |
| Secondary Actors      | Catalog                                                |
| Success end condition | The player is logged out                               |
| Failed end condition  | If the operation fails a message is sent to the player |
| Trigger               | Player interaction                                     |
| Main Success Scenario | 1) The player is logged out                            |

| Use Case 10: Delete user |                                                                                                     |
| ------------------------ | --------------------------------------------------------------------------------------------------- |
| Scope                    | Klotski Puzzle                                                                                      |
| Precondition             | User authenticated                                                                                  |
| Primary Actors           | Player                                                                                              |
| Secondary Actors         | Catalog                                                                                             |
| Success end condition    | The player is deleted from the database and logged out from the system                              |
| Failed end condition     | If the operation fails a message is sent to the player                                              |
| Trigger                  | Player interaction                                                                                  |
| Main Success Scenario    | <p>1) The player is logged out, include use case 9<br>2) The player is deleted from the catalog</p> |

| Use Case 11: Delete game |                                                                                                                    |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------ |
| Scope                    | Klotski Puzzle                                                                                                     |
| Precondition             | User authenticated and at least one game saved                                                                     |
| Primary Actors           | Player                                                                                                             |
| Secondary Actors         | Catalog                                                                                                            |
| Success end condition    | The current game is deleted from catalog                                                                           |
| Failed end condition     | If the operation fails a message is sent to the player                                                             |
| Trigger                  | Player interaction                                                                                                 |
| Main Success Scenario    | <p>1) The player player choose which game wants to delete<br>2) The game is deleted from the catalog</p>           |
| Alternative Flow         | <p>At step 1 if the player wants to delete all his games:<br>1) All player's games is deleted from the catalog</p> |
