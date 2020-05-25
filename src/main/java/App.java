import samples.Bar;

public class App {
    public static void main(String[] args) throws Exception {
        SimpleContainer c = new SimpleContainer();
        c.registerType(samples.Foo.class, true);

        samples.Foo f1 = c.resolve(samples.Foo.class);
        samples.Foo f2 = c.resolve(samples.Foo.class);
        System.out.println(f1 == f2); //true

        c.registerType(samples.IFoo.class, samples.Foo.class, false);
        samples.IFoo f = c.resolve(samples.Foo.class);
        System.out.println(f); //samples.Foo

        c.registerType(samples.IFoo.class, Bar.class, false);
        samples.IFoo b = c.resolve(samples.IFoo.class);
        System.out.println(b); // samples.Bar

    }
}
