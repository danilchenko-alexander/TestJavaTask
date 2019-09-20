import java.io.*;
import java.util.*;

/**
 * Created by Alexander on 19.09.2019.
 */
public class UserService {

    public final String filename = "users.dat";
    public void addNewUser(Scanner scan){
        scan.nextLine();
        Users user = new Users();

        System.out.println("Name:");
        user.setName(scan.nextLine());

        System.out.println("Surname:");
        user.setSurname(scan.nextLine());

        String email;
        System.out.println("Email:");
        email = scan.nextLine();
        while(!Validation.validateEmail(email)) {
            System.out.println("invalid email. try again");
            email = scan.nextLine();
        }
        user.setEmail(email);

        Integer amount;
        System.out.println("Roles: \n how many roles you want to input? (1-3)");
        amount = checkForAmount(scan);
        Set<String> roles = new HashSet<>();
        for(int i = 0; i < amount; i++){
            roles.add(scan.nextLine());
        }
        user.setRoles(roles);

        System.out.println("Phones: \n how many phones you want to input? (1-3)");
        amount = checkForAmount(scan);
        user.setPhoneNumbers(getPhoneNumbers(amount,scan));
        saveUser(user);

        System.out.println(user.getName() + " " + user.getSurname() + " " + user.getEmail());
        System.out.println("roles "+user.getRoles());
        System.out.println("phones "+user.getPhoneNumbers());
    }

    public void saveUser(Users user){
        List<Users> users = getAllUsers();
        users.add(user);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(users);
        } catch (FileNotFoundException e) {
            System.out.println("file doesn't exist");
        } catch (IOException e) {
            System.out.println("fail");
        }
    }

    public void saveAllUsers(List<Users> users){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(users);
        } catch (FileNotFoundException e) {
            System.out.println("file doesn't exist");
        } catch (IOException e) {
            System.out.println("fail");
        }
    }

    public void showAllUsers(){
        List<Users> users = getAllUsers();
        Integer index = 0;
        for(Users u : users){
            showSingleUser(u,index++);
        }
    }

    public void showSingleUser(Users u, Integer index){
        System.out.println("------------------------------------------------------");
        System.out.println("user index: " + index++);
        System.out.println("first name: " + u.getName());
        System.out.println("last name: " + u.getSurname());
        System.out.println("email: " + u.getEmail());
        System.out.println("roles: " + u.getRoles());
        System.out.println("phone numbers: " + u.getPhoneNumbers());
        System.out.println("------------------------------------------------------");
    }

    public void showUserByIndex(Integer index){
        List<Users> users = getAllUsers();
        if(users.size() > index){
            Users user = users.get(index);
            showSingleUser(user,index);
        }
        else System.out.println("user with index '"+index+"' doesn't exists");
    }

    public void deleteUserByIndex(Integer index){
        List<Users> users = getAllUsers();
        if(users.size() > index) {
            users.remove(users.get(index));
            saveAllUsers(users);
            System.out.println("user deleted successfully");
        }
        else System.out.println("user with index '"+index+"' doesn't exists");
    }

    public void changeUserByIndex(Scanner scan){
        List<Users> users = getAllUsers();
        Integer index = scan.nextInt();
        Integer amount;
        if(users.size() > index) {
            while (true) {
                userChangeMenu();
                switch (scan.nextInt()) {
                    case 1: {
                        scan.nextLine();
                        System.out.println("enter new name: ");
                        users.get(index).setName(scan.nextLine());
                    }
                    break;
                    case 2: {
                        scan.nextLine();
                        System.out.println("enter new surname: ");
                        users.get(index).setSurname(scan.nextLine());
                    }
                    break;
                    case 3: {
                        scan.nextLine();
                        System.out.println("enter new email: ");
                        String email = scan.nextLine();
                        while (!Validation.validateEmail(email)) {
                            System.out.println("invalid email. try again");
                            email = scan.nextLine();
                        }
                        users.get(index).setEmail(email);
                    }
                    break;
                    case 4: {
                        scan.nextLine();
                        System.out.println("set the number of roles: ");
                        amount = checkForAmount(scan);
                        Set<String> roles = new HashSet<>();
                        for (int i = 0; i < amount; i++) {
                            roles.add(scan.nextLine());
                        }
                        users.get(index).setRoles(roles);
                    }
                    break;
                    case 5: {
                        System.out.println("set the number of phones: ");
                        amount = checkForAmount(scan);
                        users.get(index).setPhoneNumbers(getPhoneNumbers(amount,scan));
                    }
                    break;
                    case 6: {
                        saveAllUsers(users);
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
        }
        else System.out.println("user with index '"+index+"' doesn't exists");

    }

    public Integer checkForAmount(Scanner scan){
        Integer amount;
        while (true) {
            amount = scan.nextInt();
            scan.nextLine();
            if(amount >= 1 && amount <= 3)
                break;
            System.out.println("invalid number of entries. try again");
        }
        return amount;
    }

    public Set<String> getPhoneNumbers(Integer amount, Scanner scan){
        String phoneNumber;
        Set<String> phoneNumbers = new HashSet<>();
        for(int i = 0; i < amount; i++){
            System.out.println("enter "+(i+1)+" of "+amount+" phone number");
            phoneNumber = scan.nextLine();
            if(Validation.validatePhoneNum(phoneNumber))
                phoneNumbers.add(phoneNumber);
            else {
                System.out.println("invalid phone number. should be 375*********. try again");
                i--;
            }
        }
        return phoneNumbers;
    }

    public void userChangeMenu(){
        System.out.println("1 - change name");
        System.out.println("2 - change surname");
        System.out.println("3 - change email");
        System.out.println("4 - change roles");
        System.out.println("5 - change phone numbers");
        System.out.println("6 - save and exit");

    }

    public List<Users> getAllUsers(){
        List<Users> usersDes;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            usersDes = (List<Users>) ois.readObject();
            return usersDes;
        } catch (FileNotFoundException e) {
            System.out.println("file doesn't exist");
        } catch (IOException e) {
            System.out.println("fail");
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
        }
        return new LinkedList<>();
    }
}
