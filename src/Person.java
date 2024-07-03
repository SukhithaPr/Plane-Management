public class Person {
    // Private variables to store name, surname, and email of the person
    private String name;
    private String surname;
    private String email;
    // Constructor to initialize a Person object with name, surname, and email.
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getter method to retrieve the name of the person.
    public String getName() {
        return name;
    }

    // Getter method to retrieve the surname of the person.
    public String getSurname() {
        return surname;
    }

    // Getter method to retrieve the email of the person.
    public String getEmail() {
        return email;
    }

    // Setter method to set the name of the person.
    public void setName(String name) {
        this.name = name;
    }

    // Setter method to set the surname of the person.
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Setter method to set the email of the person.
    public void setEmail(String email) {
        this.email = email;
    }
}
