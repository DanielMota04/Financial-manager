package br.com.danielmota.data.dto;

import br.com.danielmota.enums.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date date;
    private Enum<Type> type;
    private String title;
    private double value;
    private String Description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Enum<Type> getType() {
        return type;
    }

    public void setType(Enum<Type> type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Double.compare(value, that.value) == 0 && Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(type, that.type) && Objects.equals(title, that.title) && Objects.equals(Description, that.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, type, title, value, Description);
    }
}
