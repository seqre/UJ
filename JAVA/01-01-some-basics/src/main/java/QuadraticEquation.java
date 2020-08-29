public class QuadraticEquation {

    public double[] findRoots(double a, double b, double c) {
        if (a != 0) {
            double delta = Math.pow(b, 2) - 4 * a * c;

            if (delta > 0) {
                delta = Math.sqrt(delta);
                return new double[]{
                        (-b - delta) / (2 * a),
                        (-b + delta) / (2 * a)
                };
            } else if (delta == 0) {
                return new double[]{-b / (2 * a)};
            } else {
                return new double[]{};
            }

        } else {
            return new double[]{(-c) / b};
        }
    }
}
