import java.util.Scanner;

/**
 * Created by Alexander on 19.09.2019.
 */
public class main {
    public static void main(String[] args){

        UserService service = new UserService();

        while(true){
            menu();
            Scanner scan = new Scanner(System.in);
            switch (scan.nextInt()){
                case 1:{ //add new user
                    service.addNewUser(scan);
                }break;
                case 2:{
                    service.showAllUsers();
                }break;
                case 3:{
                    System.out.println("input user index: ");
                    service.showUserByIndex(scan.nextInt());
                }break;
                case 4:{
                    System.out.println("input user index: ");
                    service.deleteUserByIndex(scan.nextInt());
                }break;
                case 5:{
                    System.out.println("input user index: ");
                    service.changeUserByIndex(scan);
                }break;
                default:{

                }break;
            }
        }
    }

    public static void menu(){
        System.out.println("-------------------");
        System.out.println("1 - add new user");
        System.out.println("2 - show all users");
        System.out.println("3 - show user by index");
        System.out.println("4 - delete user by index");
        System.out.println("5 - change user by index");
        System.out.println("-------------------");
    }

}
