public class Polynomial {
    private int[] exps;
    private double[] coefs;
    private int termCount;

    public Polynomial() {
        exps = new int[]{};
        coefs = new double[]{};
        termCount = 0;
    }

    public Polynomial(double[] coefs, int[] exps) {
        if (coefs.length != exps.length) {
            throw new AssertionError("Coefficient count and exponent count don't match.");
        }
        this.coefs = coefs.clone();
        this.exps = exps.clone();
    }

    private Polynomial addMonomial(double coef, int exp) {
        if (coef == 0) {
            return new Polynomial(coefs, exps);
        }
        int index = -1;
        for (int i = 0; i < termCount; i++) {
            if (exp == exps[i]) {
                index = i;
                break;
            }
        }
        if (index >= 0) { //the term of exp already exists.
            double[] newCoefs;
            if (coefs[index] + coef == 0) { // this term is cancelled out and should be deleted.
                newCoefs = new double[termCount - 1];
                int[] newExps = new int[termCount - 1];
                for (int i = 0; i < termCount - 1; i++) {
                    if (i < index) {
                        newCoefs[i] = coefs[i];
                        newExps[i] = exps[i];
                    } else {
                        newCoefs[i] = coefs[i + 1];
                        newExps[i] = exps[i];
                    }
                }
                return new Polynomial(newCoefs, newExps);
            } else {
                newCoefs = coefs.clone();
                newCoefs[index] += coef;
                return new Polynomial(newCoefs, exps);
            }
        } else {
            double[] newCoefs = new double[termCount + 1];
            int[] newExps = new int[termCount + 1];
            for (int i = 0; i < termCount; i++) {
                newCoefs[i] = coefs[i];
                newExps[i] = exps[i];
            }
            newCoefs[termCount] = coef;
            newExps[termCount] = exp;
            return new Polynomial(newCoefs, newExps);
        }
    }

    public Polynomial add(Polynomial other) {
        Polynomial newPoly = new Polynomial(coefs, exps);
        for (int i = 0; i < other.termCount; i++) {
            newPoly = newPoly.addMonomial(other.coefs[i], other.exps[i]);
        }
        return newPoly;
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
