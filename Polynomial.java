public class Polynomial {
    private int[] exps;
    private double[] coefs;

    public Polynomial() {
        exps = new int[]{};
        coefs = new double[]{};
    }

    public Polynomial(double[] coefs, int[] exps) {
        this.coefs = coefs;
        this.exps = exps
    }

    public Polynomial add(Polynomial other) {
        int maxLength = this.coefs.length + other.coefs.length;
        double[] newCoefs = new double[maxLength];
        int[] newExps = new int[maxLength];
        for (int i = 0; i < this.coefs.length; i++) {
            newCoefs[i] = this.coefs[i];
            newExps[i] = this.exps[i];
        }
        for (int i = 0; i < other.coefs.length; i++) {
            for (int j = 0; j < newCoefs.length; j++) {
                if (other.exps[i])
            }
        }
        return new Polynomial(newCoefs);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefs.length; i++) {
            result += coefs[i] * Math.pow(x, exps[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 0.000001;
    }
}
