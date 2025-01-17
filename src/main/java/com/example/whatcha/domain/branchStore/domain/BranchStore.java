package com.example.whatcha.domain.branchStore.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class BranchStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchStoreId;

    @Column(nullable = false, unique = true)
    private String branchStoreName;
    private String location;
    private double latitude;
    private double longitude;
    private Integer ownedCarCount;
    private String phone;

    public void incrementOwnedCarCount() {
        this.ownedCarCount++;
    }
}
