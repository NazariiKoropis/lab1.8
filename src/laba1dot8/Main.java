package laba1dot8;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Лабораторна робота № 1.8: Вкладені класи (Варіант 19*) ===");

   
        System.out.println("\n--- Тест 1: Операції з вкладеним класом Payment.Date ---");
        Payment.Date d1 = new Payment.Date(2020, 2, 28);
        Payment.Date d2 = new Payment.Date();
        d2.init("2024.03.01");

        System.out.print("Дата d1: "); d1.display();
        System.out.print("Дата d2: "); d2.display();
        System.out.println("Рік 2020 високосний? " + d1.isLeapYear());
        System.out.println("Днів між датами: " + d1.daysBetween(d2));

        Payment.Date d3 = d1.addDays(1);
        System.out.print("d1 + 1 день (перехід через 29 лютого): "); d3.display();


        System.out.println("\n--- Тест 2: Розрахунок зарплати та стажу співробітника ---");
        Payment.Date hireDate = new Payment.Date(2019, 10, 15);
        Payment employee = new Payment("Мельник Тарас Григорович", 28000.0, hireDate, 15.0, 21, 22);

        employee.display();

        Payment.Date currentDate = new Payment.Date(2026, 10, 11);
        System.out.println("\nПоточна розрахункова дата: " + currentDate);
        System.out.println("Повний стаж працівника: " + employee.getExperience(currentDate) + " років");

        System.out.println("\n--- Тест 3: Масив об'єктів Payment ---");
        Payment[] department = new Payment[] {
            employee,
            new Payment("Остапчук Марія Іванівна", 32000.0, new Payment.Date(2021, 3, 1), 10.0, 22, 22),
            new Payment("Ткаченко Юрій Васильович", 20000.0, new Payment.Date(2025, 11, 20), 0.0, 18, 22)
        };

        for (int i = 0; i < department.length; i++) {
            System.out.println("\n[Співробітник #" + (i + 1) + "]");
            department[i].display();
            System.out.println("Стаж: " + department[i].getExperience(currentDate) + " р.");
        }
    }
}