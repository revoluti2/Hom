import java.util.*;

class Goods implements PrintDate {

    static Scanner sc = new Scanner(System.in);
    String code;
    String name;
    String producer;
    String date;
    String strain;
    double prime_cost;
    double retail_cost;
    double quantity;

    public Goods(String code, String name, String producer, String date,
                 String strain, double prime_cost, double retail_cost, double quantity) {
        this.code = code;
        this.name = name;
        this.producer = producer;
        this.date = date;
        this.strain = strain;
        this.prime_cost = prime_cost;
        this.retail_cost = retail_cost;
        this.quantity = quantity;

    }

    @Override
    public String print() {
        return code + " " + name + " " + producer + " " + date + " "
                + strain + " " + prime_cost + " " + retail_cost + " " + quantity;
    }

    public static Goods search(List<List<Goods>> list, String item) {
        if (item.charAt(0) - '0' - 1 < 0 || item.charAt(0) - '0' - 1 > list.size()) {
            return null;
        }
        if (list.get(item.charAt(0) - '0' - 1) != null) {
            for (Goods good : list.get(item.charAt(0) - '0' - 1)) {
                if (good.code.equalsIgnoreCase(item)) {
                    return good;
                }
            }
        }
        return null;
    }

    public static Goods search(Map<Goods, Double> list, String item){
        for (Goods code : list.keySet()) {
            if (code.code.equals(item)) {
                return code;
            }
        }
        return null;
    }
    public static void goodmodify(Goods good) throws PrintException{
        do {
            System.out.println("请输入你要修改的信息条目和修改信息");
            System.out.println("商品编号(code)、商品名称(name)、生产厂家(producer)、生产日期(date)、"
                    + "型号(strain)、进货价(prime_cost)、零售价格(retail_cost)、数量(quantity),返回(quit)");
            String item = sc.next();
            if(item.equals("quit")){
                System.out.println("返回成功!");
                return;
            }
            String info = sc.next();
            switch(item){
                case "code":
                    good.code=info;
                    break;
                case "name":
                    good.name=info;
                    break;
                case "producer":
                    good.producer=info;
                    break;
                case "date":
                    good.date=info;
                    break;
                case "strain":
                    good.strain=info;
                    break;
                case "prime_cost":
                    if(Double.parseDouble(info)<0){
                        throw new PrintException("进货价输入错误!");
                    }
                    good.prime_cost=Double.parseDouble(info);
                    break;
                case "retail_cost":
                    if(Double.parseDouble(info)<0){
                        throw new PrintException("零售价输入错误!");
                    }
                    good.retail_cost=Double.parseDouble(info);
                    break;
                case "quantity":
                    if(Double.parseDouble(info)<0 && (int)Double.parseDouble(info)!=Double.parseDouble(info)){
                        throw new PrintException("数量错误!");
                    }
                    good.quantity=Double.parseDouble(info);
                    break;
                default:
                    System.out.println("请输入正确的提示词!");
            }
        } while (true);

    }
}