package Task1;

public class TestMethods {
    @AfterSuite
    public void after(){
        System.out.println("Run after method");
    }

    @BeforeSuite
    public void before(){
        System.out.println("Run before method");
    }

    @Test(priority = 3)
    public void m1(){
        System.out.println("Run m1 method = 3");
    }

    @Test(priority = 7)
    public void m2(){
        System.out.println("Run m2 method = 7");
    }
    @Test
    public void m3(){
        System.out.println("Run m3 method = default = 5");
    }
    @Test(priority = 6)
    public void m4(){
        System.out.println("Run m4 method = 6");
    }

}
