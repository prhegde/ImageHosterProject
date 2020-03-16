package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }
    
    public boolean isPasswordValid(String password) {
    	if (is_Valid_Password(password)) {
    		return true;
        } else {
        	return false;
        }
    }

    public static boolean is_Valid_Password(String password) {
        int charCount = 0;
        int numCount = 0;
        int specialCharCount = 0;
        for (int i = 0; i < password.length(); i++) {
        	char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else if (is_SpecialCharacter(ch)) specialCharCount++;
            else return false;
        }
        return (charCount >= 1 && numCount >= 1 && specialCharCount >= 1);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }
    
    public static boolean is_Numeric(char ch) {
        return (ch >= '0' && ch <= '9');
    }
    
    public static boolean is_SpecialCharacter(char ch) {
        return (!is_Letter(ch) && !is_Numeric(ch));
    }
}
