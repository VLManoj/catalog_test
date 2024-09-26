import java.util.*;

public class ShamirSecretSharing {

    static class Point {
        int x;
        long y;

        Point(int x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        String jsonInput = "{\n" +
                "    \"keys\": {\n" +
                "        \"n\": 9,\n" +
                "        \"k\": 6\n" +
                "    },\n" +
                "    \"1\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"28735619723837\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"base\": \"16\",\n" +
                "        \"value\": \"1A228867F0CA\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"base\": \"12\",\n" +
                "        \"value\": \"32811A4AA0B7B\"\n" +
                "    },\n" +
                "    \"4\": {\n" +
                "        \"base\": \"11\",\n" +
                "        \"value\": \"917978721331A\"\n" +
                "    },\n" +
                "    \"5\": {\n" +
                "        \"base\": \"16\",\n" +
                "        \"value\": \"1A22886782E1\"\n" +
                "    },\n" +
                "    \"6\": {\n" +
                "        \"base\": \"10\",\n" +
                "        \"value\": \"28735619654702\"\n" +
                "    },\n" +
                "    \"7\": {\n" +
                "        \"base\": \"14\",\n" +
                "        \"value\": \"71AB5070CC4B\"\n" +
                "    },\n" +
                "    \"8\": {\n" +
                "        \"base\": \"9\",\n" +
                "        \"value\": \"122662581541670\"\n" +
                "    },\n" +
                "    \"9\": {\n" +
                "        \"base\": \"8\",\n" +
                "        \"value\": \"642121030037605\"\n" +
                "    }\n" +
                "}";
        
        Map<Integer, Point> points = new HashMap<>();
        int n = 9;
        int k = 6;

        points.put(1, new Point(1, Long.parseLong("28735619723837", 10)));
        points.put(2, new Point(2, Long.parseLong("1A228867F0CA", 16)));
        points.put(3, new Point(3, Long.parseLong("32811A4AA0B7B", 12)));
        points.put(4, new Point(4, Long.parseLong("917978721331A", 11)));
        points.put(5, new Point(5, Long.parseLong("1A22886782E1", 16)));
        points.put(6, new Point(6, Long.parseLong("28735619654702", 10)));
        points.put(7, new Point(7, Long.parseLong("71AB5070CC4B", 14)));
        points.put(8, new Point(8, Long.parseLong("122662581541670", 9)));
        points.put(9, new Point(9, Long.parseLong("642121030037605", 8)));

        List<Point> pointList = new ArrayList<>(points.values());
        double constantTerm = gaussianElimination(pointList, k);
        System.out.printf("Constant term (c) of the polynomial is: %.2f\n", constantTerm);
    }

    public static double gaussianElimination(List<Point> points, int k) {
        double[][] matrix = new double[k][k + 1];

        for (int i = 0; i < k; i++) {
            Point point = points.get(i);
            matrix[i][0] = 1;
            for (int j = 1; j < k; j++) {
                matrix[i][j] = Math.pow(point.x, j);
            }
            matrix[i][k] = point.y;
        }

        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                if (matrix[j][i] > matrix[i][i]) {
                    double[] temp = matrix[i];
                    matrix[i] = matrix[j];
                    matrix[j] = temp;
                }
            }
            for (int j = i + 1; j < k; j++) {
                double ratio = matrix[j][i] / matrix[i][i];
                for (int l = i; l < k + 1; l++) {
                    matrix[j][l] -= ratio * matrix[i][l];
                }
            }
        }

        double[] coefficients = new double[k];
        for (int i = k - 1; i >= 0; i--) {
            coefficients[i] = matrix[i][k] / matrix[i][i];
            for (int j = i + 1; j < k; j++) {
                coefficients[i] -= (matrix[i][j] * coefficients[j]) / matrix[i][i];
            }
        }

        return coefficients[0];
    }
}
