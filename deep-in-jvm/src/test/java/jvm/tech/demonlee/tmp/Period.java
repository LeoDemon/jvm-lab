package jvm.tech.demonlee.tmp;

import java.util.Date;

/**
 * @author Demon.Lee
 * @date 2024-10-05 21:45
 */
public final class Period {

    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }

    public static void main(String[] args) {
        Date start = new Date();
        Date end = new Date();
        Period period = new Period(start, end);
        end.setYear(98);
        System.out.println(period.end());
    }
}
