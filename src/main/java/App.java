import samples.*;

public class App {
    public static void main(String[] args) throws Exception {
        SimpleContainer c = new SimpleContainer();
        c.registerType(Foo.class, true);

        Foo f1 = c.resolve(Foo.class);
        Foo f2 = c.resolve(Foo.class);
        System.out.println(f1 == f2); //true

        c.registerType(IFoo.class, Foo.class, false);
        IFoo f = c.resolve(Foo.class);
        System.out.println(f); //samples.Foo

        c.registerType(IFoo.class, Bar.class, false);
        samples.IFoo b = c.resolve(samples.IFoo.class);
        System.out.println(b); // samples.Bar

        /* -------------------------------------- */
        /* kolejne zadanie */

        IFoo foo1 = new Foo();
        c.registerInstance( foo1 );
        IFoo foo2 = c.resolve(Foo.class);
        System.out.println(foo1 == foo2); // true

        c.registerType(A.class, false);
        c.registerType(B.class, false);
        A a = c.resolve(A.class);
        System.out.println(a.b != null); //true

        c.registerType(X.class, false);
        c.registerType(Y.class, false);

        /*
        rejestruje instancję string
        bez tej linijki:  c.registerInstance( "ala ma kota" );
        Exception in thread "main" exceptions.SimpleContainerException: java.lang.String is not registered
         */

        c.registerInstance( "ala ma kota" );
        X x = c.resolve(X.class);
        System.out.println(x);

        System.out.println(x.s); // ala ma kota
        System.out.println(x.d); // samples.Y

        /*
        "Uwaga. Podczas rozwikływania może dojść do sytuacji powstania cyklu w drzewie (najprostszy przypadek:
        obiekt A w konstrukturze żąda obiektu typu A. Kontener powinen wykryć taką
        sytuację i zaraportować ją zrozumiałym wyjątkiem."

        Qux(Qux qux){
            this.qux = qux;
            }

            atm: StackOverflowError
        */

       // c.registerType(Qux.class, false);
       // Qux q = c.resolve(Qux.class);

        /*
        * "Uwaga. W przypadku dwóch konstruktorów o tej samej, maksymalnej liczbie parametrów
        * można zachować się na trzy sposoby: ..."
        *
        * W tej implementacji:
        *
        *  */
    }
}
