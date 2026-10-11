package laba1dot8;

import java.util.Scanner;

public class Payment {


    public static class Date {
        private int year;
        private int month;
        private int day;

        private static final int[] DAYS_IN_MONTH = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        public Date() {
            this.year = 2000;
            this.month = 1;
            this.day = 1;
        }

        public Date(int year, int month, int day) {
            if (!init(year, month, day)) {
                this.year = 2000;
                this.month = 1;
                this.day = 1;
            }
        }

        public int getYear() { return year; }
        public int getMonth() { return month; }
        public int getDay() { return day; }

        public static boolean isLeapYear(int y) {
            return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
        }

        public boolean isLeapYear() {
            return isLeapYear(this.year);
        }

        public int getDaysInMonth(int y, int m) {
            if (m == 2 && isLeapYear(y)) return 29;
            return DAYS_IN_MONTH[m];
        }

        public boolean setYear(int y) {
            if (y >= 1) {
                this.year = y;
                return true;
            }
            return false;
        }

        public boolean setMonth(int m) {
            if (m >= 1 && m <= 12) {
                this.month = m;
                return true;
            }
            return false;
        }

        public boolean setDay(int d) {
            if (d >= 1 && d <= getDaysInMonth(this.year, this.month)) {
                this.day = d;
                return true;
            }
            return false;
        }

        public boolean init(int y, int m, int d) {
            if (y >= 1 && m >= 1 && m <= 12) {
                int maxDays = (m == 2 && isLeapYear(y)) ? 29 : DAYS_IN_MONTH[m];
                if (d >= 1 && d <= maxDays) {
                    this.year = y;
                    this.month = m;
                    this.day = d;
                    return true;
                }
            }
            return false;
        }

        public boolean init(String dateStr) {
            if (dateStr == null) return false;
            String[] parts = dateStr.trim().split("\\.");
            if (parts.length != 3) return false;
            try {
                int y = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);
                int d = Integer.parseInt(parts[2]);
                return init(y, m, d);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public boolean init(Date other) {
            if (other == null) return false;
            return init(other.year, other.month, other.day);
        }

        public void read() {
            Scanner sc = new Scanner(System.in);
            String s;
            do {
                System.out.print("Введіть дату (у форматі РРРР.ММ.ДД, наприклад 2019.04.12): ");
                s = sc.nextLine();
            } while (!init(s));
        }

        @Override
        public String toString() {
            return String.format("%04d.%02d.%02d", year, month, day);
        }

        public void display() {
            System.out.println(toString());
        }

        public int compareTo(Date other) {
            if (this.year != other.year) return Integer.compare(this.year, other.year);
            if (this.month != other.month) return Integer.compare(this.month, other.month);
            return Integer.compare(this.day, other.day);
        }

        public boolean isEqual(Date other) { return compareTo(other) == 0; }
        public boolean isBefore(Date other) { return compareTo(other) < 0; }
        public boolean isAfter(Date other) { return compareTo(other) > 0; }

        private long toAbsoluteDays() {
            long days = this.day;
            for (int y = 1; y < this.year; y++) {
                days += isLeapYear(y) ? 366 : 365;
            }
            for (int m = 1; m < this.month; m++) {
                days += getDaysInMonth(this.year, m);
            }
            return days;
        }

        private static Date fromAbsoluteDays(long totalDays) {
            int y = 1;
            while (true) {
                int daysInY = isLeapYear(y) ? 366 : 365;
                if (totalDays <= daysInY) break;
                totalDays -= daysInY;
                y++;
            }
            int m = 1;
            while (true) {
                int dim = (m == 2 && isLeapYear(y)) ? 29 : DAYS_IN_MONTH[m];
                if (totalDays <= dim) break;
                totalDays -= dim;
                m++;
            }
            return new Date(y, m, (int) totalDays);
        }

        public long daysBetween(Date other) {
            return Math.abs(this.toAbsoluteDays() - other.toAbsoluteDays());
        }

        public Date addDays(int days) {
            return fromAbsoluteDays(this.toAbsoluteDays() + days);
        }

        public Date subtractDays(int days) {
            return fromAbsoluteDays(this.toAbsoluteDays() - days);
        }
    }


    private String fullName;
    private double salary;
    private Date hireDate;        
    private double bonusPercent;
    private double incomeTaxRate;
    private int workedDays;
    private int totalWorkDays;
    private double grossSalary;
    private double withheldAmount;

    public Payment() {
        this.fullName = "Невідомо";
        this.salary = 0.0;
        this.hireDate = new Date();
        this.bonusPercent = 0.0;
        this.incomeTaxRate = 13.0;
        this.workedDays = 0;
        this.totalWorkDays = 1;
        calculateSalaries();
    }

    public Payment(String fullName, double salary, Date hireDate, double bonusPercent,
                   int workedDays, int totalWorkDays) {
        this.fullName = fullName;
        this.salary = Math.max(0, salary);
        this.hireDate = (hireDate != null) ? new Date(hireDate.getYear(), hireDate.getMonth(), hireDate.getDay()) : new Date();
        this.bonusPercent = Math.max(0, bonusPercent);
        this.incomeTaxRate = 13.0;
        this.workedDays = Math.max(0, workedDays);
        this.totalWorkDays = Math.max(1, totalWorkDays);
        calculateSalaries();
    }


    public String getFullName() { return fullName; }
    public double getSalary() { return salary; }
    public Date getHireDate() { return hireDate; }
    public double getBonusPercent() { return bonusPercent; }
    public int getWorkedDays() { return workedDays; }
    public int getTotalWorkDays() { return totalWorkDays; }
    public double getGrossSalary() { return grossSalary; }
    public double getWithheldAmount() { return withheldAmount; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
            calculateSalaries();
        }
    }
    public void setHireDate(Date hireDate) {
        if (hireDate != null) {
            this.hireDate.init(hireDate);
        }
    }
    public void setBonusPercent(double bonusPercent) {
        if (bonusPercent >= 0) {
            this.bonusPercent = bonusPercent;
            calculateSalaries();
        }
    }
    public boolean setDays(int worked, int total) {
        if (worked >= 0 && total > 0 && worked <= total) {
            this.workedDays = worked;
            this.totalWorkDays = total;
            calculateSalaries();
            return true;
        }
        return false;
    }

    public int getExperience(Date currentDate) {
        if (currentDate == null || currentDate.isBefore(hireDate)) return 0;
        int exp = currentDate.getYear() - hireDate.getYear();
        if (currentDate.getMonth() < hireDate.getMonth() ||
           (currentDate.getMonth() == hireDate.getMonth() && currentDate.getDay() < hireDate.getDay())) {
            exp--;
        }
        return Math.max(0, exp);
    }


    public double calculateGrossSalary() {
        double baseForDays = (salary / totalWorkDays) * workedDays;
        double bonus = baseForDays * (bonusPercent / 100.0);
        return baseForDays + bonus;
    }

 
    public double calculateWithheldAmount() {
        double gross = calculateGrossSalary();
        double pensionFund = gross * 0.01;
        double incomeTax = gross * 0.13;
        return pensionFund + incomeTax;
    }


    public double calculateNetSalary() {
        return calculateGrossSalary() - calculateWithheldAmount();
    }

    private void calculateSalaries() {
        this.grossSalary = calculateGrossSalary();
        this.withheldAmount = calculateWithheldAmount();
    }

    public boolean init(String fullName, double salary, String hireDateStr, double bonus, int worked, int total) {
        if (salary >= 0 && bonus >= 0 && worked >= 0 && total > 0 && worked <= total) {
            Date d = new Date();
            if (d.init(hireDateStr)) {
                this.fullName = fullName;
                this.salary = salary;
                this.hireDate = d;
                this.bonusPercent = bonus;
                this.workedDays = worked;
                this.totalWorkDays = total;
                calculateSalaries();
                return true;
            }
        }
        return false;
    }

    public void read() {
        Scanner sc = new Scanner(System.in);
        System.out.print("ПІБ: ");
        this.fullName = sc.nextLine();
        System.out.print("Оклад: ");
        this.salary = sc.nextDouble();
        System.out.print("Надбавка (%): ");
        this.bonusPercent = sc.nextDouble();
        System.out.print("Робочих днів: ");
        this.totalWorkDays = sc.nextInt();
        System.out.print("Відпрацьовано днів: ");
        this.workedDays = sc.nextInt();
        sc.nextLine();
        System.out.println("Дата прийому на роботу:");
        this.hireDate.read();
        calculateSalaries();
    }

    @Override
    public String toString() {
        return String.format("Працівник: %s | Дата прийому: %s\n" +
                             "Ставка: %.2f грн | Відпрацьовано: %d/%d дн. | Надбавка: %.1f%%\n" +
                             "Нараховано: %.2f грн | Утримано: %.2f грн | На руки: %.2f грн",
                             fullName, hireDate.toString(), salary, workedDays, totalWorkDays,
                             bonusPercent, grossSalary, withheldAmount, calculateNetSalary());
    }

    public void display() {
        System.out.println(toString());
    }
}