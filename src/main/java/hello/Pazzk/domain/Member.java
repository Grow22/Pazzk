package hello.Pazzk.domain;

import hello.Pazzk.repository.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20,unique = true)
    private String name;

    @NotNull @NotBlank
    private String userId;
    @NotNull @NotBlank
    private String pwd;


}