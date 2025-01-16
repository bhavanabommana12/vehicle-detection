import java.math.BigInteger;
import java.util.*;

public class Main
{

    // Helper function to decode base-specific values
    public static BigInteger decodeValue(int base, String value) {
        return new BigInteger(value, base);
    }

    // Lagrange Interpolation to calculate the polynomial and constant term c
    public static BigInteger lagrangeInterpolation(List<BigInteger> xValues, List<BigInteger> yValues) {
        BigInteger c = BigInteger.ZERO;

        int n = xValues.size();
        for (int i = 0; i < n; i++) {
            BigInteger term = yValues.get(i);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    // Calculate the product (x - x_j) / (x_i - x_j) and multiply it to the term
                    BigInteger numerator = BigInteger.ZERO.subtract(xValues.get(j)); // x - x_j (at x = 0)
                    BigInteger denominator = xValues.get(i).subtract(xValues.get(j));
                    term = term.multiply(numerator).divide(denominator);
                }
            }
            c = c.add(term);
        }
        return c;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of roots (n)
        System.out.print("Enter the number of roots (n): ");
        int n = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Map to store the roots and corresponding base-value pairs
        Map<Integer, String[]> roots = new HashMap<>();

        // Input the base and value for each root
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter the base for root " + i + ": ");
            String base = scanner.nextLine();

            System.out.print("Enter the encoded value for root " + i + ": ");
            String value = scanner.nextLine();

            // Store the base and value as an array in the map, with the root index as the key
            roots.put(i, new String[]{base, value});
        }

        // Decode the values
        List<BigInteger> xValues = new ArrayList<>();
        List<BigInteger> yValues = new ArrayList<>();
        for (Map.Entry<Integer, String[]> entry : roots.entrySet()) {
            int base = Integer.parseInt(entry.getValue()[0]);
            String value = entry.getValue()[1];
            xValues.add(BigInteger.valueOf(entry.getKey()));  // x is the root index (1, 2, 3, ...)
            yValues.add(decodeValue(base, value));  // y is the decoded value
        }

        // Compute the constant term using Lagrange interpolation at x = 0
        BigInteger constantTerm = lagrangeInterpolation(xValues, yValues);

        // Print the constant term 'c'
        System.out.println("Constant term c: " + constantTerm);

        scanner.close();  // Close the scanner
    }
}