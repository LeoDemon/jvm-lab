package jvm.tech.demonlee.generic;

import java.util.ArrayList;

/**
 * @author Demon.Lee
 * @date 2023-11-14 10:41
 */
public class VarSugar {

    public static void main(String[] args) {
        VarSugar varSugar = new VarSugar();
        varSugar.foo();
    }

    private void foo() {
        var value = 1;
        var list = new ArrayList<Integer>();
        list.add(value);
        // list.add("1");
        System.out.println("list: " + list);
    }
}
