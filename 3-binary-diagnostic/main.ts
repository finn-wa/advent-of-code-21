type bit = 0 | 1;
type binary = bit[];
type decimal = number;

async function readInput(file: string): Promise<binary[]> {
  const data: string = await Deno.readTextFile(`./3-binary-diagnostic/input/${file}.txt`);
  return data
    .split('\n')
    .map((line) => Array.from(line).map((bit) => Number.parseInt(bit, 2) as bit));
}

function toDecimal(bits: binary): decimal {
  return Number.parseInt(bits.join(''), 2);
}

function getColSums(numbers: binary[]) {
  return numbers.reduce((acc: binary, number: binary) => {
    number.forEach((bit, index) => (acc[index] += bit));
    return acc;
  }, Array(numbers[0].length).fill(0));
}

enum Comparison {
  LESSER = 0,
  GREATER = 1,
}

enum Inclusivity {
  EXCLUSIVE = 0,
  INCLUSIVE = 1,
}

enum CommonTarget {
  LEAST_COMMON = 0,
  MOST_COMMON = 1,
}

class ComparatorOperator {
  readonly compare: (x: number, y: number) => boolean;

  constructor(readonly comparison: Comparison, readonly inclusivity: Inclusivity) {
    if (comparison === Comparison.LESSER) {
      if (inclusivity === Inclusivity.INCLUSIVE) {
        this.compare = (x, y) => x <= y;
      } else {
        this.compare = (x, y) => x < y;
      }
    } else {
      if (inclusivity === Inclusivity.INCLUSIVE) {
        this.compare = (x, y) => x >= y;
      } else {
        this.compare = (x, y) => x > y;
      }
    }
  }
}

class CommonBitFinder {
  private readonly comparator: ComparatorOperator;
  constructor(readonly target: CommonTarget, readonly tiebreaker: bit) {
    this.comparator = new ComparatorOperator(target.valueOf(), tiebreaker);
  }
  findBit(numbers: binary[], position: number): bit {
    const sum = numbers.reduce((acc, number) => acc + number[position], 0);
    return this.comparator.compare(sum, numbers.length / 2) ? 1 : 0;
  }
  findAllBits(numbers: binary[]): binary {
    return Array(numbers[0].length)
      .fill(0)
      .map((_num, index) => this.findBit(numbers, index));
  }
}

function part1(input: binary[]): number {
  const gammaFinder = new CommonBitFinder(CommonTarget.MOST_COMMON, 1);
  const gamma = gammaFinder.findAllBits(input);
  const epsilonFinder = new CommonBitFinder(CommonTarget.LEAST_COMMON, 0);
  const epsilon = epsilonFinder.findAllBits(input);
  return toDecimal(gamma) * toDecimal(epsilon);
}

function findNumber(numbers: binary[], bitFinder: CommonBitFinder): number {
  let filtered = numbers;
  for (let i = 0; i < numbers.length; i++) {
    const commonBits = bitFinder.findAllBits(filtered);
    filtered = filtered.filter((number) => commonBits[i] === number[i]);
    if (filtered.length === 1) {
      return toDecimal(filtered[0]);
    }
  }
  throw new Error('Failed to find target');
}

function part2(input: binary[]) {
  const oxygenMapper = new CommonBitFinder(CommonTarget.MOST_COMMON, 1);
  const co2Mapper = new CommonBitFinder(CommonTarget.LEAST_COMMON, 0);
  return findNumber(input, oxygenMapper) * findNumber(input, co2Mapper);
}

const input = await readInput('main');

console.log('Part 1');
console.log(part1(input));
console.log();
console.log('Part 2');
console.log(part2(input));
