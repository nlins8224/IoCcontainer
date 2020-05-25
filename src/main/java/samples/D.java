package samples;

import annotations.DependencyConstructor;

public class D {
    public A a;
    public B b;
    public X x;

    public D (A a){
        this.a = a;
    }

    public D(A a, B b){
        this.a = a;
        this.b = b;
    }

    @DependencyConstructor
    public D(A a, X x){
        this.a = a;
        this.x = x;
    }
}
