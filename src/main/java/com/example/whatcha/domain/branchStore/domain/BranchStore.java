package com.example.whatcha.domain.branchStore.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class BranchStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchStoreId;

    @Column(nullable = false, unique = true)
    private String branchStoreName;
    private String location;
    private double latitude;
    private double longitude;
    private int ownedCarCount;
    private String phone;
}
