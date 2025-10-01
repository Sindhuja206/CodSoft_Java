import java.util.Scanner;

class StudentResult {
    public static String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter number of subjects: ");
        int n = s.nextInt();

        double[] marks = new double[n];
        double totalMarks = 0;
        for (int i = 0; i < n; i++) {
            int attempt=1;
            do{
                if(attempt>1){System.out.println("Enter a valid mark");}
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            marks[i] = s.nextDouble();attempt+=1;
        }while(marks[i]>100 || marks[i]<0);
            totalMarks += marks[i];
        }
        double averagePercentage = totalMarks / n;
        String grade = calculateGrade(averagePercentage);
        System.out.println("\n--- Result ---");
        System.out.println("Total Marks: " + totalMarks + " / " + (n * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
        s.close();
    }
}
