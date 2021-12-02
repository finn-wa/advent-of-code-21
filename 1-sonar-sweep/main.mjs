import fs from 'fs/promises';

/**
 * Returns the depth readings.
 * @return {Promise<number[]>} Array of integers
 */
async function readMeasurements() {
  const input = await fs.readFile('./input.txt', { encoding: 'utf-8' });
  return input.split('\n').map((line) => Number.parseInt(line));
}

/**
 * Calculates the number of times the depth increases
 * @param measurements {number[]} Depth readings
 * @returns {number} Number of times depth increases
 */
function part1(measurements) {
  let count = 0;
  let prevDepth = Number.MAX_SAFE_INTEGER;
  for (const depth of measurements) {
    if (depth > prevDepth) count++;
    prevDepth = depth;
  }
  return count;
}

/**
 * Calculates the number of times the depth increases with a sliding window
 * @param measurements {number[]} Depth readings
 * @param windowSize {number} Size of window
 * @returns {number} Number of times depth increases
 */
function part2(measurements, windowSize) {
  let count = 0;
  let prevSum = Number.MAX_SAFE_INTEGER;
  for (let i = 0; i <= measurements.length - windowSize; i++) {
    const sum = measurements[i] + measurements[i + 1] + measurements[i + 2];
    if (sum > prevSum) count++;
    prevSum = sum;
  }
  return count;
}

async function main() {
  console.log('Reading measurements');
  const measurements = await readMeasurements();

  console.log('Part 1');
  console.log(part1(measurements));
  console.log();

  console.log('Part 2');
  console.log(part2(measurements, 3));
  console.log();
}

main().then(
  () => console.log('Done'),
  (err) => console.error(err)
);
