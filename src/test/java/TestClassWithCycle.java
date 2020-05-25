public class TestClassWithCycle {
    TestClassWithCycle testClassWithCycle;

    TestClassWithCycle(TestClassWithCycle testClassWithCycle){
        this.testClassWithCycle = testClassWithCycle;
    }
}
