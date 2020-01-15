package se.alten.schoolproject.entity;

import lombok.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.*;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Teacher implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "forename")
    private String forename;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "teachers")
    private Set<Subject> subject = new HashSet<>();


    public Teacher toEntity(String teacherModel) {

        List<String> temp = new ArrayList<>();

        JsonReader reader = Json.createReader(new StringReader(teacherModel));

        JsonObject jsonObject = reader.readObject();

        Teacher teacher = new Teacher();
        if ( jsonObject.containsKey("forename")) {
            teacher.setForename(jsonObject.getString("forename"));
        } else {
            teacher.setForename("");
        }

        if ( jsonObject.containsKey("lastname")) {
            teacher.setLastname(jsonObject.getString("lastname"));
        } else {
            teacher.setLastname("");
        }

        if ( jsonObject.containsKey("email")) {
            teacher.setEmail(jsonObject.getString("email"));
        } else {
            teacher.setEmail("");
        }



        return teacher;
    }


}
