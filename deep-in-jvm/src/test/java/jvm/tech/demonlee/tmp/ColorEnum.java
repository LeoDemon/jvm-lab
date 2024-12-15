package jvm.tech.demonlee.tmp;

import java.util.Arrays;
import java.util.EnumSet;

public enum ColorEnum {

    YELLOW(1),
    RED(2),
    GREEN(3),
    BLUE(4);

    private final int seq;

    ColorEnum(int seq) {
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

    // 使用 stream 流重写下面的 valueOf 方法
    public static ColorEnum valueOf(int seq) {
        return Arrays.stream(ColorEnum.values())
                .filter(colorEnum -> colorEnum.getSeq() == seq)
                .findFirst()
                .orElse(null);
    }

    public static void main(String[] args) {
        EnumSet<ColorEnum> set = EnumSet.allOf(ColorEnum.class);
        EnumSet<ColorEnum> set2 = EnumSet.noneOf(ColorEnum.class);
        EnumSet<ColorEnum> set3 = EnumSet.range(ColorEnum.RED, ColorEnum.GREEN);
        System.out.println(set); // [YELLOW, RED, GREEN, BLUE]
        System.out.println(set.contains(ColorEnum.BLUE)); // true
        System.out.println(set2); // []
        System.out.println(set3); // [RED, GREEN]
        System.out.println(set3.contains(ColorEnum.BLUE)); // false
        set3.add(ColorEnum.YELLOW);
        System.out.println(set3);
    }
}