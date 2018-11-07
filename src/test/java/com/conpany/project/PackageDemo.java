package com.conpany.project;

import javax.xml.ws.FaultAction;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;

// declare a new annotation
@Retention(RetentionPolicy.RUNTIME)
@interface Demo {

    String str();

    int val();
}

public class PackageDemo {

    // set values for the annotation
    @Demo(str = "Demo Annotation", val = 100)
    // a method to call in the main
    @Deprecated

    public static void example() {
        PackageDemo ob = new PackageDemo();

        try {
            Class c = ob.getClass();
            System.err.println(c);
            System.err.println(c.getName());
            System.err.println(c.getSimpleName());

            // get the method example
            Method m = c.getMethod("example");

            // get the annotations
            Annotation[] annotation = m.getDeclaredAnnotations();

            // print the annotation
            for (int i = 0; i < annotation.length; i++) {
                System.out.println(annotation[i]);
                System.out.println(annotation[i].annotationType());
                System.out.println(annotation[i].annotationType().getName());
                System.out.println(annotation[i].annotationType().getSimpleName());
            }
        } catch (NoSuchMethodException exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String args[]) {
        example();
    }
}
