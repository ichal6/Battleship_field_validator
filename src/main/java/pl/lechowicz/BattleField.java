package pl.lechowicz;

import java.util.*;
import java.util.stream.Collectors;

public class BattleField {
    private static final int SIZE_FIELD = 100;
    private static final int SIZE = 10;
    private static final Map<Ship, Integer> availableShips = new HashMap<>();


    private static record Point(int x, int y) {};

    private static enum Ship {
        BATTLESHIP(4), CRUISER(3), DESTROYER(2), SUBMARINE(1);

        private int length;

        Ship(int length) {
            this.length = length;
        }
    }

    public static boolean fieldValidator(int[][] field) {
        availableShips.clear();
        availableShips.put(Ship.BATTLESHIP, 1);
        availableShips.put(Ship.CRUISER, 2);
        availableShips.put(Ship.DESTROYER, 3);
        availableShips.put(Ship.SUBMARINE, 4);

        int sum = 0;

        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                sum += field[i][j];

        if(sum != 20)
            return false;

        List<Point> checkedPoints = new ArrayList<>();

        int x = 0, y = 0;
        int possibleShip = 0;
        Point nextPointAfterShip = null;
        while(checkedPoints.size() < SIZE_FIELD) {
            var actualPoint = new Point(x, y);
            boolean isChecked = false;
            if(checkedPoints.contains(actualPoint)) {
                isChecked = true;
            } else {
                checkedPoints.add(actualPoint);
            }
            if(field[y][x] == 1 && !isChecked) {
                if(x+1 < SIZE && y+1 < SIZE && field[y+1][x+1] == 1) {
                    return false;
                }
                if(x-1 >= 0 && y+1 < SIZE && field[y+1][x-1] == 1) {
                    return false;
                }
                if(x-1 >= 0 && y-1 >= 0 && field[y-1][x-1] == 1) {
                    return false;
                }
                if(x+1 > SIZE && y-1 >= 0 && field[y-1][x+1] == 1) {
                    return false;
                }

//                if((x+1 < SIZE && field[y][x+1] == 1) && (y+1 < SIZE && field[y+1][x] == 1)) {
//                    return false;
//                }
//                if(x-1 > 0 && y-1 > 0 && field[y-1][x-1] == 1) {
//                    return false;
//                }
//                if((x-1 > 0 && field[y][x-1] == 1) && (y+1 < SIZE && field[y+1][x] == 1)) {
//                    return false;
//                }

                possibleShip++;

                if(x+1 < SIZE && field[y][x+1] == 1) {
                    x++;
                    continue;
                }

                if(y+1 < SIZE && field[y+1][x] == 1) {
                    if(nextPointAfterShip == null) {
                        if(x + 1 < SIZE)
                            nextPointAfterShip = new Point(x+1, y);
                        else {
                            nextPointAfterShip = new Point(0, y+1);
                        }
                    }
                    y++;
                    continue;
                }

                if(possibleShip > 0) {
                    switch(possibleShip) {
                        case 1:
                            checkShip(Ship.SUBMARINE);
                            break;
                        case 2:
                            checkShip(Ship.DESTROYER);
                            break;
                        case 3:
                            checkShip(Ship.CRUISER);
                            break;
                        case 4:
                            checkShip(Ship.BATTLESHIP);
                            break;
                        default:
                            return false;
                    }
                    possibleShip = 0;
                    if(nextPointAfterShip != null) {
                        x = nextPointAfterShip.x;
                        y = nextPointAfterShip.y;
                        nextPointAfterShip = null;
                        continue;
                    }
                }
            }
            if(x+1 < SIZE) {
                x++;
            } else if(y+1 < SIZE) {
                x = 0;
                y++;
            } else {
                break;
            }
        }

        Set<Point> collect = checkedPoints.stream().filter(i -> Collections.frequency(checkedPoints, i) > 1).collect(Collectors.toUnmodifiableSet());

        System.out.println(collect);

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
    private static void checkShip(Ship ship) {
        int count = availableShips.get(ship);

        if(count != 0) {
            availableShips.put(ship, count-1);
        }
    }
}