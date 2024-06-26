package hey.io.hey.domain.place.domain;


import hey.io.hey.common.entity.BaseEntityWithUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id
    private String id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;

    @Builder(toBuilder = true)
    public Place(String id, String name, String address, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
