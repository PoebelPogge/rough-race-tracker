package solutions.pge.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.OffsetDateTime;
import java.util.List;

public class Racer {
    private final String number;
    private final String firstName;
    private final String lastName;

    public Racer(String number, String firstName, String lastName) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Racer racer = (Racer) o;

        return new EqualsBuilder().append(number, racer.number).append(firstName, racer.firstName).append(lastName, racer.lastName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(number).append(firstName).append(lastName).toHashCode();
    }
}
