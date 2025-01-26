package Task1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class TestRunner {
    public static void runTests(Class c){
        Class testClass = c;
        Object instance;
        try{
            instance = c.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        Method[] methods = testClass.getDeclaredMethods();
        Method beforeSuite=null;
        Method afterSuite=null;
        Method[] tests = new Method[methods.length];
        int testCnt = 0;

        for (Method method:methods){
            Annotation[] declaredAnnotation = method.getDeclaredAnnotations();
            for (Annotation annotation:declaredAnnotation){
                if (annotation instanceof BeforeSuite) {
                    if (beforeSuite!=null) throw new RuntimeException("Методов @BeforeSuite больше одного");
                    beforeSuite=method;
                } else if (annotation instanceof AfterSuite) {
                    if (afterSuite!=null) throw new RuntimeException("Методов @AfterSuite больше одного");
                    afterSuite=method;
                } else if (annotation instanceof Test) {
                    tests[testCnt++]=method;
                }
            }
        }

        //запуск Before
        if (beforeSuite!=null){
            try{
                beforeSuite.invoke(instance);
            } catch ( Exception e){
                throw new RuntimeException(e);
            }
        }

        Arrays.sort(tests,0, testCnt, Comparator.comparingInt(m->m.getAnnotation(Test.class).priority()));

        for (Method test:tests){
            if (test!=null){
                try {
                    test.invoke(instance);
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }

        //запуск after
        if (afterSuite!=null){
            try{
                afterSuite.invoke(instance);
            } catch ( Exception e){
                throw new RuntimeException(e);
            }
        }

    }
    public static void main(String[] args){
        runTests(TestMethods.class);
    }
}
