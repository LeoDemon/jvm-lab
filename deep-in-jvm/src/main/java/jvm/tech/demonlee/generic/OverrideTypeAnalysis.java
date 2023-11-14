package jvm.tech.demonlee.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Demon.Lee
 * @date 2023-11-14 10:45
 * @desc generic override analysis
 */
public class OverrideTypeAnalysis {

    public static void main(String[] args) {
        OverrideTypeAnalysis analysis = new OverrideTypeAnalysis();
        analysis.compareGenericType();
    }

    private void compareGenericType() {
        List<String> stringList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        System.out.println("list class type is: " + stringList.getClass().getName());
        System.out.println("class type is equal: " + (stringList.getClass() == intList.getClass()));
    }

    // compile error for two `sayHello` method:
    // 'sayHello(List<String>)' clashes with 'sayHello(List<Integer>)'; both methods have same erasure
    private void sayHello(List<String> list) {
        System.out.println("say hello: " + list);
    }

    // private void sayHello(List<Integer> list) {
    //     System.out.println("say hello: " + list);
    // }
}
