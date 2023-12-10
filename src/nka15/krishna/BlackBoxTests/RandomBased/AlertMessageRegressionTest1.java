package nka15.krishna.BlackBoxTests.RandomBased;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlertMessageRegressionTest1 {

    public static boolean debug = false;

    @Test
    public void test1() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test1");
        java.lang.Object obj0 = new java.lang.Object();
        java.lang.Class<?> wildcardClass1 = obj0.getClass();
        org.junit.Assert.assertNotNull(wildcardClass1);
    }

    @Test
    public void test2() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test2");
        main.java.app.AlertMessage alertMessage0 = new main.java.app.AlertMessage();
        // The following exception was thrown during execution in test generation
        try {
// flaky:             alertMessage0.successMessage("");
// flaky:             org.junit.Assert.fail("Expected exception of type java.lang.ExceptionInInitializerError; message: null");
        } catch (java.lang.ExceptionInInitializerError e) {
            // Expected exception.
        }
    }

    @Test
    public void test3() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test3");
        main.java.app.AlertMessage alertMessage0 = new main.java.app.AlertMessage();
        java.lang.Class<?> wildcardClass1 = alertMessage0.getClass();
        org.junit.Assert.assertNotNull(wildcardClass1);
    }
}
