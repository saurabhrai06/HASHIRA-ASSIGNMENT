
# HASHIRA-ASSIGNMENT
# 🔐 Secret Solver: Polynomial Interpolation

This project demonstrates how to reconstruct the constant term (the **y-intercept**) of a hidden polynomial using **Lagrange Interpolation**.  
The twist? The polynomial’s points are provided in a JSON file where the **y-values are encoded in different number bases**.

---

## 🎯 Core Concepts

- **Polynomial Interpolation**  
  Constructing a unique polynomial that passes through a given set of points.

- **Multi-Base Decoding**  
  Numbers in the JSON input may be written in binary, octal, hexadecimal, or any other base.  
  Java’s `BigInteger` is used to decode these into decimal values.

- **Lagrange Interpolation**  
  A formula that allows us to directly compute the polynomial’s value at `x=0`  
  (the constant term) without finding the entire polynomial.

---

## 📂 File Structure

- `SecretSolver.java` → Main program logic (parsing, decoding, interpolation).  
- `json-20231013.jar` → Dependency for parsing JSON (org.json library).  
- `input.json` / `input2.json` → Example input files containing encoded points.

---

## ⚙️ How It Works

1. **Parse JSON**  
   Reads the input file (`input.json` by default).

2. **Extract Metadata**  
   - `n`: total number of points available.  
   - `k`: number of points required for interpolation.

3. **Decode Points**  
   - Keys (`"1"`, `"2"`, …) → x-coordinates.  
   - `value` + `base` → y-coordinate (decoded to decimal).  
   Example:  
   ```json
   "2": {
       "base": "2",
       "value": "111"
   }
