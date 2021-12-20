import fs from "fs/promises";

async function readInput(): Promise<number[]> {
  const input = await fs.readFile(`./input/main.txt`, { encoding: "utf-8" });
  return input.split(",").map((line) => Number.parseInt(line));
}

/**
 * Removes the first element of the array and returns it.
 * @param arr The array
 * @returns The removed element of the array
 */
function popStart(arr: Array<number>): number {
  return arr.splice(0, 1)[0];
}

function getTotalFish(initialState: number[], days: number) {
  // timerCounts[x] = number of fish with timer value x
  let timerCounts = Array(9).fill(0);
  initialState.forEach((timer) => timerCounts[timer]++);
  for (let i = 0; i < days; i++) {
    const resetFish = popStart(timerCounts);
    timerCounts[6] += resetFish;
    timerCounts.push(resetFish);
  }
  return timerCounts.reduce((sum, count) => sum + count, 0);
}

readInput().then((initialState) => {
  console.log("Part 1");
  console.log(getTotalFish(initialState, 80));
  console.log("Part 2");
  console.log(getTotalFish(initialState, 256));
});
