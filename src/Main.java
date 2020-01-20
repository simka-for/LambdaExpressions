import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main
{
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args)
    {
        ArrayList<Employee> staff = loadStaffFromFile();

        GregorianCalendar calendar = new GregorianCalendar(2017,
                Calendar.JANUARY, 0);
        Date hireDay = calendar.getTime();

        Comparator<Employee> comp = Comparator.comparing(Employee::getSalary).thenComparing(Employee::getName);
        Collections.sort(staff, comp);

//        staff.sort((o1, o2) -> {
//            int result = -o1.getSalary().compareTo(o2.getSalary());
//            if (result == 0){
//                return o1.getName().compareTo(o2.getName());
//            } else {
//                return result;
//            }
//        });

        staff.forEach(System.out::println);

        System.out.print("\n");

        staff.stream().
                filter(d -> d.getWorkStart().after(hireDay)).
                max(Comparator.comparing(Employee::getSalary)).
                ifPresent(System.out::println);
    }

    private static ArrayList<Employee> loadStaffFromFile()
    {
        ArrayList<Employee> staff = new ArrayList<>();
        try
        {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines)
            {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                    fragments[0],
                    Integer.parseInt(fragments[1]),
                    (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}