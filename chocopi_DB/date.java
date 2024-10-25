package org.example.oop_btl14;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class date {
    private int day;
    private int month;
    private int year;
    public date() {

    }

    public date(int day, int month, int year) {
        if (year > 0) {
            if (day > 0 && day < 32) {
                if (month < 13 && month > 0) {
                    if ((month % 2 == 0 && month != 8 && month != 2) || month == 9) {
                        if (day < 31) {
                            this.day = day;
                            this.month = month;
                            this.year = year;
                        } else {
                            throw new IllegalArgumentException("Invalid day surpass 30");
                        }
                    } else if (month == 2) {
                        boolean isleapYear = (day < 30 && ((year % 4 == 0 && year % 100 != 0)
                                || year % 400 != 0));
                        if (day < 29 || isleapYear) {
                            this.day = day;
                            this.month = month;
                            this.year = year;
                        } else {
                            throw new IllegalArgumentException("Invalid day for Februrary");
                        }
                    } else {
                        this.day = day;
                        this.month = month;
                        this.year = year;
                    }
                } else {
                    throw new IllegalArgumentException("Invalid month break out of Domain(1-12)");
                }
            } else {
                throw new IllegalArgumentException("Invalid day break out of Domain(1-31)");
            }
        } else {
            throw new IllegalArgumentException("Invalid year is negative");
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        date tempDate = new date(day, this.month, this.year);
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        date tempDate = new date(this.day, month, this.year);
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        date tempDate = new date(this.day, this.month, year);
        this.year = year;
    }

    public String toString() {
        String date = day + "/" + month + "/" + year;
        return date;
    }

    public int dateDiff() {
        LocalDate currentDate = LocalDate.now();
        LocalDate returnDate = LocalDate.of(year, month, day);

        return Math.toIntExact(ChronoUnit.DAYS.between(currentDate, returnDate));
    }

    public void deleteDate() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }
}
