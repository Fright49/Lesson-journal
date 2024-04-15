import java.util.ArrayList;

public class Oceny {

	   private Student student;
	    private ArrayList<Integer> grades;

	    public Oceny(Student student) {
	        this.student = student;
	        this.grades = new ArrayList<>();
	    }

	    public Student getStudent() {
	        return student;
	    }

	    public void addGrade(int grade) {
	        grades.add(grade);
	    }

	    public ArrayList<Integer> getGrades() {
	        return grades;
	    }

	    public double getAverageGrade() {
	       
	        int sum = 0;
	        for (int grade : grades) {
	            sum =sum+ grade;
	        }
	        return (double) sum / grades.size();
	    }
	
}