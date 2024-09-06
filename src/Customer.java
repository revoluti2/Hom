
import java.util.*;

class Customer extends Person implements PrintDate {

    Map<String, Map<Goods, Double>> map = new HashMap<>();
    String level;
    String time;
    double sum;
    String phone;
    String mail;
    boolean state;

    public Customer(String name, String code, String phone, String mail) {
        super(name, code);
        Date nowTime = new Date();
        this.phone = phone;
        this.mail = mail;
        this.sum = 0;
        this.level = "普通顾客";
        this.time = String.format("%tF %tH时%tM分%tS秒", nowTime, nowTime, nowTime, nowTime);
        this.state = false;
    }

    public static String Getcode() {
        String reg_cd1 = ".*\\d+.*";
        String reg_cd2 = ".*[^a-zA-Z0-9].*";
        String reg_cd3 = ".*[a-z].*";
        String reg_cd4 = ".*[A-Z].*";
        String code1;
        do {
            System.out.println("请输入您的密码!");
            code1 = sc.next();
            if (code1.length() >= 8
                    && code1.matches(reg_cd1)
                    && code1.matches(reg_cd2)
                    && code1.matches(reg_cd3)
                    && code1.matches(reg_cd4)) {
                return code1;
            }
            System.out.println("输入不符合规范!请重新输入!");
        } while (true);
    }

    public static Customer login(List<Customer> customers) {
        int count = 5;
        do {
            Customer customer = null;
            System.out.println("请输入账号名");
            String name = sc.next();
            System.out.println("请输入密码,忘记密码请输入(forget)");
            String code = sc.next();

            if (code.equals("forget")) {
                customer = Customer.forget_code(customers, name);
                if (customer == null) {
                    System.out.println("查无此顾客!");
                } else {
                    System.out.println("密码已经重置,新密码已通过邮箱发送至您的手机!");
                }
                return null;
            } else {
                for (Customer customer1 : customers) {
                    if (name.equals(customer1.name)) {
                        customer = customer1;
                        if (customer1.state) {
                            System.out.println("该账户已冻结!请联系管理员!");
                            return null;
                        }
                        if (code.equals(customer1.code)) {
                            return customer1;
                        }
                    }
                }
                count--;
                if (count == 0 && customer != null) {
                    customer.state = true;
                }
            }
            System.out.println("用户名或密码错误请重新输入!");
            System.out.println("还有" + count + "次机会!");
        } while (count > 0);

        return null;
    }

    public static Customer forget_code(List<Customer> customers, String count) {
        for (Customer customer : customers) {
            if (count.equals(customer.name)) {
                customer.code = "123abc!!";
                return customer;
            }
        }
        return null;
    }

    public static Customer enroll() {
        String reg_nm = ".{5,}";
        String reg_ml = "1[0-9]{9}";
        String reg_ph = "1[35678][0-9]{9}";
        String name, code, phone, mail;
        System.out.println("欢迎来到注册界面!");

        do {
            System.out.println("请输入新的的用户名");
            name = sc.next();
            if (!name.matches(reg_nm)) {
                System.out.println("输入不符合规范!请重新输入!");
                continue;
            }
            break;
        } while (true);
        code = Getcode();
        do {
            System.out.println("请输入您的邮箱!");
            mail = sc.next();
            if (mail.matches(reg_ml)) {
                break;
            }
            System.out.println("输入不符合规范!请重新输入!");
        } while (true);
        do {
            System.out.println("请输入您的手机号码!");
            phone = sc.next();
            if (phone.matches(reg_ph)) {
                break;
            }
            System.out.println("输入不符合规范!请重新输入!");
        } while (true);
        return new Customer(name, code, phone, mail + "@qq.com");

    }

    @Override
    void windows(List<List<Goods>> list, List<Customer> customers) {
        System.out.println("欢迎登录到顾客界面!");
        do {
            System.out.println("请选择业务:购物(purchase),修改密码(modify),"
                    + "查看个人信息(print),查看购物历史(search),登出(quit)");
            String choose = sc.next();
            switch (choose) {
                case "purchase":
                    this.purchase(list);
                    break;
                case "modify":
                    this.codeset(this);
                    break;
                case "search":
                    this.history();
                    break;
                case "print":
                    System.out.println(this.print());
                    break;
                case "quit":
                    System.out.println("退出成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词");
            }
        } while (true);
    }

    void purchase(List<List<Goods>> list) {
        Map<Goods, Double> cart = new HashMap<>();
        System.out.println("商品信息如下:");
        for (List<Goods> goods : list) {
            for (Goods good : goods) {
                System.out.println(good.print());
            }
        }
        do {
            System.out.println("购物(add),删除(delete),查看购物车(watch),修改(modify),结账(pay),返回(quit)");
            String choose = sc.next();
            switch (choose) {
                case "add":
                    try {
                        this.add(cart, list);
                    } catch (PrintException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "delete":
                    this.delete(cart);
                    break;
                case "watch":
                    this.watch_cart(cart);
                    break;
                case "modify":
                    this.modify(cart);
                    break;
                case "pay":
                    this.pay(cart);
                    return;
                case "quit":
                    System.out.println("返回成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);
    }

    void add(Map<Goods, Double> cart, List<List<Goods>> list) throws PrintException {
        System.out.println("请输入商品编码!");
        String item = sc.next();
        Goods list_good = Goods.search(list, item);
        if (list_good == null) {
            System.out.println("没有该编号的物品");
        } else {
            System.out.println("请输入购买数量!");
            double quantity = sc.nextDouble();
            Goods cart_good = Goods.search(cart, item);
            if (quantity <= 0 && (int) quantity != quantity) {
                throw new PrintException("不能输入小数和负数");
            } else {
                if (cart_good == null) {
                    if (quantity <= list_good.quantity) {
                        cart.put(list_good, list_good.retail_cost * quantity);
                        System.out.println("加入购物车成功!");
                        return;
                    }
                } else {
                    double prime_num = cart.get(cart_good) / cart_good.retail_cost;
                    if (prime_num + quantity <= list_good.quantity) {
                        cart.replace(cart_good, list_good.retail_cost * (quantity + prime_num));
                        System.out.println("加入购物车成功!");
                        return;
                    }
                }
                System.out.println("加入购物车失败,不要超出范围!");
            }
        }
    }

    public void delete(Map<Goods, Double> cart) {
        if (cart.isEmpty()) {
            System.out.println("购物车没有东西,请先购物!");
            return;
        }
        System.out.println("请输入您要删除的物品编号!");
        String co_num = sc.next();
        Goods item = Goods.search(cart, co_num);
        System.out.println("是否确定删除?");
        if (sc.next().equals("yes")) {
            if (cart.remove(item) == null) {
                System.out.println("没有该物品!");
            } else {
                System.out.println("删除成功!");
            }
        }

    }

    public void modify(Map<Goods, Double> cart) {
        if (cart.isEmpty()) {
            System.out.println("购物车没有东西,请先购物!");
            return;
        }
        System.out.println("请输入您要修改的物品的编号!");
        String co_num = sc.next();
        Goods item = Goods.search(cart, co_num);
        if (item == null) {
            System.out.println("没有该物品!");
        } else {
            System.out.println("请输入您要修改数量!");
            double new_num = sc.nextDouble();
            if (new_num <= 0) {
                cart.remove(item);
            } else if (new_num > cart.get(item)) {
                System.out.println("已达到最大配额!");
            } else {
                cart.replace(item, new_num*item.retail_cost);
                System.out.println("修改成功");
            }
        }
    }

    public void pay(Map<Goods, Double> cart) {
        if (cart.isEmpty()) {
            System.out.println("购物车没有东西,请先购物!");
            return;
        }
        double total = 0;
        for (double num : cart.values()) {
            total += num;
        }
        System.out.println("一共消费:" + total);
        do {
            System.out.println("请选择您的支付方式:支付宝(1),微信(2),银行卡(3)");
            int choose = sc.nextInt();
            switch (choose) {
                case 1:
                case 2:
                case 3:
                    sum += total;
                    this.level = this.chan_leve();
                    System.out.println("支付成功,即将返回主页面!");
                    Date nowTime = new Date();
                    map.put(String.format("%tF %tH时%tM分%tS秒", nowTime, nowTime, nowTime, nowTime), cart);
                    for (Goods good : cart.keySet()) {
                        good.quantity -= cart.get(good) / good.retail_cost;
                    }
                    return;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);
    }

    public String chan_leve() {
        if (sum > 100 && sum <= 500) {
            return "铜牌客户";
        } else if (sum > 500) {
            return "银牌客户";
        } else {
            return "金牌客户";
        }
    }

    void watch_cart(Map<Goods, Double> cart) {
        if (cart.isEmpty()) {
            System.out.println("购物车没有东西,请先购物!");
            return;
        }
        for (Goods good : cart.keySet()) {
            System.out.println(good.name + "," + cart.get(good));
        }
    }

    void history() {
        System.out.println(map.size());
        for (String str1 : map.keySet()) {
            System.out.println(str1);
            for (Goods str2 : map.get(str1).keySet()) {//通过日期找到当时的HashMap,再遍历它得信息
                System.out.println(str2.name
                        + "," + map.get(str1).get(str2) + "元");
            }
        }
    }

    @Override
    public String print() {
        return "********" + " " + name + " " + level + " " + time + " " + sum + " " + phone + " " + mail + " " + state;
    }
}