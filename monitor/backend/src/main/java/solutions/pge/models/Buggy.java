package solutions.pge.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Buggy {
    private final String tagId;

    private Racer racer;

    public Buggy(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public Racer getRacer() {
        return racer;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }

    @Override
    public String toString() {
        return tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Buggy buggy = (Buggy) o;

        return new EqualsBuilder().append(tagId, buggy.tagId).append(racer, buggy.racer).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tagId).append(racer).toHashCode();
    }
}
