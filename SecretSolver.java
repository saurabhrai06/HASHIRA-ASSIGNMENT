import org.json.JSONObject;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SecretSolver {

    // Decode number from given base to BigInteger
    private static BigInteger convertFromBase(String number, int base) {
        return new BigInteger(number, base);
    }

    // Apply Lagrange Interpolation to compute constant term (P(0))
    private static double interpolateAtZero(List<Map.Entry<Long, BigInteger>> pts, int k) {
        double constant = 0.0;

        for (int i = 0; i < k; i++) {
            long xi = pts.get(i).getKey();
            BigInteger yi = pts.get(i).getValue();

            double term = yi.doubleValue();
            for (int j = 0; j < k; j++) {
                if (i == j) continue;
                long xj = pts.get(j).getKey();
                term *= (-1.0 * xj) / (xi - xj);
            }
            constant += term;
        }
        return constant;
    }

    public static void main(String[] args) {
        try {
            // File selection (default = input.json)
            String file = args.length > 0 ? args[0] : "input.json";
            String rawJson = new String(Files.readAllBytes(Paths.get(file)));

            JSONObject json = new JSONObject(rawJson);
            JSONObject metadata = json.getJSONObject("keys");

            int n = metadata.getInt("n");
            int k = metadata.getInt("k");

            // Collect points (x, y)
            List<Map.Entry<Long, BigInteger>> points = new ArrayList<>();

            for (String key : json.keySet()) {
                if (key.equals("keys")) continue;

                JSONObject node = json.getJSONObject(key);
                int base = Integer.parseInt(node.getString("base"));
                String encoded = node.getString("value");

                BigInteger y = convertFromBase(encoded, base);
                long x = Long.parseLong(key);

                points.add(new AbstractMap.SimpleEntry<>(x, y));
            }

            // Sort by x
            points.sort(Comparator.comparingLong(Map.Entry::getKey));

            // Use only first k points
            double constant = interpolateAtZero(points, k);

            System.out.println("Constant term (c) = " + Math.round(constant));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
