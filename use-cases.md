---
description: Use cases diagram
---

# Use cases

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

<figure><img src=".gitbook/assets/Progetto Klotski-Use Case Diagram.jpg" alt=""><figcaption><p>Use cases diagram</p></figcaption></figure>
