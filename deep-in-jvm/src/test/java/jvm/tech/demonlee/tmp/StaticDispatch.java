package jvm.tech.demonlee.tmp;

/**
 * @author Demon.Lee
 * @date 2024-08-09 19:27
 */
public class StaticDispatch {
    static abstract class Human {
        public abstract void sayHello(Human guy);
    }

    static class Man extends Human {
        @Override
        public void sayHello(Human guy) {
            System.out.println("Hello, human guy...");
        }

        public void sayHello(Man guy) {
            System.out.println("Hello, human man...");
        }
    }

    static class Woman extends Human {
        @Override
        public void sayHello(Human guy) {
            System.out.println("Hello, Human lady...");
        }

        public void sayHello(Woman guy) {
            System.out.println("Hello, Woman lady...");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Woman woman = new Woman();
        man.sayHello(man);
        woman.sayHello(woman);
    }
}

