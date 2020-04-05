package ex.repo.dto;

import java.util.List;

// This is a closed projection, the methods must exactly match the names of the entity property
public interface CustomerDtoI {
    Long getId();
    String getFirstname();
    String getLastname();

    // nesting of interface DTO projections
    List<AddressDtoI> getAddresses();
}
