package jvm.tech.demonlee.tmp;

import java.util.Collection;
import java.util.List;

interface Operation {
    double apply(double x, double y);
}

public enum ExtendedOperation implements Operation {
    EXP("^") {
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        public double apply(double x, double y) {
            return x % y;
        }
    };

    private final String symbol;

    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static void main(String[] args) {
        test1(List.of(ExtendedOperation.values()), 4, 2);
        test2(ExtendedOperation.class, 4, 2);
    }

    // 使用接口的方式测试
    private static void test1(Collection<Operation> operations, double x, double y) {
        operations.forEach(op -> System.out.printf("test1: %f %s %f = %f%n", x, op, y, op.apply(x, y)));
    }

    // 使用枚举的方式测试
    private static <T extends Enum<T> & Operation> void test2(Class<T> enumClass, double x, double y) {
        for (Operation op : enumClass.getEnumConstants()) {
            System.out.printf("test2: %f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
}