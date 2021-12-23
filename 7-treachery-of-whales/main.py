class FuelUsage:
    position: int
    usage: int

    def __init__(self, position: int, usage: int):
        self.position = position
        self.usage = usage

    def __lt__(self, __other) -> bool:
        if self.usage == None:
            return False
        return self.usage < __other.usage


with open("./7-treachery-of-whales/input/main.txt") as file:
    positions = [int(p) for p in file.read().split(",")]


def part_1_usage(target_pos: int) -> int:
    "Returns the fuel usage for moving every crab to the specified position (part 1)"
    total = 0
    for pos in positions:
        total += abs(pos - target_pos)
    return total


def part_2_usage_lookup(max_move: int) -> list[int]:
    usages = []
    usage = 0
    for move in range(max_move + 1):
        usage += move
        usages.append(usage)
    return usages


max_position = max(positions)
p2_usages = part_2_usage_lookup(max_position)


def part_2_usage(target_pos: int) -> int:
    "Returns the fuel usage for moving every crab to the specified position (part 2)"
    total = 0
    for pos in positions:
        total += p2_usages[abs(pos - target_pos)]
    return total


min_pos_1 = FuelUsage(-1, None)
min_pos_2 = FuelUsage(-1, None)

for pos in range(max_position):
    min_pos_1 = min(FuelUsage(pos, part_1_usage(pos)), min_pos_1)
    min_pos_2 = min(FuelUsage(pos, part_2_usage(pos)), min_pos_2)

print("Part 1")
print(min_pos_1.position)
print(min_pos_1.usage)
print("Part 2")
print(min_pos_2.position)
print(min_pos_2.usage)
