class Register<F> {
    final Class<F> from;

    public Register(Class<F> from) {
        this.from = from;
    }

    static class TypeRegister<F, T extends F> extends Register<F> {
        final Class<T> to;
        final boolean isSingleton;

        TypeRegister(Class<F> from, Class<T> to, boolean isSingleton){
            super(from);
            this.to = to;
            this.isSingleton = isSingleton;
        }
    }

    static class TypeRegisterOne<F> extends Register<F> {
        final boolean isSingleton;

        TypeRegisterOne(Class<F> type, boolean isSingleton){
            super(type);
            this.isSingleton = isSingleton;
        }
    }

    static class TypeInstanceRegister<F> extends Register<F> {
        final F instance;

        TypeInstanceRegister(Class<F> from, F instance){
            super(from);
            this.instance = instance;
        }
    }

    static class NamedInstanceRegister<F> extends Register<F> {
        final String name;
        final F instance;

        public NamedInstanceRegister(Class<F> from, String name, F instance) {
            super(from);
            this.name = name;
            this.instance = instance;
        }
    }

}

