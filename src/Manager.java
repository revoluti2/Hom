import java.util.*;

class Manager extends Person implements DateManage {

    private static String Mnacode = "ynuinfo#777";
    List<PrintDate> Good;
    List<PrintDate> Customer;

    public Manager(List<List<Goods>> list, List<Customer> customers) {
        super("admin", Manager.Mnacode);
        this.Customer = new ArrayList<>();
        this.Good = new ArrayList<>();
        if (customers != null) {
            Customer.addAll(customers);
        }
        for (List<Goods> good : list) {
            Good.addAll(good);
        }
    }

    public static Manager login(List<List<Goods>> list, List<Customer> customers) {
        System.out.println("请输入账号名");
        String name = sc.next();
        System.out.println("请输入密码");
        String code = sc.next();
        if (name.equals("admin") && code.equals(Manager.Mnacode)) {
            return new Manager(list, customers);
        }
        return null;
    }

    @Override
    void windows(List<List<Goods>> list, List<Customer> customers) {
        System.out.println("欢迎登录到管理员界面");
        do {
            System.out.println("货物管理(Goodma),密码管理(Codema),用户管理(Cusma),返回(quit)");
            String choose = sc.next();
            switch (choose) {
                case "Goodma":
                    this.goodmanage(Good);
                    break;
                case "Codema":
                    this.codemanage(Customer);
                    break;
                case "Cusma":
                    this.cusmanage(Customer);
                    break;
                case "quit":
                    this.Update(list, customers);
                    System.out.println("退出成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);

    }

    void Update(List<List<Goods>> list, List<Customer> customers) {
        list.clear();
        customers.clear();
        for (int i = 1; i < 10; i++) {
            list.add(new ArrayList<>());
        }
        for (PrintDate printDate : Good) {
            Goods good = (Goods) printDate;
            list.get(good.code.charAt(0) - '1').add(good);
        }
        Customer.stream().map((item) -> (Customer) item).forEach(customers::add);
    }

    @Override
    public void cusmanage(List<PrintDate> list) {
        do {
            System.out.println("列出(print),删除(delete),查找(search),返回(quit)");
            String choose = sc.next();
            switch (choose) {
                case "print":
                    this.print(list);
                    break;
                case "delete":
                    this.delete(list);
                    break;
                case "search":
                    this.print(SearchInfo(list));
                    break;
                case "quit":
                    System.out.println("返回成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词");
            }
        } while (true);
    }

    @Override
    public void codemanage(List<PrintDate> list) {
        do {
            System.out.println("修改(modify),重置(reset),返回(quit)");
            String choose = sc.next();
            switch (choose) {
                case "modify":
                    this.codeset(this);
                    Manager.Mnacode = this.code;//管理员只有一个需要对统一密码就行修正
                    break;
                case "reset":
                    this.reset(list);
                    break;
                case "quit":
                    System.out.println("返回成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);
    }

    @Override
    public void goodmanage(List<PrintDate> list) {
        do {
            System.out.println("列出(print),添加(add),删除(delete)," +
                    "条目查找(search1),范围查找(search2),组合查找(search3),修改(modify),返回(quit)");
            String choose = sc.next();
            switch (choose) {
                case "print":
                    this.print(list);
                    break;
                case "add":
                    try {
                        this.add(list);
                    } catch (PrintException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "delete":
                    this.delete(list);
                    break;
                case "search1":
                    this.print(this.SearchInfo(list));
                    break;
                case "search2":
                    this.print(this.SearchInt(list));
                    break;
                case "search3":
                    this.print(this.SearchInt(this.SearchInfo(list)));
                    break;
                case "modify":
                    this.modify(list);
                    break;
                case "quit":
                    System.out.println("返回成功!");
                    return;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);
    }

    void reset(List<PrintDate> list) {
        if (list.isEmpty()) {
            System.out.println("还没有录入信息!");
            return;
        }
        List<PrintDate> list1 = this.SearchInfo(list);//调用search方法返回要找寻的顾客.
        list1.stream().map((item) -> (Customer) item).forEach((cus) -> {//再一一重置密码.
            cus.code = "123Abc!!";
        });
        System.out.println("已经重置密码!");
    }

    void print(List<PrintDate> list) {
        if (list.isEmpty()) {
            System.out.println("还没有信息录入!");
            return;
        }
        System.out.println("所查找的信息如下");
        for (PrintDate date : list) {
            System.out.println(date.print());
        }
    }

    List<PrintDate> SearchInfo(List<PrintDate> list) {
        if (list.isEmpty()) {
            System.out.println("还没有信息录入!!");
            return null;
        }
        System.out.println("请输入您要查询的信息!");
        System.out.println("注意:不同关键信息请用空格隔开");
        String str1 = sc.nextLine();
        String strs = sc.nextLine();
        List<PrintDate> dates = new ArrayList<>();
        boolean check;
        for (PrintDate date : list) {//先得到一个顾客或商品的对象
            check = false;
            for (String str : strs.split(" ")) {//把所需用来查找的词条一一分割
                if (!str.isEmpty()) {
                    for (String st : date.print().split(str)) {//再用顾客或商品的信息来切割词条
                        if (st.length() == date.print().length()) {//说明未切割
                            check = true;//该商品或顾客标为要舍弃
                            break;
                        }
                    }
                    if (check) {//true表示无需切割下一个词条因为它已不满足一个词条
                        break;
                    }
                }
            }
            if (!check) {//false表示它切割了每一个词条它应该被保留
                dates.add(date);
            }
        }

        return dates;
    }

    List<PrintDate> SearchInt(List<PrintDate> list) {
        if (list.isEmpty()) {
            System.out.println("还没有信息录入!");
            System.out.println("SearchInt");
            return null;
        }
        System.out.println("请输入您要查找的最大金额数!");
        int num = sc.nextInt();
        List<PrintDate> goods = new ArrayList<>();
        for (PrintDate printDate : list) {
            Goods good = (Goods) printDate;
            if (good.retail_cost >= num) {
                goods.add(good);
            }
        }
        return goods;
    }

    void delete(List<PrintDate> list) {
        if (list.isEmpty()) {
            System.out.println("还没有信息录入!");
            return;
        }
        List<PrintDate> items = this.SearchInfo(list);
        if (items == null) {
            System.out.println("没有该信息!");
        } else {
            System.out.println("是否确定删除?");
            if (sc.next().equals("yes")) {
                for (PrintDate item : items) {
                    list.remove(item);
                }
            }
        }
    }

    void add(List<PrintDate> list) throws PrintException{
        System.out.println("请输入物品的编码");
        String code = sc.next();
        System.out.println("请输入物品的名称");
        String name = sc.next();
        System.out.println("请输入物品的产家");
        String producer = sc.next();
        System.out.println("请输入物品的生产日期");
        String date = sc.next();
        System.out.println("请输入物品的型号");
        String strain = sc.next();
        System.out.println("请输入物品的进货价");
        double prime_cost = sc.nextDouble();
        if(prime_cost<0){
            throw new PrintException("进货价输入错误!");
        }
        System.out.println("请输入物品的零售价");
        double retail_cost = sc.nextDouble();
        if(retail_cost<0){
            throw new PrintException("零售价输入错误!");
        }
        System.out.println("请输入物品的数量");
        double quantity = sc.nextDouble();
        if(quantity<0 && (int)quantity!=quantity){
            throw new PrintException("货物数量不能为小数或负数");
        }
        list.add(new Goods(code, name, producer, date,
                strain, prime_cost, retail_cost, quantity));
    }

    void modify(List<PrintDate> list) {
        if (list.isEmpty()) {
            System.out.println("还没有商品加入!");
            return;
        }
        List<PrintDate> items = this.SearchInfo(list);
        if (items == null) {
            System.out.println("没有该物品!");
        } else {
            this.print(items);
            for (PrintDate item : items) {
                Goods good = (Goods) item;
                try {
                    Goods.goodmodify(good);
                } catch (PrintException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}