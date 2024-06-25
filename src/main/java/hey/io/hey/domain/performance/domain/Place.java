package hey.io.hey.domain.performance.domain;


import hey.io.hey.common.entity.BaseEntityWithUpdate;
import hey.io.hey.domain.performance.domain.enums.Area;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntityWithUpdate {

    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Area area;
    private String gugunName;
    private String address;
    private double latitude;
    private double longitude;

    public void updateSidoGugun(Area area, String gugunName) {
        this.area = area;
        this.gugunName = gugunName;
    }

}
