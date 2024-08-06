import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Management {
    //主函数
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<User> users = new ArrayList<>();
        //初始用户
        User u1 = new User("123pocket", "123456", "445823300125252146", "15866236548");
        users.add(u1);
        loop:
        while (true) {
            LoginFace();
            int login_choice = sc.nextInt();

            switch (login_choice) {
                case 1 -> {
                    boolean flag_Login = Login(users);//登录
                    if (flag_Login) {
                        break loop;
                    } else {
                        continue;
                    }
                }
                case 2 -> {
                    users = Register(users);//注册
                    continue;
                }
                case 3 -> {
                   FogetPassword(users);
                    continue ;
                    }

                case 4 -> {
                    System.out.println("退出");
                    System.exit(0);//停止虚拟机运作
                }
                default -> System.out.println("不存在该选项");
            }
        }

        ArrayList<Student> students = new ArrayList<>();

        while (true) {
            Face();
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> addStudent(students);
                case 2 -> deleteStudent(students);
                case 3 -> ChangeStudent(students);
                case 4 -> CheckStudent(students);
                case 5 -> {
                    System.out.println("退出");
                    System.exit(0);//停止虚拟机运作
                }
                default -> System.out.println("不存在该选项");
            }
        }
    }

    //登录提示界面
    public static void LoginFace() {
        System.out.println("---------------欢迎来到学生管理系统---------------");
        System.out.println("请选择操作1登录 2注册 3忘记密码");
    }

    //注册用户
    public static ArrayList<User> Register(ArrayList<User> users) {
        Scanner sc = new Scanner(System.in);
        User user = new User();
        //用户名输入与校验
        String user_name;
        while (true) {
            System.out.print("请输入用户名：");
            user_name = sc.next();
            boolean check_uername = Check_uername(users, user_name);
            if (check_uername) {
                user.setUser_name(user_name);
                break;
            } else {
                continue;
            }
        }

        //密码输入
        while (true) {
            System.out.print("请设置密码：");
            String password_first = sc.next();
            System.out.print("请再次确认密码：");
            String password_second = sc.next();
            boolean flag_password = Check_password(password_first, password_second);
            if (flag_password) {
                user.setUser_password(password_first);
                break;
            } else {
                System.out.println("两次密码不一致，请重新输入");
                continue;
            }
        }

        //身份证号输入与验证
        while (true) {
            System.out.print("请输入身份证号码:");
            String idnumber = sc.next();
            boolean flag_idnumber = Check_idnumber(idnumber);
            if (flag_idnumber) {
                user.setIdnumber(idnumber);
                break;
            } else {
                continue;
            }
        }

        //手机号输入与验证
        while (true) {
            System.out.print("请输入手机号码:");
            String phonenumber = sc.next();
            boolean flag_phonenumber = Check_phonenumber(phonenumber);
            if (flag_phonenumber) {
                user.setPhonenumber(phonenumber);
                break;
            } else {
                continue;
            }
        }
        users.add(user);
        System.out.println("注册成功！");
        return users;
    }

    //用户登录
    public static boolean Login(ArrayList<User> users) {

        while (true) {
            boolean flag_Login = Check_Login(users);//同时验证用户名和密码
            if (flag_Login) {
                break;
            } else {
                return flag_Login;
            }
        }


        //验证码
        while (true) {
            boolean flag_verification = Check_verification();
            if (flag_verification) {
                System.out.println("验证成功！");
                break;
            } else {
                System.out.println("验证码错误，请重新输入");
                continue;
            }
        }
        return true;
    }

    //忘记密码
    public static boolean FogetPassword(ArrayList<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String user_name = sc.next();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUser_name().equals(user_name)) {//验证用户名
                boolean flag_id_phone = Check_id_phone(user);
                if (flag_id_phone) {
                    System.out.println("验证成功！");
                    System.out.print("请输入修改后的密码：");
                    String change_password = sc.next();
                    user.setUser_password(change_password);
                    System.out.println("密码修改成功");
                    return true;
                } else {
                    return false;
                }
            }
        }
        System.out.println("用户名未注册，请先注册！");
        return false;

    }

    //验证身份证号码和手机号码
    public static boolean Check_id_phone(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入身份证号码：");
        String id = sc.next();
        System.out.print("请输入手机号码：");
        String phone = sc.next();
        if (user.getIdnumber().equals(id) && user.getPhonenumber().equals(phone)) {
            return true; //验证身份证号码
        } else {
            System.out.println("账号信息不匹配，修改失败");
            return false;
        }
    }

    //验证是否已经注册
    public static boolean Check_Login(ArrayList<User> users) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String user_name = sc.next();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUser_name().equals(user_name)) {//验证用户名
                for (int j = 0; j < 3; j++) {//验证密码
                    System.out.print("请输入密码：");
                    String user_password = sc.next();
                    boolean flag_password = Check_password(user.getUser_password(), user_password);
                    if (flag_password) {
                        return true;
                    } else if (j == 2) {
                        System.out.println("密码连续错误超过3次，请24小时后再尝试");
                        return false;
                    } else if (j < 2) {
                        System.out.println("密码错误，你还有" + (2 - j) + "次机会，请重新输入！");
                    }
                }
            }
        }
        System.out.println("用户名未注册，请先注册！");
        return false;
    }


    //判断验证码
    public static boolean Check_verification() {
        String verification = Create_verification();
        System.out.println("验证码：" + verification);
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入验证码：");
        String code = sc.next();
        boolean result = verification.equalsIgnoreCase(code);
        return result;
    }

    //生成随机验证码
    public static String Create_verification() {
        Random r = new Random();
        char[] arr = new char[4];
        char[] letter = new char[52];
        for (int i = 0; i < letter.length; i++) {
            if (i <= 25) {
                letter[i] = (char) (65 + i);
            } else if (i >= 26) {
                letter[i] = (char) (97 + i - 26);
            }
        }//获取26个字母的大小写
        for (int i = 0; i < arr.length - 1; i++) {
            int index = r.nextInt(letter.length);
            arr[i] = letter[index];
        }
        int num = r.nextInt(10);
        arr[arr.length - 1] = (char) (num + '0');

        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);
            char temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        String result = new String(arr);
        return result;
    }

    //判断用户名
    public static boolean Check_uername(ArrayList<User> users, String user_name) {
        //判断用户名长度
        if (user_name.length() >= 3 && user_name.length() <= 15) {
        } else {
            System.out.println("用户名输入错误，要求用户名长度为3~15个字符，请重新输入");
            return false;
        }
        //判断用户名是否字母加数字的组合，但是不能是纯数字
        int num = 0;
        int letter = 0;
        char[] arr = user_name.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (!((arr[i] >= 'A' && arr[i] <= 'Z') || (arr[i] >= 'a' && arr[i] <= 'z') || (arr[i] >= '0' && arr[i] <= '9'))) {
                System.out.println("用户名输入错误，要求用户名是字母加数字的组合，但是不能是纯数字，请重新输入");
                return false;
            }
            if (arr[i] >= '0' && arr[i] <= '9') {
                num++;
            } else if ((arr[i] >= 'A' && arr[i] <= 'Z') || (arr[i] >= 'a' && arr[i] <= 'z')) {
                letter++;
            }
            if (num == arr.length || letter == arr.length) {
                System.out.println("用户名输入错误，要求用户名是字母加数字的组合，但是不能是纯数字，请重新输入");
                return false;
            }

        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUser_name().equals(user_name)) {
                System.out.println("用户名已存在！请重新输入");

                return false;
            }
        }
        return true;
    }

    //校验密码
    public static boolean Check_password(String first, String second) {
        if (first.equals(second)) {
            return true;
        } else {
            return false;
        }
    }

    //校验身份证号码
    public static boolean Check_idnumber(String id) {
        char[] arr = id.toCharArray();
        //判断身份证是否为数字
        for (int i = 0; i < arr.length - 1; i++) {
            if (!(arr[i] >= '0' && arr[i] <= '9')) {
                System.out.println("输入错误，身份证号码前17位必须都是数字，请重新输入");
                return false;
            }
        }
        //判断是否为18位
        if (arr.length != 18) {
            System.out.println("输入错误，请输入18位数字的身份证号码");
            return false;
        }
        //判断是否为0开头
        if (arr[0] == '0') {
            System.out.println("输入错误，身份证号码开头不能为0，请重新输入");
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if (!((arr[arr.length - 1] >= '0' && arr[arr.length - 1] <= '9') || arr[arr.length - 1] == 'X' || arr[arr.length - 1] == 'x')) {
                System.out.println("输入错误，身份证号码最后一位可以是数字，也可以是大写X或小写x，请重新输入");
                return false;
            }
        }

        return true;

    }

    //校验手机号码
    public static boolean Check_phonenumber(String phonenumber) {
        char[] arr = phonenumber.toCharArray();
        if (arr.length != 11) {
            System.out.println("输入错误，手机号码应为11位数字，请重新输入");
            return false;
        }
        if (arr[0] == '0') {
            System.out.println("输入错误，手机号码开头不能为0，请重新输入");
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < '0' || arr[i] > '9') {
                System.out.println("输入错误，手机号码应全为数字，请重新输入");
                return false;
            }
        }
        return true;
    }

    //提示界面
    public static void Face() {
        System.out.println("---------------学生管理系统---------------");
        System.out.println("1.添加学生");
        System.out.println("2.删除学生");
        System.out.println("3.修改学生");
        System.out.println("4.查询学生");
        System.out.println("5.退出");
        System.out.println("请输入你的选择:");
    }

    //添加学生
    public static ArrayList<Student> addStudent(ArrayList<Student> students) {
        Student stu = new Student();
        Scanner sc = new Scanner(System.in);

        int id;
        while (true) {
            System.out.print("请输入要添加学生的id： ");
            id = sc.nextInt();
            boolean unique = unique(id, students);
            if (unique) {
                break;
            }
            System.out.println("id已存在，请重新输入！");
        }
        stu.setId(id);
        System.out.print("请输入要添加学生的姓名：");
        String name = sc.next();
        stu.setName(name);
        System.out.print("请输入要添加学生的年龄：");
        int age = sc.nextInt();
        stu.setAge(age);
        System.out.print("请输入要添加学生的地址：");
        String adress = sc.next();
        stu.setAdress(adress);
        students.add(stu);
        System.out.println("添加成功！");
        return students;
    }

    //删除学生
    public static ArrayList<Student> deleteStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要删除的学生的id：");
        int id = sc.nextInt();
        boolean flag = true;
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            if (stu.getId() == id) {
                students.remove(i);
                System.out.println("删除成功！");
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println("id不存在！删除失败");
        }
        return students;
    }

    //修改学生
    public static ArrayList<Student> ChangeStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要修改的学生的id：");
        int id = sc.nextInt();
        boolean flag = true;
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            if (stu.getId() == id) {
                System.out.println("  id" + "       " + "姓名" + "   " + "年龄" + "  " + "地址");
                System.out.println("  " + stu.getId() + "   " + stu.getName() + "  " + stu.getAge() + "   " + stu.getAdress());
                System.out.println("请输入新的学生信息");
                System.out.println("id:");
                int new_id = sc.nextInt();
                stu.setId(new_id);
                System.out.println("姓名:");
                String new_name = sc.next();
                stu.setId(new_id);
                System.out.println("年龄:");
                int new_age = sc.nextInt();
                stu.setId(new_id);
                System.out.println("地址:");
                String new_address = sc.next();
                stu.setId(new_id);
                students.set(i, stu);
                System.out.println("修改成功");
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println("id不存在！修改失败");
        }
        return students;
    }

    //查询学生
    public static void CheckStudent(ArrayList<Student> students) {
        System.out.println("  id" + "       " + "姓名" + "   " + "年龄" + "  " + "地址");
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            System.out.println("  " + stu.getId() + "   " + stu.getName() + "  " + stu.getAge() + "   " + stu.getAdress());
        }
    }

    //判断id是否唯一
    public static boolean unique(int id, ArrayList<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            Student stu = students.get(i);
            if (stu.getId() == id) {
                return false;
            }
        }
        return true;
    }
}
