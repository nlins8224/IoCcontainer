import annotations.DependencyConstructor;
import exceptions.SimpleContainerException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings("unchecked")
public class SimpleContainer {
    private final Map<String, Register<?>> registeredTypes = new HashMap<>();
    private final Map<String, Object> resolvedSingletonInstances = new HashMap<>();

    public <F> void registerInstance(F instance){
        registeredTypes.put(instance.getClass().getName(),
                new Register.TypeInstanceRegister<>((Class<F>) instance.getClass(), instance));
    }

    public <F> void registerType(Class<F> type, boolean isSingleton){
        this.registeredTypes.put(type.getName(), new Register.TypeRegisterOne<>(type, isSingleton));
    }

    public <F, T extends F> void registerType(Class<F> from, Class<T> to, boolean isSingleton){
        this.registeredTypes.put(from.getName(), new Register.TypeRegister<>(from, to, isSingleton));
    }

    public <T> T resolve(Class<T> type) throws Exception {
       return resolve(type.getName());
    }

    public <T> T resolve(String name) throws Exception {
        Register<?> register = registeredTypes.get(name);

        if (register == null) {
            throw new SimpleContainerException(name + " is not registered");
        }

        if (register instanceof Register.TypeRegister) {
            Register.TypeRegister typeRegister = (Register.TypeRegister) register;
            T instance = null;

            if (typeRegister.isSingleton) {
                instance = (T) resolvedSingletonInstances.get(name);
            }

            if (instance == null) {
                instance = createInstance(getInjectedClassConstructor(typeRegister.to));
            }

            if (typeRegister.isSingleton) {
                resolvedSingletonInstances.put(name, instance);
            }

            return instance;
        } else if (register instanceof Register.TypeRegisterOne){
            Register.TypeRegisterOne typeRegister = (Register.TypeRegisterOne) register;
            T instance = null;

            if (typeRegister.isSingleton){
                instance = (T) resolvedSingletonInstances.get(name);
            }

            if (instance == null){
                instance = createInstance(getInjectedClassConstructor(typeRegister.from));
            }

            if(typeRegister.isSingleton){
                resolvedSingletonInstances.put(name, instance);
            }

            return instance;
        }
        else if (register instanceof Register.TypeInstanceRegister) {
            return (T) ((Register.TypeInstanceRegister) register).instance;
        }
        else {
            throw new SimpleContainerException("Cannot provide registration for type: " + register.getClass().getName());
        }
    }
        private Constructor getInjectedClassConstructor(Class type){
            Constructor[] declaredConstructors = type.getDeclaredConstructors();
            if (declaredConstructors.length == 0){
                throw new SimpleContainerException("No public constructors was found for type: " + type.getName());
            }

            List<Constructor> annotatedConstructors = Arrays.stream(declaredConstructors)
                    .filter(constructor -> constructor.getDeclaredAnnotation(DependencyConstructor.class) != null)
                    .collect(Collectors.toList());

            if (annotatedConstructors.isEmpty()){
                return Arrays.stream(declaredConstructors)
                        .max(Comparator.comparingInt(Constructor::getParameterCount)).get();
            }

            if (annotatedConstructors.size() > 1){
                throw new SimpleContainerException("At most one constructor can be annotated with@"
                        + DependencyConstructor.class.getSimpleName());
            }

            return annotatedConstructors.get(0);
        }

        private <T> T createInstance(Constructor constructor) throws Exception {
            Parameter[] parameterTypes = constructor.getParameters();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++){

                Parameter parameter = parameterTypes[i];
                if (constructor.getDeclaringClass() == parameter.getType()){
                    throw new SimpleContainerException(constructor.getDeclaringClass() +
                            " cannot be resolved: constructor parameter and class type " +
                            "are identical");
                }
                parameters[i] = resolve(parameter.getType());
            }
            try {
                return (T) constructor.newInstance(parameters);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new SimpleContainerException(e);
            }
            }
        }





