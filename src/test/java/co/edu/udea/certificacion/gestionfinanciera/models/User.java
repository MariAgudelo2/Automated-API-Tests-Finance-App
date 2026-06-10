package co.edu.udea.certificacion.gestionfinanciera.models;

public class User {
    private final String firstName;
    private final String lastname;
    private final String email;
    private final String password;

    public User(String firstName, String lastname, String email, String password) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this("", "", email, password);
    }

    public String getFirstName() { return firstName; }
    public String getLastname() { return lastname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

