package pl.lechowicz;

import java.util.*;

public class BattleField {
    private static final int SIZE_FIELD = 100;
    private static final int SIZE = 10;

    private record Point(int x, int y) {}

    private enum Ship {
        BATTLESHIP(4), CRUISER(3), DESTROYER(2), SUBMARINE(1);

        private final int length;

        public int getLength() {
            return length;
        }

        Ship(int length) {
            this.length = length;
        }
    }

    public static boolean fieldValidator(int[][] field) {
        Map<Ship, Integer> availableShips = createAvailableShipsMap();
        Map<Integer, Ship> allShips = createMapWithLengthOfShips();

        if(getLengthOfAllShipsOnBoard(field) != getSumOfAllShips(availableShips))
            return false;

        Set<Point> checkedPoints = new HashSet<>();

        int x = 0, y = 0;
        int possibleShipLength = 0;
        Optional<Point> nextPointAfterShip = Optional.empty();
        while(checkedPoints.size() < SIZE_FIELD) {
            var actualPoint = new Point(x, y);
            boolean isChecked = false;
            if(checkedPoints.contains(actualPoint)) {
                isChecked = true;
            } else {
                checkedPoints.add(actualPoint);
            }
            if(field[y][x] == 1 && !isChecked) {
                if(checkContactShipByEdge(field, x, y))
                    return false;
                
                possibleShipLength++;

                if(shipIsPlaceInHorizontal(x, field[y])) {
                    x++;
                } else if(shipIsPlaceInVertical(field, y, x)) {
                    if(nextPointAfterShip.isEmpty())
                        nextPointAfterShip = calculateNextPointAfterFinishCheckShip(x, y);
                    y++;
                } else if(possibleShipLength > 0) {
                    if (allShips.containsKey(possibleShipLength)) {
                        checkShip(availableShips, allShips.get(possibleShipLength));
                    } else {
                       return false;
                    }

                    possibleShipLength = 0;
                    if(nextPointAfterShip.isPresent()) {
                        x = nextPointAfterShip.get().x;
                        y = nextPointAfterShip.get().y;
                        nextPointAfterShip = Optional.empty();
                    }
                }
            } else if(x+1 < SIZE) {
                x++;
            } else if(y+1 < SIZE) {
                x = 0;
                y++;
            }
        }

        for (int value : availableShips.values()) {
            if(value != 0) {
                availableShips.forEach((key, v) -> System.out.println(key + ":" + v));
                for(int i = 0; i < SIZE; i++) {
                    for(int j = 0; j < SIZE; j++)
                        System.out.print(field[i][j]);
                    System.out.println();
                }
                return false;

            }
        }


        return true;
    }

    private static Optional<Point> calculateNextPointAfterFinishCheckShip(int x, int y) {
        Optional<Point> nextPointAfterShip;
        if(x + 1 < SIZE)
            nextPointAfterShip = Optional.of(new Point(x + 1, y));
        else {
            nextPointAfterShip = Optional.of(new Point(0, y + 1));
        }

        return nextPointAfterShip;
    }

    private static boolean shipIsPlaceInVertical(int[][] field, int y, int x) {
        return y + 1 < SIZE && field[y + 1][x] == 1;
    }

    private static boolean shipIsPlaceInHorizontal(int x, int[] field) {
        return x + 1 < SIZE && field[x + 1] == 1;
    }

    private static boolean checkContactShipByEdge(int[][] field, int x, int y) {
        return checkRightBottomArea(field, x, y) ||
                checkLeftBottomArea(field, x, y) ||
                checkLeftTopArea(field, x, y) ||
                checkRightTopArea(field, x, y);
    }

    private static boolean checkRightTopArea(int[][] field, int x, int y) {
        return x + 1 > SIZE && y - 1 >= 0 && field[y - 1][x + 1] == 1;
    }

    private static boolean checkLeftTopArea(int[][] field, int x, int y) {
        return x - 1 >= 0 && y - 1 >= 0 && field[y - 1][x - 1] == 1;
    }

    private static boolean checkLeftBottomArea(int[][] field, int x, int y) {
        return x - 1 >= 0 && y + 1 < SIZE && field[y + 1][x - 1] == 1;
    }

    private static boolean checkRightBottomArea(int[][] field, int x, int y) {
        return x + 1 < SIZE && y + 1 < SIZE && field[y + 1][x + 1] == 1;
    }

    private static int getSumOfAllShips(Map<Ship, Integer> availableShips) {
        return availableShips.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getLength() * entry.getValue())
                .sum();
    }

    private static int getLengthOfAllShipsOnBoard(int[][] field) {
        return Arrays.stream(field)
                .flatMapToInt(Arrays::stream)
                .sum();
    }

    private static Map<Integer, Ship> createMapWithLengthOfShips() {
        Map<Integer, Ship> allShips = new HashMap<>();
        Arrays.stream(Ship.values())
                .forEach(ship -> allShips.put(ship.getLength(), ship));
        return allShips;
    }

    private static Map<Ship, Integer> createAvailableShipsMap() {
        Map<Ship, Integer> availableShips = new HashMap<>();
        availableShips.put(Ship.BATTLESHIP, 1);
        availableShips.put(Ship.CRUISER, 2);
        availableShips.put(Ship.DESTROYER, 3);
        availableShips.put(Ship.SUBMARINE, 4);
        return availableShips;
    }

    private static void checkShip(Map<Ship, Integer> availableShips, Ship ship) {
        int count = availableShips.get(ship);
        if(count != 0)
            availableShips.put(ship, count-1);
    }
}