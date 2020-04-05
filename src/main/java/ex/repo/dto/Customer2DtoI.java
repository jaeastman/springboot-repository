package ex.repo.dto;

import org.springframework.beans.factory.annotation.Value;

// open interface projection
// attributes are derived from the underlying entity attributes
public interface Customer2DtoI {
    @Value("#{target.firstname + '::' + target.lastname}")
    String getFullname();
}
