package jvm.tech.demonlee.tmp;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

/**
 * @author Demon.Lee
 * @date 2024-11-17 16:41
 */
class ForEachTest {

    enum Face {
        ONE, TWO, THREE, FOUR, FIVE, SIX;
    }

    @Test
    void should_failed_print_two_faces_combination() {
        Collection<Face> faces = EnumSet.allOf(Face.class);
        for (Iterator<Face> it = faces.iterator(); it.hasNext(); ) {
            // Face f1 = it.next();
            for (Iterator<Face> it2 = faces.iterator(); it2.hasNext(); ) {
                // Face f2 = it2.next();
                // System.out.println(f1 + " " + f2);
                System.out.println(it.next() + " " + it2.next());
            }
        }
    }

    @Test
    void should_print_two_faces_combination() {
        for (Face f1 : Face.values()) {
            for (Face f2 : Face.values()) {
                System.out.println(f1 + " " + f2);
            }
        }
    }
}
