# Requirements
- Java 17
- Maven

## Description
Write a method that takes a field for well-known board game "Battleship" as an argument and returns true if it has a valid disposition of ships, false otherwise. Argument is guaranteed to be 10*10 two-dimension array. Elements in the array are numbers, 0 if the cell is free and 1 if occupied by ship.

**Battleship** (also Battleships or Sea Battle) is a guessing game for two players. Each player has a 10x10 grid containing several "ships" and objective is to destroy enemy's forces by targetting individual cells on his field. The ship occupies one or more cells in the grid. Size and number of ships may differ from version to version. In this kata we will use Soviet/Russian version of the game.

![](/home/michael/Documents/VariousProjects/CodeWars/BattleshipFieldValidator/images/board.png "Board of Battleship")

Before the game begins, players set up the board and place the ships accordingly to the following rules:
- There must be single battleship (size of 4 cells), 2 cruisers (size 3), 3 destroyers (size 2) and 4 submarines (size 1). Any additional ships are not allowed, as well as missing ships.
- Each ship must be a straight line, except for submarines, which are just single cell.

![](/home/michael/Documents/VariousProjects/CodeWars/BattleshipFieldValidator/images/correct_ship.png "Correct ship schema")

- The ship cannot overlap or be in contact with any other ship, neither by edge nor by corner.

![](/home/michael/Documents/VariousProjects/CodeWars/BattleshipFieldValidator/images/uncorrect_ship.png "Incorrect ship schema")

This is all you need to solve this kata. If you're interested in more information about the game, visit this link.

### Link to origin:
[Codewars Kata](https://www.codewars.com/kata/52bb6539a4cf1b12d90005b7)
