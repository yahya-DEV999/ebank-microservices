package ma.mundiapolis.ebankservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Simple modèle (pas une entité) : les données viennent de customer-service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
