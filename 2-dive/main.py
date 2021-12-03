from enum import Enum
from typing import List, Tuple


class Direction(Enum):
    VERTICAL = 1
    HORIZONTAL = 2


class Move:
    direction: Direction
    units: int

    def __init__(self, line) -> None:
        direction_str, units_str = line.split(" ")
        self.units = int(units_str)
        if direction_str == "forward":
            self.direction = Direction.HORIZONTAL
        else:
            self.direction = Direction.VERTICAL
            if direction_str == "up":
                self.units = self.units * -1


def part_1(moves: List[Move]) -> Tuple[int, int]:
    horiz = 0
    depth = 0
    for move in moves:
        if move.direction == Direction.HORIZONTAL:
            horiz = horiz + move.units
        else:
            depth = depth + move.units
    return [horiz, depth]


def part_2(moves: List[Move]) -> Tuple[int, int]:
    horiz = 0
    depth = 0
    aim = 0
    for move in moves:
        if move.direction == Direction.HORIZONTAL:
            horiz = horiz + move.units
            depth = depth + aim * move.units
        else:
            aim = aim + move.units
    return [horiz, depth]


def print_position(name: str, pos: Tuple[int, int]):
    print(name)
    print(f"Position: ({pos[0]}, {pos[1]})")
    print(f"Sum: {pos[0]  *pos[1]}\n")


if __name__ == "__main__":
    with open("./2-dive/input.txt") as input_file:
        lines = input_file.readlines()
    moves = [Move(line) for line in lines]
    print_position("Part 1", part_1(moves))
    print_position("Part 2", part_2(moves))
