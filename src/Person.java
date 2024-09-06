import java.util.*;

public abstract class Person implements CodeSet{

    String code;
    String name;
    static Scanner sc = new Scanner(System.in);

    public Person(String name, String code) {
        this.code = code;
        this.name = name;
    }

    abstract void windows(List<List<Goods>> list, List<Customer> customers);

    @Override
    public void codeset(Person person) {
        System.out.println("请输入旧密码!(返回(quit))");
        do {
            String old_cd = sc.next();
            if (old_cd.equals(person.code)) {
                if (person instanceof Manager) {
                    System.out.println("请输入新的密码!");
                    person.code = sc.next();
                } else {
                    person.code = Customer.Getcode();
                }
                return;
            } else if (old_cd.equals("quit")) {
                return;
            } else {
                System.out.println("密码错误,请重新输入");
            }
        } while (true);
    }

}