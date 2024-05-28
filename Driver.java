import java.io.File;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = {6, 5};
        int[] e1 = {0, 3};
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = {-2, -9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        Polynomial result = new Polynomial("5.0-3.0x2+7.0x8");
        System.out.println(result.evaluate(1) + " == 9.0");
        System.out.println(result);

        Polynomial result2 = new Polynomial("-5-3x2+7x8");
        System.out.println(result2.evaluate(1) + " == -1.0");
        System.out.println(result2);

        Polynomial result3 = new Polynomial("x2+2x+1");
        System.out.println(result3.evaluate(3) + " == 16.0");
        System.out.println(result3);

        Polynomial a = new Polynomial("x-1");
        Polynomial b = new Polynomial("x2+x+1");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a+b = " + a.add(b));
        System.out.println("a*b = " + a.multiply(b));
        Polynomial c = new Polynomial("9.9x2+1.1x+1");
        System.out.println("12.0 == " + c.evaluate(1));

        Polynomial sum = result.add(result2);
        System.out.println(sum.evaluate(1) + " == 8.0");

        Polynomial prod = result.multiply(result2);
        System.out.println(prod.evaluate(1) + " == -9.0");
        System.out.println(prod);
        prod.saveToFile("output.txt");


        File file = new File("expression.txt");
        Polynomial result4 = new Polynomial(file);
        System.out.println(result4.evaluate(0.5) + " == -0.125");
        System.out.println(result4);
    }
}
