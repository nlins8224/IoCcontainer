import exceptions.SimpleContainerException;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleContainerTest {

    private SimpleContainer container;

    @BeforeEach
    void beforeEach(){
        container = new SimpleContainer();
    }

    @Test
    void shouldResolveRegisteredSingleSingletonType() throws Exception {
        container.registerType(TestClass.class, true);
        TestClass resolved1 = container.resolve(TestClass.class);
        TestClass resolved2 = container.resolve(TestClass.class);
        assertSame(resolved1, resolved2);
    }

    @Test
    void shouldResolveRegisteredSingleNonSingletonType() throws Exception {
        container.registerType(TestClass.class, false);
        TestClass resolved1 = container.resolve(TestClass.class);
        TestClass resolved2 = container.resolve(TestClass.class);
        assertNotSame(resolved1, resolved2);
    }

    @Test
    void shouldResolveRegisteredSingletonType() throws Exception {
        container.registerType(InterfaceTest.class, TestClass.class, true);
        InterfaceTest resolved1 = container.resolve(InterfaceTest.class);
        InterfaceTest resolved2 = container.resolve(InterfaceTest.class);
        assertSame(resolved1, resolved2);
    }

    @Test
    void shouldResolveRegisteredNonSingletonType() throws Exception {
        container.registerType(InterfaceTest.class, TestClass.class, false);
        InterfaceTest resolved1 = container.resolve(InterfaceTest.class);
        InterfaceTest resolved2 = container.resolve(InterfaceTest.class);
        assertNotSame(resolved1, resolved2);
    }

    @Test
    void shouldResolveToExtendedClassType() throws Exception {
        container.registerType(InterfaceTest.class, TestClassExtend.class, false);
        InterfaceTest testClassResolved = container.resolve(InterfaceTest.class);
        assertSame(testClassResolved.getClass(), TestClassExtend.class);
    }

    @Test
    void shouldNotResolveUnregisteredType(){
        SimpleContainerException exception = assertThrows(SimpleContainerException.class,
                () -> container.resolve(TestClass.class));
        assertThat(exception.getMessage(), is("TestClass is not registered"));

    }

}