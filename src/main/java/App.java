import samples.*;

public class App {
    public static void main(String[] args) throws Exception {
        SimpleContainer c = new SimpleContainer();
        c.registerType(Foo.class, true);

        Foo f1 = c.resolve(Foo.class);
        Foo f2 = c.resolve(Foo.class);

        c.registerType(IFoo.class, Foo.class, false);
        IFoo f = c.resolve(Foo.class);

        c.registerType(IFoo.class, Bar.class, false);
        samples.IFoo b = c.resolve(samples.IFoo.class);

        IFoo foo1 = new Foo();
        c.registerInstance( foo1 );
        IFoo foo2 = c.resolve(Foo.class);

        c.registerType(A.class, false);
        c.registerType(B.class, false);
        A a = c.resolve(A.class);

        c.registerType(X.class, false);
        c.registerType(Y.class, false);

        c.registerInstance( "Alice has a cat" );
        X x = c.resolve(X.class);

        c.registerType(Qux.class, false);

        c.registerType(Z.class, false);
        Z z = c.resolve(Z.class);

        c.registerType(D.class, false);
        D d = c.resolve(D.class);

    }
}
