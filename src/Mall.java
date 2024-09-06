import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Mall {

    Scanner sc = new Scanner(System.in);
    List<List<Goods>> goods = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();

    Mall() {
        for (int i = 1; i < 10; i++) {
            goods.add(new ArrayList<>());
        }
        //信息的初始化
        customers.add(new Customer("Peter", "123Abc!!", "15997939690", "1548651234@qq.com"));
        customers.add(new Customer("Tom", "321Abc!!", "18907259456", "1548651234@qq.com"));
        customers.add(new Customer("Jack", "213Abc!!", "17987939237", "1548651234@qq.com"));
        goods.get(0).add(new Goods("123", "火腿", "澳大利亚", "2024", "黑猪肉", 500, 200, 100));
        goods.get(0).add(new Goods("124", "火腿", "新西兰", "2024", "山猪肉", 500, 200, 100));
        goods.get(0).add(new Goods("125", "香蕉", "澳大利亚", "2024", "皇帝蕉", 500, 200, 100));
    }

    public void main_window() {
        System.out.println("欢迎来到登录窗口.");
        do {
            System.out.println("以管理员登录(loginM),以顾客登录(loginC),注册(enroll),返回(quit)");
            String choose = sc.next();
            switch (choose) {
                case "loginM":
                    Manager ma = Manager.login(goods, customers);
                    if (ma == null) {
                        System.out.println("登录失败!");
                    } else {
                        ma.windows(goods, customers);
                    }
                    break;
                case "loginC":
                    Customer cm = Customer.login(customers);
                    if (cm == null) {
                        System.out.println("登录失败!");
                    } else {
                        cm.windows(goods, null);
                    }
                    break;
                case "enroll":
                    customers.add(Customer.enroll());
                    break;
                case "quit":
                    System.out.println("退出成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);

    }

    public static void main(String[] args) {
        new Mall().main_window();
    }
}