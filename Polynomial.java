import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

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
        this.termCount = coefs.length;
    }

    public Polynomial(String expression) {
        if (expression.isEmpty() || expression.equals("0")) {
            exps = new int[]{};
            coefs = new double[]{};
            termCount = 0;
            return;
        }
        expression = expression.replace("-", "+-");
        if (expression.charAt(0) != '+') {
            expression = "+".concat(expression);
        }
        String[] terms = expression.split("\\+");

        termCount = terms.length - 1;
        coefs = new double[termCount];
        exps = new int[termCount];

        for (int i = 0; i < termCount; i++) {
            if (terms[i + 1].indexOf('x') == -1) {
                if (terms[i + 1].equals("-")) {
                    coefs[i] = -1;
                } else {
                    coefs[i] = Double.parseDouble(terms[i + 1]);
                }
                exps[i] = 0;
                continue;
            }
            String[] coefAndExp = terms[i + 1].split("x");
            if (coefAndExp.length == 0) {
                coefs[i] = 1;
                exps[i] = 1;
                continue;
            }
            if (coefAndExp[0].isEmpty()) {
                coefs[i] = 1;
            } else {
                coefs[i] = Double.parseDouble(coefAndExp[0]);
            }

            if (coefAndExp.length < 2) {
                exps[i] = 1;
            } else {
                exps[i] = Integer.parseInt(coefAndExp[1]);
            }
        }
    }

    public Polynomial(File file) {
        this(getExpression(file));
    }

    private static String getExpression(File file) {
        String expression;
        try {
            file.setReadable(true);
            Scanner sc = new Scanner(file);
            expression = sc.nextLine();
        } catch (Exception e) {
            throw new AssertionError();
        }
        return expression;
    }

    @Override
    public String toString() {
        if (termCount == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < termCount; i++) {
            if (i != 0 && coefs[i] > 0) {
                sb.append("+");
            }
            if (exps[i] == 0) {
                sb.append(coefs[i]);
                continue;
            }
            if (coefs[i] == -1) {
                sb.append("-");
            } else if (coefs[i] != 1) {
                sb.append(coefs[i]);
            }
            sb.append("x");
            if (exps[i] != 1) {
                sb.append(exps[i]);
            }
        }
        return sb.toString();
    }

    public void saveToFile(String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(toString());
        } catch (IOException e) {
            System.out.println("Failed to write.");
        }
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
                        newExps[i] = exps[i + 1];
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
        Polynomial sum = new Polynomial(coefs, exps);
        for (int i = 0; i < other.termCount; i++) {
            sum = sum.addMonomial(other.coefs[i], other.exps[i]);
        }
        return sum;
    }

    public Polynomial multiplyMonomial(double coef, int exp) {
        if (coef == 0) {
            return new Polynomial();
        }
        Polynomial prod = new Polynomial(coefs, exps);
        for (int i = 0; i < termCount; i++) {
            prod.coefs[i] *= coef;
            prod.exps[i] += exp;
        }
        return prod;
    }

    public Polynomial multiply(Polynomial other) {
        Polynomial prod = new Polynomial();
        for (int i = 0; i < other.termCount; i++) {
            prod = prod.add(this.multiplyMonomial(other.coefs[i], other.exps[i]));
        }
        return prod;
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
