# Illumio Coding Assignment, PCE teams

## About this project
- This is a maven project and the source code is written in Java 8.
- To build this project, first clone it and run `mvn install`
- To run the automated tests that I've written, run `mvn test`

## Project Structure and development
- I used Test Driven Development (TDD) to develop this code.
- There are 2 sample input csv files (located inside `src/test/resources` folder)
- To check if my program is running correctly, I use 2 additional csv files that contain 1 additional column as compared to the input provided, which stores the result of each query.

## Design decisions
- A simple solution would be to store the rules in 4 different lists for different directions and protocols
  - downside of this approach is that it would take a lot of time to scale when there are a lot of queries. There will be delay in both saving the rules and then checking each rule. 
  - Search complexity to check one test case = `O(n)`
  - Total time complexity would be `O(n*n)`
- **[My solution]** I have used Interval trees (https://www.geeksforgeeks.org/interval-tree/), which reduce the search complexity to `O(log n)`. Instead of implementing one myself, I decided to use an existing implementation taken from here: https://github.com/lodborg/interval-tree
  - advantages: interval trees save intervals, so any overlapping ranges would be merged. This would save space too
  - Search time complexity would be `O(log n)`, since interval trees are built on the principle of BSTs and AVL trees.
  - Complexity to build up the tree would be `O(n log n)`
- Another way to drastically reduce the time complexity is to use 2 boolean arrays for ports and hash for IP addresses. All ports that are allowed would be marked true and same for IP addresses.
  - The downside of this approach is that it would take too much memory (4294967296 elements for IP address array), so I did not implement this solution
  - Search complexity of this approach would be constant [`O(1)`]
  - Due to space restrictions, I decided not to implement this technique.
  - Preparing the hash array would also take a long time if we specify large intervals (we can use multithreading, but the space is still an issue which we can't ignore, so using interval trees is the best strategy here)

## External libraries used (maven repositories)
- `JUnit` to write automated tests
- `com.lodborg:interval-tree` for the implementation of interval tree

## Team preference

1. Platform team
2. Data team
