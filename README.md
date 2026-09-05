# Infosys SP & DSE Coding Preparation 🚀

Comprehensive solutions and detailed conceptual explanations in Java for coding problems frequently asked in **Infosys Specialist Programmer (SP)** and **Digital Specialist Engineer (DSE)** assessment rounds.

---

## 📂 Repository Structure

```text
├── InfosysSPDSEOneShot/
│   ├── q2.java    # Subarray Dominance Score (Dynamic Programming)
│   ├── q3.java    # Minimum Adjacent Swaps for Binary Strings (Median & Two-Pointer)
│   ├── q4.java    # Paint House with Budget Constraint (2D Dynamic Programming)
│   ├── q5.java    # Maximum Consecutive Vacation Days (Sliding Window & Boundary Array)
│   └── q6.java    # Data Center Cooling System (Greedy Interval Queries with Turbo Mode)
│
├── InfosysDSP_SP/
│   ├── q1.java    # Maximum Value by Removing a Digit
│   ├── q5.java    # Monster RPG Game (Greedy level & bonus power)
│   ├── q7.java    # Advanced Graph / DP Problem
│   ├── q10.java   # Array & String Manipulation
│   └── q11.java   # Tree / Graph Operations
│
├── .gitignore
└── README.md
```

---

## 💡 Problem Highlights & Logic

### 1. Subarray Dominance Score (`InfosysSPDSEOneShot/q2.java`)
- **Objective**: Partition an array into $K$ non-empty contiguous subarrays to minimize or maximize total dominance score (frequency of the mode element in each subarray).
- **Core Technique**: Dynamic Programming with backward iteration ($O(K \cdot N^2)$ time, $O(K \cdot N)$ space).
- **Key Insight**: Moving the split point $j$ backward allows incremental $O(1)$ frequency updates without recomputing subarray frequencies from scratch.

### 2. Minimum Adjacent Swaps to Group Characters (`InfosysSPDSEOneShot/q3.java`)
- **Objective**: Group all `'1'`s or all `'0'`s into a single contiguous block using the minimum number of adjacent swaps.
- **Core Technique**: Two-Pointer / Median Minimization ($O(N)$ time, $O(N)$ space).
- **Key Insight**: 
  $$\text{Swaps} = (\text{Distance between outer pair}) - (\text{Count of elements between them})$$
  which is literally the count of opposite characters to jump over!

### 3. Paint House with Budget Constraint (`InfosysSPDSEOneShot/q4.java`)
- **Objective**: Paint $N$ houses in a line using $C$ colors such that adjacent houses never share a color, minimizing total cost under budget $B$.
- **Core Technique**: 2D Dynamic Programming ($O(N \cdot C)$ time, $O(N \cdot C)$ space).
- **Recurrence**:
  $$\text{dp}[i][j] = \text{cost}[i][j] + \min_{k \neq j}(\text{dp}[i - 1][k])$$

### 4. Maximum Consecutive Vacation Days (`InfosysSPDSEOneShot/q5.java`)
- **Objective**: Maximize consecutive vacation days out of $N$ total days after canceling at most $K$ of $M$ obligations.
- **Core Technique**: Sliding Window with Virtual Boundary Array ($O(M \log M)$ time, $O(M)$ space).
- **Key Formula**:
  $$\text{Vacation Days} = \text{arr}[j + K] - \text{arr}[j - 1] - 1$$

### 5. Data Center Cooling System (`InfosysSPDSEOneShot/q6.java`)
- **Objective**: Execute point updates and range queries to greedily select up to $k$ cooling units at least $d$ intervals apart to maximize heat reduction.
- **Core Technique**: Greedy Sorting + `TreeSet` distance checking ($O(L \log L + L \log k)$ per query).
- **Key Mode**:
  - Turbo Mode ($\text{heat} \ge \text{critical}$): Contributes $4 \times \text{heat}$.
  - Normal Mode ($\text{heat} < \text{critical}$): Contributes $1 \times \text{heat}$.
  - Computes and returns the cumulative sum of all query results.

---

## 🛠️ How to Compile & Run

Make sure you have JDK 8+ installed:

```bash
# Compile any file
javac InfosysSPDSEOneShot/q5.java

# Run the class
java -cp InfosysSPDSEOneShot q5
```
