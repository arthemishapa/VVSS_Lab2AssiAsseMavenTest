package validation;
import domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        StringBuilder stringBuilder = new StringBuilder();
        if (student == null)
            throw new ValidationException("Student must not be null");
        if (student.getID() == null || student.getID().equals(""))
            stringBuilder.append("Invalid ID\n");
        if (student.getNume() == null || student.getNume().equals(""))
            stringBuilder.append("Invalid name\n");
        if (student.getGrupa() <= 110 || student.getGrupa() >= 938)
            stringBuilder.append("Invalid group\n");
        String errors = stringBuilder.toString();
        if (!errors.equals(""))
            throw new ValidationException(errors);
    }
}

